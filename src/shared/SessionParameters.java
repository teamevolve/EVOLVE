package shared;

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
 * 
 * @author ericscollins
 *
 */
public class SessionParameters {
	// all members should private
	
	private int popSize, seed;
	private boolean popConst, fixedMig;
	private double reproRateAA, reproRateAB, reproRateBB;
	private double survRateAA, survRateAB, survRateBB;
	private double absFitAA, absFitAB, absFitBB;
	private double relFitAA, relFitAB, relFitBB;
	private double mutAtoB, mutBtoA;
	private double migAA, migAB, migBB;
	private double freqA;
	private double freqAA, freqAB, freqBB;
	private double mateAAtoAA, mateAAtoAB, mateAAtoBB;
	private double mateABtoAA, mateABtoAB, mateABtoBB;
	private double mateBBtoAA, mateBBtoAB, mateBBtoBB;

	/* normal stuff */
	
	public void refresh(){
		
	}
	
	
	
	/* accessors and mutators */	
	public int getPopSize() {
		return popSize;
	}
	public void setPopSize(int popSize) {
		this.popSize = popSize;
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
	public double getReproRateAA() {
		return reproRateAA;
	}
	public void setReproRateAA(double reproRateAA) {
		this.reproRateAA = reproRateAA;
	}
	public double getReproRateBB() {
		return reproRateBB;
	}
	public void setReproRateBB(double reproRateBB) {
		this.reproRateBB = reproRateBB;
	}
	public double getReproRateAB() {
		return reproRateAB;
	}
	public void setReproRateAB(double reproRateAB) {
		this.reproRateAB = reproRateAB;
	}
	public double getSurvRateAB() {
		return survRateAB;
	}
	public void setSurvRateAB(double survRateAB) {
		this.survRateAB = survRateAB;
	}
	public double getSurvRateBB() {
		return survRateBB;
	}
	public void setSurvRateBB(double survRateBB) {
		this.survRateBB = survRateBB;
	}
	public double getSurvRateAA() {
		return survRateAA;
	}
	public void setSurvRateAA(double survRateAA) {
		this.survRateAA = survRateAA;
	}
	public double getAbsFitBB() {
		return absFitBB;
	}
	public void setAbsFitBB(double absFitBB) {
		this.absFitBB = absFitBB;
	}
	public double getAbsFitAA() {
		return absFitAA;
	}
	public void setAbsFitAA(double absFitAA) {
		this.absFitAA = absFitAA;
	}
	public double getAbsFitAB() {
		return absFitAB;
	}
	public void setAbsFitAB(double absFitAB) {
		this.absFitAB = absFitAB;
	}
	public double getRelFitAA() {
		return relFitAA;
	}
	public void setRelFitAA(double relFitAA) {
		this.relFitAA = relFitAA;
	}
	public double getRelFitBB() {
		return relFitBB;
	}
	public void setRelFitBB(double relFitBB) {
		this.relFitBB = relFitBB;
	}
	public double getRelFitAB() {
		return relFitAB;
	}
	public void setRelFitAB(double relFitAB) {
		this.relFitAB = relFitAB;
	}
	public double getMigAA() {
		return migAA;
	}
	public void setMigAA(double migAA) {
		this.migAA = migAA;
	}
	public double getMutAtoB() {
		return mutAtoB;
	}
	public void setMutAtoB(double mutAtoB) {
		this.mutAtoB = mutAtoB;
	}
	public double getMutBtoA() {
		return mutBtoA;
	}
	public void setMutBtoA(double mutBtoA) {
		this.mutBtoA = mutBtoA;
	}
	public double getFreqA() {
		return freqA;
	}
	public void setFreqA(double freqA) {
		this.freqA = freqA;
	}
	public double getMigAB() {
		return migAB;
	}
	public void setMigAB(double migAB) {
		this.migAB = migAB;
	}
	public double getMigBB() {
		return migBB;
	}
	public void setMigBB(double migBB) {
		this.migBB = migBB;
	}
	public double getMateAAtoAA() {
		return mateAAtoAA;
	}
	public void setMateAAtoAA(double mateAAtoAA) {
		this.mateAAtoAA = mateAAtoAA;
	}
	public double getFreqAB() {
		return freqAB;
	}
	public void setFreqAB(double freqAB) {
		this.freqAB = freqAB;
	}
	public double getFreqAA() {
		return freqAA;
	}
	public void setFreqAA(double freqAA) {
		this.freqAA = freqAA;
	}
	public double getFreqBB() {
		return freqBB;
	}
	public void setFreqBB(double freqBB) {
		this.freqBB = freqBB;
	}
	public double getMateAAtoBB() {
		return mateAAtoBB;
	}
	public void setMateAAtoBB(double mateAAtoBB) {
		this.mateAAtoBB = mateAAtoBB;
	}
	public double getMateAAtoAB() {
		return mateAAtoAB;
	}
	public void setMateAAtoAB(double mateAAtoAB) {
		this.mateAAtoAB = mateAAtoAB;
	}
	public double getMateABtoAB() {
		return mateABtoAB;
	}
	public void setMateABtoAB(double mateABtoAB) {
		this.mateABtoAB = mateABtoAB;
	}
	public double getMateABtoBB() {
		return mateABtoBB;
	}
	public void setMateABtoBB(double mateABtoBB) {
		this.mateABtoBB = mateABtoBB;
	}
	public double getMateABtoAA() {
		return mateABtoAA;
	}
	public void setMateABtoAA(double mateABtoAA) {
		this.mateABtoAA = mateABtoAA;
	}
	public double getMateBBtoAB() {
		return mateBBtoAB;
	}
	public void setMateBBtoAB(double mateBBtoAB) {
		this.mateBBtoAB = mateBBtoAB;
	}
	public double getMateBBtoBB() {
		return mateBBtoBB;
	}
	public void setMateBBtoBB(double mateBBtoBB) {
		this.mateBBtoBB = mateBBtoBB;
	}
	public double getMateBBtoAA() {
		return mateBBtoAA;
	}
	public void setMateBBtoAA(double mateBBtoAA) {
		this.mateBBtoAA = mateBBtoAA;
	}
	
}
