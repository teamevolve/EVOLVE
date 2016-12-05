package importexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import shared.Allele;
import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import shared.Utilities;
import simulation.GenerationRecord;
import simulation.Population;

/**
 * Exports all data generated during simulation to a CSV file (currently set to
 * "output.csv" in the working directory) which allows for processing of data in
 * a spreadsheet application.
 * 
 * @author ericscollins
 *
 */
public class CSVExport {
	/**
	 * Enables singleton class
	 */
	private static CSVExport instance = null;
	private static int runCounter = 0;

	/**
	 * Enables singleton class, use this function to obtain the singelton
	 * instance of the CSVExport class.
	 * 
	 * @return singelton instance of CSVExport class
	 */
	public static CSVExport getInstance() {
		if (instance == null)
			instance = new CSVExport();
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
	 * @param p
	 *            population to export
	 */
	public void export(ArrayList<Population> popList) {
		PrintStream original = System.out;
			try {
				System.setOut(new PrintStream(Utilities.generateSaveDialog(null, "csv")));
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		SessionParameters sp = DataManager.getInstance().getSessionParams();
		printLabInfo();
		for (Population p : popList) {
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
		}
		System.setOut(original);
	}

	/**
	 * Prints out information about deaths for a single generation.
	 * 
	 * @param rec
	 *            GenerationRecord to pull information from
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
	 * @param rec
	 *            GenerationRecord to pull information from
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
	 * @param rec
	 *            GenerationRecord to pull information from
	 */
	private static void printMutations(GenerationRecord rec) {
		int total = 0;

		for (Genotype gt1 : Genotype.getValues()) {
			for (Genotype gt2 : Genotype.getValues()) {
				if (gt1 == gt2)
					continue;
				System.out.printf("%d,", rec.getMutationCount(gt1, gt2));
				total += rec.getMutationCount(gt1, gt2);
			}
		}

		System.out.printf("%d,", total);
	}

	/**
	 * Prints out information about births for a single generation.
	 * 
	 * @param rec
	 *            GenerationRecord to pull information from
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
	 * @param rec
	 *            GenerationRecord to pull information from
	 */
	private static void printSubPopSizes(GenerationRecord rec) {
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%d,", rec.getGenotypeSubpopulationSize(gt));
		}

	}

	/**
	 * Prints out information about genotype frequency for a single generation.
	 * 
	 * @param rec
	 *            GenerationRecord to pull information from
	 */
	private static void printGenotypeFreq(GenerationRecord rec) {
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%.2f,", rec.getGenotypeFreq(gt));
		}
	}

	/**
	 * Prints out information about allele frequencies for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printAlleleFreq(GenerationRecord rec) {
		HashMap<Allele, Double> perAllele = new HashMap<Allele, Double>();
		for (Allele a : Allele.getValues()) {
			perAllele.put(a, 0.0);
		}

		for (Genotype gt : Genotype.getValues()) {
			perAllele.put(gt.getFirstAllele(),
					perAllele.get(gt.getFirstAllele()) + rec.getGenotypeSubpopulationSize(gt));
			perAllele.put(gt.getSecondAllele(),
					perAllele.get(gt.getSecondAllele()) + rec.getGenotypeSubpopulationSize(gt));
		}

		for (Allele a : Allele.getValues()) {
			System.out.printf("%.2f,", perAllele.get(a) / (rec.getPopulationSize() * 2));
		}
	}

	/**
	 * Prints out the header for each population entry in the output.
	 * 
	 * @param p
	 *            Population being processed
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
	
	public void printLabInfo() {
		SessionParameters sp = DataManager.getInstance().getSessionParams();

		System.out.println(sp.getTitle());
		System.out.println(sp.getQuestion());
		System.out.println(sp.getDesign());
		System.out.println(sp.getResults());
		System.out.println(sp.getDiscuss());
		System.out.printf("Seed:,%d\n",sp.getSeed());
		System.out.printf("# Generations:,%d,,# Populations:,%d,,Population Size:,%d\n", sp.getNumGens(),sp.getNumPops(),sp.getPopSize());
		for (Allele a : Allele.getValues()) {
			System.out.printf("Allele Freq. %s:,%f,,", a.toString(), sp.getAlleleFrequency(a));
		}
		System.out.println();
		if (!sp.isPopConst()) {
			System.out.printf("Carrying Capacity:, %d,,Post-Crash Size:,%d\n",sp.getPopCapacity(),sp.getCrashCapacity());
		}
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("Survival Rate %s,%f,,", gt.toString(), sp.getSurvivalRate(gt));
		}
		System.out.println();
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("Reproductive Rate %s,%f,,", gt.toString(), sp.getReproductionRate(gt));
		}
		System.out.println();
		if (sp.isMutationChecked()) {
			for (Allele a1 : Allele.getValues()) {
				for (Allele a2 : Allele.getValues()) {
					if (a1 == a2) continue;
					System.out.printf("Mutation Rate %s->%s:,%f,,", a1.toString(), a2.toString(), sp.getAlleleMutationRate(a1, a2));
				}
				System.out.println();
			}
		}

		System.out.println("\n");

	}
}
