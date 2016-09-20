package simulation;

/**
 * SimulationEngine is the top of level class of the simulation component of 
 * EVOLVE. This class orchestrates all action of the simulation component, 
 * delegating all work to more specialized components including managing 
 * populations and stepping through generations, as well as recording data to
 * DataManager. It is controlled by EvolveDirector.
 * 
 * @see shared.EvolveDirector
 * @see shared.DataManager
 * 
 * @author ericscollins
 *
 */

public class SimulationEngine {
	/**
	 * Member to enable singleton class
	 */
	public static SimulationEngine instance = null;
	
	
	/**
	 * Returns singleton instance of SimulationEngine
	 * @return singleton instance of SimulationEngine
	 */
	public static SimulationEngine getInstance() {
		if (instance == null) {
			instance  = new SimulationEngine();
		}
		return instance;
	}
	
	
	/**
	 * Private constructor to disable normal instantiation
	 */
	private SimulationEngine() {
		
	}

}
