package importexport;

import shared.DataManager;
import simulation.Population;
/**
 * @author ericscollins
 */
public class ExportManager {
	/**
	 * Enables singleton class
	 */
	private static ExportManager instance = null;
	
	
	/**
	 * Use this instead of constructor. Enables singleton class.
	 * 
	 * @return singleton instance of ExportManager
	 */
	public static ExportManager getInstance() {
		if (instance == null)
			instance = new ExportManager();
		return instance;
	}
	
	/**
	 * Private constructor to enable singelton class.
	 */
	private ExportManager() {}
	
	
	/**
	 * Inititates export process for a given format.
	 * 
	 * @param format to export to
	 */
	public void exportTo(ExportFormat format) {
		switch(format) {
		case CSV:
			CSVExport.getInstance().export(DataManager.getInstance().getSimulationData());
		}
	}
}
