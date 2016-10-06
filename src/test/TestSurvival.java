package test;



import java.lang.reflect.Method;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.GenerationRecord;
import simulation.Population;

public class TestSurvival {

	public static void main(String[] args) {
		
		
		SessionParameters sp = new SessionParameters();
		sp.setSurvivalRate(Genotype.AA, 0.85);
		sp.setSurvivalRate(Genotype.AB, 0.90);
		sp.setSurvivalRate(Genotype.BB, 1.00);
		
		sp.setPopSize(300);
		sp.setGenotypeFrequency(Genotype.AA, 0.25);
		sp.setGenotypeFrequency(Genotype.AB, 0.50);
		sp.setGenotypeFrequency(Genotype.BB, 0.25);
		
		sp.setPopCapacity(250);
		sp.setCrashCapacity(200);
		
		DataManager.getInstance().setSessionParams(sp);
		
		
		GenerationRecord ngr = new GenerationRecord(0, 1);
		ngr.setGenotypeSubpopulationSize(Genotype.AA, 100);
		ngr.setGenotypeSubpopulationSize(Genotype.AB, 100);
		ngr.setGenotypeSubpopulationSize(Genotype.BB, 100);
		
		for (Genotype gt1 : Genotype.values()) {
			System.out.println(ngr.getGenerationNumber() + " <-> " + ngr.getGenotypeSubpopulationSize(gt1));
		}
		
		try {
			Method mutate = Population.class.getDeclaredMethod("survive", GenerationRecord.class);
			mutate.setAccessible(true);
			Population p = new Population((new Random().nextLong()));
			for (int i=0; i < 15; i++) {
				mutate.invoke(p, ngr);
				ngr.quickWrite();
				
			}
			//System.out.println("success is always an option");
		}
		catch (Exception e) {
			System.out.println("failure is always an option");
			e.printStackTrace();
		}
		
		
	}

}
