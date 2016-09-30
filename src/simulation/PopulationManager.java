package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;

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
	/**
	 * Member to enable singleton class
	 */
	final Random INTERNAL_RNG = new Random(DataManager.getInstance().getSessionParams().getSeed());
	
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
		// Individuals cannot immigrate to their population of origin
		final double distributeTo = populationList.size() - 1;
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
		final Random random = new Random();
		
		// Map of contributions each population has made to the pool of 
		// drifting individuals
		HashMap<Population, HashMap<Genotype, Integer>> contrib = new HashMap<Population, HashMap<Genotype, Integer>>();

		// Containers for temporarily storing values used more than once
		int numEmigrations;
		int contribution;
		int adjusted;
		int randInd;
		double genotypeMigrationRate;
		double totalMigrations;
		double perPopulationDistribution;
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
				numEmigrations = (int)(p.getLastGeneration().getGenotypeSubpopulationSize(gt) * genotypeMigrationRate);
				totalMigrations += numEmigrations;
				contrib.get(p).put(gt, numEmigrations);
			}
			
			// Calculate approximately how many individuals will be distributed
			// to each population
			perPopulationDistribution = totalMigrations / distributeTo; 
			
			
			// General redistribution
			for (Population p : populationList) {
				record = p.getLastGeneration();
				contribution = contrib.get(p).get(gt);
				
				// Remove populations contribution, since individuals cannot
				// immigrate into their population of origin
				adjusted = (int)(perPopulationDistribution - ((double) contribution) / distributeTo);
				
				totalMigrations -= adjusted;
				record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) - contribution + adjusted);
				record.setEmigrationCount(gt, contribution);
				record.setImmigrationCount(gt, adjusted);
			}
			
			// Redistribute remaining individuals, or correct for overdistribution -- NEEDS FIXIN
			if (totalMigrations != 0) {
				randInd = random.nextInt(populationList.size());
				for (; totalMigrations > 0; totalMigrations--, randInd = (randInd + 1) % populationList.size()) {
					record = populationList.get(randInd).getLastGeneration();
					record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) + 1);
				}
				for (; totalMigrations < 0; totalMigrations++, randInd = (randInd + 1) % populationList.size()) {
					record = populationList.get(randInd).getLastGeneration();
					record.setGenotypeSubpopulationSize(gt, record.getGenotypeSubpopulationSize(gt) - 1);
				}
			}

		}
	}
}