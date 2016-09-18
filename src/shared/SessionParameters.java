package shared;

/**
 * SessionParameters is a passive data structure used across the EVOLVE 
 * product, making parameters input by the user portable and accessible. Its 
 * data is populated and changed in EvolveDirector and the import component, 
 * and elsewhere is only accessed. To access these parameters, a method only 
 * needs to obtain its instance from DataManager, then use the appropriate 
 * accessor or mutator.
 * <br>
 * e.g.
 * <br>
 * <code>
 * 		...
 * <br>
 *		Bar bar = DataManager.getInstance().getSessionParams().getBar()); 
 * <br>
 *      ...	
 * </code>
 * 
 * 
 * @see EvolveDirector
 * @see DataManager
 * 
 * 
 * @author ericscollins
 *
 */
public class SessionParameters {
	// all members should private
	
	
		
	
	// each member should have a dedicated public accessor and mutator 

	
	
}
