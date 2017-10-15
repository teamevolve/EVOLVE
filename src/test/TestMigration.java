package test;

import shared.DataManager;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Random;

import shared.Genotype;
import shared.SessionParameters;
import simulation.Population;
import simulation.PopulationManager;

public class TestMigration {

	final private static double MIGRATION_RATE_AA = 0.1;
	final private static double MIGRATION_RATE_AB = 0.1;
	final private static double MIGRATION_RATE_BB = 0.1;

	final private static double ALLELE_FREQ_A = 0.5;
	final private static double ALLELE_FREQ_B = 1.0 - ALLELE_FREQ_A;

	final private static double GENOTYPE_FREQ_AA = Math.pow(ALLELE_FREQ_A, 2.0);
	final private static double GENOTYPE_FREQ_AB = 2.0 * ALLELE_FREQ_A * ALLELE_FREQ_B;
	final private static double GENOTYPE_FREQ_BB = Math.pow(ALLELE_FREQ_B, 2.0);


	final private static int NUMBER_OF_POPULATIONS = 10;
	final private static int POPULATION_SIZE = 1000;

	final private static long SEED = (new Random()).nextLong();

	//------------------------------------------------------------

	final private static int NUM_ITERATIONS = 100;

	public static void main(String[] args) {
		TestResources.init();
		final SessionParameters sp = DataManager.getInstance().getSessionParams();

		sp.setSeed(SEED);
		sp.setPopSize(POPULATION_SIZE);
		sp.setNumPops(NUMBER_OF_POPULATIONS);

		sp.setMigrationRate(Genotype.AA, MIGRATION_RATE_AA);
		sp.setMigrationRate(Genotype.AB, MIGRATION_RATE_AB);
		sp.setMigrationRate(Genotype.BB, MIGRATION_RATE_BB);

		sp.setGenotypeFrequency(Genotype.AA, GENOTYPE_FREQ_AA);
		sp.setGenotypeFrequency(Genotype.AB, GENOTYPE_FREQ_AB);
		sp.setGenotypeFrequency(Genotype.BB, GENOTYPE_FREQ_BB);

		final PopulationManager popMan = PopulationManager.getInstance();

		for (int i=0; i < NUM_ITERATIONS; i++) {
			popMan.processMigrations();
		}

		try {
			for (Population p : popMan.getPopulationList()) {
					System.out.println(p.getLastGeneration().getPopulationSize());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
