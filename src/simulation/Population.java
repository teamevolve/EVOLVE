package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import org.omg.CORBA.INTERNAL;

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
 * @auther rwenner
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
		INTERNAL_RNG = new Random(rngSeed);
		generationHistory.add(getInitialGeneration());
	}

	
	/**
	 * Constructs a GenerationRecord object to be used as the initial 
	 * generation.
	 * 
	 * @return GenerationRecord object representing initial generation
	 */
	private GenerationRecord getInitialGeneration() {
		GenerationRecord gr = new GenerationRecord(populationID, 0);

		for (Genotype gt : Genotype.getValues()) {
			gr.setGenotypeSubpopulationSize(gt, (int)(DataManager.getInstance().getSessionParams().getPopSize() * DataManager.getInstance().getSessionParams().getGenotypeFrequency(gt)));
			gr.setImmigrationCount(gt, 0);
			gr.setEmigrationCount(gt, 0);
			gr.setBirths(gt, 0);
			gr.setDeaths(gt, 0);

			for (Genotype gt2 : Genotype.getValues()) {
				gr.setMutationCount(gt, gt2, 0);
			}
		}
		return gr;

	}

	/**
	 * Resets the population counter 
	 * @author jasonfortunato
	 */
	public static void resetPopulationCounter() {
		populationCounter = 0;
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
	 * Accessor for populationID.
	 * 
	 * @return population ID
	 */
	public int getPopID() {
		return populationID;
	}


	/**
	 * Accessor for generationHistory.
	 * 
	 * @return list of GenerationRecords in Population's history
	 */
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
		//are they extinct or are we below the population size?  if so, return
		if (getLastGeneration().getPopulationSize() < popSize) {
			return;
		}
		double scaleRatio = (double)popSize / (double)getLastGeneration().getPopulationSize();
		Genotype maxGenotype = null;
		int maxPopulation = 0;
		for (Genotype gt: Genotype.getValues()) {
			if (getLastGeneration().getGenotypeSubpopulationSize(gt) > maxPopulation) {
				maxPopulation = getLastGeneration().getGenotypeSubpopulationSize(gt);
				maxGenotype = gt;	
			}
			getLastGeneration().setGenotypeSubpopulationSize(gt, (int)Math.round((double)getLastGeneration().getGenotypeSubpopulationSize(gt) * scaleRatio));
		}
		//fix population if it's off by a few numbers by rounding the highest genotype
		getLastGeneration().setGenotypeSubpopulationSize(maxGenotype, getLastGeneration().getGenotypeSubpopulationSize(maxGenotype) + (popSize - getLastGeneration().getPopulationSize()));
	}


	private void reproduce(GenerationRecord previous, GenerationRecord current) {
		///*DEBUG*/long start = System.currentTimeMillis();
		
		HashMap<Genotype, HashMap<Genotype, Integer>> pairings = mateIterativeIntegers(previous);
//		HashMap<Genotype, HashMap<Genotype, Integer>> pairings = mateIterativeBiased(previous);
		
		
		
		///*DEBUG*/System.out.println(System.currentTimeMillis() - start);
		generateOffspring(current, pairings);
	}
	
	
	/**
	 * Simulates production of juveniles in a population.
	 *
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by reproduce() to reflect reproduction
	 */
	@Deprecated
	private void reproduce_old(GenerationRecord previous, GenerationRecord current) {
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

	private HashMap<Genotype, HashMap<Genotype, Integer>> mateIterativeList(GenerationRecord previous) {
		ArrayList<Genotype> individuals = new ArrayList<Genotype>();
		HashMap<Genotype, HashMap<Genotype, Integer>> results = new HashMap<Genotype, HashMap<Genotype, Integer>>();
		for (Genotype gt : Genotype.getValues()) {
			for (int i=0; i < previous.getGenotypeSubpopulationSize(gt); i++) {
				individuals.add(gt);
			}
		}
		Collections.shuffle(individuals);
		while(individuals.size() > 1) {
			Genotype gt1;
			Genotype gt2;
			if (Utilities.isValidPairing(individuals.get(0), individuals.get(1))) {
				gt1 = individuals.remove(0);
				gt2 = individuals.remove(0);
			}
			else {
				gt2 = individuals.remove(0);
				gt1 = individuals.remove(0);
			}
			
			if (results.get(gt1) == null)
				results.put(gt1, new HashMap<Genotype, Integer>());
			if (results.get(gt1).get(gt2) == null)
				results.get(gt1).put(gt2,  0);
			results.get(gt1).put(gt2, results.get(gt1).get(gt2) + 1);			
		}
		
		if (!individuals.isEmpty()) {
			Genotype gt = individuals.remove(0);
			if (results.get(gt) == null)
				results.put(gt, new HashMap<Genotype, Integer>());
			if (results.get(gt).get(gt) == null)
				results.get(gt).put(gt,  0);
			results.get(gt).put(gt, results.get(gt).get(gt) + 1);			
		}
			
		return results;
	}
	
	private HashMap<Genotype, HashMap<Genotype, Integer>> mateIterativeIntegers(GenerationRecord previous) {
		HashMap<Genotype, HashMap<Genotype, Integer>> results = new HashMap<Genotype, HashMap<Genotype, Integer>>();
		
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		boolean isBiased = sp.isSexSelectChecked();

		// Preferenes for sexual selection
		HashMap<Genotype, HashMap<Genotype, Double>> preferences = new HashMap<Genotype, HashMap<Genotype, Double>>();
		
		// create new hashmaps for each valid pairing in results:
		for (Genotype gt1 : Genotype.getValues()) {
			
			results.put(gt1, new HashMap<Genotype, Integer>());
			preferences.put(gt1, new HashMap<Genotype, Double>());
			
			for (Genotype gt2 : Genotype.getValues()) {
				preferences.get(gt1).put(gt2, sp.getSexualSelectionRate(gt1, gt2));

				if (!Utilities.isValidPairing(gt1, gt2)) continue;
				results.get(gt1).put(gt2, 0);
			}
		}
		
		// store current sub population sizes in a separate hashmap:
		HashMap<Genotype, Integer> subpopSizes = new HashMap<Genotype, Integer>();
		for (Genotype gt : Genotype.getValues()) {
			subpopSizes.put(gt, previous.getGenotypeSubpopulationSize(gt));
		}
		
		// store current total and keep track of probabilities in a separate hashmap:
		int total = previous.getPopulationSize();
		HashMap<Genotype, Double> probabilities = new HashMap<Genotype, Double>();
		
		
		System.out.print("SubPopSizes: ");
		for (Genotype gt : Genotype.getValues())
		{
			System.out.print(gt.name()+ ": " + subpopSizes.get(gt) + ", ");
		}
		System.out.println();

		
		// loop until there are no pairs left
		while (total > 1) {
			System.out.println("---------------------------------------------------");
			double accumulator = 0.0; // total frequency (not necessarily = 1)

			// update probabilities and increment accumulator:
			for (Genotype gt : Genotype.getValues()) {
				accumulator += (double)subpopSizes.get(gt) / (double)total;
				probabilities.put(gt, accumulator);
			}
			
			double r1 = INTERNAL_RNG.nextDouble();			
			
			Genotype gt1 = null, gt2 = null;

			System.out.println("r1 = " + r1);
			for (Genotype gt : Genotype.getValues()) {
				if (r1 < probabilities.get(gt)) {
					gt1 = gt;
					System.out.println("Individual 1: " + gt1.toString());
					break;
				}
			}
			
			
			
			// remove first individual and decrement total
			subpopSizes.put(gt1, subpopSizes.get(gt1) - 1);	
			total--;
			
			System.out.print("SubPopSizes: ");
			for (Genotype gt : Genotype.getValues())
			{
				System.out.print(gt.name()+ ": " + subpopSizes.get(gt) + ", ");
			}
			System.out.println();


			
			double maxPref = 1;
			for (Genotype gt : Genotype.getValues())
			{
				if (subpopSizes.get(gt) < 1) // if current genotype is empty
				{
//						maxPref -= preferences.get(gt1).get(gt);
						maxPref -= sp.getSexualSelectionRate(gt1, gt);
						preferences.get(gt1).put(gt, 0.0);
				}
			}
			
			System.out.print("Preferences of " + gt1.name() + ": ");
			for (Genotype gt : Genotype.getValues())
			{
				System.out.print(gt.name() + " = " 
						+ preferences.get(gt1).get(gt) + " : ");
			}
			System.out.println();

			
			System.out.println("MaxPref: " + maxPref);
			double r2 = INTERNAL_RNG.nextDouble()*maxPref; // from 0 to cf 

			
			System.out.println();
			System.out.println("r2 = " + r2);
			if (isBiased) {
				double sexualPref = 0.0;
				for (Genotype gt : Genotype.getValues()) {
					
//					sexualPref += sp.getSexualSelectionRate(gt1, gt);
					sexualPref += preferences.get(gt1).get(gt); // 0 if gt subpopSize is empty
					
					System.out.println("Cumulative Preference(" + gt1 + "->" + gt +"): " + sexualPref);
					
					if (r2 < sexualPref)
					{	// pick this genotype for individual 2:
						gt2 = gt;
						System.out.println("Individual 2: " + gt2.toString());
						break;
					}
				}
			}
			else {
				for (Genotype gt : Genotype.getValues()) {	
					if (r2 < probabilities.get(gt)) {
						gt2 = gt;
						System.out.println("Individual 2: " + gt2.toString());
						break;
					}
				}				
			}

			// remove second individual and decrement total
			subpopSizes.put(gt2, subpopSizes.get(gt2) - 1);
			total --;			
			
			// swap pair so that genotypes are ordered alphabetically:
			if (!Utilities.isValidPairing(gt1, gt2)) {
				Genotype temp = gt1;
				gt1 = gt2;
				gt2 = temp;
			}

			System.out.println("Ordered Pair: (" + gt1 + ", " + gt2 + ")");
			
			
			
			// increment (gt1, gt2) mating pair + 1
			results.get(gt1).put(gt2, results.get(gt1).get(gt2) + 1);

			printPairMatrix(results);
								
			System.out.print("SubPopSizes: ");
			for (Genotype gt : Genotype.getValues())
			{
				System.out.print(gt.name()+ ": " + subpopSizes.get(gt) + ", ");
			}
			System.out.println();

			
		}
		
		// in the case that one individual is left alone, mate with self:
		if (total == 1) {
			Genotype gt = null;
			for (Genotype g : Genotype.getValues()) {
				if (subpopSizes.get(g) != 0) {
					gt = g;
					break;
				}
			}

			// we are adding an extra individual to the population
			// by doing this - how to resolve?
			results.get(gt).put(gt, results.get(gt).get(gt) + 1);
			
			printPairMatrix(results);
			
		}
		
		return results;
	}
	
	private void printPairMatrix(HashMap<Genotype, HashMap<Genotype, Integer>> results)
	{
		System.out.println("Pair Matrix:");
		System.out.print("      ");
		for (Genotype gtB : Genotype.getValues())
		{
			System.out.print(gtB.name() + "   ");
		}
		System.out.println();
		
		for (Genotype gtA : Genotype.getValues()) {
			System.out.print(gtA.name() + " ");
			for (Genotype gtB : Genotype.getValues()) {
				if (!Utilities.isValidPairing(gtA, gtB)) {continue;}					
//				System.out.print(gtA.name() + "-" +  gtB.name() + ":");
				String str = "" + results.get(gtA).get(gtB);
				System.out.print(String.format("%1$"+5+"s", str));
//				System.out.print(":");
			}
			System.out.println();
		}
		System.out.println();		
	}
	
	private void generateOffspring(GenerationRecord current, HashMap<Genotype, HashMap<Genotype, Integer>> pairings) {
		HashMap<Genotype, Double> offspring = new HashMap<Genotype, Double>();
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		for (Genotype gt : Genotype.getValues()) {
			offspring.put(gt, 0.0);
		}

		for (Genotype gt1 : Genotype.getValues()) {
			for (Genotype gt2 : Genotype.getValues()) {
				if (!Utilities.isValidPairing(gt1, gt2)) continue;
				for (Genotype off : Utilities.getOffspringGenotypes(gt1, gt2)) {
					offspring.put( off,
							offspring.get(off) + 
							(sp.getReproductionRate(gt1) + sp.getReproductionRate(gt2)) * .25 *
							pairings.get(gt1).get(gt2) *
							Utilities.nextGaussianRand(INTERNAL_RNG, REPRODUCTION_MEAN, REPRODUCTION_STDDEV));
				}
			}
		}
		
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
			current.setDeaths(gt, subPopulation - numSurvived);
			totalAdults += numSurvived;
		}

		//Kill off populations if larger than carrying capacity
		if (!sp.isPopConst()) {
			if (totalAdults > sp.getPopCapacity()) {
				crash = (double)(sp.getCrashCapacity()) / (double)(totalAdults);
				for (Genotype gt: Genotype.getValues()) {
					current.setGenotypeSubpopulationSize(gt, (int)(current.getGenotypeSubpopulationSize(gt) * crash));
				}
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






