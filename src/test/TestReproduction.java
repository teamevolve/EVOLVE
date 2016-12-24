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
		
		// 2 alleles
		sp.setThreeAlleles(false);
		
		sp.setReproductionRate(Genotype.AA, REPROD_RATE_AA);
		sp.setReproductionRate(Genotype.AB, REPROD_RATE_AB);
		sp.setReproductionRate(Genotype.BB, REPROD_RATE_BB);
		
		sp.setSexualSelectionRate(Genotype.AA, Genotype.AA, .50);
		sp.setSexualSelectionRate(Genotype.AA, Genotype.AB, .25);
		sp.setSexualSelectionRate(Genotype.AA, Genotype.BB, .25);
		
		sp.setSexualSelectionRate(Genotype.AB, Genotype.AA, .25);
		sp.setSexualSelectionRate(Genotype.AB, Genotype.AB, .50);
		sp.setSexualSelectionRate(Genotype.AB, Genotype.BB, .25);
		
		sp.setSexualSelectionRate(Genotype.BB, Genotype.AA, .25);
		sp.setSexualSelectionRate(Genotype.BB, Genotype.AB, .25);
		sp.setSexualSelectionRate(Genotype.BB, Genotype.BB, .50);
		
		
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
		
		System.out.println("--------------------RESULTS------------------------");
		Genotype[] twoAlleleArray = {Genotype.AA, Genotype.AB, Genotype.BB};
		for (Genotype gt : twoAlleleArray) {
			System.out.printf("%s: %d\n", gt.toString(), currentGen.getGenotypeSubpopulationSize(gt));
		}
		
		
		System.out.println("Population Size:" + currentGen.getPopulationSize());
		
	}
}


