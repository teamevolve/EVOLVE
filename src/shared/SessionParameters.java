package shared;

/**
 * SessionParameters is a passive data structure used across the EVOLVE 
 * product, making parameters input by the user portable and accessible. To 
 * access these parameters, a method only needs to directly call an accessor  
 * method sinceSessionParameters since its data is stored and accessed 
 * statically. Its data is populated and changed in EvolveDirector and the  
 * import component, and elsewhere is only accessed.
 * 
 * e.g.
 * <code>
 * 		public void foo() {
 *			System.out.printf("The bar parameter has value %d.\n", 
 *                            SessionParameters.getBar());	
 *		}
 * </code>
 * 
 * @see EvolveDirector
 * 
 * @author ericscollins
 *
 */


// Should this be a static class directly available to all? Or should it be
// instantiated and accessed through DataManager? There's design benefits to
// both, with keeping it in DataManager being more organized, but making it
// globally available leads to more efficiency in both time and space.


public class SessionParameters {
	// all members should be static and private
	
	
		
	
	// each member should have a dedicated public static accessor and mutator 

	
	
}
