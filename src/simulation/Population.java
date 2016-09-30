package simulation;

import java.util.ArrayList;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import shared.Utilities;

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

	final static double MUTATION_STDDEV = 0.1;
	final static double MUTATION_MEAN = 1.0;
	
	final Random INTERNAL_RNG;
	
	private static int populationCounter = 0;
	private int populationID;
	private ArrayList<GenerationRecord> generationHistory;
	private boolean extinct;
	
	
	
	/**
	 * Constructor: initializes generationHistory, assigns ID
	 */
	public Population(long rngSeed) {
		populationID = populationCounter++;
		generationHistory = new ArrayList<GenerationRecord>();
		GenerationRecord gr = new GenerationRecord(populationID, 0);
		INTERNAL_RNG = new Random(rngSeed);
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
	private void reproduce(GenerationRecord previous, 
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
	private void survive(GenerationRecord current) {
		
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
	private void mutate(GenerationRecord current) {
		
		final SessionParameters sp = DataManager.getInstance().getSessionParams();

		// Containers to hold temporary values used more than once
		int remainingMutations;
		int numMutations;
		double mutationRate;

		// For all possible combinations of genotypes...
		for (Genotype from : Genotype.values()) {
			remainingMutations = current.getGenotypeSubpopulationSize(from);
			for (Genotype to : Genotype.values()) {
				// Mutations to self are ignored
				if (from == to) continue;

				mutationRate = sp.getMutationRate(from, to);
				
				// Produce a random number with a mean of MUTATION_MEAN (usually 1.0) and a standard deviation of 
				// MUTATION_STDDEV and multiply that by the expected average number of mutations 
				numMutations = (int)Math.round(Utilities.nextGaussianRand(INTERNAL_RNG, MUTATION_MEAN, MUTATION_STDDEV) * 
						                       current.getGenotypeSubpopulationSize(from) * mutationRate);
				
				// Ensure number of mutations never exceeds source
				// subpopulation's size, and that rng did not produce a 
				// negative value
				if (remainingMutations - numMutations < 0) {
					numMutations = remainingMutations;
				}
				else if (numMutations < 0) {
					numMutations = 0;
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