package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import shared.Utilities;

import static gui.GUI.DEBUG_MATE;
import static gui.GUI.DEBUG_REPRO;
import static gui.GUI.DEBUG_MIGRATION;
import static gui.GUI.DEBUG_SURVIVAL;
import static gui.GUI.DEBUG_MUTATION;
import static gui.GUI.DEBUG_SUMMARY;

import static gui.GUI.MAT_SUM;
import static gui.GUI.REP_SUM;
import static gui.GUI.SURV_SUM;
import static gui.GUI.MUT_SUM;

import static gui.GUI.BINOMIAL;
import static gui.GUI.POISSON;

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
 * @author rwenner
 * @author candicezhao
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
	public void simulateMatingRepro() {
		GenerationRecord newGeneration = new GenerationRecord(populationID, generationHistory.size());
		if (DEBUG_MIGRATION && DEBUG_SUMMARY) {
			System.out.println("GENERATION: "+ generationHistory.size() + " Population NO." + populationID);
		}
		else if ((DEBUG_MATE || DEBUG_REPRO || DEBUG_MUTATION || DEBUG_SURVIVAL) && populationID == 0){
			System.out.println("GENERATION: "+ generationHistory.size() + " Population NO." + populationID);
		}
		
		reproduce(getLastGeneration(), newGeneration); // mating and reproduce
		generationHistory.add(newGeneration);
	}
	
	public void simulateSurviveMutation() {
		GenerationRecord newGeneration = generationHistory.get(generationHistory.size() - 1);
		
		survive_binomial(newGeneration);
//
//		if (BINOMIAL) {
//		survive_binomial(newGeneration);
//		}
//		else if (POISSON) {
//			survive_poisson(newGeneration);
//		}
//		else {
//			survive(newGeneration); //natural selection
//		}
		
		if ((DEBUG_SUMMARY && DEBUG_MIGRATION) || ((DEBUG_SUMMARY || SURV_SUM) && populationID == 0)) {

			System.out.println("GENERATION: " + newGeneration.getGenerationNumber() + " Population NO." + newGeneration.getParentPopID());
			System.out.println();
			System.out.println("  summary for SURVIVING:");
			printGenoNum_indent(newGeneration);
		}
		
		if (DataManager.getInstance().getSessionParams().isMutationChecked()) {
//			if (BINOMIAL) {
//				mutate_binomial(newGeneration);
//			}
//			else if (POISSON) {
//				mutate_poisson(newGeneration);
//			}
//			else {
//				mutate(newGeneration); //mutate
//			}
			mutate_binomial(newGeneration);

		}

		if ((DEBUG_SUMMARY && DEBUG_MIGRATION) || ((DEBUG_SUMMARY || MUT_SUM) && populationID == 0)) {
			System.out.println();
			System.out.println("  summary for MUTATION:");
			printGenoNum_indent(newGeneration);
			System.out.println();
			System.out.println();
		}

		if (!DEBUG_SUMMARY 
				&& (DEBUG_MATE || DEBUG_REPRO || DEBUG_MUTATION || DEBUG_SURVIVAL || DEBUG_MIGRATION) 
				&& populationID == 0) {
			System.out.println();
			System.out.println();
			System.out.println();
		}
		
		
		generationHistory.remove(generationHistory.size() - 1);
		generationHistory.add(newGeneration);
	}


	/**
	 * Only run if the user sets population size as constant. Scales a population
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

	/**
	 * Simulates production of juveniles in a population.
	 *
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by reproduce() to reflect reproduction
	 */
	private void reproduce(GenerationRecord previous, GenerationRecord current) {
		///*DEBUG*/long start = System.currentTimeMillis();
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		boolean isBiased = sp.isSexSelectChecked();
		HashMap<Genotype, HashMap<Genotype, Integer>> pairings = new HashMap<Genotype, HashMap<Genotype, Integer>> ();
	
		
		if(!isBiased) {
			pairings = mateIterativeIntegers(previous);
		}
		else {
			pairings = mateIterativeBiased(previous);
		}
		
		//----------DEBUGGING CODE------------
		if ((DEBUG_SUMMARY && DEBUG_MIGRATION) || ((DEBUG_SUMMARY || MAT_SUM) && populationID == 0)) {
			System.out.println();
			System.out.println("  summary for MATING:");
			printPairings_indent(pairings);
		}

		
		// generate Offspring
		generateOffspring_binomial(current, pairings);

		//----------DEBUGGING CODE-------------
		if ((DEBUG_SUMMARY && DEBUG_MIGRATION) || ((DEBUG_SUMMARY || REP_SUM) && populationID == 0)) {
			System.out.println();
			System.out.println("  summary for REPRODUCTION:");
			printGenoNum_indent(current);
			System.out.println();
			System.out.println();
		}
	}
	
	
	
	// mating with no sexual preferences
	private HashMap<Genotype, HashMap<Genotype, Integer>> mateIterativeIntegers(GenerationRecord previous) {
		// results for mating record
		HashMap<Genotype, HashMap<Genotype, Integer>> results = new HashMap<Genotype, HashMap<Genotype, Integer>>();
		for (Genotype gt1 : Genotype.getValues()) {
			results.put(gt1, new HashMap<Genotype, Integer>());
			for (Genotype gt2 : Genotype.getValues()) {
				if (!Utilities.isValidPairing(gt1, gt2)) continue;
				results.get(gt1).put(gt2, 0);
			}
		}
				
		HashMap<Genotype, Integer> subpopSizes = new HashMap<Genotype, Integer>();
		for (Genotype gt : Genotype.getValues()) {
			subpopSizes.put(gt, previous.getGenotypeSubpopulationSize(gt));
		}
		int total = previous.getPopulationSize();
		HashMap<Genotype, Double> probabilities = new HashMap<Genotype, Double>();
		HashMap<Genotype, Double> subProb = new HashMap<Genotype, Double>();

		// ---------------------------------------------------------------
		// print out matings we have so far
		// print out numbers and ratios of each genotype in the population
		if (DEBUG_MATE && populationID == 0) {
			printPairings(results);
			System.out.println();
		}
		// ---------------------------------------------------------------
		// ---------------------------------------------------------------
		int counter = 1;
		while (total > 1) {
			double accumulator = 0.0;
			for (Genotype gt : Genotype.getValues()) {
				accumulator += (double)subpopSizes.get(gt) / (double)total;
				subProb.put(gt, accumulator - (double)subpopSizes.get(gt) / (double)total);
				probabilities.put(gt, accumulator);
			}

			// ---------------------------------------------------------------
			// print out matings we have so far
			// print out numbers and ratios of each genotype in the population
			if (DEBUG_MATE && populationID == 0) {
				System.out.printf("\n   PAIR %d - 1st \n", counter);
				printGenoInfo_indent(subpopSizes, probabilities, subProb);
			}
			// ---------------------------------------------------------------
			// ---------------------------------------------------------------

			Genotype gt1 = null;
			Genotype gt2 = null;
			double r1 = INTERNAL_RNG.nextDouble();
			double r2 = INTERNAL_RNG.nextDouble();
			
			for (Genotype gt : Genotype.getValues()) {
				if (r1 < probabilities.get(gt)) {
					gt1 = gt;
					break;
				}
			}
			
			// take gt1 from population, update the ratios and numbers.
			subpopSizes.put(gt1, subpopSizes.get(gt1) - 1);
			total -= 1;
			accumulator = 0.0;
			for (Genotype gt : Genotype.getValues()) {
				accumulator += (double)subpopSizes.get(gt) / (double)total;
				subProb.put(gt, accumulator - (double)subpopSizes.get(gt) / (double)total);
				probabilities.put(gt, accumulator);
			}

			// ---------------------------------------------------------------
			// print out numbers and ratios of each genotype in the population
			// print out random numbers chosen and the genotypes chosen
			if (DEBUG_MATE && populationID == 0) {
				System.out.printf("\n     r1: %.5f   \n", r1);
				System.out.println("     gt1: " + gt1.toString());
				System.out.printf("\n   PAIR %d - 2nd \n", counter);
				counter++;
				printGenoInfo_indent(subpopSizes, probabilities, subProb);
			}
			// ---------------------------------------------------------------
			// ---------------------------------------------------------------
			
			for (Genotype gt : Genotype.getValues()) {	
				if (r2 < probabilities.get(gt)) {
					gt2 = gt;
					break;
				}
			}
			
			subpopSizes.put(gt2, subpopSizes.get(gt2) - 1);
			total -= 1;
			
			// ---------------------------------------------------------------
			// print out numbers and ratios of each genotype in the population
			// print out random numbers chosen and the genotypes chosen
			if (DEBUG_MATE && populationID == 0) {
				System.out.printf("\n     r2: %.5f   \n", r2);
				System.out.println("     gt2: " + gt2.toString());
			}
			// ---------------------------------------------------------------
			// ---------------------------------------------------------------
			
			if (!Utilities.isValidPairing(gt1, gt2)) {
				Genotype temp = gt1;
				gt1 = gt2;
				gt2 = temp;
			}
			
			results.get(gt1).put(gt2, results.get(gt1).get(gt2) + 1);
			
			if (DEBUG_MATE && populationID == 0) {
				System.out.println();
				printPairings_indent(results);
				System.out.println("-------------------------------------------------------------------------------");
			}
		}
		
		if (total == 1) {
			Genotype gt = null;
			for (Genotype g : Genotype.getValues()) {
				if (subpopSizes.get(g) != 0) {
					gt = g;
					break;
				}
			}
			results.get(gt).put(gt, results.get(gt).get(gt) + 1);
			//debug printing
			if (DEBUG_MATE && populationID == 0) {
				System.out.printf("   PAIR %d \n", counter);
				printGenoInfo_indent(subpopSizes, probabilities, subProb);
				System.out.println( "     single reproduction: " + "gt: " + gt.toString() + "\n");
			}
		}
		
		
		if (DEBUG_MATE && populationID == 0) {
			System.out.println();
			printPairings(results);
			//System.out.println("--------------------------------------------------------------------");
		}

		return results;
	}
	
	// mating with sexual preferences
	private HashMap<Genotype, HashMap<Genotype, Integer>> mateIterativeBiased(GenerationRecord previous) {
		HashMap<Genotype, HashMap<Genotype, Integer>> results = new HashMap<Genotype, HashMap<Genotype, Integer>>();
		SessionParameters sp = DataManager.getInstance().getSessionParams();

		// Preferences for sexual selection
		HashMap<Genotype, HashMap<Genotype, Double>> preferences = new HashMap<Genotype, HashMap<Genotype, Double>>();
		HashMap<Genotype, HashMap<Genotype, Double>> startPref = new HashMap<Genotype, HashMap<Genotype, Double>>();
		HashMap<Genotype, HashMap<Genotype, Double>> endPref = new HashMap<Genotype, HashMap<Genotype, Double>>();

		// create new hashmaps for each valid pairing in results:
		for (Genotype gt1 : Genotype.getValues()) {
			
			results.put(gt1, new HashMap<Genotype, Integer>());
			preferences.put(gt1, new HashMap<Genotype, Double>());
			startPref.put(gt1, new HashMap<Genotype, Double>());
			endPref.put(gt1, new HashMap<Genotype, Double>());

			// calculate relative preferences
			double totalpref = 0.0;
			for (Genotype gt2 : Genotype.getValues()) {
				totalpref += sp.getSexualSelectionRate(gt1, gt2);					
			}
			
			for (Genotype gt2 : Genotype.getValues()) {
				if (totalpref == 0) {
					preferences.get(gt1).put(gt2, 1.0/3.0);					
				}
				else {
					preferences.get(gt1).put(gt2, sp.getSexualSelectionRate(gt1, gt2)/totalpref);
				}
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
		HashMap<Genotype, Double> subProb = new HashMap<Genotype, Double>();

		// loop until there are no pairs left
		
		// ---------------------------------------------------------------
		// print out matings we have so far
		// print out numbers and ratios of each genotype in the population
		if (DEBUG_MATE && populationID == 0) {
			printPairings(results);
			System.out.println();
		}
		// ---------------------------------------------------------------
		// ---------------------------------------------------------------
		
		int counter = 1;

		while (total > 1) {
			double accumulator = 0.0; // total frequency (not necessarily = 1)
			// update probabilities and increment accumulator:
			for (Genotype gt : Genotype.getValues()) {
				accumulator += (double)subpopSizes.get(gt) / (double)total;
				subProb.put(gt, accumulator- (double)subpopSizes.get(gt) / (double)total);
				probabilities.put(gt, accumulator);
			}
			
			// ---------------------------------------------------------------
			// print out matings we have so far
			// print out numbers and ratios of each genotype in the population
			if (DEBUG_MATE && populationID == 0) {
				System.out.printf("\n   PAIR %d - 1st \n", counter);
				printGenoInfo_indent(subpopSizes, probabilities, subProb);
			}
			// ---------------------------------------------------------------
			// ---------------------------------------------------------------

			
			Genotype gt1 = null;
			Genotype gt2 = null;
			double r1 = INTERNAL_RNG.nextDouble();
			
			for (Genotype gt : Genotype.getValues()) {
				if (r1 < probabilities.get(gt)) {
					gt1 = gt;
					break;
				}
			}			

			// remove first individual and decrement total
			subpopSizes.put(gt1, subpopSizes.get(gt1) - 1);	
			total--;
			
			accumulator = 0.0;
			for (Genotype gt : Genotype.getValues()) {
				accumulator += (double)subpopSizes.get(gt) / (double)total;
				subProb.put(gt, accumulator - (double)subpopSizes.get(gt) / (double)total);
				probabilities.put(gt, accumulator);
			}

			// ---------------------------------------------------------------
			// print out numbers and ratios of each genotype in the population
			// print out random numbers chosen and the genotypes chosen
			if (DEBUG_MATE && populationID == 0) {
				System.out.printf("\n     r1: %.5f   \n", r1);
				System.out.println("     gt1: " + gt1.toString());
				printGenoInfo_indent(subpopSizes, probabilities, subProb);
			}
			// ---------------------------------------------------------------
			// ---------------------------------------------------------------
			
			double maxPref = 0.0;
			for (Genotype gt : Genotype.getValues()) {
				if (subpopSizes.get(gt) < 1) {
					preferences.get(gt1).put(gt, 0.0);
				}
				else {
					maxPref += preferences.get(gt1).get(gt);
				}
			}
			
			double r2 = INTERNAL_RNG.nextDouble()*maxPref; // from 0 to cf 

			double sexualPref = 0.0;
			for (Genotype gt : Genotype.getValues()) {
				sexualPref += preferences.get(gt1).get(gt);
				endPref.get(gt1).put(gt, sexualPref);
				startPref.get(gt1).put(gt, sexualPref - preferences.get(gt1).get(gt));
			}
			
			for (Genotype gt : Genotype.getValues()) {
				if (r2 < endPref.get(gt1).get(gt)) {	// pick this genotype for individual 2:
					//System.out.print("selected");
					gt2 = gt;
					break;
				}
			}
			
			// ---------------------------------------------------------------
			// print out numbers and ratios of each genotype in the population
			// print out random numbers chosen and the genotypes chosen
			if (DEBUG_MATE && populationID == 0) {
				System.out.printf("\n   PAIR %d - 2nd \n", counter);
				counter++;
				
				//System.out.printf("r2: %.5f   \n", r2);
				printPref_indent(startPref.get(gt1), endPref.get(gt1), gt1);
				System.out.printf("\n     r2: %.5f   \n", r2);
				System.out.println("     MaxPref: " + maxPref);
				System.out.println("     gt2: " + gt2.toString());
			}
			// ---------------------------------------------------------------
			// ---------------------------------------------------------------
				
			// remove second individual and decrement total
			subpopSizes.put(gt2, subpopSizes.get(gt2) - 1);
			total --;			
			
			// swap pair so that genotypes are ordered alphabetically:
			if (!Utilities.isValidPairing(gt1, gt2)) {
				Genotype temp = gt1;
				gt1 = gt2;
				gt2 = temp;
			}			
			
			// increment (gt1, gt2) mating pair + 1
			results.get(gt1).put(gt2, results.get(gt1).get(gt2) + 1);
					
			if (DEBUG_MATE && populationID == 0) {
				System.out.println();
				printPairings_indent(results);
				System.out.println("-------------------------------------------------------------------------------");

			}
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
			if (DEBUG_MATE && populationID == 0) {
				System.out.printf("   PAIR %d \n", counter);
				printGenoInfo_indent(subpopSizes, probabilities, subProb);
				System.out.println( "     single reproduction: " + "gt: " + gt.toString() + "\n");
			}
		}
		if (DEBUG_MATE && populationID == 0) {
			System.out.println();
			printPairings(results);
		}

		return results;
	}
	
	private void printPairings(HashMap<Genotype, HashMap<Genotype, Integer>> results) {
		
		String pairingStr = "";
		String valueStr = "";
		
		for (Genotype gtA : Genotype.getValues()) {
			for (Genotype gtB : Genotype.getValues()) {
				if (!Utilities.isValidPairing(gtA, gtB)) {continue;}					
				pairingStr = pairingStr + String.format("|%-11s", gtA.name() + "x" + gtB.name());
				valueStr = valueStr + String.format("|%-11d", results.get(gtA).get(gtB));
			}
		}
		
		System.out.println("*mmmmmmmmmmmmmmmmmmmmmmm---Mating Table---mmmmmmmmmmmmmmmmmmmmmmmmmmmmmm*");
		System.out.print(pairingStr +"|\n");	
		System.out.print(valueStr + "|\n");	
		System.out.println("*mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm*");
	}
	
	private void printPairings_indent(HashMap<Genotype, HashMap<Genotype, Integer>> results) {
		
		String pairingStr = "     ";
		String valueStr = "     ";
		
		for (Genotype gtA : Genotype.getValues()) {
			for (Genotype gtB : Genotype.getValues()) {
				if (!Utilities.isValidPairing(gtA, gtB)) {continue;}					
				pairingStr = pairingStr + String.format("|%-11s", gtA.name() + "x" + gtB.name());
				valueStr = valueStr + String.format("|%-11d", results.get(gtA).get(gtB));
			}
		}

		System.out.println("     *mmmmmmmmmmmmmmmmmmmmmmm---Mating Table---mmmmmmmmmmmmmmmmmmmmmmmmmmmmmm*");
		System.out.print(pairingStr +"|\n");	
		System.out.print(valueStr + "|\n");	
		System.out.println("     *mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm*");
	}
	
	private void printPref(HashMap<Genotype, Double> start, HashMap<Genotype, Double> end, Genotype gt1) {
		
		String prefInterval = "|Prob. Interval ";
		String genotypes = "|               ";
		
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-15s", gt.toString());
			prefInterval += String.format("|%1$.4f-%2$.4f  ", start.get(gt), end.get(gt));
		}

		System.out.println("*ppppppppppppp---Preference Table for " + gt1.toString() +"---ppppppppppppppppppppp*");
		System.out.print(genotypes + "|\n" + prefInterval + "|\n");
		System.out.println("*ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp*");
	}
	
	private void printPref_indent(HashMap<Genotype, Double> start, HashMap<Genotype, Double> end, Genotype gt1) {
		String prefInterval = "     |Prob. Interval ";
		String genotypes = "     |               ";
		
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-15s", gt.toString());
			prefInterval += String.format("|%1$.4f-%2$.4f  ", start.get(gt), end.get(gt));
		}
		
		System.out.println("     *ppppppppppppp---Preference Table for " + gt1.toString() +"---ppppppppppppppppppppp*");
		System.out.print(genotypes + "|\n" + prefInterval + "|\n");
		System.out.println("     *ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp*");
	}
	
	private void printGenoInfo(HashMap<Genotype, Integer> subpopSizes, HashMap<Genotype, Double> probabilities, HashMap<Genotype, Double> subProb) {
		String genoNum =   "|Number         ";
		String genoRatio = "|Prob. Interval ";
		String genotypes = "|               ";
		
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-15s", gt.toString());
			genoNum += String.format("|%-15d",subpopSizes.get(gt));
			genoRatio += String.format("|%1$.4f-%2$.4f  ", subProb.get(gt), probabilities.get(gt));
		}
		
		System.out.println("*ggggggggggggggggg---Genotype Information---gggggggggggggggggggg*");
		System.out.print(genotypes + "|\n" + genoNum + "|\n" + genoRatio + "|\n");
		System.out.println("*ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg*");
	}
	
	private void printGenoInfo_indent(HashMap<Genotype, Integer> subpopSizes, HashMap<Genotype, Double> probabilities, HashMap<Genotype, Double> subProb) {
		String genoNum =   "     |Number         ";
		String genoRatio = "     |Prob. Interval ";
		String genotypes = "     |               ";
		
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-15s", gt.toString());
			genoNum += String.format("|%-15d",subpopSizes.get(gt));
			genoRatio += String.format("|%1$.4f-%2$.4f  ", subProb.get(gt), probabilities.get(gt));
		}

		System.out.println("     *ggggggggggggggggg---Genotype Information---gggggggggggggggggggg*");
		System.out.print(genotypes + "|\n" + genoNum + "|\n" + genoRatio + "|\n");
		System.out.println("     *ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg*");
	}

	
	private void generateOffspring(GenerationRecord current, HashMap<Genotype, HashMap<Genotype, Integer>> pairings) {
		HashMap<Genotype, Double> offspring = new HashMap<Genotype, Double>();
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		// initialize the young generation
		
		for (Genotype gt : Genotype.getValues()) {
			offspring.put(gt, 0.0);
		}
		
		for (Genotype gt1 : Genotype.getValues()) {
			for (Genotype gt2 : Genotype.getValues()) {
				// if gt1xgt2 is not a valid pair or there is no pair of gt1xgt2 chosen in the mating process, skip
				if (!Utilities.isValidPairing(gt1, gt2)) continue;
				if (pairings.get(gt1).get(gt2) == 0) continue;
				
				//------------------------------------------------------------------------
				//------------------------------------------------------------------------
				if (DEBUG_REPRO && populationID == 0) {
					System.out.println();
					printPairings_indent(pairings);
					System.out.println();
				}
				//------------------------------------------------------------------------
				//------------------------------------------------------------------------
				
				for (Genotype off : Utilities.getOffspringGenotypes(gt1, gt2)) {
					double numOfMates = pairings.get(gt1).get(gt2);
					if (numOfMates != 0) {
						// questioning the mean and standard deviation...
						double mean = (sp.getReproductionRate(gt1) + sp.getReproductionRate(gt2)) * 0.25;
						double sd = mean / Math.sqrt(numOfMates);
						double random_co = Utilities.nextGaussianRand(INTERNAL_RNG, REPRODUCTION_MEAN, REPRODUCTION_STDDEV);
						double off_num = mean * numOfMates * random_co;
						offspring.put(off, offspring.get(off) + off_num);
						//------------------------------------------------------------------------
						//------------------------------------------------------------------------
						if (DEBUG_REPRO && populationID == 0) {
							System.out.print("  ");
							System.out.println("[ " +gt1.toString() +" x "+  gt2.toString() + " => "+ off.toString()+" ]");
							System.out.print("     ");
							System.out.println(gt1.toString() + " repror8: " + sp.getReproductionRate(gt1));
							System.out.print("     ");
							System.out.println(gt2.toString() + " repror8: " + sp.getReproductionRate(gt2));
							System.out.print("     ");
							System.out.println("average number of off: " + mean);
							System.out.print("     ");
							System.out.println("number of mates: " + numOfMates);
							System.out.print("     ");
							System.out.println("standard deviation: " + sd);
							System.out.print("     ");
							System.out.println("random coefficient: " + random_co);
							System.out.print("     ");
							System.out.println("number of " + off.toString() + " added to the young gen: "+ off_num);
							System.out.println();
							printOffspring_indent(offspring);
							System.out.println();
						}
						//------------------------------------------------------------------------					
						//------------------------------------------------------------------------
					}
					else {
						//------------------------------------------------------------------------
						//------------------------------------------------------------------------
						if (DEBUG_REPRO && populationID == 0) {
							System.out.print("  ");
							System.out.println("[ " +gt1.toString() +" x "+  gt2.toString() + " => "+ off.toString()+" ]");
							System.out.print("     ");
							System.out.println("0 mate will generate 0 offspring.");
							System.out.println();
							printOffspring_indent(offspring);
							System.out.println();
						}
						//------------------------------------------------------------------------
						//------------------------------------------------------------------------
					}
				}
			}
		}
		
		for (Genotype gt : Genotype.getValues()) {
			current.setBirths(gt, (int)Math.round(offspring.get(gt)));
			current.setGenotypeSubpopulationSize(gt, (int)Math.round(offspring.get(gt)));
		}
		//------------------------------------------------------------------------
		//------------------------------------------------------------------------
		if (DEBUG_REPRO && populationID == 0) {
			System.out.println();
			System.out.println();
			System.out.println("results after rounding...");
			printGenoNum_indent(current);
		}
		//------------------------------------------------------------------------
		//------------------------------------------------------------------------
		
	}
	
	
	
	private void generateOffspring_binomial(GenerationRecord current, 
			HashMap<Genotype, HashMap<Genotype, Integer>> pairings) {
		HashMap<Genotype, Double> offspring = new HashMap<Genotype, Double>();
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		// initialize the young generation
		for (Genotype gt : Genotype.getValues()) {
			offspring.put(gt, 0.0);
		}
		
		for (Genotype gt1 : Genotype.getValues()) {
			for (Genotype gt2 : Genotype.getValues()) {
				// if gt1xgt2 is not a valid pair or there is no pair of gt1xgt2 chosen in the mating process, skip
				if (!Utilities.isValidPairing(gt1, gt2)) continue;
				if (pairings.get(gt1).get(gt2) == 0) continue;
				
				//------------------------------------------------------------------------
				//------------------------------------------------------------------------
				if (DEBUG_REPRO && populationID == 0) {
					System.out.println();
					printPairings_indent(pairings);
					System.out.println();
				}
				//------------------------------------------------------------------------
				//------------------------------------------------------------------------
				
				for (Genotype off : Utilities.getOffspringGenotypes(gt1, gt2)) {
					double numOfMates = pairings.get(gt1).get(gt2);
					if (numOfMates != 0) {
						// questioning the mean and standard deviation...
						int n = (int)(sp.getReproductionRate(gt1) + sp.getReproductionRate(gt2));
						double p = 0.25;
						int off_num = 0;
						for(int i = 0 ; i < numOfMates; i++) {
							off_num += Utilities.getBinomial(INTERNAL_RNG, n, p);
						}
						offspring.put(off, offspring.get(off) + off_num);
						//------------------------------------------------------------------------
						//------------------------------------------------------------------------
						if (DEBUG_REPRO && populationID == 0) {
							System.out.print("  ");
							System.out.println("[ " +gt1.toString() +" x "+  gt2.toString() + " => "+ off.toString()+" ]");
							System.out.print("     ");
							System.out.println(gt1.toString() + " repror8: " + sp.getReproductionRate(gt1));
							System.out.print("     ");
							System.out.println(gt2.toString() + " repror8: " + sp.getReproductionRate(gt2));
							System.out.print("     ");
							System.out.println("expected # of offspring per pair: " + n);
							System.out.print("     ");
							System.out.println("number of mates: " + numOfMates);
							System.out.print("     ");
							System.out.println("number of " + off.toString() + " added to the young gen: "+ off_num);
							System.out.println();
							printOffspring_indent(offspring);
							System.out.println();
						}
						//------------------------------------------------------------------------					
						//------------------------------------------------------------------------
					}
					else {
						//------------------------------------------------------------------------
						//------------------------------------------------------------------------
						if (DEBUG_REPRO && populationID == 0) {
							System.out.print("  ");
							System.out.println("[ " +gt1.toString() +" x "+  gt2.toString() + " => "+ off.toString()+" ]");
							System.out.print("     ");
							System.out.println("0 mate will generate 0 offspring.");
							System.out.println();
							printOffspring_indent(offspring);
							System.out.println();
						}
						//------------------------------------------------------------------------
						//------------------------------------------------------------------------
					}
				}
			}
		}
		
		for (Genotype gt : Genotype.getValues()) {
			current.setBirths(gt, (int)Math.round(offspring.get(gt)));
			current.setGenotypeSubpopulationSize(gt, (int)Math.round(offspring.get(gt)));
		}
		//------------------------------------------------------------------------
		//------------------------------------------------------------------------
		if (DEBUG_REPRO && populationID == 0) {
			System.out.println();
			System.out.println();
			System.out.println("results after rounding...");
			printGenoNum_indent(current);
		}
		//------------------------------------------------------------------------
		//------------------------------------------------------------------------
		
	}
	
//	private void generateOffspring_poisson(GenerationRecord current, 
//			HashMap<Genotype, HashMap<Genotype, Integer>> pairings) {
//		HashMap<Genotype, Double> offspring = new HashMap<Genotype, Double>();
//		SessionParameters sp = DataManager.getInstance().getSessionParams();
//		// initialize the young generation
//		for (Genotype gt : Genotype.getValues()) {
//			offspring.put(gt, 0.0);
//		}
//		
//		for (Genotype gt1 : Genotype.getValues()) {
//			for (Genotype gt2 : Genotype.getValues()) {
//				// if gt1xgt2 is not a valid pair or there is no pair of gt1xgt2 chosen in the mating process, skip
//				if (!Utilities.isValidPairing(gt1, gt2)) continue;
//				if (pairings.get(gt1).get(gt2) == 0) continue;
//				
//				//------------------------------------------------------------------------
//				//------------------------------------------------------------------------
//				if (DEBUG_REPRO && populationID == 0) {
//					System.out.println();
//					printPairings_indent(pairings);
//					System.out.println();
//				}
//				//------------------------------------------------------------------------
//				//------------------------------------------------------------------------
//				
//				for (Genotype off : Utilities.getOffspringGenotypes(gt1, gt2)) {
//					double numOfMates = pairings.get(gt1).get(gt2);
//					if (numOfMates != 0) {
//						// questioning the mean and standard deviation...
//						double lambda = (sp.getReproductionRate(gt1) + sp.getReproductionRate(gt2)) * 0.25;
//						int off_num = 0;
//						for(int i = 0 ; i < numOfMates; i++) {
//							off_num += Utilities.getPoisson(lambda);
//						}
//						offspring.put(off, offspring.get(off) + off_num);
//						//------------------------------------------------------------------------
//						//------------------------------------------------------------------------
//						if (DEBUG_REPRO && populationID == 0) {
//							System.out.print("  ");
//							System.out.println("[ " +gt1.toString() +" x "+  gt2.toString() + " => "+ off.toString()+" ]");
//							System.out.print("     ");
//							System.out.println(gt1.toString() + " repror8: " + sp.getReproductionRate(gt1));
//							System.out.print("     ");
//							System.out.println(gt2.toString() + " repror8: " + sp.getReproductionRate(gt2));
//							System.out.print("     ");
//							System.out.println("expected # of offspring" + off.toString() + " per pair: " + lambda);
//							System.out.print("     ");
//							System.out.println("number of mates: " + numOfMates);
//							System.out.print("     ");
//							System.out.println("number of " + off.toString() + " added to the young gen: "+ off_num);
//							System.out.println();
//							printOffspring_indent(offspring);
//							System.out.println();
//						}
//						//------------------------------------------------------------------------					
//						//------------------------------------------------------------------------
//					}
//					else {
//						//------------------------------------------------------------------------
//						//------------------------------------------------------------------------
//						if (DEBUG_REPRO && populationID == 0) {
//							System.out.print("  ");
//							System.out.println("[ " +gt1.toString() +" x "+  gt2.toString() + " => "+ off.toString()+" ]");
//							System.out.print("     ");
//							System.out.println("0 mate will generate 0 offspring.");
//							System.out.println();
//							printOffspring_indent(offspring);
//							System.out.println();
//						}
//						//------------------------------------------------------------------------
//						//------------------------------------------------------------------------
//					}
//				}
//			}
//		}
//		
//		for (Genotype gt : Genotype.getValues()) {
//			current.setBirths(gt, (int)Math.round(offspring.get(gt)));
//			current.setGenotypeSubpopulationSize(gt, (int)Math.round(offspring.get(gt)));
//		}
//		//------------------------------------------------------------------------
//		//------------------------------------------------------------------------
//		if (DEBUG_REPRO && populationID == 0) {
//			System.out.println();
//			System.out.println();
//			System.out.println("results after rounding...");
//			printGenoNum_indent(current);
//		}
//		//------------------------------------------------------------------------
//		//------------------------------------------------------------------------
//		
//	}
	
	private void printOffspring(HashMap<Genotype, Double> offspring) {
		String number = "|Num ";
		String genotypes = "|    ";
		
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-10s", gt.toString());
			number += String.format("|%-10.4f", offspring.get(gt));
		}
		
		System.out.println("*oooooooo---Offspring Table---oooooooo*");
		System.out.print(genotypes + "|\n" + number + "|\n");
		System.out.println("*ooooooooooooooooooooooooooooooooooooo*");
	}
	
	private void printOffspring_indent(HashMap<Genotype, Double> offspring) {
		String number = "     |Num ";
		String genotypes = "     |    ";
		
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-10s", gt.toString());
			number += String.format("|%-10.4f", offspring.get(gt));
		}
		
		System.out.println("     *oooooooo---Offspring Table---oooooooo*");
		System.out.print(genotypes + "|\n" + number + "|\n");
		System.out.println("     *ooooooooooooooooooooooooooooooooooooo*");
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
	 * @author candicezhao
	 */
	private void survive(GenerationRecord current) {

		int numSurvived, subPopulation;
		int totalAdults = 0; 
		double crash;
		final SessionParameters sp = DataManager.getInstance().getSessionParams();

		if (DEBUG_SURVIVAL && populationID == 0) {
			System.out.println();
			printGenoNum_indent(current);
			System.out.println();
		}
		
		//Calculate the number of each genotype surviving
		for (Genotype gt: Genotype.getValues()) {
			subPopulation = current.getGenotypeSubpopulationSize(gt);
			//Typecasting to int in java is analogous to flooring
			double random_coefficient = Utilities.nextGaussianRand(INTERNAL_RNG, SURVIVAL_MEAN, SURVIVAL_STDDEV);
			numSurvived = (int)Math.round(random_coefficient * subPopulation * sp.getSurvivalRate(gt));

			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
			if (DEBUG_SURVIVAL && populationID == 0) {
				System.out.println("     [ " + gt.toString() + " ]");
				System.out.print("       ");
				System.out.println("random coefficient: " +random_coefficient);
				System.out.print("       ");
				System.out.println("number of genotype " + gt.toString() + ": " + subPopulation);
				System.out.print("       ");
				System.out.println("survival rate of genotype " + gt.toString() + ": " + sp.getSurvivalRate(gt));
				System.out.print("       ");
				System.out.println("number of " + gt.toString() + " survived: " + numSurvived);
			}
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
			
			
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
		
		//----------------------------------------------------------------------------
		//----------------------------------------------------------------------------
		if (DEBUG_SURVIVAL && populationID == 0) {
			System.out.println();
			System.out.println("     RESULT POP: ");
			printGenoNum_indent(current);
		}
		//----------------------------------------------------------------------------
		//----------------------------------------------------------------------------
		
		//Kill off populations if larger than carrying capacity
		if (totalAdults > sp.getPopCapacity()) {
			crash = (double)(sp.getCrashCapacity()) / (double)(totalAdults);
			for (Genotype gt: Genotype.getValues()) {
				current.setGenotypeSubpopulationSize(gt, (int)(current.getGenotypeSubpopulationSize(gt) * crash));
			}
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
			if (DEBUG_SURVIVAL && populationID == 0) {
				System.out.println();
				System.out.print("     ");
				System.out.println("Scale the subpop sizes since pop. size is larger than carrying capacity.");
				printGenoNum_indent(current);
			}
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------		
		}
		
	}
	
	/**
	 * Simulates survival of members of a population.  Each allele combination has a
	 * survival rate that will determine how many live/die, modified by a random number
	 * generated by a binomial distribution.
	 * If the population goes over the populationCapacity, we set the population down
	 * to a crashCapacity level.
	 * 
	 *
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by survive() to reflect death
	 *
	 * @author candicezhao
	 */
	private void survive_binomial(GenerationRecord current) {

		int numSurvived;
		int totalAdults = 0; 
		double crash;
		final SessionParameters sp = DataManager.getInstance().getSessionParams();

		if (DEBUG_SURVIVAL && populationID == 0) {
			System.out.println();
			printGenoNum_indent(current);
			System.out.println();
		}
		
		//Calculate the number of each genotype surviving
		for (Genotype gt: Genotype.getValues()) {
			//Typecasting to int in java is analogous to flooring
			double p = sp.getSurvivalRate(gt);
			int n = current.getGenotypeSubpopulationSize(gt);
			numSurvived = Utilities.getBinomial(INTERNAL_RNG, n, p);

			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
			if (DEBUG_SURVIVAL && populationID == 0) {
				System.out.println("     [ " + gt.toString() + " ]");
				System.out.print("       ");
				System.out.println("number of genotype " + gt.toString() + ": " + n);
				System.out.print("       ");
				System.out.println("survival rate of genotype " + gt.toString() + ": " + p);
				System.out.print("       ");
				System.out.println("number of " + gt.toString() + " survived: " + numSurvived);
			}
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
			
		
			if (numSurvived > n){
				numSurvived = n;
			}
			
			
			current.setGenotypeSubpopulationSize(gt, numSurvived);
			current.setDeaths(gt, n - numSurvived);
			totalAdults += numSurvived;
		}
		
		//----------------------------------------------------------------------------
		//----------------------------------------------------------------------------
		if (DEBUG_SURVIVAL && populationID == 0) {
			System.out.println();
			System.out.println("     RESULT POP: ");
			printGenoNum_indent(current);
		}
		//----------------------------------------------------------------------------
		//----------------------------------------------------------------------------
		
		//Kill off populations if larger than carrying capacity
		if (totalAdults > sp.getPopCapacity()) {
			crash = (double)(sp.getCrashCapacity()) / (double)(totalAdults);
			for (Genotype gt: Genotype.getValues()) {
				current.setGenotypeSubpopulationSize(gt, (int)(current.getGenotypeSubpopulationSize(gt) * crash));
			}
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
			if (DEBUG_SURVIVAL && populationID == 0) {
				System.out.println();
				System.out.print("     ");
				System.out.println("Scale the subpop sizes since pop. size is larger than carrying capacity.");
				printGenoNum_indent(current);
			}
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------		
		}
		
	}
	
	
	/**
	 * Simulates survival of members of a population.  Each allele combination has a
	 * survival rate that will determine how many live/die, modified by a random number
	 * generated by a poisson distribution.
	 * If the population goes over the populationCapacity, we set the population down
	 * to a crashCapacity level.
	 * 
	 *
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by survive() to reflect death
	 *
	 * @author candicezhao
	 */
//	private void survive_poisson(GenerationRecord current) {
//
//		int numSurvived;
//		int totalAdults = 0; 
//		double crash;
//		final SessionParameters sp = DataManager.getInstance().getSessionParams();
//
//		if (DEBUG_SURVIVAL && populationID == 0) {
//			System.out.println();
//			printGenoNum_indent(current);
//			System.out.println();
//		}
//		
//		//Calculate the number of each genotype surviving
//		for (Genotype gt: Genotype.getValues()) {
//			//Typecasting to int in java is analogous to flooring
//			int n = current.getGenotypeSubpopulationSize(gt);
//			double lambda = sp.getSurvivalRate(gt) * (double) n;
//			numSurvived = Utilities.getPoisson(lambda);
//
//			//----------------------------------------------------------------------------
//			//----------------------------------------------------------------------------
//			if (DEBUG_SURVIVAL && populationID == 0) {
//				System.out.println("     [ " + gt.toString() + " ]");
//				System.out.print("       ");
//				System.out.println("number of genotype " + gt.toString() + ": " + n);
//				System.out.print("       ");
//				System.out.println("survival rate of genotype " + gt.toString() + ": " + sp.getSurvivalRate(gt));
//				System.out.print("       ");
//				System.out.println("expected number of " + gt.toString() + " survived: " + lambda);
//				System.out.print("       ");
//				System.out.println("number of " + gt.toString() + " survived: " + numSurvived);
//			}
//			//----------------------------------------------------------------------------
//			//----------------------------------------------------------------------------
//			
//		
//			if (numSurvived > n){
//				numSurvived = n;
//			}
//			
//			
//			current.setGenotypeSubpopulationSize(gt, numSurvived);
//			current.setDeaths(gt, n - numSurvived);
//			totalAdults += numSurvived;
//		}
//		
//		//----------------------------------------------------------------------------
//		//----------------------------------------------------------------------------
//		if (DEBUG_SURVIVAL && populationID == 0) {
//			System.out.println();
//			System.out.println("     RESULT POP: ");
//			printGenoNum_indent(current);
//		}
//		//----------------------------------------------------------------------------
//		//----------------------------------------------------------------------------
//		
//		//Kill off populations if larger than carrying capacity
//		if (totalAdults > sp.getPopCapacity()) {
//			crash = (double)(sp.getCrashCapacity()) / (double)(totalAdults);
//			for (Genotype gt: Genotype.getValues()) {
//				current.setGenotypeSubpopulationSize(gt, (int)(current.getGenotypeSubpopulationSize(gt) * crash));
//			}
//			//----------------------------------------------------------------------------
//			//----------------------------------------------------------------------------
//			if (DEBUG_SURVIVAL && populationID == 0) {
//				System.out.println();
//				System.out.print("     ");
//				System.out.println("Scale the subpop sizes since pop. size is larger than carrying capacity.");
//				printGenoNum_indent(current);
//			}
//			//----------------------------------------------------------------------------
//			//----------------------------------------------------------------------------		
//		}
//		
//	}

	private void printGenoNum(GenerationRecord record) {
		String genoNum =   "|Number         ";
		String genotypes = "|               ";
		
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-15s", gt.toString());
			genoNum += String.format("|%-15d",record.getGenotypeSubpopulationSize(gt));
		}
		
		System.out.println("*nnnnnnnnnnnnnnnnnnnn---Genotype Number---nnnnnnnnnnnnnnnnnnnnnn*");
		System.out.print(genotypes + "|\n" + genoNum + "|\n");
		System.out.println("*nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn*");
	}
	
	private void printGenoNum_indent(GenerationRecord record) {
		String genoNum =   "     |Number         ";
		String genotypes = "     |               ";
		
		for (Genotype gt : Genotype.getValues())
		{
			genotypes += String.format("|%-15s", gt.toString());
			genoNum += String.format("|%-15d",record.getGenotypeSubpopulationSize(gt));
		}
		
		System.out.println("     *nnnnnnnnnnnnnnnnnnnn---Genotype Number---nnnnnnnnnnnnnnnnnnnnnn*");
		System.out.print(genotypes + "|\n" + genoNum + "|\n");
		System.out.println("     *nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn*");
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
	 * @author candicezhao
	 */
	private void mutate(GenerationRecord current) {

		final SessionParameters sp = DataManager.getInstance().getSessionParams();

		// Containers to hold temporary values used more than once
		int totalMutations;
		double rawMutations;
		int numMutations;
		int adjustedMutations;
		double ratio;
		HashMap<Genotype, Integer> contrib;

		// For all possible combinations of genotypes...
		for (Genotype from : Genotype.getValues()) {
			contrib = new HashMap<Genotype, Integer>();
			totalMutations = 0;
			
			if (DEBUG_MUTATION && populationID == 0) {
				System.out.println();
				printGenoNum_indent(current);
			}
			
			for (Genotype to : Genotype.getValues()) {
				double random_co = Utilities.nextGaussianRand(INTERNAL_RNG, MUTATION_MEAN, MUTATION_STDDEV);
				// Produce a random number with a mean of MUTATION_MEAN (usually 1.0) and a standard deviation of
				// MUTATION_STDDEV and multiply that by the expected average number of mutations
				rawMutations = random_co * current.getGenotypeSubpopulationSize(from) * sp.getMutationRate(from, to);
				numMutations = (int)Math.round(rawMutations);

				// Ensure rng did not produce negative value
				if (numMutations < 0) numMutations = 0;

				totalMutations += numMutations;
				contrib.put(to, numMutations);
				
				//------------------------------------------------------------
				//------------------------------------------------------------
				if (DEBUG_MUTATION && populationID == 0) {
					System.out.println();
					System.out.print("     ");
					System.out.println("[ " + from.toString() + " -> " + to.toString() + " ]");
					System.out.print("        ");
					System.out.println("mutation rate from " + from.toString() + " to " + to.toString() 
										+ ": " + sp.getMutationRate(from, to));
					System.out.print("        ");
					System.out.println("random coefficient for " + to.toString() + ": " + random_co);
					System.out.print("        ");
					System.out.println("raw number of " + from.toString() + " -> " + to.toString() + 
							": " + rawMutations);
					System.out.print("        ");
					System.out.println("rounded number of " + from.toString() + " -> " + to.toString() + 
							"(before adjustment) : " + numMutations);
				}
				//------------------------------------------------------------
				//------------------------------------------------------------
			}

			// If no mutations happened, move on to the next genotype
			if (totalMutations == 0) 
			{
				for (Genotype to : Genotype.getValues()) {
					current.setMutationCount(from, to, 0);
				}
				continue;
			}

			// Ratio to scale mutations by to keep population size constant
			ratio = (double) current.getGenotypeSubpopulationSize(from) / (double)totalMutations;
			
			//------------------------------------------------------------
			//------------------------------------------------------------
			if (DEBUG_MUTATION && populationID == 0) {
				System.out.println();
				mutationTable_indent(contrib, from);
				System.out.println("     scale mutations to keep population size constant");
			}
			//------------------------------------------------------------
			//------------------------------------------------------------
			
			for (Genotype to : Genotype.getValues()) {
				
				// Scale mutation count appropriately
				adjustedMutations = (int)Math.round(ratio * contrib.get(to));

				// Adjust subpopulation counts
				current.setMutationCount(from, to, adjustedMutations);
				current.setGenotypeSubpopulationSize(from, current.getGenotypeSubpopulationSize(from) - adjustedMutations);
				current.setGenotypeSubpopulationSize(to, current.getGenotypeSubpopulationSize(to) + adjustedMutations);

				//----------------------------------------------------------------------------
				//----------------------------------------------------------------------------
				if (DEBUG_MUTATION && populationID == 0) {
					System.out.print("        ");
					System.out.println("number of " + from.toString() + " -> " + to.toString() + 
							"(after adjustment): " + adjustedMutations);
				}
				//----------------------------------------------------------------------------
				//----------------------------------------------------------------------------
			}
			
			if (DEBUG_MUTATION && populationID == 0) {
				System.out.println();
				printGenoNum_indent(current);
				System.out.println("    -------------------------------------------------------------------------");
			}

		}
	}

	/**
	 * Simulates mutations within a population. Each allele combination has a
	 * mutation rate that will determine how many will mutate, modified by a 
	 * random number generated by a binomial distribution.
	 *
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by mutate() to reflect mutations
	 *
	 * @author candicezhao
	 */
	private void mutate_binomial(GenerationRecord current) {

		final SessionParameters sp = DataManager.getInstance().getSessionParams();

		// Containers to hold temporary values used more than once
		int totalMutations;
		double rawMutations;
		int numMutations;
		int adjustedMutations;
		double ratio;
		HashMap<Genotype, Integer> contrib;

		// For all possible combinations of genotypes...
		for (Genotype from : Genotype.getValues()) {
			contrib = new HashMap<Genotype, Integer>();
			totalMutations = 0;
			
			if (DEBUG_MUTATION && populationID == 0) {
				System.out.println();
				printGenoNum_indent(current);
			}
			
			for (Genotype to : Genotype.getValues()) {
				int n = current.getGenotypeSubpopulationSize(from);
				double p = sp.getMutationRate(from, to);
				rawMutations = Utilities.getBinomial(INTERNAL_RNG, n, p);
				numMutations = (int)Math.round(rawMutations);

				// Ensure rng did not produce negative value
				if (numMutations < 0) numMutations = 0;

				totalMutations += numMutations;
				contrib.put(to, numMutations);
				
				//------------------------------------------------------------
				//------------------------------------------------------------
				if (DEBUG_MUTATION && populationID == 0) {
					System.out.println();
					System.out.print("     ");
					System.out.println("[ " + from.toString() + " -> " + to.toString() + " ]");
					System.out.print("        ");
					System.out.println("mutation rate from " + from.toString() + " to " + to.toString() 
										+ ": " + sp.getMutationRate(from, to));
					System.out.print("        ");
					System.out.println("raw number of " + from.toString() + " -> " + to.toString() + 
							": " + rawMutations);
					System.out.print("        ");
					System.out.println("rounded number of " + from.toString() + " -> " + to.toString() + 
							"(before adjustment) : " + numMutations);
				}
				//------------------------------------------------------------
				//------------------------------------------------------------
			}

			// If no mutations happened, move on to the next genotype
			if (totalMutations == 0) 
			{
				for (Genotype to : Genotype.getValues()) {
					current.setMutationCount(from, to, 0);
				}
				continue;
			}

			// Ratio to scale mutations by to keep population size constant
			ratio = (double) current.getGenotypeSubpopulationSize(from) / (double)totalMutations;
			
			//------------------------------------------------------------
			//------------------------------------------------------------
			if (DEBUG_MUTATION && populationID == 0) {
				System.out.println();
				mutationTable_indent(contrib, from);
				System.out.println("     scale mutations to keep population size constant");
			}
			//------------------------------------------------------------
			//------------------------------------------------------------
			
			for (Genotype to : Genotype.getValues()) {
				//if (to == from) continue;

				// Scale mutation count appropriately
				adjustedMutations = (int)Math.round(ratio * contrib.get(to));

				// Adjust subpopulation counts
				current.setMutationCount(from, to, adjustedMutations);
				current.setGenotypeSubpopulationSize(from, current.getGenotypeSubpopulationSize(from) - adjustedMutations);
				current.setGenotypeSubpopulationSize(to, current.getGenotypeSubpopulationSize(to) + adjustedMutations);

				//----------------------------------------------------------------------------
				//----------------------------------------------------------------------------
				if (DEBUG_MUTATION && populationID == 0) {
					System.out.print("        ");
					System.out.println("number of " + from.toString() + " -> " + to.toString() + 
							"(after adjustment): " + adjustedMutations);
				}
				//----------------------------------------------------------------------------
				//----------------------------------------------------------------------------
			}
			
			if (DEBUG_MUTATION && populationID == 0) {
				System.out.println();
				printGenoNum_indent(current);
				System.out.println("    -------------------------------------------------------------------------");
			}

		}
	}
	
	/**
	 * Simulates mutations within a population. Each allele combination has a
	 * mutation rate that will determine how many will mutate, modified by a 
	 * random number generated by a poisson distribution.
	 *
	 * @param previous GenerationRecord representing the previous generation
	 *                 used to calculate data about the new generation
	 * @param current  GenerationRecord representing the current generation,
	 *                 will be modified by mutate() to reflect mutations
	 *
	 * @author candicezhao
	 */
//	private void mutate_poisson(GenerationRecord current) {
//
//		final SessionParameters sp = DataManager.getInstance().getSessionParams();
//
//		// Containers to hold temporary values used more than once
//		int totalMutations;
//		double rawMutations;
//		int numMutations;
//		int adjustedMutations;
//		double ratio;
//		HashMap<Genotype, Integer> contrib;
//
//		// For all possible combinations of genotypes...
//		for (Genotype from : Genotype.getValues()) {
//			contrib = new HashMap<Genotype, Integer>();
//			totalMutations = 0;
//			
//			if (DEBUG_MUTATION && populationID == 0) {
//				System.out.println();
//				printGenoNum_indent(current);
//			}
//			
//			for (Genotype to : Genotype.getValues()) {
//				double lambda = current.getGenotypeSubpopulationSize(from) * sp.getMutationRate(from, to);
//				rawMutations = Utilities.getPoisson(lambda);
//				numMutations = (int)Math.round(rawMutations);
//
//				// Ensure rng did not produce negative value
//				if (numMutations < 0) numMutations = 0;
//
//				totalMutations += numMutations;
//				contrib.put(to, numMutations);
//				
//				//------------------------------------------------------------
//				//------------------------------------------------------------
//				if (DEBUG_MUTATION && populationID == 0) {
//					System.out.println();
//					System.out.print("     ");
//					System.out.println("[ " + from.toString() + " -> " + to.toString() + " ]");
//					System.out.print("        ");
//					System.out.println("mutation rate from " + from.toString() + " to " + to.toString() 
//										+ ": " + sp.getMutationRate(from, to));
//					System.out.print("        ");
//					System.out.println("expected number of mutation from " + from.toString() + " to " + to.toString() 
//										+ ": " + lambda);
//					System.out.print("        ");
//					System.out.println("raw number of " + from.toString() + " -> " + to.toString() + 
//							": " + rawMutations);
//					System.out.print("        ");
//					System.out.println("rounded number of " + from.toString() + " -> " + to.toString() + 
//							"(before adjustment) : " + numMutations);
//				}
//				//------------------------------------------------------------
//				//------------------------------------------------------------
//			}
//
//			// If no mutations happened, move on to the next genotype
//			if (totalMutations == 0) 
//			{
//				for (Genotype to : Genotype.getValues()) {
//					current.setMutationCount(from, to, 0);
//				}
//				continue;
//			}
//
//			// Ratio to scale mutations by to keep population size constant
//			ratio = (double) current.getGenotypeSubpopulationSize(from) / (double)totalMutations;
//			
//			//------------------------------------------------------------
//			//------------------------------------------------------------
//			if (DEBUG_MUTATION && populationID == 0) {
//				System.out.println();
//				mutationTable_indent(contrib, from);
//				System.out.println("     scale mutations to keep population size constant");
//			}
//			//------------------------------------------------------------
//			//------------------------------------------------------------
//			
//			for (Genotype to : Genotype.getValues()) {
//				//if (to == from) continue;
//
//				// Scale mutation count appropriately
//				adjustedMutations = (int)Math.round(ratio * contrib.get(to));
//
//				// Adjust subpopulation counts
//				current.setMutationCount(from, to, adjustedMutations);
//				current.setGenotypeSubpopulationSize(from, current.getGenotypeSubpopulationSize(from) - adjustedMutations);
//				current.setGenotypeSubpopulationSize(to, current.getGenotypeSubpopulationSize(to) + adjustedMutations);
//
//				//----------------------------------------------------------------------------
//				//----------------------------------------------------------------------------
//				if (DEBUG_MUTATION && populationID == 0) {
//					System.out.print("        ");
//					System.out.println("number of " + from.toString() + " -> " + to.toString() + 
//							"(after adjustment): " + adjustedMutations);
//				}
//				//----------------------------------------------------------------------------
//				//----------------------------------------------------------------------------
//			}
//			
//			if (DEBUG_MUTATION && populationID == 0) {
//				System.out.println();
//				printGenoNum_indent(current);
//				System.out.println("    -------------------------------------------------------------------------");
//			}
//
//		}
//	}

	private void mutationTable(HashMap<Genotype, Integer> contrib, Genotype gt) {
		String mutation = "|Mutations      ";
		String genotypes = "|               ";
		
		for (Genotype gt1 : Genotype.getValues())
		{
			genotypes += String.format("|%-10s", gt1.toString());
			mutation += String.format("|%-10d", contrib.get(gt1));
		}

		System.out.println("*mmmmmmmmmm---Mutation Table for " + gt.toString() +"---mmmmmmmmmm*");
		System.out.print(genotypes + "|\n" + mutation + "|\n");
		System.out.println("*mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm*");
	}
	
	private void mutationTable_indent(HashMap<Genotype, Integer> contrib, Genotype gt) {
		String mutation = "     |Mutations      ";
		String genotypes = "     |               ";
		
		for (Genotype gt1 : Genotype.getValues())
		{
			genotypes += String.format("|%-10s", gt1.toString());
			mutation += String.format("|%-10d", contrib.get(gt1));
		}
		
		System.out.println("     *mmmmmmmmmm---Mutation Table for " + gt.toString() +"---mmmmmmmmmm*");
		System.out.print(genotypes + "|\n" + mutation + "|\n");
		System.out.println("     *mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm*");
	}
	
	
}






