package shared;

import java.io.Serializable;
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
public class SessionParameters implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int popSize, numPops, numGens, popCapacity, crashCapacity;
	private long seed;
	private String title, question, design, results, discuss;
	private boolean popConst, fixedMig;
	private HashMap<Allele, Double> alleleFrequencies;
	private HashMap<Genotype, Double> reproductionRates;
	private HashMap<Genotype, Double> survivalRates;
	private HashMap<Genotype, Double> absoluteFitnesses;
	private HashMap<Genotype, Double> relativeFitnesses;
	private HashMap<Genotype, HashMap<Genotype, Double>> mutationRates;
	private HashMap<Allele, HashMap<Allele, Double>> alleleMutationRates;
	private HashMap<Genotype, Double> migrationRates;
	private HashMap<Genotype, Double> genotypeFrequencies;
	private HashMap<Genotype, HashMap<Genotype, Double>> sexualSelectionRates;
	private boolean popSizeChecked, selectChecked, mutationChecked, 
					migrationChecked, sexSelectChecked, threeAlleles;

	/* normal stuff */
	
	public void refresh(){
		
	}
	
	public SessionParameters() {
		reproductionRates = new HashMap<Genotype, Double>();
		survivalRates = new HashMap<Genotype, Double>();
		absoluteFitnesses = new HashMap<Genotype, Double>();
		relativeFitnesses = new HashMap<Genotype, Double>();
		mutationRates = new HashMap<Genotype, HashMap<Genotype, Double>>();
		alleleMutationRates = new HashMap<Allele, HashMap<Allele, Double>>();
		migrationRates = new HashMap<Genotype, Double>();
		genotypeFrequencies = new HashMap<Genotype, Double>();
		sexualSelectionRates = new HashMap<Genotype, HashMap<Genotype, Double>>();
		alleleFrequencies = new HashMap<Allele, Double>();
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
	public int getNumGens() {
		return numGens;
	}

	public void setNumGens(int numGens) {
		this.numGens = numGens;
	}

	public long getSeed() {
		return seed;
	}
	public void setSeed(long seed) {
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
	public double getAlleleMutationRate(Allele a1, Allele a2) {
		return alleleMutationRates.get(a1).get(a2);
	}
	public void setAlleleMutationRate(Allele a1, Allele a2, double rate){
		if (!alleleMutationRates.containsKey(a1)) {
			alleleMutationRates.put(a1, new HashMap<Allele, Double>());
		}
		alleleMutationRates.get(a1).put(a2, rate);
	}
	public double getMutationRate(Genotype gt1, Genotype gt2) {
		return mutationRates.get(gt1).get(gt2);
	}	
	public void setMutationRate(Genotype gt1, Genotype gt2, double rate) {
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
	public void setPopCapacity(int pop) {
		this.popCapacity = pop;
	}
	public int getCrashCapacity() {
		return crashCapacity;
	}
	public void setCrashCapacity(int pop) {
		this.crashCapacity = pop;
	}

	public boolean isPopSizeChecked() {
		return popSizeChecked;
	}

	public void setPopSizeChecked(boolean popSizeChecked) {
		this.popSizeChecked = popSizeChecked;
	}
	
	public boolean isSelectChecked() {
		return selectChecked;
	}

	public void setSelectChecked(boolean selectChecked) {
		this.selectChecked = selectChecked;
	}

	public boolean isMutationChecked() {
		return mutationChecked;
	}

	public void setMutationChecked(boolean mutationChecked) {
		this.mutationChecked = mutationChecked;
	}

	public boolean isMigrationChecked() {
		return migrationChecked;
	}

	public void setMigrationChecked(boolean migrationChecked) {
		this.migrationChecked = migrationChecked;
	}
	
	public boolean isSexSelectChecked() {
		return sexSelectChecked;
	}

	public void setSexSelectChecked(boolean sexSelectChecked) {
		this.sexSelectChecked = sexSelectChecked;
	}

	public boolean isThreeAlleles() {
		return threeAlleles;
	}

	public void setThreeAlleles(boolean threeAlleles) {
		this.threeAlleles = threeAlleles;
	}
	
	public double getAlleleFrequency(Allele a) {
		return alleleFrequencies.get(a);
	}
	
	public void setAlleleFrequency(Allele a, double freq) {
		alleleFrequencies.put(a, freq);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDesign() {
		return design;
	}

	public void setDesign(String design) {
		this.design = design;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

}
