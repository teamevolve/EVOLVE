package shared;

/*
import shared.SessionParameters;
*/

/**
 * EvolveDirector is the interface, or "middleman" between the GUI of EVOLVE
 * and its back-end. EvolveDirector plugs directly into the engine for each 
 * module of the product, issuing commands to each per the user's interaction 
 * with the GUI. It also stores simulation parameters into a SessionParameters
 * object which it then stores in DataManager.
 * 
 * @see DataManager
 * @see SessionParameters
 * @see simulation.SimulationEngine
 * @see graphing.GraphingEngine
 * @see importexport.ImportExportEngine
 * @see help.HelpEngine
 * @see debug.DebugEngine
 * 
 * @author ericscollins
 * 
 */

public class EvolveDirector {
	/**
	 * Member to enable singleton class
	 */
	private static EvolveDirector instance = null;
	
	
	
	/**
	 * Returns a singleton instance of EvolveDirector
	 * 
	 * @return a singleton instance of EvolveDirector
	 */
	public static EvolveDirector getInstance() {
		if (instance == null) {
			instance = new EvolveDirector();
		}
		return instance;
	}
	
	
	/**
	 * Private constructor to disable normal instantiation
	 */
	private EvolveDirector() {
		
	}
	

}
