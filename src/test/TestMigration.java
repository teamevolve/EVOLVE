package test;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.PopulationManager;

public class TestMigration {
	public static void main(String[] args) {
		SessionParameters sp = new SessionParameters();
		sp.setMigrationRate(Genotype.AA, 0.25);
		sp.setMigrationRate(Genotype.AB, 0.50);
		sp.setMigrationRate(Genotype.BB, 0.75);
		
		sp.setPopSize(1000000);
		sp.setNumPops(5);
		sp.setGenotypeFrequency(Genotype.AA, 0.15);
		sp.setGenotypeFrequency(Genotype.AB, 0.30);
		sp.setGenotypeFrequency(Genotype.BB, 0.55);
		
		DataManager.getInstance().setSessionParams(sp);

		PopulationManager pm = PopulationManager.getInstance();
		System.out.println("hi");
		for (int i=0; i < 1000; i ++) {
		pm.processMigrations();
		}
		System.out.println("bye");
		
	
	}
}
