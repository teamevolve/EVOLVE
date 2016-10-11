package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import shared.Utilities;

/**
 * PopulationManager is controlled mostly by SimulationEngine, and delegates
 * work to Population objects. Additionally, it logistically manages
 * Populations and handles inter-population exchanges using GeneFlow objects.
 *
 * @see Population
 * @see SimulationEngine
 * @see GeneFlow
 *
 * @author ericscollins
 *
 */

public class PopulationManager {
	final static double EMIGRATION_MEAN = 1.0;
	final static double EMIGRATION_STDDEV = 0.05;
	final private Random INTERNAL_RNG = new Random(DataManager.getInstance().getSessionParams().getSeed());

	private static PopulationManager instance = null;
	private ArrayList<Population> populationList;
	private ArrayList<Population> extinctList;


	/**
	 * Returns singleton instance of PopulationManager
	 *
	 * @return singleton instance of populationManager
	 */
	public static PopulationManager getInstance() {
		if (instance == null) {
			instance = new PopulationManager();
		}
		return instance;
	}


	/**
	 * Private constructor to disable normal instantiation
	 */
	private PopulationManager() {
		populationList = new ArrayList<Population>();
		extinctList = new ArrayList<Population>();
		int numPops = DataManager.getInstance().getSessionParams().getNumPops();
		for (int i=0; i < numPops; i++) {
			populationList.add(new Population(INTERNAL_RNG.nextLong()));
		}
	}

	/**
	 * Process generation
	 */
	public void processGeneration(){
		for (Population p : populationList) {
			p.simulateGeneration();
		}
		
		processMigrations();
	}

	/**
	 * Process interpopulation migrations, modifying populations to reflect
	 * immigrations and emigrations. Organisms emigrating from one population
	 * cannot immigrate to their original population, and are distributed as
	 * evenly as possible among all other populations.
	 */
	public void processMigrations() {
		// Used to determine how individuals are redistributed, as individuals
		// may not immigrate to the population they emigrated from
		final double distributeTo = populationList.size() - 1;
		// Contains all populations that can take an extra individual after
		// initial redistribution of organisms
		final HashSet<Population> accepting = new HashSet<Population>();
		// Records how many individuals of each genotype a population has
		// contributed to the pool of drifting organism
		final HashMap<Population, HashMap<Genotype, Integer>> contributions = new HashMap<Population, HashMap<Genotype, Integer>>();
		final SessionParameters sp = DataManager.getInstance().getSessionParams();

		// Containers to hold temporary values used more than once
		int numEmigrations;
		int contribution;
		int adjustedI;
		int subPopSize;
		double totalImmigrations;
		double adjustedD;
		double genotypeMigrationRate;
		double totalEmigrations;
		GenerationRecord record;

		for (Population p : populationList) {
			contributions.put(p, new HashMap<Genotype, Integer>());
		}

		for (Genotype gt : Genotype.values()) {
			genotypeMigrationRate = sp.getMigrationRate(gt);
			totalEmigrations = 0;

			// Process emigrations
			for (Population pop : populationList) {
				subPopSize = pop.getLastGeneration().getGenotypeSubpopulationSize(gt);
				numEmigrations = (int)Math.round(Utilities.nextGaussianRand(INTERNAL_RNG, EMIGRATION_MEAN, EMIGRATION_STDDEV) * subPopSize * genotypeMigrationRate);

				// Correct for extreme values from the rng
				if (numEmigrations < 0) numEmigrations = 0;
				else if (numEmigrations > subPopSize) numEmigrations = subPopSize;

				totalEmigrations += numEmigrations;
				contributions.get(pop).put(gt, numEmigrations);
			}

			totalImmigrations = totalEmigrations;

			// Process immigrations
			for (Population pop : populationList) {
				record = pop.getLastGeneration();
				contribution = contributions.get(pop).get(gt);

				// Calculate how many individuals will immigrate, and if any
				// stragglers can be taken later on
				adjustedD = (totalEmigrations - contribution) / distributeTo;
				adjustedI = (int)adjustedD;

				// Record if population can accept another individual
				if (adjustedD > adjustedI) accepting.add(pop);

				totalImmigrations -= adjustedI;

				record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) - contribution + adjustedI);
				record.setEmigrationCount(gt, contribution);
				record.setImmigrationCount(gt, adjustedI);
			}

			// Redistribute remaining individuals
			for (Iterator<Population> it = accepting.iterator(); totalImmigrations > 0; totalImmigrations--) {
				record = it.next().getLastGeneration();
				record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) + 1);
			}
		}
	}
}