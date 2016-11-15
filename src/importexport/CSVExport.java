package importexport;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import shared.Allele;
import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.GenerationRecord;
import simulation.Population;

/**
 * Exports all data generated during simulation to a CSV file (currently
 * set to "output.csv" in the working directory) which allows for processing
 * of data in a spreadsheet application.
 * 
 * @author ericscollins
 *
 */
public class CSVExport {
	/**
	 * Enables singleton class
	 */
	private static CSVExport instance  = null;
	
	
	/**
	 * Enables singleton class, use this function to obtain the singelton
	 * instance of the CSVExport class.
	 * 
	 * @return singelton instance of CSVExport class
	 */
	public static CSVExport getInstance() {
		if (instance == null) 
			instance  = new CSVExport();
		return instance;
	}

	
	/**
	 * Private constructor to enable singelton class. Sets the output stream to
	 * a file to more easily import data into a spreadsheet application
	 */
	private CSVExport() {
	}

	
	/**
	 * The main driver of exporting to a CSV file.
	 * 
	 * @param p population to export
	 */
	public void export(Population p) {
		PrintStream original = System.out;
		try{
			PrintStream out = new PrintStream("output.csv");
			System.setOut(out);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		printHeader(p);
		
		ArrayList<GenerationRecord> genHistory = p.getGenerationHistory();
		for (GenerationRecord rec : genHistory) {
			int netChange = 0;
		
			System.out.printf("%d,", rec.getGenerationNumber());
			System.out.printf("%d,", rec.getPopulationSize());
			
			printAlleleFreq(rec);
			printGenotypeFreq(rec);
			printSubPopSizes(rec);
			netChange += printBirths(rec);
			
			if (sp.isMutationChecked())
				printMutations(rec);
			
			if (sp.isMigrationChecked())
				netChange += printMigrations(rec);
			
			netChange -= printDeaths(rec);
			
			System.out.printf("%d\n", netChange);
		}
		System.setOut(original);
	}

	
	/**
	 * Prints out information about deaths for a single generation. 
	 * 
	 * @param rec GenerationRecord to pull information from
	 * 
	 * @return total number of deaths during this generation
	 */
	private static int printDeaths(GenerationRecord rec) {
		int total = 0;
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%d,", rec.getDeaths(gt));
			total += rec.getDeaths(gt);
		}
		
		System.out.printf("%d,", total);
		
		return total;
	}

	
	/**
	 * Prints out information about migrations for a single generation.
	 * 
	 * @param rec GenerationRecord to pull information from
	 * 
	 * @return net population change due to migration
	 */
	private static int printMigrations(GenerationRecord rec) {
		int totalEmigrations = 0;
		int totalImmigrations = 0;
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%d,", rec.getEmigrationCount(gt));
			totalEmigrations = rec.getEmigrationCount(gt);
		}
		
		System.out.printf("%d,", totalEmigrations);
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%d,", rec.getImmigrationCount(gt));
			totalImmigrations = rec.getImmigrationCount(gt);
		}
		
		System.out.printf("%d,", totalImmigrations);
		
		System.out.printf("%d,", totalImmigrations - totalEmigrations);
		
		return totalImmigrations - totalEmigrations;
	}

	
	/**
	 * Prints out information about mutations for a single generation.
	 * 
	 * @param rec GenerationRecord to pull information from
	 */
	private static void printMutations(GenerationRecord rec) {
		int total = 0;
		
		for (Genotype gt1 : Genotype.getValues()) {
			for (Genotype gt2 : Genotype.getValues()) {
				if (gt1 == gt2) continue;
				System.out.printf("%s -> %s", gt1, gt2);
				System.out.printf("%d,", rec.getMutationCount(gt1, gt2));
				total += rec.getMutationCount(gt1, gt2);
			}
		}
		
		System.out.printf("%d,", total);
	}

	
	/**
	 * Prints out information about births for a single generation.
	 * 
	 * @param rec GenerationRecord to pull information from
	 * 
	 * @return total number of births during this generation
	 */
	private static int printBirths(GenerationRecord rec) {
		int total = 0;
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%d,", rec.getBirths(gt));
			total += rec.getBirths(gt);
		}
		
		System.out.printf("%d,", total);
		
		return total;
	}

	
	/**
	 * Prints out information about subpopulation sizes
	 * 
	 * @param rec GenerationRecord to pull information from
	 */
	private static void printSubPopSizes(GenerationRecord rec) {
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%d,", rec.getGenotypeSubpopulationSize(gt));
		}
		
	}

	
	/**
	 * Prints out information about genotype frequency for a single generation.
	 * 
	 * @param rec GenerationRecord to pull information from
	 */
	private static void printGenotypeFreq(GenerationRecord rec) {
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%.2f,", rec.getGenotypeFreq(gt));
		}
	}

	
	/**
	 * Prints out information about allele frequencies for a single generation.
	 * 
	 * @param rec Generation Record to pull information from
	 */
	private static void printAlleleFreq(GenerationRecord rec) {
		HashMap<Allele, Double> perAllele = new HashMap<Allele, Double>();
		for (Allele a : Allele.getValues()) {
			perAllele.put(a,  0.0);
		}
		
		for (Genotype gt : Genotype.getValues()) {
			perAllele.put(gt.getFirstAllele(), perAllele.get(gt.getFirstAllele()) + rec.getGenotypeSubpopulationSize(gt));
			perAllele.put(gt.getSecondAllele(), perAllele.get(gt.getSecondAllele()) + rec.getGenotypeSubpopulationSize(gt));			
		}
		
		for (Allele a : Allele.getValues()) {
			System.out.printf("%.2f,", perAllele.get(a) / (rec.getPopulationSize() * 2));
		}		
	}

	
	/**
	 * Prints out the header for each population entry in the output.
	 * 
	 * @param p Population being processed
	 */
	private static void printHeader(Population p) {
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		int popID = p.getPopID();

		System.out.printf("Population %d\n", popID);
		System.out.print("Generation #,Population Size,");
		
		for (Allele a : Allele.getValues()) {
			System.out.printf("Allele Freq. %s,", a.toString());
		}
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("Genotype Freq. %s,", gt.toString());
		}
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%s SubPop Size,", gt.toString());
		}
		
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("# %s Births,", gt.toString());
		}

		System.out.print("Total Births,");
		
		if (sp.isMutationChecked()) {

			for (Genotype gt1 : Genotype.getValues()) {
				for (Genotype gt2 : Genotype.getValues()) {
					System.out.printf(" #Mutations %s->%s,", gt1.toString(), gt2.toString());
				}
			}

			System.out.print("Total Mutations,");
		}
		
		if (sp.isMigrationChecked()) {
			for (Genotype gt : Genotype.getValues()) {
				System.out.printf("# %s Emmigrations,", gt.toString());
			}

			System.out.print("Total Emigrations,");


			for (Genotype gt : Genotype.getValues()) {
				System.out.printf("# %s Immigrations,", gt.toString());
			}

			System.out.print("Total Immigrations,");

			System.out.print("Net Migration,");
		}

		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("# %s Deaths,", gt.toString());
		}

		System.out.print("Total Deaths, ");
		
		System.out.println("Net Pop Size Change");
	}
}
