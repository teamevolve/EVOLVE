package importexport;

import java.util.ArrayList;
import java.util.HashMap;

import shared.Allele;
import shared.Genotype;
import simulation.GenerationRecord;
import simulation.Population;

public class CSVExport {
	public static void export(Population p) {
		int netChange = 0;
		printHeader(p);
		ArrayList<GenerationRecord> genHistory = p.getGenerationHistory();
		for (GenerationRecord rec : genHistory) {
			System.out.printf("%d,", rec.getParentPopID());
			System.out.printf("%d,", rec.getPopulationSize());
			printAlleleFreq(rec);
			printGenotypeFreq(rec);
			printSubPopSizes(rec);
			netChange += printBirths(rec);
			printMutations(rec);
			netChange += printMigrations(rec);
			netChange -= printDeaths(rec);
			System.out.printf("%d\n", netChange);
		}
	}

	private static int printDeaths(GenerationRecord rec) {
		int total = 0;
		
		for (Genotype gt : Genotype.values()) {
			System.out.printf("%d,", rec.getDeaths(gt));
			total += rec.getDeaths(gt);
		}
		
		System.out.printf("%d,", total);
		
		return total;
	}

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

	private static void printMutations(GenerationRecord rec) {
		int total = 0;
		
		for (Genotype gt1 : Genotype.values()) {
			for (Genotype gt2 : Genotype.values()) {
				if (gt1 == gt2) continue;
				System.out.printf("%d,", rec.getMuationCount(gt1, gt2));
				total += rec.getMuationCount(gt1, gt2);
			}
		}
		
		System.out.printf("%d,", total);
	}

	private static int printBirths(GenerationRecord rec) {
		int total = 0;
		
		for (Genotype gt : Genotype.values()) {
			System.out.printf("%d,", rec.getBirths(gt));
			total += rec.getBirths(gt);
		}
		
		System.out.printf("%d,", total);
		
		return total;
	}

	private static void printSubPopSizes(GenerationRecord rec) {
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("%d,", rec.getGenotypeSubpopulationSize(gt));
		}
		
	}

	private static void printGenotypeFreq(GenerationRecord rec) {
		for (Genotype gt : Genotype.values()) {
			System.out.printf("%.2f,", rec.getGenotypeFreq(gt));
		}
	}

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
	
	private static void printHeader(Population p) {
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
		
		
		for (Genotype gt1 : Genotype.getValues()) {
			for (Genotype gt2 : Genotype.getValues()) {
				System.out.printf(" #Mutations %s->%s,", gt1.toString(), gt2.toString());
			}
		}

		System.out.print("Total Mutations,");
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("# %s Emmigrations,", gt.toString());
		}

		System.out.print("Total Emigrations,");
		
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("# %s Immigrations,", gt.toString());
		}

		System.out.print("Total Immigrations,");

		System.out.print("Net Migration,");
		
		
		for (Genotype gt : Genotype.getValues()) {
			System.out.printf("# %s Deaths,", gt.toString());
		}

		System.out.print("Total Deaths, ");
		
		System.out.println("Net Pop Size Change");
	}
}
