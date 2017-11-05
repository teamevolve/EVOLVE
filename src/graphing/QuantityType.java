package graphing;

import java.awt.Color;

public enum QuantityType implements AxisType {
	POPSIZE("Population Size"), 
//	SUBPOPSIZE_AA("Subpop Size of AA"),
//	SUBPOPSIZE_AB("Subpop Size of AB"),
//	SUBPOPSIZE_BB("Subpop Size of BB"),
//	SUBPOPSIZE_AC("Subpop Size of AC"),
//	SUBPOPSIZE_BC("Subpop Size of BC"),
//	SUBPOPSIZE_CC("Subpop Size of CC"),

	IMMIGRATION("Immigration"), 
//	IMMIGRATION_AA("Immigration of AA"), 
//	IMMIGRATION_AB("Immigration of AB"), 
//	IMMIGRATION_BB("Immigration of BB"), 
//	IMMIGRATION_AC("Immigration of AC"), 
//	IMMIGRATION_BC("Immigration of BC"), 
//	IMMIGRATION_CC("Immigration of CC"), 

	EMIGRATION("Emigration"), 
//	EMIGRATION_AA("Emigration of AA"), 
//	EMIGRATION_AB("Emigration of AB"), 
//	EMIGRATION_BB("Emigration of BB"), 
//	EMIGRATION_AC("Emigration of AC"), 
//	EMIGRATION_BC("Emigration of BC"), 
//	EMIGRATION_CC("Emigration of CC"), 

	NETMIGRATION("Net Migration"), 
//	NETMIGRATION_AA("Net Migration of AA"), 
//	NETMIGRATION_AB("Net Migration of AB"), 
//	NETMIGRATION_BB("Net Migration of BB"), 
//	NETMIGRATION_AC("Net Migration of AC"), 
//	NETMIGRATION_BC("Net Migration of BC"), 
//	NETMIGRATION_CC("Net Migration of CC"), 

	MUTATION("Num Mutations"), 
//	MUTATION_AA("Num Mutations of AA"), 
//	MUTATION_AB("Num Mutations of AB"), 
//	MUTATION_BB("Num Mutations of BB"), 
//	MUTATION_AC("Num Mutations of AC"), 
//	MUTATION_BC("Num Mutations of BC"), 
//	MUTATION_CC("Num Mutations of CC"), 

	BIRTHS("Births"), 
//	BIRTHS_AA("Births of AA"), 
//	BIRTHS_AB("Births of AB"), 
//	BIRTHS_BB("Births of BB"), 
//	BIRTHS_AC("Births of AC"), 
//	BIRTHS_BC("Births of BC"), 
//	BIRTHS_CC("Births of CC"), 

	DEATHS("Deaths");
//	DEATHS_AA("Deaths of AA"),
//	DEATHS_AB("Deaths of AB"),
//	DEATHS_BB("Deaths of BB"),
//	DEATHS_AC("Deaths of AC"),
//	DEATHS_BC("Deaths of BC"),
//	DEATHS_CC("Deaths of CC");

	private String value;
	
	private static final Color POPSIZE_COLOR = Color.getHSBColor(166,206,227);
	private static final Color SUBPOPSIZE_AA_COLOR = Color.getHSBColor(31,120,180);
	
	QuantityType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
//	public boolean isSubpop() {
//		if (this == SUBPOPSIZE_AA || this == SUBPOPSIZE_AB || this == SUBPOPSIZE_AC || 
//			this == SUBPOPSIZE_BC || this == SUBPOPSIZE_BB || this == SUBPOPSIZE_CC)
//			return true;
//		return false;
//	}
//		
//	public boolean isSubImmi() {
//		if (this == IMMIGRATION_AA || this == IMMIGRATION_AB || this == IMMIGRATION_AC || 
//			this == IMMIGRATION_BB || this == IMMIGRATION_BC || this == IMMIGRATION_CC)
//			return true;
//		return false;
//	}
//	
//	public boolean isSubEmi() {
//		if (this == EMIGRATION_AA || this == EMIGRATION_AB || this == EMIGRATION_AC || 
//			this == EMIGRATION_BB || this == EMIGRATION_BC || this == EMIGRATION_CC)
//			return true;
//		return false;
//	}
//	
//	public boolean isSubMig() {
//		if (this == NETMIGRATION_AA || this == NETMIGRATION_AB || this == NETMIGRATION_AC || 
//			this == NETMIGRATION_BB || this == NETMIGRATION_BC || this == NETMIGRATION_CC)
//			return true;
//		return false;
//	}
//	
//	public boolean isSubMut() {
//		if (this == MUTATION_AA || this == MUTATION_AB || this == MUTATION_AC || 
//			this == MUTATION_BB || this == MUTATION_BC || this == MUTATION_CC)
//			return true;
//		return false;
//	}
//	
//	public boolean isSubBirth() {
//		if (this == BIRTHS_AA || this == BIRTHS_AB || this == BIRTHS_AC || 
//			this == BIRTHS_BB || this == BIRTHS_BC || this == BIRTHS_CC)
//			return true;
//		return false;
//	}
//	
//	public boolean isSubDeath() {
//		if (this == DEATHS_AA || this == DEATHS_AB || this == DEATHS_AC || 
//			this == DEATHS_BB || this == DEATHS_BC || this == DEATHS_CC)
//			return true;
//		return false;
//	}
	
	public static Color getColor(AxisType type) {
		if (type == POPSIZE)
			return POPSIZE_COLOR;
//		else if (type ==  SUBPOPSIZE_AA)
//			return SUBPOPSIZE_AA_COLOR;
		else
			return Color.BLACK;
		
	}
}
