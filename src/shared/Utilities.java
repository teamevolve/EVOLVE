package shared;


import java.util.ArrayList;
import java.util.Random;


/**
 * A set of shared utilities to be used across the product.
 * 
 * @author ericscollins
 *
 */
public class Utilities {
	
	/**
	 * Generates a random number with a normal distribution based on a given
	 * mean and a given standard deviation. Uses a supplied RNG.
	 * 
	 * @param rng RNG to perform generation with
	 * @param mean average value to be produced
	 * @param stdDev standard deviation of values produced
	 * 
	 * @return random number with a normal distribution
	 */
	public static double nextGaussianRand(Random rng, double mean, double stdDev) {
		assert(rng != null);
		return rng.nextGaussian() * stdDev + mean;
	}
	
	
	/**
	 * Returns whether or not the pairing gt1, gt2 is valid (order matters).
	 * This prevents duplications of parings (AB, AA and AA, AB). Pairing is
	 * valid iff gt1 is first alphabetically (AA, AB is valid, AB, AA is not.
	 * 
	 * @param gt1 first genotype in paring
	 * @param gt2 second genotype in pairing
	 * 
	 * @return true iff gt1 is first alphabetically
	 */
	public static boolean isValidPairing(Genotype gt1, Genotype gt2) {
		return (gt1.getPairingIndex() <= gt2.getPairingIndex());
	}
	
	
	/**
	 * Calculates from two given genotypes what offspring genotypes are
	 * possible, and their likelihoods;
	 * 
	 * @param gt1 first genotype
	 * @param gt2 second genotype
	 * @return Multiset of possible genotypes
	 */
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
