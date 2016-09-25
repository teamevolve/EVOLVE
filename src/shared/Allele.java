package shared;

import java.util.HashMap;

public enum Allele {
	A, B;
	
	public HashMap<Allele, Double> mutationRate = new HashMap<Allele, Double>();
	public void setMutationRate(Allele a, double rate) {
		mutationRate.put(a, rate);
	}
	public double getMutationRate(Allele a) {
		return mutationRate.get(a);
	}
	
}
