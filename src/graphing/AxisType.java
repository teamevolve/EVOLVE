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
}
