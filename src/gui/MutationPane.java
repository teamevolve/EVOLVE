package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * 
 * @author jasonfortunato
 * @author linneasahlberg
 */
public class MutationPane extends JPanel {

	static final int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 3;

	JLabel mutLabel,					// Mutation (0 to 1)
		mutAtoBLabel, mutBtoALabel;
	JTextField mutAtoB, mutBtoA;

	GridBagConstraints c = new GridBagConstraints();
	
	public MutationPane(){
		
		// Problems were coming from non-uniformed column widths
		// This will standardize them
		for(int i = 0; i < 6; i++) {
			c.gridx = i; c.gridy = 1;
			add(new JLabel("_______________________________"), c);
		}
		
		
		setLayout(new GridBagLayout());
		
		// Mutation (0 to 1)
		mutLabel = new JLabel("Mutation (0 to 1): ");
		mutAtoBLabel = new JLabel("A to B: ");
		mutBtoALabel = new JLabel("B to A: ");
		mutAtoB = new JTextField(TEXT_LEN_SHORT);
		mutBtoA = new JTextField(TEXT_LEN_SHORT);
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(mutLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(mutAtoBLabel, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		add(mutAtoB, c);
	
		c.gridx = 2;
		c.anchor = GridBagConstraints.CENTER;
		add(mutBtoALabel, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.EAST;
		add(mutBtoA, c);

		
		
	}

	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		MutationPane test = new MutationPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
		
		
	}

	
	
}
