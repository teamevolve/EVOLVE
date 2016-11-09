package simulation;

import shared.DataManager;

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

	public void runSimulation() {
		System.out.println("Starting simulation...");
		for (int i = 0; i < DataManager.getInstance().getSessionParams().getNumGens(); i++) {
			PopulationManager.getInstance().processGeneration();
		}
		System.out.println("Finishing simulation...");
		DataManager.getInstance().setSimilulationData(PopulationManager.getInstance().getPopulationList());
		System.out.println(DataManager.getInstance().getSimulationData());
	}

	/**
	 * Private constructor to disable normal instantiation
	 */
	private SimulationEngine() {

	}

}
