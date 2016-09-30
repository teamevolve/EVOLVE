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
	final static double EMIGRATION_STDDEV = 0.1;
	final Random INTERNAL_RNG = new Random(DataManager.getInstance().getSessionParams().getSeed());

	/**
	 * Member to enable singleton class
	 */
	private static PopulationManager instance = null;
	//private HashMap<Integer, Population> populationMap;
	private ArrayList<Population> populationList;
	
	
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
		int numPops = DataManager.getInstance().getSessionParams().getNumPops();
		for (int i=0; i < numPops; i++) {
			populationList.add(new Population(INTERNAL_RNG.nextLong()));
		}
	}
	
	
	/**
	 * Process interpopulation migrations, modifying populations to reflect 
	 * immigrations and emigrations. Organisms emigrating from one population
	 * cannot immigrate to their original population, and are distributed as 
	 * evenly as possible among all other populations.
	 */
	public void processMigrations() {
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		// Individuals cannot immigrate to their population of origin
		final double distributeTo = populationList.size() - 1;
		
		// Map of contributions each population has made to the pool of drifting individuals
		HashMap<Population, HashMap<Genotype, Integer>> contrib = new HashMap<Population, HashMap<Genotype, Integer>>();
		
		// Set of populations that could take on an extra organism without 
		// concern that they'd be taking their own individual back
		HashSet<Population> accepting = new HashSet<Population>();

		// Containers for temporarily storing values used more than once
		int numEmigrations;
		int contribution;
		int adjustedI;
		int subPopSize;
		double adjustedD;
		double genotypeMigrationRate;
		double totalMigrations;
		GenerationRecord record;
		

		// Initialize contribution hashmap
		for (Population p : populationList) {
			contrib.put(p, new HashMap<Genotype, Integer>());
		}

		// For each genotype...
		for (Genotype gt : Genotype.values()) {
			genotypeMigrationRate = sp.getMigrationRate(gt);
			totalMigrations = 0;
			
			// Determine how many individuals emigrate from each population
			for (Population p : populationList) {
				subPopSize = p.getLastGeneration().getGenotypeSubpopulationSize(gt);
				numEmigrations = (int)Math.round(Utilities.nextGaussianRand(INTERNAL_RNG, EMIGRATION_MEAN, EMIGRATION_STDDEV) * 
						                         subPopSize * genotypeMigrationRate);
				
				// Correct extreme random values
				if (numEmigrations < 0) {
					numEmigrations = 0;
				}
				else if (numEmigrations > subPopSize) {
					numEmigrations = subPopSize;
				}
				
				totalMigrations += numEmigrations;
				contrib.get(p).put(gt, numEmigrations);
			}
			
			
			// General redistribution
			for (Population p : populationList) {
				record = p.getLastGeneration();
				contribution = contrib.get(p).get(gt);
				
				// Remove population's contribution, since individuals cannot
				// immigrate into their population of origin
				adjustedD = (totalMigrations - contribution) / distributeTo;
				adjustedI = (int)adjustedD;
				
				// Collect populations that can take an extra individual
				if (adjustedD > adjustedI)
					accepting.add(p);
				
				totalMigrations -= adjustedI;
				
				record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) - contribution + adjustedI);
				record.setEmigrationCount(gt, contribution);
				record.setImmigrationCount(gt, adjustedI);
			}
			
			// Redistribute remaining individuals
			for (Iterator<Population> it = accepting.iterator(); totalMigrations > 0; totalMigrations--) {
				record = it.next().getLastGeneration();
				record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) + 1);
			}

		}
	}
}