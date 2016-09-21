package simulation;

import java.util.HashMap;
import shared.Genotype;



/**
 * GenerationRecord is a data holding class used by Population. This contains 
 * the information about the information of each generation, including 
 * population number, genotype frequency, and so on. This class is created for 
 * each generation and stored in a list in Population.  
 * 
 * @author richwenner
 * @author ericscollins
 *
 */

public class GenerationRecord {

	private int generationNumber;
	private int parentPopID;
	private HashMap<Genotype, Integer> genotypeSubpopulationSizes;
	private HashMap<Genotype, Integer> immigrations;
	private HashMap<Genotype, Integer> emigrations;
	private HashMap<Genotype, HashMap<Genotype, Integer>> mutations;
	private HashMap<Genotype, Integer> births;
	private HashMap<Genotype, Integer> deaths;
	
	public GenerationRecord(int popID, int genNum) {
		genotypeSubpopulationSizes = new HashMap<Genotype, Integer>();
		parentPopID = popID;
		generationNumber = genNum;
	}
	
	//Getters & Setters
	public int getGenerationNumber() {
		return generationNumber;
	}	
	
	public int getParentPopID() {
		return parentPopID;
	}

	public int getPopulationSize() {
		int populationSize = 0;
		for (int i : genotypeSubpopulationSizes.values()) {
			populationSize += i;
		}
		return populationSize;		
	}
	
	public double getGenotypeFreq(Genotype gt) {
		return (double) genotypeSubpopulationSizes.get(gt) / (double) getPopulationSize();
	}
	
	public int getGenotypeSubpopulationSize(Genotype gt) {
		return genotypeSubpopulationSizes.get(gt);
	}
	public void setGenotypeSubpopulationSize(Genotype gt, int size) {
		genotypeSubpopulationSizes.put(gt, size);
	}
	
	public int getImmigrationCount(Genotype gt) {
		return immigrations.get(gt);
	}
	public void setImmigrationCount(Genotype gt, int count) {
		immigrations.put(gt, count);
	}
	
	public int getEmigrationCount(Genotype gt) {
		return emigrations.get(gt);
	}
	public void setEmigrationCount(Genotype gt, int count) {
		emigrations.put(gt,  count);
	}
	
	public int getMuationCount(Genotype from, Genotype to) {
		return mutations.get(from).get(to);
	}
	public void setMutationCount(Genotype from, Genotype to, int count) {
		if (!mutations.containsKey(from)) {
			mutations.put(from, new HashMap<Genotype, Integer>());
		}
		mutations.get(from).put(to, count);
	}
	
	public int getBirths(Genotype gt) {
		return births.get(gt);
	}
	public void setBirths(Genotype gt, int n) {
		births.put(gt, n);
	}
	
	public int getDeaths(Genotype gt) {
		return deaths.get(gt);
	}
	public void setDeaths(Genotype gt, int n) {
		deaths.put(gt, n);
	}
}
