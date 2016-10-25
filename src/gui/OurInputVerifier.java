package gui;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * We'll use this to verify the input on the GUI
 * @author jasonfortunato 
 *
 */
public class OurInputVerifier extends InputVerifier {

	@Override
	public boolean verify(JComponent input) {
		String text = ((JTextField) (input)).getText().trim();
	    String type = input.getName();
	    
		switch (type) {
			case EvoPane.RATE: 		// check that text is a double 0 to 1 inclusive
				if(isDouble(text)) {
					double val = Double.parseDouble(text);
					return val >= 0 && val <= 1;
				}	
			case EvoPane.INT: 		// check that text is an int
				return isInteger(text);
			case EvoPane.ANY_DOUBLE: // check text is a double
				return isDouble(text);	
			case EvoPane.ANY_DOUBLE_ZERO_TO_TEN: // check text is a double 0 to 10 inclusive
				if(isDouble(text)) {
					double val = Double.parseDouble(text);
					return val >= 0 && val <= 10;
				}		
			case EvoPane.ANY_NUMBER:
				return isDouble(text);
				
			default: 
				return true;
		}
				
	}
	/**
	 * Using exceptions to check if the string is an int (lol)
	 * @author jason
	 * @param s
	 * @return
	 */
	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
}
