/*********************************************************************
 * Linnea and Jason
 * 
 * 9/18/16
 * 
 * GUI
 ********************************************************************/
package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author linneasahlberg
 *
 */
public class GUI extends JPanel implements ActionListener {
	/**
	 * eclipse wants this
	 */
	private static final long serialVersionUID = 1L;
	protected JTextField seedField;
	
	public GUI() {
		super(new GridBagLayout());
		seedField = new JTextField(8);
		seedField.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String seed = seedField.getText();
		seedField.selectAll();
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