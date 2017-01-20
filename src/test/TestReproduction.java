package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

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
	final static int SUBPOPSIZE_AC = 100;
	final static int SUBPOPSIZE_BC = 100;
	final static int SUBPOPSIZE_CC = 100;
	
	final static double REPROD_RATE_AA = 5.0;
	final static double REPROD_RATE_AB = 5.0;
	final static double REPROD_RATE_BB = 5.0;
	final static double REPROD_RATE_AC = 5.0;
	final static double REPROD_RATE_BC = 5.0;
	final static double REPROD_RATE_CC = 5.0;
		
	final static long SEED = new Random().nextLong();
	
	public static void main(String[] args) {
	
		TestResources.init();
		int numAlleles=0;
		
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
	
		String filePath = args[0];
		
		try{
//			BufferedReader br = new BufferedReader(new FileReader("src/test.csv"));
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			
			// number of alleles
			numAlleles = Integer.parseInt(br.readLine().split(",")[1]);
			System.out.println("NumAlleles: " + numAlleles);
			sp.setThreeAlleles((numAlleles == 3));
			
			boolean sexualSelectionOn = (br.readLine().split(",")[1].equals("on"));
			System.out.println("Sexual Selection: " + sexualSelectionOn);
			sp.setSexSelectChecked(sexualSelectionOn);
			
			if (numAlleles == 2)
			{
				br.readLine(); br.readLine(); br.readLine();
				
				String[] line = br.readLine().split(",");
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.AA, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.AA, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.AA, Genotype.BB, Double.parseDouble(line[3]));
				
				line = br.readLine().split(",");				
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.AB, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.AB, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.AB, Genotype.BB, Double.parseDouble(line[3]));
				
				line = br.readLine().split(",");
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.BB, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.BB, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.BB, Genotype.BB, Double.parseDouble(line[3]));				
			}
			else if (numAlleles == 3)
			{
				sp.setGenotypeFrequency(Genotype.AC, .5);
				sp.setGenotypeFrequency(Genotype.BC, .5);
				sp.setGenotypeFrequency(Genotype.CC, .5);
				
				br.readLine(); br.readLine(); br.readLine();
				br.readLine(); br.readLine(); br.readLine();
				br.readLine(); br.readLine(); br.readLine();
				
				String[] line = br.readLine().split(",");
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.AA, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.AA, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.AA, Genotype.AC, Double.parseDouble(line[3]));
				sp.setSexualSelectionRate(Genotype.AA, Genotype.BB, Double.parseDouble(line[4]));
				sp.setSexualSelectionRate(Genotype.AA, Genotype.BC, Double.parseDouble(line[5]));
				sp.setSexualSelectionRate(Genotype.AA, Genotype.CC, Double.parseDouble(line[6]));

				line = br.readLine().split(",");
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.AB, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.AB, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.AB, Genotype.AC, Double.parseDouble(line[3]));
				sp.setSexualSelectionRate(Genotype.AB, Genotype.BB, Double.parseDouble(line[4]));
				sp.setSexualSelectionRate(Genotype.AB, Genotype.BC, Double.parseDouble(line[5]));
				sp.setSexualSelectionRate(Genotype.AB, Genotype.CC, Double.parseDouble(line[6]));
				
				line = br.readLine().split(",");
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.AC, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.AC, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.AC, Genotype.AC, Double.parseDouble(line[3]));
				sp.setSexualSelectionRate(Genotype.AC, Genotype.BB, Double.parseDouble(line[4]));
				sp.setSexualSelectionRate(Genotype.AC, Genotype.BC, Double.parseDouble(line[5]));
				sp.setSexualSelectionRate(Genotype.AC, Genotype.CC, Double.parseDouble(line[6]));
				
				line = br.readLine().split(",");
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.BB, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.BB, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.BB, Genotype.AC, Double.parseDouble(line[3]));
				sp.setSexualSelectionRate(Genotype.BB, Genotype.BB, Double.parseDouble(line[4]));
				sp.setSexualSelectionRate(Genotype.BB, Genotype.BC, Double.parseDouble(line[5]));
				sp.setSexualSelectionRate(Genotype.BB, Genotype.CC, Double.parseDouble(line[6]));
				
				line = br.readLine().split(",");
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.BC, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.BC, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.BC, Genotype.AC, Double.parseDouble(line[3]));
				sp.setSexualSelectionRate(Genotype.BC, Genotype.BB, Double.parseDouble(line[4]));
				sp.setSexualSelectionRate(Genotype.BC, Genotype.BC, Double.parseDouble(line[5]));
				sp.setSexualSelectionRate(Genotype.BC, Genotype.CC, Double.parseDouble(line[6]));
				
				line = br.readLine().split(",");
				System.out.println("Preferences of "+ line[0] +" added.");
				sp.setSexualSelectionRate(Genotype.CC, Genotype.AA, Double.parseDouble(line[1]));
				sp.setSexualSelectionRate(Genotype.CC, Genotype.AB, Double.parseDouble(line[2]));
				sp.setSexualSelectionRate(Genotype.CC, Genotype.AC, Double.parseDouble(line[3]));
				sp.setSexualSelectionRate(Genotype.CC, Genotype.BB, Double.parseDouble(line[4]));
				sp.setSexualSelectionRate(Genotype.CC, Genotype.BC, Double.parseDouble(line[5]));
				sp.setSexualSelectionRate(Genotype.CC, Genotype.CC, Double.parseDouble(line[6]));
				
			}
			
		}
		catch(Exception e)
		{
			
		}		
		sp.setReproductionRate(Genotype.AA, REPROD_RATE_AA);
		sp.setReproductionRate(Genotype.AB, REPROD_RATE_AB);
		sp.setReproductionRate(Genotype.BB, REPROD_RATE_BB);
		
		
		
		GenerationRecord previousGen = new GenerationRecord(0, 0);
		
		previousGen.setGenotypeSubpopulationSize(Genotype.AA, SUBPOPSIZE_AA);
		previousGen.setGenotypeSubpopulationSize(Genotype.AB, SUBPOPSIZE_AB);
		previousGen.setGenotypeSubpopulationSize(Genotype.BB, SUBPOPSIZE_BB);


		if (numAlleles == 3)
		{
		sp.setReproductionRate(Genotype.AC, REPROD_RATE_AC);
		sp.setReproductionRate(Genotype.BC, REPROD_RATE_BC);
		sp.setReproductionRate(Genotype.CC, REPROD_RATE_CC);
		previousGen.setGenotypeSubpopulationSize(Genotype.AC, SUBPOPSIZE_AC);
		previousGen.setGenotypeSubpopulationSize(Genotype.BC, SUBPOPSIZE_BC);
		previousGen.setGenotypeSubpopulationSize(Genotype.CC, SUBPOPSIZE_CC);
	}
		
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
		
		

		if (numAlleles == 2)
		{
			Genotype[] AlleleArray = {Genotype.AA, Genotype.AB, Genotype.BB};
			for (Genotype gt : AlleleArray) {
				System.out.printf("%s: %d\n", gt.toString(), currentGen.getGenotypeSubpopulationSize(gt));
			}

		}
		else if (numAlleles == 3)
		{
			for (Genotype gt : Genotype.getValues()) {
				System.out.printf("%s: %d\n", gt.toString(), currentGen.getGenotypeSubpopulationSize(gt));
			}
		}
		
		
		
		System.out.println("Population Size:" + currentGen.getPopulationSize());
		
	}
}


