package shared;



/**
 * DataManager is a mostly (possibly wholly) passive data structure used by 
 * components throughout EVOLVE as a repository for simulation data and
 * metadata including evolution data for each population, simulation 
 * parameters, and (possibly) information automatically calculated from
 * stored simulation data. Since DataManager is a singleton class, code similar
 * to the following must be used to use the object:
 * <br>
 * e.g.
 * <br>
 * <code>
 *     ...
 *     <br>
 *     DataManager dm = DataManager.getInstance();
 *     <br>
 *     ...
 * </code>
 * 
 * @author ericscollins
 *
 */
public class DataManager {
	
	/**
	 * Variable to ensure singleton nature of DataManager
	 */
	private static DataManager instance = null;
	/**
	 * Holds parameters input by user
	 */
	private SessionParameters sessionParams = null;
	
	
	
	/* ********************************************************************* */
	
	
	
	/**
	 * Private constructor to prevent inappropriate instantiation. Use 
	 * getInstance() instead.
	 * 
	 * @see #getInstance()
	 */
	private DataManager() {}
	
	
	
	/**
	 * Returns the singelton instance of DataManager, instantiating it first if
	 * necessary.
	 * 
	 * @return singleton instance of DataManager
	 */
	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	
	
	/**
	 * Mutator for sessionParams member, used only by EvolveDirector and the 
	 * import component.
	 * 
	 * @param sp SessionParameters object to store
	 * 
	 * @see EvolveDirector
	 */
	public void setSessionParams(SessionParameters sp) {
		sessionParams = sp;
	}
	
	
	
	/**
	 * Accessor for sessionParams member.
	 * 
	 * @return returns stored SessionParameters object
	 */
	public SessionParameters getSessionParams() {
		return sessionParams;
	}
}
