package graphing;

public enum QuantityType implements AxisType {
	POPSIZE("Population Size"), 
	IMMIGRATION("Immigration"), 
	EMIGRATION("Emigration"), 
	NETMIGRATION("Net Migration"), 
	MUTATION("Num Mutations"), 
	BIRTHS("Births"), 
	DEATHS("Deaths");
	
	private String value;
	
	QuantityType(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return value;
	}
}
