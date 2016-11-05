package graphing;

public enum AxisType {
	POPSIZE("Population Size"), 
	IMMIGRATION("Immigration"), 
	EMIGRATION("Emigration"), 
	NETMIGRATION("Net Migration"), 
	MUTATION("Num Mutations"), 
	BIRTHS("Births"), 
	DEATHS("Deaths"),
	ALLELEFREQ("Allele Frequency");
	
	private String value;
	AxisType(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
	
	public static AxisType toEnum(String s) {
		switch(s) {
		case "Population Size":
			return POPSIZE;
		case "Immigration":
			return IMMIGRATION;
		case "Emigration":
			return EMIGRATION;
		case "Net Migration":
			return NETMIGRATION;
		case "Num Mutations":
			return MUTATION;
		case "Births":
			return BIRTHS;
		case "Deaths":
			return DEATHS;
		case "Allele Frequency":
			return ALLELEFREQ;
		default:
			return ALLELEFREQ;	
		}
		
	}
}
