package simulation;

import java.util.ArrayList;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;

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
	private boolean extinct;
	
	
	
	/**
	 * Constructor: initializes generationHistory, assigns ID
	 */
	public Population() {
		populationID = populationCounter++;
		generationHistory = new ArrayList<GenerationRecord>();
		GenerationRecord gr = new GenerationRecord(populationID, 0);
		for (Genotype gt : Genotype.values()) {
			
			gr.setGenotypeSubpopulationSize(gt, (int)(DataManager.getInstance().getSessionParams().getPopSize() *
					DataManager.getInstance().getSessionParams().getGenotypeFrequency(gt)));
		}
		generationHistory.add(gr);
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
		GenerationRecord newGeneration = new GenerationRecord(populationID, generationHistory.size());
		reproduce(getLastGeneration(), newGeneration);
		mutate(newGeneration);
		survive(newGeneration);
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
	 *                 
	 * @author richwenner
	 */
	private static void survive(GenerationRecord current) {
		
		int temp;
		int totalAdults = 0; //as of yet unclear whether popSize continually changes, probs can remove
		double crash;
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		//Calculate the number of each genotype surviving
		for (Genotype gt: Genotype.values()) {
			//Typecasting to int in java is analogous to flooring
			//Unclear if we want survival to work like mutate/reprod with 'randomness'
			temp = (int)(current.getGenotypeSubpopulationSize(gt) * sp.getSurvivalRate(gt));
			current.setGenotypeSubpopulationSize(gt, temp);
			totalAdults += temp;
		}
		
		//Kill off populations if larger than carrying capacity
		if (totalAdults > sp.getPopCapacity()) {
			crash = (double)(sp.getCrashCapacity()) / (double)(totalAdults);
			for (Genotype gt: Genotype.values()) {
				current.setGenotypeSubpopulationSize(gt, (int)(current.getGenotypeSubpopulationSize(gt) * crash));
			}
		}
	}
	
	
	/**
	 * Simulates mutations within a population.
	 * 
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by mutate() to reflect mutations
	 *                 
	 * @author ericscollins
	 */
	private static void mutate(GenerationRecord current) {
		final Random rng = new Random();
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		// Containers to hold temporary values used more than once
		int remainingMutations;
		int numMutations;
		double mutationRate;
		double randomValue;
		
		// For all possible combinations of genotypes...
		for (Genotype from : Genotype.values()) {
			remainingMutations = current.getGenotypeSubpopulationSize(from);
			for (Genotype to : Genotype.values()) {
				// Mutations to self are ignored
				if (from == to) continue;
				
				mutationRate = sp.getMutationRate(from, to);
				randomValue = rng.nextDouble();
				
				// Check if any mutations occurred
				if (randomValue <= mutationRate) {
					
					// Calculate how many mutations occurred
					numMutations = (int)(Math.log(randomValue) / Math.log(mutationRate));
					
					// Ensure number of mutations never exceeds source
					// subpopulation's size
					if (remainingMutations - numMutations < 0) {
						numMutations = remainingMutations;
					}
					
					current.setMutationCount(from, to, numMutations);
					remainingMutations -= numMutations;
					current.setGenotypeSubpopulationSize(from, current.getGenotypeSubpopulationSize(from) - numMutations);
					current.setGenotypeSubpopulationSize(to, current.getGenotypeSubpopulationSize(to) + numMutations);
					
					// If source subpopulation has been depleted, move on to 
					// the next subpopulation
					if (remainingMutations == 0) break;
				}
			}
		}
	}
}
