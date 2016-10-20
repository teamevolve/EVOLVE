package shared;


/**
 * DataManager is a mostly (possibly wholly) passive data structure used by 
 * components throughout EVOLVE as a repository for simulation data and
 * metadata including evolution data for each population, simulation 
 * parameters, and (possibly) information automatically calculated from
 * stored simulation data. Since DataManager is a singleton class, code similar
 * to the following must be used to use the object:
 * <br>
 * e.g.
 * <br>
 * <code>
 *     ...
 *     <br>
 *     DataManager dm = DataManager.getInstance();
 *     <br>
 *     ...
 * </code>
 * 
 * @author ericscollins
 *
 */
public class DataManager {
	
	/**
	 * Variable to ensure singleton nature of DataManager
	 */
	private static DataManager instance = null;
	/**
	 * Holds parameters input by user
	 */
	private SessionParameters sessionParams = null;
	
	
	/**
	 * Private constructor to prevent inappropriate instantiation. Use 
	 * getInstance() instead.
	 * 
	 * @see #getInstance()
	 */
	private DataManager() {}
	
	
	
	/**
	 * Returns the singelton instance of DataManager, instantiating it first if
	 * necessary.
	 * 
	 * @return singleton instance of DataManager
	 */
	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	
	
	/**
	 * Mutator for sessionParams member, used only by EvolveDirector and the 
	 * import component.
	 * 
	 * @param sp SessionParameters object to store
	 * 
	 * @see EvolveDirector
	 */
	public void setSessionParams(SessionParameters sp) {
		sessionParams = sp;
	}
	
	
	
	/**
	 * Accessor for sessionParams member.
	 * 
	 * @return returns stored SessionParameters object
	 */
	public SessionParameters getSessionParams() {
		return sessionParams;
	}
	
	
	/**
	 * Takes input parameters, and calculates constants used throughout
	 * the simulation.
	 */
	public void processSessionParams() {
		calculateMutationRates();
		calculateGenotypeFrequencies();
		calculateRelativeFitness();
	}
	
	
	/**
	 * Calculates genotype mutation rates based on allele mutation rates.
	 */
	private void calculateMutationRates() {
		// Populate self mutation data
		for (Allele a1 : Allele.getValues()) {
			double total = 0;
			for (Allele a2 : Allele.getValues()) {
				if (a1 != a2)
					total += sessionParams.getAlleleMutationRate(a1, a2);
			}
			sessionParams.setAlleleMutationRate(a1, a1, 1 - total);
		}
		
		// Generate genotype mutation data
		for (Genotype gt1 : Genotype.getValues()) {
			for (Genotype gt2 : Genotype.getValues()) {
				Allele a1_0 = gt1.getFirstAllele(), a1_1 = gt1.getSecondAllele();
				Allele a2_0 = gt2.getFirstAllele(), a2_1 = gt1.getSecondAllele();
				
				if (a1_0 == a1_1 && a2_0 == a2_1)
					sessionParams.setMutationRate(gt1, gt2, 
							Math.pow(sessionParams.getAlleleMutationRate(a1_0, a2_0), 2));
				else
					sessionParams.setMutationRate(gt1, gt2, 
							sessionParams.getAlleleMutationRate(a1_0, a2_0) * sessionParams.getAlleleMutationRate(a1_1, a2_1) +
							sessionParams.getAlleleMutationRate(a1_0, a2_1) * sessionParams.getAlleleMutationRate(a1_1, a2_0));
			}
		}
	}
	
	
	/**
	 * Calculates genotype frequencies from allele frequencies.
	 */
	private void calculateGenotypeFrequencies() {
		for (Genotype gt : Genotype.getValues()) {
			Allele a1 = gt.getFirstAllele(), a2 = gt.getSecondAllele();
			sessionParams.setGenotypeFrequency(gt, 
					sessionParams.getAlleleFrequency(a1) * 
					sessionParams.getAlleleFrequency(a2) *
					((a1 == a2) ? 1 : 2));
		}
	}
	
	
	/**
	 * Calculate relative fitness based on either absolute fitness or 
	 * reproduction and survival.
	 */
	private void calculateRelativeFitness() {
	}
}
