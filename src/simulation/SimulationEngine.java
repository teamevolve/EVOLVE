package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import shared.DataManager;
import shared.SessionParameters;

import static gui.GUI.DEBUG_MATE;
import static gui.GUI.DEBUG_REPRO;
import static gui.GUI.DEBUG_SELECTION;

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
	private static int counter = 0;

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
	
	public void setOutputStream() {
		counter = 1;
		SessionParameters sp = DataManager.getInstance().getSessionParams();
		String filename = String.format("debug-%s-%d.txt",sp.getTitle(), counter);
		File[] files = new File("output").listFiles();
		for (File file : files) {
			while (filename.equals(file.getName())) {
				counter ++;
				filename = String.format("debug-%s-%d.txt",sp.getTitle(), counter);
			}
		}
		
		filename = String.format("output/debug-%s-%d.txt",sp.getTitle(), counter);
		try {
			PrintStream original = new PrintStream(new File(filename));
			System.setOut(original);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void runSimulation() {
		if (DEBUG_MATE || DEBUG_REPRO || DEBUG_SELECTION) {
			setOutputStream();
		}
		for (int i = 0; i < DataManager.getInstance().getSessionParams().getNumGens(); i++) {
			PopulationManager.getInstance().processGeneration();
		}
		DataManager.getInstance().setSimilulationData(PopulationManager.getInstance().getPopulationList());
	}

	/**
	 * Private constructor to disable normal instantiation
	 */
	private SimulationEngine() {

	}

}
