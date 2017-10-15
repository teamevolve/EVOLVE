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

import static gui.GUI.DEBUG_MATE;
import static gui.GUI.DEBUG_MIGRATION;
import static gui.GUI.DEBUG_MUTATION;
import static gui.GUI.DEBUG_REPRO;
import static gui.GUI.DEBUG_SURVIVAL;
import static gui.GUI.DEBUG_SUMMARY;


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
	 * Clears population manager, allowing for multiple runs off same gui --Jason
	 */
	public void reset() {
		instance = null;
	}
	
	
	/**
	 * Accessor for populationList. Merges living and extinct populations.
	 * 
	 * @return list of all populations
	 */
	public ArrayList<Population> getPopulationList() {
		ArrayList<Population> result = new ArrayList<Population>();
		result.addAll(populationList);
		result.addAll(extinctList);
		return result;
	}
	

	/**
	 * Run the population through a simulation of a single generation.
	 */
	public void processGeneration(){
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		for (Population p : populationList) {
			p.simulateMatingRepro();	
		}

		if (sp.isMigrationChecked() && populationList.size() > 1)
			processMigrations();

		for (Population p : populationList) {
			p.simulateSurviveMutation();	
		}
		
		if (DEBUG_SUMMARY) {
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println();
			System.out.println();
		}
		
//		if (sp.isPopConst()) {
//			int popSize = sp.getPopSize();
//			for (Population p : populationList) {
//				p.scale(popSize);
//			}
//		}
	}

	
	/**
	 * Process interpopulation migrations, modifying populations to reflect
	 * immigrations and emigrations. Organisms emigrating from one population
	 * cannot immigrate to their original population, and are distributed as
	 * evenly as possible among all other populations.
	 */
	public void processMigrations() {
//		if (DEBUG_MIGRATION) {
//			System.out.println("migration debugging!");
//		}
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
		int index;
		double totalImmigrations;
		double adjustedD;
		double genotypeMigrationRate;
		double totalEmigrations;
		double random_co;
		GenerationRecord record;

		for (Population p : populationList) {
			contributions.put(p, new HashMap<Genotype, Integer>());
		}
		//printContributions(contributions);

		for (Genotype gt : Genotype.getValues()) {
			genotypeMigrationRate = sp.getMigrationRate(gt);
			totalEmigrations = 0;
			
			if (DEBUG_MIGRATION) {
				
				System.out.println("Migration Rate of " + gt.toString() +": " + genotypeMigrationRate);
				System.out.println("*---------------------------------------------------------*");
				System.out.printf("|                | rand coef | num of gt | emigration num |");
				System.out.println();
			}
			
			// Process emigrations
			String popInfo;
			for (Population pop : populationList) {
				index = populationList.indexOf(pop);
				subPopSize = pop.getLastGeneration().getGenotypeSubpopulationSize(gt);
				random_co = Utilities.nextGaussianRand(INTERNAL_RNG, EMIGRATION_MEAN, EMIGRATION_STDDEV);
				numEmigrations = (int)Math.round(random_co * subPopSize * genotypeMigrationRate);
				
				// Correct for extreme values from the rng
				if (numEmigrations < 0) numEmigrations = 0;
				else if (numEmigrations > subPopSize) numEmigrations = subPopSize;

				totalEmigrations += numEmigrations;
				contributions.get(pop).put(gt, numEmigrations);
				
				if (DEBUG_MIGRATION) {
					popInfo = String.format("|population %1$-5d|%2$-11f|%3$-11d|%4$-16d|", index, random_co,  subPopSize, numEmigrations);
					System.out.println(popInfo);
				}
			}

			if (DEBUG_MIGRATION) {
				System.out.println("*---------------------------------------------------------*");
				System.out.println("total emigrations of " + gt.toString() + ": " + totalEmigrations);
				System.out.println();
				System.out.println("*---------------------------------------------------*");
				System.out.printf("|                | immigration num | emigration num |");
				System.out.println();
			}

			totalImmigrations = totalEmigrations;

			// Process immigrations
			String migrationInfo;
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
				
				if (DEBUG_MIGRATION) {
					migrationInfo = String.format("|population %1$-5d|%2$-17d|%3$-16d|", populationList.indexOf(pop), 
							adjustedI, contribution);
					System.out.println (migrationInfo);
				}
				
				record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) - contribution + adjustedI);
				record.setEmigrationCount(gt, contribution);
				record.setImmigrationCount(gt, adjustedI);
			}
			if (DEBUG_MIGRATION) {
				System.out.println("*---------------------------------------------------*");
				System.out.println();
			}

			// Redistribute remaining individuals
			for (Iterator<Population> it = accepting.iterator(); totalImmigrations > 0; totalImmigrations--) {
				record = it.next().getLastGeneration();
				record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) + 1);
			}
		}
		if (DEBUG_MIGRATION) {
		
			printContributions(contributions);
		}
	}
	
	public void printContributions (HashMap<Population, HashMap<Genotype, Integer>> contributions) {
		String genotypes = "|population #   ";
		for (Genotype gt1 : Genotype.getValues())
		{
			genotypes += String.format("|%-10s", gt1.toString());
		}
		System.out.println("*--------------Contribution Table----------------*");
		System.out.println(genotypes + "|");
		for (int i = 0; i < populationList.size(); i++) {
			String popInfo = String.format("|   %-10d  ", i);
			for (Genotype gt : Genotype.getValues()) {
				popInfo += String.format("|%-10d", contributions.get(populationList.get(i)).get(gt));
			}
			System.out.println(popInfo + "|");
		}
		System.out.println("*------------------------------------------------*");
	}
	
	
	private void printGenoNum(GenerationRecord record) {
		String genoNum =   "|Number         ";
		String genotypes = "|               ";
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-15s", gt.toString());
			genoNum += String.format("|%-15d",record.getGenotypeSubpopulationSize(gt));
		}
		System.out.println("*-----------------------Genotype Number-------------------------*");
		System.out.print(genotypes + "|\n" + genoNum + "|\n");
		System.out.println("*---------------------------------------------------------------*");
	}
}