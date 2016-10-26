package gui;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import shared.Genotype;

/**
 * 
 * @author linneasahlberg
 * @author jasonfortunato
 *
 */

public class SexSelectPane extends EvoPane {
	
	static final int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 3;
	
	JLabel mateFreqLabel, AAPrefLabel, 
	ABPrefLabel, BBPrefLabel;
	JLabel freqAAxAALabel, freqAAxABLabel, freqAAxBBLabel;
	JTextField freqAAxAA, freqAAxAB, freqAAxBB;
	JLabel freqABxAALabel, freqABxABLabel, freqABxBBLabel;
	JTextField freqABxAA, freqABxAB, freqABxBB;
	JLabel freqBBxAALabel, freqBBxABLabel, freqBBxBBLabel;
	JTextField freqBBxAA, freqBBxAB, freqBBxBB;
	
	
	public SexSelectPane() {
		mateFreqLabel = new JLabel("Sexual Selection (Mating Preference): ");
		AAPrefLabel = new JLabel("% AA Preference for:");
		freqAAxAALabel = new JLabel("AA: ");
		freqAAxABLabel = new JLabel("AB: ");
		freqAAxBBLabel = new JLabel("BB: ");
		ABPrefLabel = new JLabel("% AB Preference for:");
		freqABxAALabel = new JLabel("AA: ");
		freqABxABLabel = new JLabel("AB: ");
		freqABxBBLabel = new JLabel("BB: ");
		BBPrefLabel = new JLabel("% BB Preference for:");
		freqBBxAALabel = new JLabel("AA: ");
		freqBBxABLabel = new JLabel("AB: ");
		freqBBxBBLabel = new JLabel("BB: ");
		freqAAxAA = new JTextField(TEXT_LEN_SHORT);
		freqAAxAB = new JTextField(TEXT_LEN_SHORT);
		freqAAxBB = new JTextField(TEXT_LEN_SHORT);

		freqABxAA = new JTextField(TEXT_LEN_SHORT);
		freqABxAB = new JTextField(TEXT_LEN_SHORT);
		freqABxBB = new JTextField(TEXT_LEN_SHORT);

		freqBBxAA = new JTextField(TEXT_LEN_SHORT);
		freqBBxAB = new JTextField(TEXT_LEN_SHORT);
		freqBBxBB = new JTextField(TEXT_LEN_SHORT);
			
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 3;
		add(mateFreqLabel, c);
			
		//AA x __
		c.gridx = 1; c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1;
		AAPrefLabel.setFont(boldFont);
		add(AAPrefLabel, c);
		
		c.gridx = 1; c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		add(freqAAxAALabel, c);
		c.gridx = 1; c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(freqAAxAA, c);
			
		c.gridx = 2; c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		add(freqAAxABLabel, c);
		c.gridx = 2; c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(freqAAxAB, c);
			
		c.gridx = 3; c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		add(freqAAxBBLabel, c);
		c.gridx = 3; c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(freqAAxBB, c);
			
		// AB x __
		c.gridx = 1; c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		ABPrefLabel.setFont(boldFont);
		add(ABPrefLabel, c);
		
		c.gridx = 1; c.gridy = 5;
		c.anchor = GridBagConstraints.WEST;
		add(freqABxAALabel, c);
		c.gridx = 1; c.gridy = 5;
		c.anchor = GridBagConstraints.CENTER;
		add(freqABxAA, c);
			
		c.gridx = 2; c.gridy = 5;
		c.anchor = GridBagConstraints.WEST;
		add(freqABxABLabel, c);
		c.gridx = 2; c.gridy = 5;
		c.anchor = GridBagConstraints.CENTER;
		add(freqABxAB, c);
			
		c.gridx = 3; c.gridy = 5;
		c.anchor = GridBagConstraints.WEST;
		add(freqABxBBLabel, c);
		c.gridx = 3; c.gridy = 5;
		c.anchor = GridBagConstraints.CENTER;
		add(freqABxBB, c);
			
		// BB x __
		c.gridx = 1; c.gridy = 6;
		c.anchor = GridBagConstraints.WEST;
		BBPrefLabel.setFont(boldFont);
		add(BBPrefLabel, c);
		
		c.gridx = 1; c.gridy = 7;
		c.anchor = GridBagConstraints.WEST;
		add(freqBBxAALabel, c);
		c.gridx = 1; c.gridy = 7;
		c.anchor = GridBagConstraints.CENTER;
		add(freqBBxAA, c);
			
		c.gridx = 2; c.gridy = 7;
		c.anchor = GridBagConstraints.WEST;
		add(freqBBxABLabel, c);
		c.gridx = 2; c.gridy = 7;
		c.anchor = GridBagConstraints.CENTER;
		add(freqBBxAB, c);
			
		c.gridx = 3; c.gridy = 7;
		c.anchor = GridBagConstraints.WEST;
		add(freqBBxBBLabel, c);
		c.gridx = 3; c.gridy = 7;
		c.anchor = GridBagConstraints.CENTER;
		add(freqBBxBB, c);
	}
	
	public void submit(shared.SessionParameters p) {
		// AA x __
		p.setSexualSelectionRate(Genotype.AA, Genotype.AA, 
				Double.parseDouble(freqAAxAA.getText()));
		p.setSexualSelectionRate(Genotype.AA, Genotype.AB, 
				Double.parseDouble(freqAAxAB.getText()));
		p.setSexualSelectionRate(Genotype.AA, Genotype.BB, 
				Double.parseDouble(freqAAxBB.getText()));
		
		// AB x __
		p.setSexualSelectionRate(Genotype.AB, Genotype.AA, 
				Double.parseDouble(freqABxAA.getText()));
		p.setSexualSelectionRate(Genotype.AB, Genotype.AB, 
				Double.parseDouble(freqABxAB.getText()));
		p.setSexualSelectionRate(Genotype.AB, Genotype.BB, 
				Double.parseDouble(freqABxBB.getText()));
		
		// BB x __
		p.setSexualSelectionRate(Genotype.BB, Genotype.AA, 
				Double.parseDouble(freqBBxAA.getText()));
		p.setSexualSelectionRate(Genotype.BB, Genotype.AB, 
				Double.parseDouble(freqBBxAB.getText()));
		p.setSexualSelectionRate(Genotype.BB, Genotype.BB, 
				Double.parseDouble(freqBBxBB.getText()));
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		SexSelectPane test = new SexSelectPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
	}

}
