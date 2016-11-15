package graphing;

import shared.Allele;
import shared.Genotype;

public enum FrequencyType implements AxisType {
	ALLELE_FREQ_A("Allele Freq. A"),
	ALLELE_FREQ_B("Allele Freq. B"),
	ALLELE_FREQ_C("Allele Freq. C"),
	GT_FREQ_AA("Genotype Freq. AA"),
	GT_FREQ_AB("Genotype Freq. AB"),
	GT_FREQ_AC("Genotype Freq. AC"),
	GT_FREQ_BB("Genotype Freq. BB"),
	GT_FREQ_BC("Genotype Freq. BC"),
	GT_FREQ_CC("Genotype Freq. CC");
	
	private String value;
	
	FrequencyType(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
	
	public boolean isGenotype() {
		if (this != ALLELE_FREQ_A && this != ALLELE_FREQ_B && this != ALLELE_FREQ_C)
			return true;
		else
			return false;
	}
	
	public boolean isAllele() {
		return !isGenotype();
	}

	
	public Allele getAllele() {
		switch(this) {
		case ALLELE_FREQ_A:
			return Allele.A;
		case ALLELE_FREQ_B:
			return Allele.B;
		case ALLELE_FREQ_C:
			return Allele.C;
		default:
			return null;
		}
	}
	
	public Genotype getGenotype() {
		switch(this) {
		case GT_FREQ_AA:
			return Genotype.AA;
		case GT_FREQ_AB:
			return Genotype.AB;
		case GT_FREQ_AC:
			return Genotype.AC;
		case GT_FREQ_BB:
			return Genotype.BB;
		case GT_FREQ_BC:
			return Genotype.BC;
		case GT_FREQ_CC:
			return Genotype.CC;
		default:
			return null;
		}
	}
	
	public static FrequencyType toEnum(Genotype gt) {
		switch (gt) {
		case AA:
			return GT_FREQ_AA;
		case AB:
			return GT_FREQ_AB;
		case AC:
			return GT_FREQ_AC;
		case BB:
			return GT_FREQ_BB;
		case BC:
			return GT_FREQ_BC;
		case CC:
			return GT_FREQ_CC;
		default:
			return null;
		}
	}
	
	
	public static FrequencyType toEnum(Allele a) {
		switch(a) {
		case A:
			return ALLELE_FREQ_A;
		case B:
			return ALLELE_FREQ_B;
		case C:
			return ALLELE_FREQ_C;
		default:
			return null;	
		}
	}
}