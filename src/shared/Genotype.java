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
	AA(0), AB(1), BB(2);
	
	
	/**
	 *  Used to ensure pairings aren't overcounted during reproduction
	 */
	private int pairingIndex;
	
	
	/**
	 * Required to set pairing index value
	 * @param index pairing index
	 */
	private Genotype(int index) {
		pairingIndex = index;
	}
	
	
	/**
	 * Accessor for pairing index
	 * 
	 * @return pairing index of genotype
	 */
	public int getPairingIndex() {
		return pairingIndex;
	}

	
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