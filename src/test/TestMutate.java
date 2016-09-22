package test;
import test.TestResources;

import java.lang.reflect.Method;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.GenerationRecord;
import simulation.Population;

public class TestMutate extends Population {

	public static void main(String[] args) {
		TestResources.init();
		
		GenerationRecord gr = new GenerationRecord(0,0);
		gr.setGenotypeSubpopulationSize(Genotype.AA, 1000);
		gr.setGenotypeSubpopulationSize(Genotype.AB, 1000);
		gr.setGenotypeSubpopulationSize(Genotype.BB, 1000);
		
		GenerationRecord ngr = new GenerationRecord(0, 1);
		ngr.setGenotypeSubpopulationSize(Genotype.AA, 1000);
		ngr.setGenotypeSubpopulationSize(Genotype.AB, 1000);
		ngr.setGenotypeSubpopulationSize(Genotype.BB, 1000);
		
		try {
			Method mutate = Population.class.getDeclaredMethod("mutate", GenerationRecord.class, GenerationRecord.class);
			mutate.setAccessible(true);
			mutate.invoke(Population.class, gr, ngr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Genotype gt1 : Genotype.values()) {
			System.out.println(ngr.getGenotypeSubpopulationSize(gt1));
		}
		
	}

}
