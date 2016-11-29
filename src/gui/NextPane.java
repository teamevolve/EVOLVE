package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NextPane extends EvoPane {
	 JLabel nextLabel;
	 JButton sameVals;
	 JButton newExp;
	 
	public NextPane() {
		nextLabel = new JLabel("<html><b>Next:");
		sameVals = new JButton("Run Again");
		newExp =  new JButton("New Simulation");
		c.gridx = 0; c.gridy = 1;
		add(nextLabel, c);
		c.gridx++;
		add(sameVals, c);
		c.gridx++;
		add(newExp, c);
		
	}
}
