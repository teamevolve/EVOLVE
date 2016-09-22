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
		// Will hold per-genotype totals for drifting organisms
		HashMap<Genotype, Integer> inTransit = new HashMap<Genotype, Integer>();
		
		// Initialize drifting population 
		for (Genotype gt : Genotype.values()) {
			inTransit.put(gt, 0);
		}
		
		// Pool drifters from populations
		{
			final SessionParameters sp = DataManager.getInstance().getSessionParams();
			GenerationRecord gr;
			int numOut;
			int subPopulationSize;
			for (Population pop : populationList) {
				gr = pop.getLastGeneration();
				for (Genotype gt : Genotype.values()) {
					subPopulationSize = gr.getGenotypeSubpopulationSize(gt);
					numOut = (int)(subPopulationSize * sp.getMigrationRate(gt));
					gr.setGenotypeSubpopulationSize(gt, subPopulationSize - numOut);
					inTransit.put(gt, inTransit.get(gt) + numOut);
				}	
			}
		}

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
			}
		}
		// How to calculate num immigration from each population?
		// How to redistribute drifters?
	}
}
