package simulation;


/**
 * PopulationManager is controlled mostly by SimulationEngine, and delegates
 * work to Population objects. Additionally, it logistically manages
 * Populations and handles inter-population exchanges using GeneFlow objects.
 * 
 * @see Population
 * @see SimulationEngine
 * @see GeneFlow
 * 
 * @author ericscollins
 *
 */

public class PopulationManager {
	/**
	 * Member to enable singleton class
	 */
	private static PopulationManager instance = null;
	
	
	/**
	 * Returns singleton instance of PopulationManager
	 * 
	 * @return single instance of populationManager
	 */
	public static PopulationManager getInstance() {
		if (instance == null) {
			instance = new PopulationManager();
		}
		return instance;
	}
	
	
	/**
	 * Private constructor to disable normal instantiation
	 */
	private PopulationManager() {
		
	}

}
