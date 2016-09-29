package gui;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



/**
 * @author linneasahlberg
 * @author jasonfortunato
 * Started 9/18/16
 */
public class GUI extends JPanel {
	static final int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 3;
	
	// we'll dump our input into this guy/object
	shared.SessionParameters parms;
	
	// eventually delete this for real time inputs
	JButton submit;
	
	public GridBagConstraints c = new GridBagConstraints();
	
	JLabel seedLabel; 				// Seed
	JTextField seedField;
	JLabel initFreqALabel; 			// Initial frequencies
	JTextField initFreqA;
	JLabel calcFreqAA, calcFreqAB, 
		calcFreqBB;
	JLabel numGensLabel; 			// Number of Gens
	JTextField numGens;
	
	/** 
	 * This is the panel that will be added to the window (the frame)
	 */
	public GUI() {
		
		// our input will go in this guy/object <-- lowkey offensive
		parms = new shared.SessionParameters();
		
		setLayout(new GridBagLayout());
		
		// left align
		c.anchor = GridBagConstraints.WEST;
		
		// add spacing
		c.insets = new Insets(1, 10, 0, 0);
		
		// Problems were coming from non-uniformed column widths
		// This will standardize them
		for(int i = 0; i < 6; i++) {
			c.gridx = i; c.gridy = 0;
			add(new JLabel("_______________________________"), c);
		}
		
		// seed stuff
		seedLabel = new JLabel("Seed: ");
		seedField = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 5; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(seedLabel, c);		
		c.gridx = 5; c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		add(seedField, c);	
		
		// num generations stuff
		numGensLabel = new JLabel("Number of Generations: ");
		numGens = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		add(numGensLabel, c);
		c.gridx = 1; c.gridy = 1;
		add(numGens, c);
		
		// initial frequencies stuff
		
		initFreqALabel = new JLabel("Initial Frequency of Allele A: ");
		initFreqA = new JTextField(TEXT_LEN_SHORT);
		
		// add spacing
		c.insets = new Insets(5, 10, 5, 0);
		
		c.gridx = 0; c.gridy = 70;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(initFreqALabel, c);
		
		c.gridx = 1; c.gridy = 70;
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = 1;
		add(initFreqA, c);
		
		calcFreqAA = new JLabel("AA: ___");
		calcFreqAB = new JLabel("AB: ___");
		calcFreqBB = new JLabel("BB: ___");
		c.gridwidth = 2;
		c.gridx = 1; c.gridy = 80;
		c.anchor = GridBagConstraints.WEST;
		add(calcFreqAA, c);
		c.gridx = 1; c.gridy = 80;
		c.anchor = GridBagConstraints.CENTER;
		add(calcFreqAB, c);
		c.gridx = 1; c.gridy = 80;
		c.anchor = GridBagConstraints.EAST;
		add(calcFreqBB, c);
		
		/* EVOLUTIONARY FORCES ***************************************************************/
		JLabel evoForces = new JLabel("Select active evolutionary forces:");
		JCheckBox popSizeCheck = new JCheckBox("Population Size", true);
		popSizeCheck.setEnabled(false);
		JCheckBox selectCheck = new JCheckBox("Natural Selection");
		JCheckBox mutationCheck = new JCheckBox("Mutation");
		JCheckBox migrationCheck = new JCheckBox("Migration");
		JCheckBox sexualSelectCheck = new JCheckBox("Non-Random Mating");

		c.gridx = 0; c.gridy = 84;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		add(evoForces, c);
		c.gridx = 0; c.gridy = 86;
		c.gridwidth = 1;
		add(popSizeCheck, c);
		c.gridx = 1; c.gridy = 86;
		add(selectCheck, c);
		c.gridx = 2; c.gridy = 86;
		add(mutationCheck, c);
		c.gridx = 3; c.gridy = 86;
		add(migrationCheck, c);
		c.gridx = 4; c.gridy = 86;
		add(sexualSelectCheck, c);
		
		// **************submit button***************
		submit = new JButton(">> Submit <<");
		
		c.gridx = 999999; c.gridy = 999999;
		add(submit, c);		
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parms.setSeed(Integer.parseInt(seedField.getText()));
//				parms.setPopSize(Integer.parseInt(popSizeField.getText()));
				
				System.out.println(parms.getSeed());
				System.out.println(parms.getPopSize());
				System.out.println(selectCheck.isSelected());
				System.out.println(popSizeCheck.isSelected());
			}
		});
		
	}

	public static void createAndShowGUI() {

		//make the GUI panel and add evo force panels to GUI
		GUI g = new GUI();

		PopSizePane pp = new PopSizePane();
		SelectionPane sp = new SelectionPane();
		MutationPane mp = new MutationPane();
		MigrationPane mip = new MigrationPane();
		SexSelectPane ssp = new SexSelectPane();
		
		g.c.gridwidth = 6;
		g.c.gridx = 0; g.c.gridy = 10;
		g.add(pp, g.c);

		g.c.gridx = 0; g.c.gridy = 20;
		g.add(sp, g.c);

		g.c.gridx = 0; g.c.gridy = 30;
		g.add(mp, g.c);
		
		g.c.gridx = 0; g.c.gridy = 40;
		g.add(mip, g.c);
		
		g.c.gridx = 0; g.c.gridy = 50;
		g.add(ssp, g.c);
		
		
		
		//make the window
		JFrame frame = new JFrame();
		frame.setTitle("EVOLVE - v0.0");
		//frame.setSize(800, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//add the GUI to a scrollable pane
		JScrollPane scrPane = new JScrollPane(g);

		// add the scrollable pane to the window
		frame.add(scrPane);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		createAndShowGUI();
	}
}