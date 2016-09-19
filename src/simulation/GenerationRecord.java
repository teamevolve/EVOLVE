package simulation;

import java.util.HashMap;

/**
 * GenerationRecord is a data holding class used by Population. This contains 
 * the information about the information of each generation, including 
 * population number, genotype frequency, and so on. This class is created for 
 * each generation and stored in a list in Population.  
 * 
 * @author richwenner
 *
 */

public class GenerationRecord {

	private int generationNumber;
	private int populationID;
	private int populationSize;	
	private HashMap<String, Float> genotypeFrequencies;
	private GeneFlow geneFlow;
	
	//Getters & Setters
	public int getGenerationNumber() {
		return generationNumber;
	}
	public void setGenerationNumber(int genNumber) {
		generationNumber = genNumber;
	}
	public int getPopulationID() {
		return populationID;
	}
	public void setPopulationID(int popID) {
		populationID = popID;
	}
	public int getPopulationSize() {
		return populationSize;
	}
	public void setPopulationSize(int popSize) {
		populationSize = popSize;
	}
	public HashMap<String, Float> getGenotypeFrequencies() {
		return genotypeFrequencies;
	}
	public void setGenotypeFrequencies(HashMap<String, Float> gtFreq) {
		genotypeFrequencies = gtFreq;
	}
	public GeneFlow getGeneFlow() {
		return geneFlow;
	}
	public void setGeneFlow(GeneFlow gf) {
		geneFlow = gf;
	}
	
	
	
}
