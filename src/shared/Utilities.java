package shared;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utilities {
	
	public static double nextGaussianRand(Random rng, double mean, double stdDev) {
		assert(rng != null);
		//double answer = rng.nextGaussian() * stdDev + mean;
		//System.out.printf("Generated %f from mean %f and stddev %f\n", answer, mean, stdDev);
		return rng.nextGaussian() * stdDev + mean;
	}
	
	public static Collection<Genotype> getShuffledGenotypes(Random rng) {
		List<Genotype> l = Arrays.asList(Genotype.values());
		Collections.shuffle(l, rng);
		return l;
	}

	public static Collection<Genotype> getPunnetSquare(Genotype gt1, 
			Genotype gt2, Random rng){
		Allele a1_0 = gt1.getAllele(0), a1_1 = gt1.getAllele(1);
		Allele a2_0 = gt2.getAllele(0), a2_1 = gt2.getAllele(1);
		List<Genotype> l = Arrays.asList(Genotype.getGenotype(a1_0, a2_0),
				Genotype.getGenotype(a1_0, a2_0),
				Genotype.getGenotype(a1_0, a2_0),
				Genotype.getGenotype(a1_0, a2_0));
		Collections.shuffle(l, rng);				
		return l;	
	}
}
