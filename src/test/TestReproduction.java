package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.reflect.Method;
import java.util.Random;

import shared.Allele;
import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.GenerationRecord;
import simulation.Population;


public class TestReproduction {
	
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
		
		int PopSize=0;
		double freqA=0, freqB=0, freqC=0;
		
		try{
//			BufferedReader br = new BufferedReader(new FileReader("src/test.csv"));
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			
			// number of alleles
			numAlleles = Integer.parseInt(br.readLine().split(",")[1]);
			System.out.println("NumAlleles: " + numAlleles);
			sp.setThreeAlleles((numAlleles == 3));
			
			//sexual selection on/off
			boolean sexualSelectionOn = (br.readLine().split(",")[1].equals("on"));
			System.out.println("Sexual Selection: " + sexualSelectionOn);
			sp.setSexSelectChecked(sexualSelectionOn);
			
			//num gens
			int NumGens = Integer.parseInt(br.readLine().split(",")[1]);
			sp.setNumGens(NumGens);
			System.out.println("Num Gens: " + NumGens);
			
			//num populations
			int NumPops = Integer.parseInt(br.readLine().split(",")[1]);
			sp.setNumPops(NumPops);
			System.out.println("Num Pops: " + NumPops);
			
			//population size
			PopSize = Integer.parseInt(br.readLine().split(",")[1]);
			sp.setPopSize(PopSize);
			System.out.println("Pop Size: " + PopSize);
			
			//allele frequencies
			br.readLine();
			String[] line = br.readLine().split(",");
			freqA = Double.parseDouble(line[1]);
			freqB = Double.parseDouble(line[2]);
			freqC = Double.parseDouble(line[3]);
					
			sp.setAlleleFrequency(Allele.A, freqA);
			sp.setAlleleFrequency(Allele.B, freqB);
			sp.setAlleleFrequency(Allele.C, freqC);
			
			sp.setGenotypeFrequency(Genotype.AA, freqA*freqA);
			sp.setGenotypeFrequency(Genotype.AB, 2*freqA*freqB);
			sp.setGenotypeFrequency(Genotype.BB, freqB*freqB);
			
			if (numAlleles == 3)
			{
				sp.setGenotypeFrequency(Genotype.AC, 2*freqA*freqC);
				sp.setGenotypeFrequency(Genotype.BC, 2*freqB*freqC);
				sp.setGenotypeFrequency(Genotype.CC, freqC*freqC);
			}
			
			if (numAlleles == 2)
			{
				br.readLine(); br.readLine(); br.readLine();
				
				line = br.readLine().split(",");
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
				
				br.readLine(); br.readLine(); br.readLine();
				br.readLine(); br.readLine(); br.readLine();
				br.readLine(); br.readLine(); br.readLine();
				
				line = br.readLine().split(",");
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
		sp.setReproductionRate(Genotype.AC, REPROD_RATE_AC);
		sp.setReproductionRate(Genotype.BC, REPROD_RATE_BC);
		sp.setReproductionRate(Genotype.CC, REPROD_RATE_CC);
		
		
		
		GenerationRecord previousGen = new GenerationRecord(0, 0);
		
		previousGen.setGenotypeSubpopulationSize(Genotype.AA, (int) (PopSize*freqA*freqA+.5));
		previousGen.setGenotypeSubpopulationSize(Genotype.AB, (int) (PopSize*2*freqA*freqB+.5));
		previousGen.setGenotypeSubpopulationSize(Genotype.BB, (int) (PopSize*freqB*freqB+.5));

		double a = PopSize*freqA*freqA+.5;
		double b = PopSize*2*freqA*freqB+.5;
		double c = PopSize*freqB*freqB+.5;


		if (numAlleles == 3)
		{
			
			previousGen.setGenotypeSubpopulationSize(Genotype.AC, (int) (PopSize*2*freqA*freqC+.5));
			previousGen.setGenotypeSubpopulationSize(Genotype.BC, (int) (PopSize*2*freqB*freqC+.5));
			previousGen.setGenotypeSubpopulationSize(Genotype.CC, (int) (PopSize*freqC*freqC+.5));

			
//		sp.setReproductionRate(Genotype.AC, REPROD_RATE_AC);
//		sp.setReproductionRate(Genotype.BC, REPROD_RATE_BC);
//		sp.setReproductionRate(Genotype.CC, REPROD_RATE_CC);
//		previousGen.setGenotypeSubpopulationSize(Genotype.AC, SUBPOPSIZE_AC);
//		previousGen.setGenotypeSubpopulationSize(Genotype.BC, SUBPOPSIZE_BC);
//		previousGen.setGenotypeSubpopulationSize(Genotype.CC, SUBPOPSIZE_CC);
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


