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
	private int populationID;
	private int populationSize;	
	private HashMap<Genotype, Double> genotypeFrequencies;
	private GeneFlow geneFlow;
	
	public GenerationRecord() {
		genotypeFrequencies = new HashMap<Genotype, Double>();
	}
	
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
	public double getGenotypeFreq(Genotype gt) {
		return genotypeFrequencies.get(gt);
	}
	public void setGenotypeFreq(Genotype gt, double freq) {
		genotypeFrequencies.put(gt, freq);
	}
	public GeneFlow getGeneFlow() {
		return geneFlow;
	}
	public void setGeneFlow(GeneFlow gf) {
		geneFlow = gf;
	}
	
	
	
}
