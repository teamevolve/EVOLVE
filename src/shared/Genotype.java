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
	AA(0), AB(1), AC(2), BB(3), BC(4), CC(5);
	
	
	/**
	 *  Used to ensure pairings aren't overcounted during reproduction
	 */
	private int pairingIndex;
	private static Genotype[] twoAlleleArray = {AA, AB, BB};
	
	
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
		case AC: return Allele.A;
		case BB: return Allele.B;
		case BC: return Allele.B;
		case CC: return Allele.C;
		
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
		case AC: return Allele.C;
		case BB: return Allele.B;
		case BC: return Allele.C;
		case CC: return Allele.C;
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
			switch (a1) {
			case A: return AA;
			case B: return BB;
			case C: return CC;
			default:
				return null;
			}
		else {
			switch (a1) {
			case A:
				if (a2 == Allele.B)
					return AB;
				else
					return AC;
			case B: 
				if (a2 == Allele.A)
					return AB;
				else
					return BC;
			case C:
				if (a2 == Allele.A)
					return AC;
				else 
					return BC;
			default:
				return null;
			}
		}
			
	}
	
	
	public static Genotype[] getValues() {
		if (DataManager.getInstance().getSessionParams().isThreeAlleles()) {
			return values();
		}
		else {
			return twoAlleleArray;
		}
	}
}