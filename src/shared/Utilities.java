package shared;


import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	 * 
	 */
	public static double nextGaussianRand(Random rng, double mean, double stdDev) {
		assert(rng != null);
		return rng.nextGaussian() * stdDev + mean;
	}
	
	/**
	 * Generates a random number with a Poisson distribution based on a given
	 * mean lambda. 
	 * 
	 * @param lambda average value to be produced
	 * 
	 * @return random number with a Poisson distribution
	 * 
	 */
	public static int getPoisson(double lambda) {
		 double L = Math.exp(-lambda);
		 double p = 1.0;
		 int k = 0;
		 do {
			 k++;
			 p *= Math.random();
		 } while (p > L);
		 return k - 1;
	}
	
	/**
	 * Generates a random number with a Binomial distribution based on a given
	 * number of trials n and probability of an event p. 
	 * 
	 * @param n number of trials
	 * @param p probability of each trial
	 * 
	 * @return random number with a binomial distribution
	 * 
	 */
	public static int getBinomial(Random rng, int n, double p) {
		assert(rng != null);
		int x = 0;
		for(int i = 0; i < n; i++) {
			double randomNext = rng.nextDouble();
			if(randomNext < p)
				x++;
		}
		return x;
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
	 * 
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
	 * 
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
	
	
	/**
	 * Creates a save dialog and returns the selected file.
	 * 
	 * @param parent component to inherit look and feel from; can be null
	 * @param filters list of file extensions to filter on
	 * @return File object if selection was successful, null otherwise
	 * 
	 */
	public static File generateSaveDialog(Component parent, String... filters){
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(new FileNameExtensionFilter("", filters));
		chooser.setSelectedFile(new File(String.format("%1$s.%2$s", sp.getTitle(), filters[0].toString())));
		if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	/**
	 * Creates open dialog and returns the selected file.
	 * 
	 * @param parent component to inherit look and feel from; can be null
	 * @param filters list of file extensions to filter on
	 * @return File object if selection was successful, null otherwise
	 * 
	 */
	public static File generateOpenDialog(Component parent, String... filters){
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(new FileNameExtensionFilter("", filters));
		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
}
