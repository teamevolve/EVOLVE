/*********************************************************************
 * Linnea and Jason
 * 
 * 9/18/16
 * 
 * GUI
 ********************************************************************/
package gui;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author linneasahlberg
 * @author jasonfortunato
 */
public class GUI extends Applet {
	private static final long serialVersionUID = 1L;
	protected JTextField seedField;
	
	public GUI() {
		seedField = new JTextField(8);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setFont(new Font ("SansSerif", Font.PLAIN, 14));
		setLayout(layout);
		
		c.fill =GridBagConstraints.BOTH;
		c.weightx = 1.0;
		
	}

	public static void createAndShowGUI() {
		// Create and set up window
		JFrame frame = new JFrame("EVOLVE - v0.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add components to the window
		frame.add(new PanelA()); // First group of fields on GUI design
		
		// Display window
		frame.setSize(800, 640);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		createAndShowGUI();
	}
}