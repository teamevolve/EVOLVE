package test;

import java.lang.reflect.Method;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.GenerationRecord;
import simulation.Population;

public class TestReproduction {

	final static int SUBPOPSIZE_AA = 100;
	final static int SUBPOPSIZE_AB = 100;
	final static int SUBPOPSIZE_BB = 100;
	
	final static double REPROD_RATE_AA = 5.0;
	final static double REPROD_RATE_AB = 5.0;
	final static double REPROD_RATE_BB = 5.0;
		
	final static long SEED = new Random().nextLong();
	
	public static void main(String[] args) {
	
		TestResources.init();
		
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		sp.setReproductionRate(Genotype.AA, REPROD_RATE_AA);
		sp.setReproductionRate(Genotype.AB, REPROD_RATE_AB);
		sp.setReproductionRate(Genotype.BB, REPROD_RATE_BB);
		
		GenerationRecord previousGen = new GenerationRecord(0, 0);
		
		previousGen.setGenotypeSubpopulationSize(Genotype.AA, SUBPOPSIZE_AA);
		previousGen.setGenotypeSubpopulationSize(Genotype.AB, SUBPOPSIZE_AB);
		previousGen.setGenotypeSubpopulationSize(Genotype.BB, SUBPOPSIZE_BB);
		
		GenerationRecord currentGen = new GenerationRecord(0, 1);
		
		try {
			Method reproduction = Population.class.getDeclaredMethod("reproduce", GenerationRecord.class, GenerationRecord.class);
			reproduction.setAccessible(true);
			
			Population p = new Population(SEED);
			
			reproduction.invoke(p, previousGen, currentGen);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Genotype gt : Genotype.values()) {
			System.out.printf("%s: %d\n", gt.toString(), currentGen.getGenotypeSubpopulationSize(gt));
		}
		System.out.println(currentGen.getPopulationSize());
		
	}
}


