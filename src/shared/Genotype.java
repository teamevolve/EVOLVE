package shared;
/**
 * The acceptable genotypes to use.  Should be expanded upon for the addition of a third allele.
 * 
 * @see Allele
 * 
 * @author richwenner
 * @author taav-isaac
 * @author ericscollins
 */

public enum Genotype {
	AA, AB, BB;
	
	
	/**
	 * Returns the first allele, alphabetically, making up the genotype.
	 * 
	 * @return first allele in genotype
	 */
	public Allele getFirstAllele() {
		switch(this) {
		case AA: return Allele.A;
		case AB: return Allele.A;
		case BB: return Allele.B;
		default: return null;
		}
	}
	
	
	/**
	 * Returns the second allele, alphabetically, making up the genotype.
	 * 
	 * @return second allele in the genotype
	 */
	public Allele getSecondAllele() {
		switch(this) {
		case AA: return Allele.A;
		case AB: return Allele.B;
		case BB: return Allele.B;
		default: return null;
		}
	}
	
	
	/**
	 * Generates a genotype based on its composing alleles.
	 * 
	 * @param a1 an allele composing the desired genotype
	 * @param a2 an allele composing the desired genotype
	 * 
	 * @return the genotype composed of the given alleles
	 */
	public static Genotype genotypeFromAlleles(Allele a1, Allele a2) {
		if (a1 == a2)
			if (a1 == Allele.A)
				return AA;
			else
				return BB;
		else
			return AB;
	}
}