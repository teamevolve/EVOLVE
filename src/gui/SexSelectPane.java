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
 * @author linneasahlberg
 * @author jasonfortunato
 *
 */

public class SexSelectPane extends JPanel {
	static final int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 3;
	
	public SexSelectPane() {
		
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		// Problems were coming from non-uniformed column widths
		// This will standardize them
		for(int i = 0; i < 6; i++) {
			c.gridx = i; c.gridy = 1;
			add(new JLabel("_______________________________"), c);
		}
		
		// Sexual selection -> evolutionary force #5
		JLabel mateFreqLabel = new JLabel("Mating Frequencies: ");
		JLabel freqAAxAALabel = new JLabel("AA x AA: ");
		JLabel freqAAxABLabel = new JLabel("AA x AB: ");
		JLabel freqAAxBBLabel = new JLabel("AA x BB: ");
		JLabel freqABxAALabel = new JLabel("AB x AA: ");
		JLabel freqABxABLabel = new JLabel("AB x AB: ");
		JLabel freqABxBBLabel = new JLabel("AB x BB: ");
		JLabel freqBBxAALabel = new JLabel("BB x AA: ");
		JLabel freqBBxABLabel = new JLabel("BB x AB: ");
		JLabel freqBBxBBLabel = new JLabel("BB x BB: ");
		JTextField freqAAxAA = new JTextField(TEXT_LEN_SHORT);
		JTextField freqAAxAB = new JTextField(TEXT_LEN_SHORT);
		JTextField freqAAxBB = new JTextField(TEXT_LEN_SHORT);
		JTextField freqABxAA = new JTextField(TEXT_LEN_SHORT);
		JTextField freqABxAB = new JTextField(TEXT_LEN_SHORT);
		JTextField freqABxBB = new JTextField(TEXT_LEN_SHORT);
		JTextField freqBBxAA = new JTextField(TEXT_LEN_SHORT);
		JTextField freqBBxAB = new JTextField(TEXT_LEN_SHORT);
		JTextField freqBBxBB = new JTextField(TEXT_LEN_SHORT);
			
		c.gridx = 0; c.gridy = 240;
		c.anchor = GridBagConstraints.WEST;
		add(mateFreqLabel, c);
			
		//AA x __
		c.gridx = 1; c.gridy = 250;
		c.anchor = GridBagConstraints.WEST;
		add(freqAAxAALabel, c);
		c.gridx = 1; c.gridy = 250;
		c.anchor = GridBagConstraints.CENTER;
		add(freqAAxAA, c);
			
		c.gridx = 2; c.gridy = 250;
		c.anchor = GridBagConstraints.WEST;
		add(freqAAxABLabel, c);
		c.gridx = 2; c.gridy = 250;
		c.anchor = GridBagConstraints.CENTER;
		add(freqAAxAB, c);
			
		c.gridx = 3; c.gridy = 250;
		c.anchor = GridBagConstraints.WEST;
		add(freqAAxBBLabel, c);
		c.gridx = 3; c.gridy = 250;
		c.anchor = GridBagConstraints.CENTER;
		add(freqAAxBB, c);
			
		// AB x __
		c.gridx = 1; c.gridy = 260;
		c.anchor = GridBagConstraints.WEST;
		add(freqABxAALabel, c);
		c.gridx = 1; c.gridy = 260;
		c.anchor = GridBagConstraints.CENTER;
		add(freqABxAA, c);
			
		c.gridx = 2; c.gridy = 260;
		c.anchor = GridBagConstraints.WEST;
		add(freqABxABLabel, c);
		c.gridx = 2; c.gridy = 260;
		c.anchor = GridBagConstraints.CENTER;
		add(freqABxAB, c);
			
		c.gridx = 3; c.gridy = 260;
		c.anchor = GridBagConstraints.WEST;
		add(freqABxBBLabel, c);
		c.gridx = 3; c.gridy = 260;
		c.anchor = GridBagConstraints.CENTER;
		add(freqABxBB, c);
			
		// BB x __
		c.gridx = 1; c.gridy = 270;
		c.anchor = GridBagConstraints.WEST;
		add(freqBBxAALabel, c);
		c.gridx = 1; c.gridy = 270;
		c.anchor = GridBagConstraints.CENTER;
		add(freqBBxAA, c);
			
		c.gridx = 2; c.gridy = 270;
		c.anchor = GridBagConstraints.WEST;
		add(freqBBxABLabel, c);
		c.gridx = 2; c.gridy = 270;
		c.anchor = GridBagConstraints.CENTER;
		add(freqBBxAB, c);
			
		c.gridx = 3; c.gridy = 270;
		c.anchor = GridBagConstraints.WEST;
		add(freqBBxBBLabel, c);
		c.gridx = 3; c.gridy = 270;
		c.anchor = GridBagConstraints.CENTER;
		add(freqBBxBB, c);
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		SexSelectPane test = new SexSelectPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
	}

}
