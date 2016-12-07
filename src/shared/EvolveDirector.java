package shared;

import graphing.GraphType;
import graphing.GraphingEngine;
import graphing._2DGraphingManager;
import importexport.ExportFormat;
import importexport.ImportExportEngine;
import simulation.Population;
import simulation.PopulationManager;
import simulation.SimulationEngine;

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
	private boolean isInitialRun = true;
	
	
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
		
	public void export(ExportFormat format) {
		ImportExportEngine.getInstance().export(format);
	}
	
	
	/**
	 * Private constructor to disable normal instantiation
	 */
	private EvolveDirector() {
		
	}
	
	public void runSimulation() {
		SimulationEngine.getInstance().runSimulation();
	}
	
	public void resetSimulationEngine() {
		if (isInitialRun)
			isInitialRun = false;
		else {
			PopulationManager.getInstance().reset();
			DataManager.getInstance().reset();
			Population.resetPopulationCounter();
			_2DGraphingManager.destroyInstance();
		}
	}
	
	public void graph() {
		GraphingEngine.getInstance().generateGraph(GraphType._2D);		
	}
	
	public void storeSessionParameters(SessionParameters sp) {
		DataManager.getInstance().setSessionParams(sp);
	}
}
