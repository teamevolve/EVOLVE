package simulation;

import java.util.HashMap;

/**
 * GenerationInfo is a data holding class used by Population.  This contains the information about the information of
 * each generation, including population number, genotype frequency, and so on. This class is created for each 
 * generation and stored in a list in Population.  
 * @author richwenner
 *
 */

public class GenerationInfo {

	private int generationNumber;
	private int populationID;
	private int populationSize;	
	private HashMap<String, Float> genotypeFrequencies;
	private int mutationCount;
	private int inFlow, outFlow;
	
	//Getters & Setters
	public int getGenerationNumber() {
		return generationNumber;
	}
	public void setGenerationNumber(int generationNumber) {
		this.generationNumber = generationNumber;
	}
	public int getPopulationID() {
		return populationID;
	}
	public void setPopulationID(int populationID) {
		this.populationID = populationID;
	}
	public int getPopulationSize() {
		return populationSize;
	}
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	public HashMap<String, Float> getGenotypeFrequencies() {
		return genotypeFrequencies;
	}
	public void setGenotypeFrequencies(HashMap<String, Float> genotypeFrequencies) {
		this.genotypeFrequencies = genotypeFrequencies;
	}
	public int getMutationCount() {
		return mutationCount;
	}
	public void setMutationCount(int mutationCount) {
		this.mutationCount = mutationCount;
	}
	public int getInFlow() {
		return inFlow;
	}
	public void setInFlow(int inFlow) {
		this.inFlow = inFlow;
	}
	public int getOutFlow() {
		return outFlow;
	}
	public void setOutFlow(int outFlow) {
		this.outFlow = outFlow;
	}
	
	
	
}
