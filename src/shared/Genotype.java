package shared;
/**
 * The acceptable genotypes to use.  Should be expanded upon for the addition of a third allele.
 * @author richwenner
 * @author taav-isaac
 */

public enum Genotype {
	AA, AB, BB;
	
	public Allele getAllele(int index)
	{
		if (index > 1 || index < 0){ assert false;} // add exception here later
		switch (this.name().charAt(index)){
			case 'A' : return Allele.A;
			case 'B' : return Allele.B;
		}
		assert false; // function should never make it here..
		return Allele.A;
	}
	public static Genotype getGenotype(Allele a1, Allele a2)
	{
		String genotypeName = a1.name().concat(a2.name());
		return Genotype.valueOf(genotypeName);
	}
}