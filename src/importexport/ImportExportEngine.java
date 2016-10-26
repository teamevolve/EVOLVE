package importexport;

/**
 * ImportExportEngine is the top of level class of the import/export component
 * of EVOLVE. This class orchestrates all action of the import/export 
 * components, delegating all work to more specialized components that perform
 * actions such as parsing and loading simulation parameters or (eventually) 
 * Notebook files, and exporting simulation data to spreadsheets and 
 * (eventually) Notebook files. It is controlled by EvolveDirector.
 * 
 * @see shared.EvolveDirector
 * 
 * @author ericscollins
 *
 */

public class ImportExportEngine {
	/**
	 * Enables singelton class.
	 */
	private static ImportExportEngine instance = null;
	
	
	/**
	 * Use this instead of constructor. Enables singelton instance of class.
	 * 
	 * @return singleton instance of ImportExportEngine
	 */
	public static ImportExportEngine getInstance() {
		if (instance == null)
			instance = new ImportExportEngine();
		return instance;
	}
	
	
	/**
	 * Private constructor to enable singleton class.
	 */
	private ImportExportEngine() {}
	
	
	/**
	 * Exports data to a desired format.
	 * 
	 * @param format format to export to
	 */
	public void export(ExportFormat format) {
		ExportManager.getInstance().exportTo(format);
	}

}
