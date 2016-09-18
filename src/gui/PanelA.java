package gui;
import java.awt.BorderLayout;

import javax.swing.*;

public class PanelA extends JPanel {

	/**
	 * eclipse still wants this
	 */
	private static final long serialVersionUID = 1L;

	protected JTextField seedField = new JTextField(8);
	public PanelA() {
		// Group fields to the panel
		JPanel panel = new JPanel();
		panel.add(seedField);
		
		// Add things to the panel
		add(panel, BorderLayout.CENTER);
	}
	
}