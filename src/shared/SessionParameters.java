package shared;

import java.util.HashMap;

/**
 * SessionParameters is a passive data structure used across the EVOLVE 
 * product, making parameters input by the user portable and accessible. Its 
 * data is populated and changed in EvolveDirector and the import component, 
 * and elsewhere is only accessed. To access these parameters, a method only 
 * needs to obtain its instance from DataManager, then use the appropriate 
 * accessor or mutator.
 * <br>
 * e.g.
 * <br>
 * <code>
 * 		...
 * <br>
 *		Bar bar = DataManager.getInstance().getSessionParams().getBar()); 
 * <br>
 *      ...	
 * </code>
 * 
 * 
 * @see EvolveDirector
 * @see DataManager
 * 
 * @author linneasahlberg
 * @author jasonfortunato
 * @author ericscollins
 * @author richwenner
 *
 */
public class SessionParameters {
	// all members should private
	
	private int popSize, seed, numPops, popCapacity, crashCapacity;
	private boolean popConst, fixedMig;
	private HashMap<Genotype, Double> reproductionRates;
	private HashMap<Genotype, Double> survivalRates;
	private HashMap<Genotype, Double> absoluteFitnesses;
	private HashMap<Genotype, Double> relativeFitnesses;
	private HashMap<Genotype, HashMap<Genotype, Double>> mutationRates;
	private HashMap<Genotype, Double> migrationRates;
	private HashMap<Genotype, Double> genotypeFrequencies;
	private HashMap<Genotype, HashMap<Genotype, Double>> sexualSelectionRates;

	/* normal stuff */
	
	public void refresh(){
		
	}
	
	public SessionParameters() {
		reproductionRates = new HashMap<Genotype, Double>();
		survivalRates = new HashMap<Genotype, Double>();
		absoluteFitnesses = new HashMap<Genotype, Double>();
		relativeFitnesses = new HashMap<Genotype, Double>();
		mutationRates = new HashMap<Genotype, HashMap<Genotype, Double>>();
		migrationRates = new HashMap<Genotype, Double>();
		genotypeFrequencies = new HashMap<Genotype, Double>();
		sexualSelectionRates = new HashMap<Genotype, HashMap<Genotype, Double>>();
	}
	
	/* accessors and mutators */	
	public int getPopSize() {
		return popSize;
	}
	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}
	public int getNumPops() {
		return numPops;
	}
	public void setNumPops(int n) {
		numPops = n;
	}
	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}
	public boolean isFixedMig() {
		return fixedMig;
	}
	public void setFixedMig(boolean fixedMig) {
		this.fixedMig = fixedMig;
	}
	public boolean isPopConst() {
		return popConst;
	}
	public void setPopConst(boolean popConst) {
		this.popConst = popConst;
	}
	public double getReproductionRate(Genotype gt) {
		return reproductionRates.get(gt);
	}
	public void setReproductionRate(Genotype gt, double rate) {
		reproductionRates.put(gt,  rate);
	}
	public double getSurvivalRate(Genotype gt) {
		return survivalRates.get(gt);
	}
	public void setSurvivalRate(Genotype gt, double rate) {
		survivalRates.put(gt,  rate);
	}
	public double getAbsoluteFitness(Genotype gt) {
		return absoluteFitnesses.get(gt);
	}
	public void setAbsoluteFitness(Genotype gt, double fitness) {
		absoluteFitnesses.put(gt, fitness);
	}
	public double getRelativeFitness(Genotype gt) {
		return relativeFitnesses.get(gt);
	}
	public void setRelativeFitness(Genotype gt, double fitness) {
		relativeFitnesses.put(gt, fitness);
	}
	public double getMigrationRate(Genotype gt) {
		return migrationRates.get(gt);
	}
	public void setMigrationRate(Genotype gt, double rate) {
		migrationRates.put(gt, rate);
	}
	public double getMutationRate(Genotype gt1, Genotype gt2) {
		return mutationRates.get(gt1).get(gt2);
	}
	public void setMutationRate(Genotype gt1, Genotype gt2, double rate) {
		System.out.println();
		if (!mutationRates.containsKey(gt1)) {
			mutationRates.put(gt1, new HashMap<Genotype, Double>());
		}
		mutationRates.get(gt1).put(gt2, rate);
	}
	public double getGenotypeFrequency(Genotype gt) {
		return genotypeFrequencies.get(gt);
	}
	public void setGenotypeFrequency(Genotype gt, double freq) {
		genotypeFrequencies.put(gt, freq);
	}
	public double getSexualSelectionRate(Genotype gt1, Genotype gt2) {
		return sexualSelectionRates.get(gt1).get(gt2);
	}
	public void setSexualSelectionRate(Genotype gt1, Genotype gt2, double rate) {
		if (!sexualSelectionRates.containsKey(gt1)) {
			sexualSelectionRates.put(gt1, new HashMap<Genotype, Double>());
		}
		sexualSelectionRates.get(gt1).put(gt2, rate);
	}

	public int getPopCapacity() {
		return popCapacity;
	}

	public void setPopCapacity(int popCapacity) {
		this.popCapacity = popCapacity;
	}

	public int getCrashCapacity() {
		return crashCapacity;
	}

	public void setCrashCapacity(int crashCapacity) {
		this.crashCapacity = crashCapacity;
	}
}
