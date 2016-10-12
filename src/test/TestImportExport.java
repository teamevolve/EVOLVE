package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Random;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.GenerationRecord;
import simulation.Population;

public class TestImportExport {
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
			
			ngr.quickWrite();
				
					
			// write object to file
			FileOutputStream fos = new FileOutputStream("testGenRecord.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ngr);
			oos.close();

			// read object from file
			FileInputStream fis = new FileInputStream("testGenRecord.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			GenerationRecord result = (GenerationRecord) ois.readObject();
			ois.close();
				
			result.quickWrite();
			//weird equivalence thing
			if (result.getGenerationNumber() == ngr.getGenerationNumber()) {
				System.out.println("Pass test 1");
			}
			else {
				System.out.println("Did not pass test 1");
			}
			
			
			ngr.setGenotypeSubpopulationSize(Genotype.AA, 150);
			if (result.getGenotypeSubpopulationSize(Genotype.AA) != ngr.getGenotypeSubpopulationSize(Genotype.AA)) {
				System.out.println("Pass test 2");
			}
			else {
				System.out.println("Did not pass test 1");
			}
			
			
			File file = new File("testGenRecord.ser");
			file.delete();
			}	
		catch (Exception e) {
			System.out.println("failure is always an option");
			e.printStackTrace();
		}
		
		
	}

}
