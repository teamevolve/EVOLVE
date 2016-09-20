package simulation;

import java.util.ArrayList;

/**
 * Population represents a single population within the simulation. It holds
 * all GenerationRecords regarding the population, calculates survival and 
 * reproduction results, and does all direct editing of data about a
 * population.
 * 
 * @see PopulationManager
 * @see GenerationRecord
 * 
 * @author ericscollins
 *
 */
public class Population {

	private static int populationCounter = 0;
	private int populationID;
	private ArrayList<GenerationRecord> generationHistory;
	private GenerationRecord currentGeneration;
	private boolean extinct;
	
	
	
	/**
	 * Constructor: initializes generationHistory, assigns ID
	 */
	public Population() {
		populationID = populationCounter++;
		generationHistory = new ArrayList<GenerationRecord>();
	}
	
	
	/**
	 * Determine whether population has gone extinct
	 * 
	 * @return true iff population is extinct
	 */
	public boolean isExtinct() {
		return extinct;
	}
	
	
	/**
	 * Retrieves the last generation simulated.
	 * 
	 * @return the last generation simulated
	 */
	public GenerationRecord getLastGeneration() {
		return generationHistory.get(generationHistory.size() - 1);
	}
	
		
	/**
	 * Simulates birth and death over a generation of a population
	 */
	public void simulateGeneration() {
		GenerationRecord newGeneration = new GenerationRecord();
		reproduce(getLastGeneration(), newGeneration);
		survive(getLastGeneration(), newGeneration);
		generationHistory.add(newGeneration);
	}
	
	
	/**
	 * Adjust population data based on migration and mutation calculated by
	 * PopulationManager.
	 * 
	 * @param flow GeneFlow object containing all information about mutations 
	 *             and migrations into and out of the population
	 * 
	 * @see   PopulationManager
	 * @see   GeneFlow
	 */
	public void adjustForFlow(GeneFlow flow) {
		
	}
	
	
	/**
	 * Simulates production of juveniles in a population.
	 * 
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by reproduce() to reflect reproduction
	 */
	private static void reproduce(GenerationRecord previous, 
			                      GenerationRecord current) {
	}
	
	
	/**
	 * Simulates death of members of a population.
	 * 
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by survive() to reflect death
	 */
	private static void survive(GenerationRecord previous, 
			                    GenerationRecord current) {
	}
	
}
