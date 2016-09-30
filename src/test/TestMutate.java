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

	public static void main(String[] args) {
		TestResources.init();
		
		
		GenerationRecord ngr = new GenerationRecord(0, 1);
		ngr.setGenotypeSubpopulationSize(Genotype.AA, 100);
		ngr.setGenotypeSubpopulationSize(Genotype.AB, 100);
		ngr.setGenotypeSubpopulationSize(Genotype.BB, 100);
		
		try {
			Method mutate = Population.class.getDeclaredMethod("mutate", GenerationRecord.class);
			mutate.setAccessible(true);
			Population p = new Population((new Random().nextLong()));
			mutate.invoke(p, ngr);
			System.out.println("success is always an option");
		}
		catch (Exception e) {
			System.out.println("failure is always an option");
			e.printStackTrace();
		}
		
		for (Genotype gt1 : Genotype.values()) {
			System.out.println(ngr.getGenotypeSubpopulationSize(gt1));
		}
		
	}

}
