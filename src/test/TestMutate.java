package test;
import test.TestResources;
import java.lang.reflect.Method;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.GenerationRecord;
import simulation.Population;

public class TestMutate {
	final static double MUTATION_A_B = 0.01;
	final static double MUTATION_B_A = 0.00;

	final static int SUBPOPSIZE_AA = 100;
	final static int SUBPOPSIZE_AB = 100;
	final static int SUBPOPSIZE_BB = 100;
	
	final static int GENS_TO_SIMULATE = 1000000;
	
	final static long SEED = (new Random()).nextLong();

	
	final static double MUTATION_A_A = 1 - MUTATION_A_B;
	final static double MUTATION_B_B = 1 - MUTATION_B_A;
	final static double MUTATION_RATE_AA_TO_AA = Math.pow(MUTATION_A_A, 2);
	final static double MUTATION_RATE_BB_TO_BB = Math.pow(MUTATION_B_B, 2);
	final static double MUTATION_RATE_AB_TO_AB = MUTATION_A_A * MUTATION_B_B + 2 * MUTATION_A_B * MUTATION_B_A; 
	final static double MUTATION_RATE_AA_TO_AB = 2 * MUTATION_A_B * MUTATION_A_A;
	final static double MUTATION_RATE_AA_TO_BB = Math.pow(MUTATION_A_B, 2);
	final static double MUTATION_RATE_AB_TO_AA = MUTATION_B_A * MUTATION_A_A;
	final static double MUTATION_RATE_AB_TO_BB = MUTATION_A_B * MUTATION_B_B;
	final static double MUTATION_RATE_BB_TO_AA = Math.pow(MUTATION_B_A, 2);
	final static double MUTATION_RATE_BB_TO_AB = 2 * MUTATION_B_A * MUTATION_B_B;
	
		
	public static void main(String[] args) {
		TestResources.init();
		
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		sp.setMutationRate(Genotype.AA, Genotype.AB, MUTATION_RATE_AA_TO_AB);
		sp.setMutationRate(Genotype.AA, Genotype.BB, MUTATION_RATE_AA_TO_BB);
		sp.setMutationRate(Genotype.AB, Genotype.AA, MUTATION_RATE_AB_TO_AA);
		sp.setMutationRate(Genotype.AB, Genotype.BB, MUTATION_RATE_AB_TO_BB);
		sp.setMutationRate(Genotype.BB, Genotype.AA, MUTATION_RATE_BB_TO_AA);
		sp.setMutationRate(Genotype.BB, Genotype.AB, MUTATION_RATE_BB_TO_AB);
		sp.setMutationRate(Genotype.AA, Genotype.AA, MUTATION_RATE_AA_TO_AA);
		sp.setMutationRate(Genotype.BB, Genotype.BB, MUTATION_RATE_BB_TO_BB);
		sp.setMutationRate(Genotype.AB, Genotype.AB, MUTATION_RATE_AB_TO_AB);
		
		GenerationRecord genRecord = new GenerationRecord(0, 0);
		
		genRecord.setGenotypeSubpopulationSize(Genotype.AA, SUBPOPSIZE_AA);
		genRecord.setGenotypeSubpopulationSize(Genotype.AB, SUBPOPSIZE_AB);
		genRecord.setGenotypeSubpopulationSize(Genotype.BB, SUBPOPSIZE_BB);
		
		try {
			Method mutate = Population.class.getDeclaredMethod("mutate", GenerationRecord.class);
			mutate.setAccessible(true);
			
			Population p = new Population(SEED);
			long startTime = System.currentTimeMillis();
			
			for (long i = 0; i < GENS_TO_SIMULATE; i++) {
				mutate.invoke(p, genRecord);
			}
			
			System.out.printf("Simulated %d generations in %fs with %d random numbers.\n", 
					GENS_TO_SIMULATE,
					(System.currentTimeMillis() - startTime) / 1000.0,
					18 * GENS_TO_SIMULATE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Genotype gt : Genotype.values()) {
			System.out.printf("%s: %d\n",gt.toString(),genRecord.getGenotypeSubpopulationSize(gt));
		}
		
	}

}
