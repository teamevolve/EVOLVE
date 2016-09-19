package simulation;

import java.util.HashMap;
import shared.Genotype;



/**
 * GeneFlow is a container used by PopulationManager to store data regarding 
 * gene flow from migrations and from mutations. Once created it is stored in a
 * GenerationInfo object by the Population that received it.
 * 
 * @see PopulationManager
 * @see Population
 * @see GenerationInfo
 * 
 * @author ericscollins
 *
 */
public class GeneFlow {
	/**
	 * Gene flow maps for in flows, out flows, and mutations
	 */
	private HashMap<Genotype, Integer> immigrations;
	private HashMap<Genotype, Integer> emigrations;
	private HashMap<Genotype, HashMap<Genotype, Integer>> mutations;
	
	
	
	/**
	 * Constructor: initializes the three gene flow maps
	 */
	public GeneFlow() {
		immigrations = new HashMap<Genotype, Integer>();
		emigrations  = new HashMap<Genotype, Integer>();
		mutations    = new HashMap<Genotype, HashMap<Genotype, Integer>>();
	}
	
	
	/**
	 * Accessor for immigration counts
	 * 
	 * @param gt genotype to get immigration count for
	 * @return immigration count for given genotype
	 */
	public int getInflow(Genotype gt) {
		return immigrations.get(gt);
	}
	
	
	/**
	 * Accessor for emmigration counts
	 * 
	 * 
	 * @param gt genotype to get emigration count for
	 * @return emmigration count for given genotype
	 */
	public int getOutflow(Genotype gt) {
		return emigrations.get(gt);
	}
	
	
	/**
	 * Accessor for mutation counts
	 * 
	 * @param from genotype that mutation affected
	 * @param to resulting genotype
	 * @return from -> to mutation count
	 */
	public int getMutations(Genotype from, Genotype to) {
		return mutations.get(from).get(to);
	}
	
	
	/**
	 * Mutator for immigration counts
	 * 
	 * @param gt genotype to set immigration count for
	 * @param n number of immigrating individuals
	 */
	public void setImmigration(Genotype gt, int n) {
		immigrations.put(gt, n);
	}
	
	
	/**
	 * Mutator for emigration counts
	 * 
	 * @param gt genotypte to set emigration count for
	 * @param n number of emigrating individuals
	 */
	public void setEmigration(Genotype gt, int n) {
		emigrations.put(gt,  n);
	}
	
	
	/**
	 * Mutator for mutation counts
	 * 
	 * @param from genotype affected by mutation
	 * @param to resulting genotype
	 * @param n number of mutations from -> to
	 */
	public void setMutations(Genotype from, Genotype to, int n) {
		if (!mutations.containsKey(from)) {
			mutations.put(from, new HashMap<Genotype, Integer>());
		}
		mutations.get(from).put(to, n);
	}
}
