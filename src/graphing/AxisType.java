package graphing;

public interface AxisType {
	@Override
	public String toString();
	public static AxisType toEnum(String s) {
		switch(s) {
		case "Population Size":
			return QuantityType.POPSIZE;
//		case "Subpop Size of AA":
//			return QuantityType.SUBPOPSIZE_AA;
//		case "Subpop Size of AB":
//			return QuantityType.SUBPOPSIZE_AB;
//		case "Subpop Size of BB":
//			return QuantityType.SUBPOPSIZE_BB;
		case "Immigration":
			return QuantityType.IMMIGRATION;
		case "Emigration":
			return QuantityType.EMIGRATION;
		case "Net Migration":
			return QuantityType.NETMIGRATION;
		case "Num Mutations":
			return QuantityType.MUTATION;
		case "Births":
			return QuantityType.BIRTHS;
		case "Deaths":
			return QuantityType.DEATHS;
		case "Allele Freq. A":
			return FrequencyType.ALLELE_FREQ_A;
		case "Allele Freq. B":
			return FrequencyType.ALLELE_FREQ_B;
		case "Allele Freq. C":
			return FrequencyType.ALLELE_FREQ_C;
		case "Change in Allele Freq. A":
			return FrequencyType.D_ALLELE_FREQ_A;
		case "Change in Allele Freq. B":
			return FrequencyType.D_ALLELE_FREQ_B;
		case "Change in Allele Freq. C":
			return FrequencyType.D_ALLELE_FREQ_C;
		case "Genotype Freq. AA":
			return FrequencyType.GT_FREQ_AA;
		case "Genotype Freq. AB":
			return  FrequencyType.GT_FREQ_AB;
		case "Genotype Freq. AC":
			return FrequencyType.GT_FREQ_AC;
		case "Genotype Freq. BB":
			return FrequencyType.GT_FREQ_BB;
		case "Genotype Freq. BC":
			return FrequencyType.GT_FREQ_BC;
		case "Genotype Freq. CC":
			return FrequencyType.GT_FREQ_CC;
		default:
			return null;
		}


	}
}
