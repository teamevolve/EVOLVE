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
 * @author candicezhao
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
		File save = Utilities.generateSaveDialog(null, "csv");
		
		try {
			if (save == null) return;
			System.setOut(new PrintStream(save));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		
		printLabInfo();
		
		if (popList.size() < 2) {
			sp.setMigrationChecked(false);
		}
		
		
		for (Population p : popList) {
			printHeader(p);
			ArrayList<GenerationRecord> genHistory = p.getGenerationHistory();
			for (GenerationRecord rec : genHistory) {
				int netChange = 0;
				System.out.printf("%d,", rec.getGenerationNumber());
				System.out.printf("%d,", rec.getPopulationSize());
				printSubPopSizes(rec);

				printAlleleFreq(rec);
				printGenotypeFreq(rec);

				printDeltaAlleleFreq(rec, genHistory);
				printDeltaGenotypeFreq(rec, genHistory);
				
				netChange += printBirths(rec);

				if (sp.isMutationChecked())
					printMutations(rec);

				if (sp.isMigrationChecked())
					netChange += printMigrations(rec);

				netChange -= printDeaths(rec);

				System.out.printf("%d,", netChange);
				
				printMeanFitAbs(rec);
				printMeanFitRel(rec);
				
				printDeltaMeanFitAbs(rec, genHistory);
				printDeltaMeanFitRel(rec, genHistory);

				printHetObs(rec);
				printHetExp(rec);
				printDeltaHetExp(rec);
				
				System.out.println();
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
	 * Prints out information about change in genotype frequency for a single generation.
	 * 
	 * @param rec
	 *            GenerationRecord to pull information from
	 */
	private static void printDeltaGenotypeFreq(GenerationRecord rec, ArrayList<GenerationRecord> genHistory) {
		if (rec.getGenerationNumber() == 0) {
			for (Genotype gt : Genotype.getValues()) {
				System.out.print("0,");
			}
			return;
		}
		
		GenerationRecord parent = genHistory.get(rec.getGenerationNumber() - 1);

		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%.2f,", rec.getGenotypeFreq(gt) - parent.getGenotypeFreq(gt));
		}
	}

	/**
	 * Prints out information about allele frequencies for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printAlleleFreq(GenerationRecord rec) {
		
		for (Allele a : Allele.getValues()) {
			System.out.printf("%.2f,", rec.getAlleleFreq(a));
		}
		
	}
	
	/**
	 * Prints out information about change in allele frequencies for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printDeltaAlleleFreq(GenerationRecord rec, ArrayList<GenerationRecord> genHistory) {
		
		if (rec.getGenerationNumber() == 0) {
			for (Allele a : Allele.getValues()) {
				System.out.print("0,");
			}
			return;
		}
		
		GenerationRecord parent = genHistory.get(rec.getGenerationNumber() - 1);
		for (Allele a : Allele.getValues()) {
			System.out.printf("%.2f,", rec.getAlleleFreq(a) - parent.getAlleleFreq(a));
		}
	}

	/**
	 * Prints out information about mean absolute fitness for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printMeanFitAbs(GenerationRecord rec) {
		
		System.out.printf("%.2f,", rec.getMeanFitAbs());	
		
	}
	
	/**
	 * Prints out information about change in mean absolute fitness for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printDeltaMeanFitAbs(GenerationRecord rec, ArrayList<GenerationRecord> genHistory) {
		if (rec.getGenerationNumber() == 0) {
			System.out.print("0,");
			return;
		}
		GenerationRecord parent = genHistory.get(rec.getGenerationNumber() - 1);
		System.out.printf("%.2f,", rec.getMeanFitAbs() - parent.getMeanFitAbs());
	}
	
	/**
	 * Prints out information about mean relative fitness for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printMeanFitRel(GenerationRecord rec) {
		
		System.out.printf("%.2f,", rec.getMeanFitRel());	
		
	}
	
	/**
	 * Prints out information about change in mean relative fitness for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printDeltaMeanFitRel(GenerationRecord rec, ArrayList<GenerationRecord> genHistory) {
		if (rec.getGenerationNumber() == 0) {
			System.out.print("0,");
			return;
		}
		GenerationRecord parent = genHistory.get(rec.getGenerationNumber() - 1);
		System.out.printf("%.2f,", rec.getMeanFitRel() - parent.getMeanFitRel());
	}

	/**
	 * Prints out information about observed heteozygosity for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printHetObs(GenerationRecord rec) {
		
		System.out.printf("%.2f,", rec.getHetObs());	
		
	}
	
	/**
	 * Prints out information about expected heteozygosity for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printHetExp(GenerationRecord rec) {
		
		System.out.printf("%.2f,", rec.getHetExp());	
		
	}
	
	/**
	 * Prints out information about change in heteozygosity for a single generation.
	 * 
	 * @param rec
	 *            Generation Record to pull information from
	 */
	private static void printDeltaHetExp(GenerationRecord rec) {
		
		System.out.printf("%.2f,", rec.getDeltaHetExp());	
		
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

		System.out.printf("Pop. # %d\n", popID);
		System.out.print("Gen. #, Pop. Size,");

		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("# %s,", gt.toString());
		}
		
		for (Allele a : Allele.getValues()) {
			System.out.printf("Freq.%s,", a.toString());
		}

		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("Freq.%s,", gt.toString());
		}

		for (Allele a : Allele.getValues()) {
			System.out.printf("Delta Freq.%s,", a.toString());
		}
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("Delta Freq.%s,", gt.toString());
		}
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("# %s Born,", gt.toString());
		}

		System.out.print("Total Births,");

		if (sp.isMutationChecked()) {
			for (Genotype gt1 : Genotype.getValues()) {
				for (Genotype gt2 : Genotype.getValues()) {
					if (gt1 == gt2)
						continue;
					System.out.printf("#Mut.s %s->%s,", gt1.toString(), gt2.toString());
				}
			}
			System.out.print("Total Mut.s,");
		}

		if (sp.isMigrationChecked()) {
			for (Genotype gt : Genotype.getValues()) {
				System.out.printf("# %s Em.s,", gt.toString());
			}

			System.out.print("Total Em.s,");

			for (Genotype gt : Genotype.getValues()) {
				System.out.printf("# %s Im.s,", gt.toString());
			}

			System.out.print("Total Im.s,");

			System.out.print("Net Mig.n,");
		}

		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("# %s Deaths,", gt.toString());
		}

		System.out.print("Total Deaths, ");

		System.out.print("Net Pop Change,");
		
		System.out.println("MeanFitAbs,MeanFitRel,Delta MeanFitAbs,Delta MeanFitRel,HetObs,HetExp,Delta HetExp");

	}
	
	public void printLabInfo() {
		SessionParameters sp = DataManager.getInstance().getSessionParams();

		System.out.println("Title,\"" + sp.getExportTitle() + "\"");
		
		if (sp.getQuestion().trim().length() > 0) 
			System.out.println("Question,\"" + sp.getQuestion() + "\"");
		if (sp.getDesign().trim().length() > 0) 
			System.out.println("Design,\"" + sp.getDesign() + "\"");
		if (sp.getResults().trim().length() > 0) 
			System.out.println("Results,\"" + sp.getResults() + "\"");
		if (sp.getDiscuss().trim().length() > 0) 
			System.out.println("Notes,\"" + sp.getDiscuss() + "\"");
		System.out.println();
		System.out.println();

		if (!DataManager.getInstance().getSessionParams().isThreeAlleles()) {
			System.out.println("Seed, #Gen.s, #Pop.s, Freq.A, Freq.B, 1st Pop., Carrying Cap, Post-Crash Size");
			System.out.printf("%d,%d,%d,%.10f,%.10f,%d,%d,%d\n",
						sp.getSeed(), 
						sp.getNumGens(), 
						sp.getNumPops(),
						sp.getAlleleFrequency(Allele.A), 
						sp.getAlleleFrequency(Allele.B),
						sp.getPopSize(),
						sp.getPopCapacity(), 
						sp.getCrashCapacity());
		
			System.out.println();
			// Natural selection
			System.out.println("NAT. SEL.,AA,AB,BB");
			System.out.printf("Surv.,%.10f,%.10f,%.10f\n",
					sp.getSurvivalRate(Genotype.AA),
					sp.getSurvivalRate(Genotype.AB),
					sp.getSurvivalRate(Genotype.BB));
			System.out.printf("Repr.,%.10f,%.10f,%.10f\n",
					sp.getReproductionRate(Genotype.AA),
					sp.getReproductionRate(Genotype.AB),
					sp.getReproductionRate(Genotype.BB));
			
			System.out.println();

			
			// migration
			if (sp.isMigrationChecked()) {
				System.out.printf("MIGRATION,%.10f,%.10f,%.10f\n", 
						sp.getMigrationRate(Genotype.AA),
						sp.getMigrationRate(Genotype.AB),
						sp.getMigrationRate(Genotype.BB));			}
			else {
				System.out.print("MIGRATION,0,0,0\n");
			}
			
			System.out.println();
			
			// sexual selection
			System.out.println("SEXUAL SEL.,,,,Total");
			if (sp.isSexSelectChecked()) {
				for (Genotype gt1 : Genotype.getValues()) {
					double sum = 0.0;
					System.out.printf("%s Pref.,", gt1.toString());
					for (Genotype gt2: Genotype.getValues()) {
						System.out.printf("%.10f,",sp.getSexualSelectionRate(gt1, gt2));
						sum += sp.getSexualSelectionRate(gt1, gt2);
					}
					System.out.printf("%.10f",sum);
					System.out.println();
				}
			}
			else {
				for (Genotype gt1 : Genotype.getValues()) {
					double sum = 1.0;
					System.out.printf("%s Pref.,", gt1.toString());
					for (Genotype gt2: Genotype.getValues()) {
						System.out.printf("%.10f,", sum/3);
					}
					System.out.printf("%.10f",sum);
					System.out.println();
				}
			}
			
			System.out.println();

			// mutation
			if (sp.isMutationChecked()) {
				System.out.println("MUTATION, A>B, B>A");
				System.out.printf(",%.10f,%.10f\n",sp.getAlleleMutationRate(Allele.A, Allele.B), sp.getAlleleMutationRate(Allele.B, Allele.A));
			}
			else {
				System.out.println("MUTATION, A>B, B>A");
				System.out.println(",0,0");
			}
			System.out.println();
			System.out.println();
			
		}
		else {			
			System.out.println("Seed, #Gen.s, #Pop.s, Freq.A, Freq.B, Freq.C, 1st Pop, Carrying Cap, Post-Crash Size");
			System.out.printf("%d,%d,%d,%.10f,%.10f,%.10f,%d,%d,%d\n",
					sp.getSeed(), sp.getNumGens(), sp.getNumPops(),
					sp.getAlleleFrequency(Allele.A), 
					sp.getAlleleFrequency(Allele.B),
					sp.getAlleleFrequency(Allele.C),
					sp.getPopSize(),
					sp.getPopCapacity(), 
					sp.getCrashCapacity());
	
			System.out.println();
			
			// Natural selection
			System.out.println("NAT. SEL.,AA,AB,BB,AC,BC,CC");
			System.out.printf("Surv.,%.10f,%.10f,%.10f,%.10f,%.10f,%.10f\n",
					sp.getSurvivalRate(Genotype.AA),
					sp.getSurvivalRate(Genotype.AB),
					sp.getSurvivalRate(Genotype.BB),
					sp.getSurvivalRate(Genotype.AC),
					sp.getSurvivalRate(Genotype.BC),
					sp.getSurvivalRate(Genotype.CC));
			System.out.printf("Repr.,%.10f,%.10f,%.10f,%.10f,%.10f,%.10f\n",
					sp.getReproductionRate(Genotype.AA),
					sp.getReproductionRate(Genotype.AB),
					sp.getReproductionRate(Genotype.BB),
					sp.getReproductionRate(Genotype.AC),
					sp.getReproductionRate(Genotype.BC),
					sp.getReproductionRate(Genotype.CC));
			
			System.out.println();

			
			// migration
			if (sp.isMigrationChecked()) {
				System.out.printf("MIGRATION,%.10f,%.10f,%.10f,%.10f,%.10f,%.10f\n", 
						sp.getMigrationRate(Genotype.AA),
						sp.getMigrationRate(Genotype.AB),
						sp.getMigrationRate(Genotype.BB),
						sp.getMigrationRate(Genotype.AC),
						sp.getMigrationRate(Genotype.BC),
						sp.getMigrationRate(Genotype.CC));
				System.out.println();
			}
			else {
				System.out.print("MIGRATION,0,0,0,0,0,0\n");
				System.out.println();
			}
			
			//sexual selection
			if (sp.isSexSelectChecked()) {
				System.out.println("SEXUAL SEL.,,,,,,,Total");
				for (Genotype gt1 : Genotype.getValues()) {
					double sum = 0.0;
					System.out.printf("%s Pref.,", gt1.toString());
					for (Genotype gt2: Genotype.getValues()) {
						System.out.printf("%.10f,",sp.getSexualSelectionRate(gt1, gt2));
						sum += sp.getSexualSelectionRate(gt1, gt2);
					}
					System.out.print(sum);
					System.out.println();
				}
				System.out.println();
			}
			else {
				System.out.println("SEXUAL SEL.,,,,,,,Total");
				for (Genotype gt1 : Genotype.getValues()) {
					double sum = 1.0;
					System.out.printf("%s Pref.,", gt1.toString());
					for (Genotype gt2: Genotype.getValues()) {
						System.out.printf("%.10f,", sum/6);
					}
					System.out.print(sum);
					System.out.println();
				}
				System.out.println();
			}
	
			// mutation
			if (sp.isMutationChecked()) {
				System.out.println("MUTATION, A>B, B>A, A>C, C>A, B>C, C>B");
				System.out.printf(",%.10f,%.10f,%.10f,%.10f,%.10f,%.10f\n",
						sp.getAlleleMutationRate(Allele.A, Allele.B), 
						sp.getAlleleMutationRate(Allele.B, Allele.A),
						sp.getAlleleMutationRate(Allele.A, Allele.C), 
						sp.getAlleleMutationRate(Allele.C, Allele.A),
						sp.getAlleleMutationRate(Allele.B, Allele.C), 
						sp.getAlleleMutationRate(Allele.C, Allele.B));
			}
			else {
				System.out.println("MUTATION, A>B, B>A, A>C, C>A, B>C, C>B");
				System.out.print(",0,0,0,0,0,0\n");
			}
			
			System.out.println();
			System.out.println();
		}

	}
}
