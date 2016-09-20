package gui;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * 
 * @author linneasahlberg
 * @author jasonfortunato
 */

public class PanelA extends JPanel {

	private static final long serialVersionUID = 1L;
	protected JTextField seedField = new JTextField(8);
	
	
	public PanelA() {
		// Set up panel
		JPanel panel = new JPanel();
		setLayout(new GridBagLayout());
		
		
		// Group fields to the panel
		JLabel seedLabel = new JLabel("Seed: ");
		panel.add(seedLabel);
		panel.add(seedField);
		
		// Add things to the panel
		add(panel, BorderLayout.CENTER);
	}	
}