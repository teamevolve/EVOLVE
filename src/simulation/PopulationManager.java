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
			populationList.add(new Population());
		}
	}
	
	
	/**
	 * Process interpopulation migrations, modifying populations to reflect 
	 * immigrations and emigrations.
	 * 
	 */
	public void processMigrations() {
		HashMap<Population, HashMap<Genotype, Double>> contrib = new HashMap<Population, HashMap<Genotype, Double>>();
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		int distributeTo = populationList.size() - 1;

		double genotypeMigrationRate;
		double perPopulationDistribution;
		int totalMigrations;
		int subPopulationSize;
		int numEmigrations;
		double temp;
		GenerationRecord record;
		
		
		for (Population p : populationList) {
			contrib.put(p, new HashMap<Genotype, Double>());
		}

<<<<<<< HEAD
		
		for (Genotype gt : Genotype.values()) {
			genotypeMigrationRate = sp.getMigrationRate(gt);
			perPopulationDistribution = 0.0;
			totalMigrations = 0;
			for (Population p : populationList) {
				record = p.getLastGeneration();
				subPopulationSize = record.getGenotypeSubpopulationSize(gt);
				
				numEmigrations = (int)(subPopulationSize * genotypeMigrationRate);
				totalMigrations += numEmigrations;
				temp = numEmigrations / distributeTo;
				perPopulationDistribution += temp;
				contrib.get(p).put(gt, temp);
				record.setGenotypeSubpopulationSize(gt, subPopulationSize - numEmigrations);
=======
		// Distribute drifters from pool among populations
		{
			Random rng = new Random();
			int randImm;
			int randInd;
			GenerationRecord gr;
			for (Genotype gt : inTransit.keySet()) {
				while(inTransit.get(gt) > 0) {
					randImm = rng.nextInt(inTransit.get(gt));
					if (randImm == 0) randImm++;
					randInd = rng.nextInt(populationList.size());
					gr = populationList.get(randInd).getLastGeneration();
					gr.setGenotypeSubpopulationSize(gt, gr.getGenotypeSubpopulationSize(gt) + randImm);
					inTransit.put(gt, inTransit.get(gt) - randImm);
				}
>>>>>>> bb7c3f84c553b6c78c79459da7500e8299d92227
			}
			
			// Redistribute
			
			

		}
		// How to calculate num immigration from each population?
		// How to redistribute drifters?
	}
}