package simulation;

import java.util.ArrayList;
import java.util.HashMap;
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

	final private static double MUTATION_STDDEV = 0.05;
	final private static double MUTATION_MEAN = 1.0;
	final private static double SURVIVAL_STDDEV = 0.05;
	final private static double SURVIVAL_MEAN = 1.0;
	final private static double REPRODUCTION_STDDEV = 0.05;
	final private static double REPRODUCTION_MEAN = 1.0;
	
	final private Random INTERNAL_RNG;

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
		for (Genotype gt : Genotype.getValues()) {

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

	
	 public int getPopID() {
		 return populationID;
	 }
	 
	 
	 public ArrayList<GenerationRecord> getGenerationHistory() {
		 return generationHistory;
	 }
	

	/**
	 * Simulates birth and death over a generation of a population
	 */
	public void simulateGeneration() {
		GenerationRecord newGeneration = new GenerationRecord(populationID, generationHistory.size());
		reproduce(getLastGeneration(), newGeneration);
		if (DataManager.getInstance().getSessionParams().isMutationChecked()) 
			mutate(newGeneration);
		if (DataManager.getInstance().getSessionParams().isSelectChecked()) 
			survive(newGeneration);
		generationHistory.add(newGeneration);
	}


	/**
	 * Only run if use sets population size as constant. Scales a population
	 * keeping its genotype frequencies constant so that its total population
	 * size is equal to popSize.
	 *
	 * @param popSize target size to scale population to
	 */
	public void scale(int popSize) {

	}


	/**
	 * Simulates production of juveniles in a population.
	 *
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by reproduce() to reflect reproduction
	 */
	private void reproduce(GenerationRecord previous, GenerationRecord current) {
		// Constants and persistent objects we'll use
		final double previousSize = previous.getPopulationSize();
		final double numParings = previousSize / 2;
		final HashMap<Genotype, Double> offspring = new HashMap<Genotype, Double>();
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		// Containers to hold temporary values used more than once
		double gt1SubPopRatio;
		double gt1Rate;
		
		// Initialize result HashMap
		for (Genotype gt : Genotype.getValues()) {
			offspring.put(gt, 0.0);
		}
		
		// Iterate over every mating pair
		for (Genotype gt1 :  Genotype.getValues()) {
			gt1SubPopRatio = previous.getGenotypeSubpopulationSize(gt1);
			gt1Rate = sp.getReproductionRate(gt1);
			
			for (Genotype gt2 : Genotype.getValues()) {
				// Ensure we don't overcount pairs
				if (!Utilities.isValidPairing(gt1, gt2)) continue;
				
				// Calculate how many offspring of each genotype are produced
				for (Genotype gt : Utilities.getOffspringGenotypes(gt1, gt2)) {
					offspring.put(gt, 
							// Already accumulated offspring of genotype gt
							offspring.get(gt) +
							// Likelyhood of a pairing of gt1 and gt2
							gt1SubPopRatio * previous.getGenotypeSubpopulationSize(gt2) / Math.pow(previousSize, 2) * ((gt1 == gt2) ? 1 : 2) *
							// Total number of mating pairs
							numParings *
							// Sum of each genotype's reproductive rate (avg total number of offspring)
							(gt1Rate + sp.getReproductionRate(gt2)) *
							// Random varying
							Utilities.nextGaussianRand(INTERNAL_RNG, REPRODUCTION_MEAN, REPRODUCTION_STDDEV) *
							// Percentage of offspring from punnet
							.25);
				}
			}
		}
		
		// Store results in new generation
		for (Genotype gt : Genotype.getValues()) {
			current.setBirths(gt, (int)Math.round(offspring.get(gt)));
			current.setGenotypeSubpopulationSize(gt, (int)Math.round(offspring.get(gt)));
		}
	}


	/**
	 * Simulates survival of members of a population.  Each allele combination has a
	 * survival rate that will determine how many live/die, modified by a gaussian RNG.
	 * If the population goes over the populationCapacity, we set the population down
	 * to a crashCapacity level.
	 * TODO: talk to Frank about constant populations & their implementation 
	 * 
	 *
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by survive() to reflect death
	 *
	 * @author richwenner
	 */
	private void survive(GenerationRecord current) {
		
		int numSurvived, subPopulation;
		int totalAdults = 0; 
		double crash;
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
	

		//Calculate the number of each genotype surviving
		for (Genotype gt: Genotype.getValues()) {
			subPopulation = current.getGenotypeSubpopulationSize(gt);
			//Typecasting to int in java is analogous to flooring
			numSurvived = (int)Math.round(Utilities.nextGaussianRand(INTERNAL_RNG, SURVIVAL_MEAN, SURVIVAL_STDDEV) * 
                    subPopulation * sp.getSurvivalRate(gt));
			
			
			if (numSurvived <= 0) {
				numSurvived = 0;
			}
			else if (numSurvived > subPopulation){
				numSurvived = subPopulation;
			}
			current.setGenotypeSubpopulationSize(gt, numSurvived);
			totalAdults += numSurvived;
		}

		//Kill off populations if larger than carrying capacity
		if (totalAdults > sp.getPopCapacity()) {
			crash = (double)(sp.getCrashCapacity()) / (double)(totalAdults);
			for (Genotype gt: Genotype.getValues()) {
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
		int totalMutations;
		int numMutations;
		int adjustedMutations;
		double ratio;
		HashMap<Genotype, Integer> contrib;

		// For all possible combinations of genotypes...
		for (Genotype from : Genotype.getValues()) {
			contrib = new HashMap<Genotype, Integer>();
			totalMutations = 0;
			for (Genotype to : Genotype.getValues()) {

				// Produce a random number with a mean of MUTATION_MEAN (usually 1.0) and a standard deviation of
				// MUTATION_STDDEV and multiply that by the expected average number of mutations
				numMutations = (int)Math.round(Utilities.nextGaussianRand(INTERNAL_RNG, MUTATION_MEAN, MUTATION_STDDEV) *
						current.getGenotypeSubpopulationSize(from) * sp.getMutationRate(from, to));

				// Ensure rng did not produce negative value
				if (numMutations < 0) numMutations = 0;

				totalMutations += numMutations;
				contrib.put(to, numMutations);
			}

			// If no mutations happened, move on to the next genotype
			if (totalMutations == 0) continue;

			// Ratio to scale mutations by to keep population size constant
			ratio = current.getGenotypeSubpopulationSize(from) / totalMutations;

			for (Genotype to : Genotype.getValues()) {
				if (to == from) continue;

				// Scale mutation count appropriately
				adjustedMutations = (int)Math.round(ratio * contrib.get(to));

				// Adjust subpopulation counts
				current.setMutationCount(from, to, adjustedMutations);
				current.setGenotypeSubpopulationSize(from, current.getGenotypeSubpopulationSize(from) - adjustedMutations);
				current.setGenotypeSubpopulationSize(to, current.getGenotypeSubpopulationSize(to) + adjustedMutations);
			}
		}
	}
}






