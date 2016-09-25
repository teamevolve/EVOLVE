package simulation;

import java.util.HashMap;
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
 */

public class GenerationRecord {

	final private int generationNumber;
	final private int parentPopID;
	private HashMap<Genotype, Integer> genotypeSubpopulationSizes;
	private HashMap<Genotype, Integer> immigrations;
	private HashMap<Genotype, Integer> emigrations;
	private HashMap<Genotype, HashMap<Genotype, Integer>> mutations;
	private HashMap<Genotype, Integer> births;
	private HashMap<Genotype, Integer> deaths;
	
	
	/**
	 * Constructor; initializes internal representations and sets IDs
	 * 
	 * @param popID		parent population's ID
	 * @param genNum	generation number
	 */
	public GenerationRecord(int popID, int genNum) {
		genotypeSubpopulationSizes = new HashMap<Genotype, Integer>();
		parentPopID = popID;
		generationNumber = genNum;
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
	public int getMuationCount(Genotype from, Genotype to) {
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
}
