package shared;

/**
 * Alleles are rarely used in the simulation, but are necessary when
 * calculating reproduction results. These values are used in conjunction
 * with the Genotype class.
 * 
 * @see Genotype
 * 
 * @author ericscollins
 */
public enum Allele {
	A, B, C;
	
	private static Allele[] twoAlleleArray = {A, B};
	private static Allele[] threeAlleleArray = {A, B, C};
	
	public static Allele[] getValues() {
		if (DataManager.getInstance().getSessionParams().isThreeAlleles())
			return threeAlleleArray;
		else
			return twoAlleleArray;
		
	}
	
}
