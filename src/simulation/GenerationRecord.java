package simulation;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;

import shared.Allele;
import shared.DataManager;
import shared.Genotype;



/**
 * GenerationRecord is a passive data repository used by populations to store 
 * information about each generation. This structure includes births, deaths,
 * mutations, emigrations, immigrations, generation number, genotype ratios,
 * and population size. An instance is created for each generation and stored 
 * in a list in a Population.
 * 
 * @author richwenner
 * @author ericscollins
 * @author taav-isaac
 */

public class GenerationRecord implements Serializable{

	private static final long serialVersionUID = 2L;
	final private int generationNumber;
	final private int parentPopID;
	private HashMap<Genotype, Integer> genotypeSubpopulationSizes;
	private HashMap<Genotype, Integer> immigrations;
	private HashMap<Genotype, Integer> emigrations;
	private HashMap<Genotype, HashMap<Genotype, Integer>> mutations;
	private HashMap<Genotype, Integer> births;
	private HashMap<Genotype, Integer> deaths;
	private HashMap<Allele, Integer> alleleNumber;
	private double MeanFitAbs;
	private double MeanFitRel;
	private double HetObs;
	private double HetExp;
	private double DeltaHetExp;

	
	/**
	 * Constructor; initializes internal representations and sets IDs
	 * 
	 * @param popID		parent population's ID
	 * @param genNum	generation number
	 */
	public GenerationRecord(int popID, int genNum) {
		genotypeSubpopulationSizes = new HashMap<Genotype, Integer>();
		immigrations = new HashMap<Genotype, Integer>();
		emigrations = new HashMap<Genotype, Integer>();
		mutations = new HashMap<Genotype, HashMap<Genotype, Integer>>();
		births = new HashMap<Genotype, Integer>();
		deaths = new HashMap<Genotype, Integer>();
		alleleNumber = new HashMap<Allele, Integer>();
		parentPopID = popID;
		generationNumber = genNum;
		MeanFitAbs = 0;
		MeanFitRel = 0;
		HetObs = 0;
		HetExp = 0;
		DeltaHetExp = 0;
	}
	
	
	/**
	 * Get internal generation number
	 * @return int representing which generation this is
	 */
	public int getGenerationNumber() {
		return generationNumber;
	}	
	
	
	/**
	 * Get the ID of the population this generation is for
	 * @return parent population's ID
	 */
	public int getParentPopID() {
		return parentPopID;
	}
	
	
	/**
	 * Returns the total population size of this generation
	 * @return total population size of generation
	 */
	public int getPopulationSize() {
		int populationSize = 0;
		for (int i : genotypeSubpopulationSizes.values()) {
			populationSize += i;
		}
		return populationSize;		
	}
	
	public double getMeanFitAbs() {
		MeanFitAbs = 0;
		for (Genotype gt: Genotype.getValues()) {
			MeanFitAbs += getGenotypeFreq(gt) * DataManager.getInstance().getSessionParams().getAbsoluteFitness(gt);
		}
		return MeanFitAbs;
	}
	
	public double getMeanFitRel() {
		MeanFitRel = 0;
		for (Genotype gt: Genotype.getValues()) {
			MeanFitRel += getGenotypeFreq(gt) * DataManager.getInstance().getSessionParams().getRelativeFitness(gt);
		}
		return MeanFitRel;
	}
	
	public double getHetObs() {
		HetObs = 0.0;
		for (Genotype gt: Genotype.getValues()) {
			if (gt.getFirstAllele() != gt.getSecondAllele())
				HetObs += getGenotypeFreq(gt);
		}
		return HetObs;
	}
	
	public double getHetExp() {
		HetExp = 1.0;
		for (Allele a1: Allele.getValues()) {
			HetExp -= (DataManager.getInstance().getSessionParams().getAlleleFrequency(a1)) * 
					 (DataManager.getInstance().getSessionParams().getAlleleFrequency(a1));
		}
		return HetExp;
	}
	
	public double getDeltaHetExp() {
		return HetExp - HetObs;
	}
	/**
	 * Get the frequency of a given genotype for this generation
	 * 
	 * @param gt genotype to get frequency of
	 * @return frequency of given genotype for this generation
	 */
	public double getGenotypeFreq(Genotype gt) {
		return (double) genotypeSubpopulationSizes.get(gt) / (double) getPopulationSize();
	}
	
	/**
	 * Get the frequency of a given allele for this generation
	 * 
	 * @param al allele to get frequency of
	 * @return frequency of given allele for this generation
	 */
	public double getAlleleFreq(Allele al) {
		
		for (Allele a : Allele.getValues()) {
			alleleNumber.put(a, 0);
		}
		
		for (Genotype gt : Genotype.getValues()) {
			alleleNumber.put(gt.getFirstAllele(),
					alleleNumber.get(gt.getFirstAllele()) + genotypeSubpopulationSizes.get(gt));
			alleleNumber.put(gt.getSecondAllele(),
					alleleNumber.get(gt.getSecondAllele()) + genotypeSubpopulationSizes.get(gt));
		}
		
		return (double) alleleNumber.get(al) / (getPopulationSize() * 2);
	}
	
	
	/**
	 * Get number of individuals in this generation with a given genotype
	 * 
	 * @param gt target genotype
	 * @return number of individuals in this generation with target genotype
	 */
	public int getGenotypeSubpopulationSize(Genotype gt) {
		return genotypeSubpopulationSizes.get(gt);
	}
	
	
	/**
	 * Set the number of individuals in this generation with a given genotype
	 * 
	 * @param gt target genotype
	 * @param size number of individuals of target genotype in this generation
	 */
	public void setGenotypeSubpopulationSize(Genotype gt, int size) {
		genotypeSubpopulationSizes.put(gt, size);
	}
	
	/**
	 * Get the number of individuals immigrating into this population of a
	 * given genotype
	 * 
	 * @param gt target genotype
	 * @return number of individuals immigrating of target genotype
	 */
	public int getImmigrationCount(Genotype gt) {
		return immigrations.get(gt);
	}
	
	
	/**
	 * Set the number of individuals immigrating into this population of a 
	 * given genotype
	 * 
	 * @param gt	target genotype
	 * @param count	number of individuals immigrating of target genotype
	 */
	public void setImmigrationCount(Genotype gt, int count) {
		immigrations.put(gt, count);
	}
	
	
	/**
	 * Get the number of individuals emigrating from this population of a given
	 * genotype
	 * 
	 * @param gt	target genotype
	 * @return		number of individuals emigrating fo target genotype
	 */
	public int getEmigrationCount(Genotype gt) {
		return emigrations.get(gt);
	}
	
	
	/**
	 * Sets the number of individuals emigrating from this population of a 
	 * given genotype
	 * 
	 * @param gt	target genotype
	 * @param count	number of individuals emigrating of target genotype
	 */
	public void setEmigrationCount(Genotype gt, int count) {
		emigrations.put(gt,  count);
	}
	
	
	/**
	 * Get number of mutations in this generation from one genotype to another
	 * @param from	original genotype
	 * @param to	mutated genotype
	 * @return		number of muations from 'from' to 'to'
	 */
	public int getMutationCount(Genotype from, Genotype to) {
		return mutations.get(from).get(to);
	}
	
	
	/**
	 * Set number of mutations in this generation from one genotype to another
	 * @param from	original genotype
	 * @param to	mutated genotype
	 * @param count	number of muations from 'from' to 'to'
	 */
	public void setMutationCount(Genotype from, Genotype to, int count) {
		if (!mutations.containsKey(from)) {
			mutations.put(from, new HashMap<Genotype, Integer>());
		}
		mutations.get(from).put(to, count);
	}
	
	
	/**
	 * Get number of births in this generation for a specific genotype
	 * 
	 * @param gt	target genotype
	 * @return		number of births in this generation for target genotype
	 */
	public int getBirths(Genotype gt) {
		return births.get(gt);
	}
	
	
	/**
	 * Set number of births in this generation for a specific genotype
	 * 
	 * @param gt	target genotype
	 * @param n		number of births in this generation for target genotype
	 */
	public void setBirths(Genotype gt, int n) {
		births.put(gt, n);
	}
	
	
	/**
	 * Get number of deaths in this generation for a specific genotype
	 * 
	 * @param gt	target genotype
	 * @return		number of deaths in this generation for target genotype
	 */
	public int getDeaths(Genotype gt) {
		return deaths.get(gt);
	}
	
	
	/**
	 * Set number of deaths in this generation for a specific genotype
	 * 
	 * @param gt	target genotype
	 * @param n		number of deaths in this generation for target genotype
	 */
	public void setDeaths(Genotype gt, int n) {
		deaths.put(gt, n);
	}
	
	/**
	 * Writes out a brief ASCII Generation Record for debugging purposes
	 * Includes Population Size, Number and Genotype Subpopulations for any Generation Record 
	 * 
	 */
	public void quickWrite() {
		System.out.println("-----------------------\nGeneration Record " + generationNumber + "\n-----------------------\n" + 
				"Population Size: " + getPopulationSize() + "\n" +
				"Genotype Subpopulations:");
		for (Genotype gt: Genotype.getValues()) {
			System.out.println(gt +  " - "  + String.valueOf(getGenotypeSubpopulationSize(gt)));
		}
		System.out.println("-----------------------");
	}
	
	public void pipedWrite(PrintStream s) {
		System.setOut(s);
		for (Genotype gt: Genotype.getValues()) {
			System.out.println(gt +  " - "  + String.valueOf(getGenotypeSubpopulationSize(gt)));
		}
	}
	
}
