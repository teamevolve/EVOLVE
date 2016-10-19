package shared;


import java.util.ArrayList;
import java.util.Random;

public class Utilities {
	
	public static double nextGaussianRand(Random rng, double mean, double stdDev) {
		assert(rng != null);
		return rng.nextGaussian() * stdDev + mean;
	}
	
	

	public static ArrayList<Genotype> getOffspringGenotypes(Genotype gt1, Genotype gt2){
		Allele a1_0 = gt1.getFirstAllele(), a1_1 = gt1.getSecondAllele();
		Allele a2_0 = gt2.getFirstAllele(), a2_1 = gt2.getSecondAllele();
		
		ArrayList<Genotype> offspringGenotypes = new ArrayList<Genotype>();
		offspringGenotypes.add(Genotype.genotypeFromAlleles(a1_0, a2_0));
		offspringGenotypes.add(Genotype.genotypeFromAlleles(a1_0, a2_1));
		offspringGenotypes.add(Genotype.genotypeFromAlleles(a1_1, a2_0));
		offspringGenotypes.add(Genotype.genotypeFromAlleles(a1_1, a2_1));
		
		return offspringGenotypes;	
	}
}
