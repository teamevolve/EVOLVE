package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 
 * @author jasonfortunato
 * @author linneasahlberg
 * 
 */
public class PopSizePane extends JPanel {

	static final int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 3;

	JLabel popSizeLabel;			// Population size
	JTextField popSizeField;
	JLabel popConstLabel;			// Population constant
	ButtonGroup popConstGroup;
	JRadioButton popConstTrue;
	JRadioButton popConstFalse;
	JLabel initPopLabel;  			// Initial population
	JTextField initPop;
	JLabel carryCapLabel;			// Carrying Capacity
	JTextField carryCap;
	JLabel postCrashLabel; 			// Crash
	JTextField postCrash;

	public PopSizePane() {
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		// Problems were coming from non-uniformed column widths
		// This will standardize them
		for(int i = 0; i < 6; i++) {
			c.gridx = i; c.gridy = 1;
			add(new JLabel("_______________________________"), c);
		}
		
		// population size stuff
		popSizeLabel = new JLabel("Initial Population Size: ");
		popSizeField = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 0; c.gridy = 10;
		c.anchor = GridBagConstraints.WEST;
		add(popSizeLabel, c);
		c.gridx = 1; c.gridy = 10;
		add(popSizeField, c);
		
		// population constant radio button stuff
		popConstLabel = new JLabel("Population Size is: ");
		popConstGroup = new ButtonGroup();
		popConstTrue = new JRadioButton("Constant");
		popConstFalse = new JRadioButton("Varying");
		popConstGroup.add(popConstTrue);
		popConstGroup.add(popConstFalse);
		
		c.gridx = 0; c.gridy = 20;
		add(popConstLabel, c);
		c.gridx = 1; c.gridy = 20;
		add(popConstTrue, c);
		c.gridx = 2; c.gridy = 20;
		add(popConstFalse, c);

		
		
		// carrying capacity stuff - appears when popSize varying
		carryCapLabel = new JLabel("Carrying Capacity: ");
		carryCap = new JTextField(TEXT_LEN_LONG);
		c.gridx = 1; c.gridy = 40;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(carryCapLabel, c);
		c.gridx = 2; c.gridy = 40;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		//c.ipadx = -55;
		add(carryCap, c);
		c.ipadx = 0;
		
		
		// post crash population size stuff - appears when popSize varying
		postCrashLabel = new JLabel("Post Crash Population Size: ");
		postCrash = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 1; c.gridy = 50;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(postCrashLabel, c);
		c.gridx = 2; c.gridy = 50;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		//c.ipadx = -55;
		add(postCrash, c);
		c.ipadx = 0;
		

	
		}
	
		public static void main(String[] args){
			JFrame window = new JFrame();
			
			PopSizePane test = new PopSizePane();
			
			window.add(test);
			window.pack();
			window.setVisible(true);
			
			
		}
	
	
}
