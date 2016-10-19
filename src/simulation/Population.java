package simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	final private static double REPRODUCTION_MEAN = 0.05;
	
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
//		reproduce(getLastGeneration(), newGeneration);
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
	private void reproduce(GenerationRecord previous,
			GenerationRecord current) {
		final double previousSize = previous.getPopulationSize();
		final double numParings = previousSize / 2;
		final HashMap<Genotype, Integer> countPunnet = new HashMap<Genotype, Integer>();
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		double numOffspring = 0;
		double totalPunnet = 0;
		
		double gt1SubPopRatio;
		double gt1Rate;
		
		for (Genotype gt : Genotype.values()) {
			countPunnet.put(gt, 0);
		}
				
		for (Genotype gt1 :  Genotype.values()) {
			gt1SubPopRatio = previous.getGenotypeSubpopulationSize(gt1);
			gt1Rate = sp.getReproductionRate(gt1);
			for (Genotype gt2 : Genotype.values()) {
				if (!Genotype.isValidPairing(gt1, gt2)) continue;
				
				numOffspring += (int)Math.round((gt1Rate + sp.getReproductionRate(gt2)) * 
						gt1SubPopRatio * previous.getGenotypeSubpopulationSize(gt2) / previousSize * 
						numParings * 
						Utilities.nextGaussianRand(INTERNAL_RNG, REPRODUCTION_MEAN, REPRODUCTION_STDDEV));

				for (Genotype gt : Utilities.getOffspringGenotypes(gt1, gt2)) {
					countPunnet.put(gt, countPunnet.get(gt) + 1);
					totalPunnet++;
				}
			}
		}
		
		for (Genotype gt : Genotype.values()) {
			current.setGenotypeSubpopulationSize(gt, (int)Math.round(countPunnet.get(gt) / totalPunnet * numOffspring));
		}
		
		
		
//		// For each pair of mates, calculate the number of offspring for each type:
//
//		// genotype iterator
//		ArrayList<Genotype> gtItr = new
//				ArrayList<Genotype>(Arrays.asList(Genotype.values()));
//		Random rand = new Random();
//
//		for (Genotype gt1 : Utilities.getShuffledGenotypes(rand))
//		{
//			// Set Sub Population Size to 0 to start:
//			current.setGenotypeSubpopulationSize(gt1, 0);
//
//
//
//			// Calculate amount of offspring from each combination of two genotypes:
//			for (Genotype gt2: gtItr)
//			{
//				// Do punnett squares, get shuffled list of offspring values:
////				Collection<Genotype> offspringGenotypes =
////						Utilities.getPunnetSquare(gt1, gt2, rand);
//
//
//				int sum = 	previous.getGenotypeSubpopulationSize(gt1) +
//						previous.getGenotypeSubpopulationSize(gt2);
//				double mean = ((double) sum)/2;
//
//				// adjustment factor - gaussian random that accounts for fluctuation from
//				// ideal reproduction
//				double adjFactor = Utilities.nextGaussianRand(rand, 1.0, 0.05);
//
//				if(gt1 == Genotype.AA && gt2 == Genotype.BB
//						|| gt1 == Genotype.BB && gt2 == Genotype.AA){
//					// in the AAxBB case, we will have 100% of the population in the AB subpop
//
//					current.setGenotypeSubpopulationSize(Genotype.AB, sum);
//				}
//				else{	// assuming 50% parent 1 and 50% parent 2
//					current.setGenotypeSubpopulationSize(gt1, (int) (mean*adjFactor+0.5));
//					current.setGenotypeSubpopulationSize(gt2, (int) (mean*(2-adjFactor)));
//				}
//			}
//			gtItr.remove(gt1);
//		}
//
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
		for (Genotype gt: Genotype.values()) {
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
		int totalMutations;
		int numMutations;
		int adjustedMutations;
		double ratio;
		HashMap<Genotype, Integer> contrib;

		// For all possible combinations of genotypes...
		for (Genotype from : Genotype.values()) {
			contrib = new HashMap<Genotype, Integer>();
			totalMutations = 0;
			for (Genotype to : Genotype.values()) {

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

			for (Genotype to : Genotype.values()) {
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

