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

	JLabel migLabel;					// Migration
	ButtonGroup migGroup;
	JRadioButton fixedMig, varMig;
	JLabel fixedMigRateLabel;
	JTextField fixedMigRate;
	JLabel varMigRateLabel;
	JLabel varMigRateAALabel, varMigRateABLabel, varMigRateBBLabel;
	JTextField varMigRateAA, varMigRateAB, varMigRateBB;
	
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
		
			
		// Migration radio buttons
		migLabel = new JLabel("Migration: ");
		migGroup = new ButtonGroup();
		fixedMig = new JRadioButton("Fixed");
		varMig = new JRadioButton("Varies by Genotype");
		migGroup.add(fixedMig);
		migGroup.add(varMig);
		
		c.gridx = 0; c.gridy = 200;
		c.anchor = GridBagConstraints.WEST;
		add(migLabel, c);
		c.gridx = 0; c.gridy = 200;
		c.anchor = GridBagConstraints.EAST;
		add(fixedMig, c);
		c.gridx = 1; c.gridy = 200;
		c.anchor = GridBagConstraints.WEST;
		add(varMig, c);
		
		// Migration rate - if fixed
		fixedMigRateLabel = new JLabel("Migration Rate: ");
		fixedMigRate = new JTextField(TEXT_LEN_SHORT);
		
		c.gridx = 1; c.gridy = 210;
		c.anchor = GridBagConstraints.WEST;
		add(fixedMigRateLabel, c);
		c.gridx = 1; c.gridy = 210;
		c.anchor = GridBagConstraints.EAST;
		add(fixedMigRate, c);
		
		// Migration Rates by genotype - if varies
		varMigRateLabel = new JLabel("Migration Rate (by genotype): ");
		varMigRateAALabel = new JLabel("AA: ");
		varMigRateABLabel = new JLabel("AB: ");
		varMigRateBBLabel = new JLabel("BB: ");
		varMigRateAA = new JTextField(TEXT_LEN_SHORT);
		varMigRateAB = new JTextField(TEXT_LEN_SHORT);
		varMigRateBB = new JTextField(TEXT_LEN_SHORT);
		
		c.gridwidth = 3;
		c.gridx = 1; c.gridy = 220;
		c.anchor = GridBagConstraints.WEST;
		add(varMigRateLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 230;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(varMigRateAALabel, c);
		c.gridx = 1; c.gridy = 230;
		c.anchor = GridBagConstraints.CENTER;
		add(varMigRateAA, c);
	
		c.gridx = 2; c.gridy = 230;
		c.anchor = GridBagConstraints.WEST;
		add(varMigRateABLabel, c);
		c.gridx = 2; c.gridy = 230;
		c.anchor = GridBagConstraints.CENTER;
		add(varMigRateAB, c);
		
		c.gridx = 3; c.gridy = 230;
		c.anchor = GridBagConstraints.WEST;
		add(varMigRateBBLabel, c);
		c.gridx = 3; c.gridy = 230;
		c.anchor = GridBagConstraints.CENTER;
		add(varMigRateBB, c);
		
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
		
		g.c.gridwidth = 6;
		g.c.gridx = 0; g.c.gridy = 10;
		g.add(pp, g.c);

		g.c.gridx = 0; g.c.gridy = 20;
		g.add(sp, g.c);

		g.c.gridx = 0; g.c.gridy = 30;
		g.add(mp, g.c);
		
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