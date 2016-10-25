package test;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;

public class TestResources {
	private static SessionParameters sp = null;
	
	final private static int     POPULATION_SIZE          = 1000;
	final private static int     NUMBER_OF_POPULATIONS    = 500;
	final private static int     SEED                     = 1234567890;
	final private static boolean CONSTANT_POPULATION_SIZE = false;
	final private static boolean FIXED_MIGRATION          = false;	

	final private static double  REPRODUCTION_RATE_AA     = 0.5;
	final private static double  REPRODUCTION_RATE_AB     = 0.5;
	final private static double  REPRODUCTION_RATE_BB     = 0.5;
	
	final private static double  SURVIVAL_RATE_AA         = 0.5;
	final private static double  SURVIVAL_RATE_AB         = 0.5;
	final private static double  SURVIVAL_RATE_BB         = 0.5;
	
	final private static double  ABSOLUTE_FITNESS_AA      = 0.5;
	final private static double  ABSOLUTE_FITNESS_AB      = 0.5;
	final private static double  ABSOLUTE_FITNESS_BB      = 0.5;
	
	final private static double  RELATIVE_FITNESS_AA      = 0.5;
	final private static double  RELATIVE_FITNESS_AB      = 0.5;
	final private static double  RELATIVE_FITNESS_BB      = 0.5;

	final static double MUTATION_A_B = 0.01;
	final static double MUTATION_B_A = 0.01;
	
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
	
	final private static double  MIGRATION_RATE_AA        = 0.2;
	final private static double  MIGRATION_RATE_AB        = 0.2;
	final private static double  MIGRATION_RATE_BB        = 0.2;
	
	final private static double  GENOTYPE_FREQUENCY_AA    = 0.25;
	final private static double  GENOTYPE_FREQUENCY_AB    = 0.25;
	final private static double  GENOTYPE_FREQUENCY_BB    = 0.5;
	
	final private static double  SEXUAL_SELECTION_AA_AA   = 0.25;
	final private static double  SEXUAL_SELECTION_AA_AB   = 0.25;
	final private static double  SEXUAL_SELECTION_AA_BB   = 0.25;
	final private static double  SEXUAL_SELECTION_AB_AA   = 0.25;
	final private static double  SEXUAL_SELECTION_AB_AB   = 0.25;
	final private static double  SEXUAL_SELECTION_AB_BB   = 0.25;
	final private static double  SEXUAL_SELECTION_BB_AA   = 0.25;
	final private static double  SEXUAL_SELECTION_BB_AB   = 0.25;
	final private static double  SEXUAL_SELECTION_BB_BB   = 0.25;
	
	public static void init() {
		sp = new SessionParameters();
		
		sp.setPopSize(POPULATION_SIZE);
		sp.setNumPops(NUMBER_OF_POPULATIONS);
		sp.setSeed(SEED);
		
		sp.setPopConst(CONSTANT_POPULATION_SIZE);
		sp.setFixedMig(FIXED_MIGRATION);
		
		sp.setReproductionRate(Genotype.AA, REPRODUCTION_RATE_AA);
		sp.setReproductionRate(Genotype.AB, REPRODUCTION_RATE_AB);
		sp.setReproductionRate(Genotype.BB, REPRODUCTION_RATE_BB);
		
		sp.setSurvivalRate(Genotype.AA, SURVIVAL_RATE_AA);
		sp.setSurvivalRate(Genotype.AB, SURVIVAL_RATE_AB);
		sp.setSurvivalRate(Genotype.BB, SURVIVAL_RATE_BB);
		
		sp.setAbsoluteFitness(Genotype.AA, ABSOLUTE_FITNESS_AA);
		sp.setAbsoluteFitness(Genotype.AB, ABSOLUTE_FITNESS_AB);
		sp.setAbsoluteFitness(Genotype.BB, ABSOLUTE_FITNESS_BB);
		
		sp.setRelativeFitness(Genotype.AA, RELATIVE_FITNESS_AA);
		sp.setRelativeFitness(Genotype.AB, RELATIVE_FITNESS_AB);
		sp.setRelativeFitness(Genotype.BB, RELATIVE_FITNESS_BB);
		
		sp.setMutationRate(Genotype.AA, Genotype.AB, MUTATION_RATE_AA_TO_AB);
		sp.setMutationRate(Genotype.AA, Genotype.BB, MUTATION_RATE_AA_TO_BB);
		sp.setMutationRate(Genotype.AB, Genotype.AA, MUTATION_RATE_AB_TO_AA);
		sp.setMutationRate(Genotype.AB, Genotype.BB, MUTATION_RATE_AB_TO_BB);
		sp.setMutationRate(Genotype.BB, Genotype.AA, MUTATION_RATE_BB_TO_AA);
		sp.setMutationRate(Genotype.BB, Genotype.AB, MUTATION_RATE_BB_TO_AB);
		sp.setMutationRate(Genotype.AA, Genotype.AA, MUTATION_RATE_AA_TO_AA);
		sp.setMutationRate(Genotype.AB, Genotype.AB, MUTATION_RATE_AB_TO_AB);
		sp.setMutationRate(Genotype.BB, Genotype.BB, MUTATION_RATE_BB_TO_BB);
		
		sp.setMigrationRate(Genotype.AA, MIGRATION_RATE_AA);
		sp.setMigrationRate(Genotype.AB, MIGRATION_RATE_AB);
		sp.setMigrationRate(Genotype.BB, MIGRATION_RATE_BB);
		
		sp.setGenotypeFrequency(Genotype.AA, GENOTYPE_FREQUENCY_AA);
		sp.setGenotypeFrequency(Genotype.AB, GENOTYPE_FREQUENCY_AB);
		sp.setGenotypeFrequency(Genotype.BB, GENOTYPE_FREQUENCY_BB);
		
		sp.setSexualSelectionRate(Genotype.AA, Genotype.AA, SEXUAL_SELECTION_AA_AA);
		sp.setSexualSelectionRate(Genotype.AA, Genotype.AB, SEXUAL_SELECTION_AA_AB);
		sp.setSexualSelectionRate(Genotype.AA, Genotype.BB, SEXUAL_SELECTION_AA_BB);
		sp.setSexualSelectionRate(Genotype.AB, Genotype.AA, SEXUAL_SELECTION_AB_AA);
		sp.setSexualSelectionRate(Genotype.AB, Genotype.AB, SEXUAL_SELECTION_AB_AB);
		sp.setSexualSelectionRate(Genotype.AB, Genotype.BB, SEXUAL_SELECTION_AB_BB);
		sp.setSexualSelectionRate(Genotype.BB, Genotype.AA, SEXUAL_SELECTION_BB_AA);
		sp.setSexualSelectionRate(Genotype.BB, Genotype.AB, SEXUAL_SELECTION_BB_AB);
		sp.setSexualSelectionRate(Genotype.BB, Genotype.BB, SEXUAL_SELECTION_BB_BB);
		
		DataManager.getInstance().setSessionParams(sp);
	}
}
	