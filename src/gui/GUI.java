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
	
	GridBagConstraints c = new GridBagConstraints();
	
	JLabel seedLabel; 				// Seed
	JTextField seedField;
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
	JLabel numGensLabel; 			// Number of Gens
	JTextField numGens;
	JLabel postCrashLabel; 			// Crash
	JTextField postCrash;
	JLabel initFreqALabel; 			// Initial frequencies
	JTextField initFreqA;
	JLabel calcFreqAA, calcFreqAB, 
		calcFreqBB;
	JLabel selectLabel;				// Selection
	ButtonGroup selectGroup;
	JRadioButton selectRandS, 
		selectAbs;
	JLabel reproRateLabel, 					// Reproduction Rates (0 to 10, decimal allowed)
		reproAALabel, reproABLabel, reproBBLabel;
	JTextField reproAA, reproAB, reproBB;
	JLabel survRateLabel,					// Survival Rates (0 to 1)
		survAALabel, survABLabel, survBBLabel;
	JTextField survAA, survAB, survBB;
	JLabel absFitLabel,					// Absolute fitness (any num)
		absFitAALabel, absFitABLabel, absFitBBLabel;
	JTextField absFitAA, absFitAB, absFitBB;
	JLabel relFitLabel,					// Relative fitness (0 to 1)
		relFitAALabel, relFitABLabel, relFitBBLabel;
	JLabel mutLabel,					// Mutation (0 to 1)
		mutAtoBLabel, mutBtoALabel;
	JTextField mutAtoB, mutBtoA;
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
			c.gridx = i; c.gridy = 1;
			add(new JLabel("_______________________________"), c);
		}
		
		// seed stuff
		seedLabel = new JLabel("Seed: ");
		seedField = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 999998; c.gridy = 0;
		c.anchor = GridBagConstraints.EAST;
		add(seedLabel, c);		
		c.gridx = 999999; c.gridy = 0;
		add(seedField, c);	
		
		// population size stuff
		popSizeLabel = new JLabel("Population Size: ");
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
		
		/*// initial population stuff - appears when popSize varying
		initPopLabel = new JLabel("Initial Population Size: ");
		initPop = new JTextField(TEXT_LEN_LONG);

		c.gridx = 1; c.gridy = 30;
		c.gridwidth = 2;
		add(initPopLabel, c);
		c.gridx = 3; c.gridy = 30;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		//c.ipadx = -55;
		add(initPop, c);
		c.ipadx = 0;*/
		
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
		
		// num generations stuff
		numGensLabel = new JLabel("Number of Generations: ");
		numGens = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 0; c.gridy = 60;
		c.anchor = GridBagConstraints.EAST;
		add(numGensLabel, c);
		c.gridx = 1; c.gridy = 60;
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
//		popSizeCheck.setSelected(true);
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
		
		// Selection radio buttons
		selectLabel = new JLabel("Selection: ");
		selectGroup = new ButtonGroup();
		selectRandS = new JRadioButton("Reproduction and Survival");
		selectAbs = new JRadioButton("Absolute Fitness");
		selectGroup.add(selectRandS);
		selectGroup.add(selectAbs);
		
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 90;
		c.anchor = GridBagConstraints.WEST;
		add(selectLabel, c);
		c.gridx = 1; c.gridy = 90;
		c.gridwidth = 2;
		add(selectRandS, c);
		c.gridx = 3; c.gridy = 90;
		c.gridwidth = 1;
		add(selectAbs, c);
		
		// Reproduction Rates (visible if Repro and Surv is selected)
		reproRateLabel = new JLabel("Reproduction Rates (0 to 10, decimals allowed): ");
		reproAALabel = new JLabel("AA: ");
		reproABLabel = new JLabel("AB: ");
		reproBBLabel = new JLabel("BB: ");
		reproAA = new JTextField(TEXT_LEN_LONG);
		reproAB = new JTextField(TEXT_LEN_LONG);
		reproBB = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 1; c.gridy = 100;
		c.gridwidth = 3;
		add(reproRateLabel, c);
		
		// add label then field x3
		c.gridwidth = 1;
		c.gridx = 1; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproAALabel, c);
		c.gridx = 1; //c.gridy = 110;
		c.anchor = GridBagConstraints.CENTER;
		add(reproAA, c);
		
		c.gridx = 2; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproABLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 2; c.gridy = 110;
		add(reproAB, c);
		
		c.gridx = 3; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproBBLabel, c);
		c.gridx = 3; c.gridy = 110;
		c.anchor = GridBagConstraints.CENTER;
		add(reproBB, c);
		
		// Survival Rates (visible if Repro and Surv is selected)
		survRateLabel = new JLabel("Survival Rates (0 to 1): ");
		survAALabel = new JLabel("AA: ");
		survABLabel = new JLabel("AB: ");
		survBBLabel = new JLabel("BB: ");
		survAA = new JTextField(TEXT_LEN_SHORT);
		survAB = new JTextField(TEXT_LEN_SHORT);
		survBB = new JTextField(TEXT_LEN_SHORT);
		
		c.gridx = 1; c.gridy = 120;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(survRateLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 130;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(survAALabel, c);
		c.gridx = 1; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survAA, c);
		
		c.gridx = 2; c.gridy = 130;
		c.anchor = GridBagConstraints.WEST;
		add(survABLabel, c);
		c.gridx = 2; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survAB, c);
		
		c.gridx = 3; c.gridy = 130;
		c.anchor = GridBagConstraints.WEST;
		add(survBBLabel, c);
		c.gridx = 3; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survBB, c);
		
		// Absolute Fitness Rates (any number)
		absFitLabel = new JLabel("Absolute Fitness (any number): ");
		absFitAALabel = new JLabel("AA: ");
		absFitABLabel = new JLabel("AB: ");
		absFitBBLabel = new JLabel("BB: ");
		absFitAA = new JTextField(TEXT_LEN_LONG);
		absFitAB = new JTextField(TEXT_LEN_LONG);
		absFitBB = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 1; c.gridy = 140;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(absFitLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 150;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(absFitAALabel, c);
		c.gridx = 1; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitAA, c);
	
		c.gridx = 2; c.gridy = 150;
		c.anchor = GridBagConstraints.WEST;
		add(absFitABLabel, c);
		c.gridx = 2; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitAB, c);
		
		c.gridx = 3; c.gridy = 150;
		c.anchor = GridBagConstraints.WEST;
		add(absFitBBLabel, c);
		c.gridx = 3; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitBB, c);
		
		// Relative Fitness Rates (display only, 0 to 1)
		relFitLabel = new JLabel("Relative Fitness: ");
		relFitAALabel = new JLabel("AA: ___");
		relFitABLabel = new JLabel("AB: ___");
		relFitBBLabel = new JLabel("BB: ___");
		
		c.gridx = 1; c.gridy = 160;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(relFitLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 170;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(relFitAALabel, c);
	
//		c.gridx = 2; c.gridy = 170;
		c.anchor = GridBagConstraints.CENTER;
		add(relFitABLabel, c);
		
//		c.gridx = 3; c.gridy = 170;
		c.anchor = GridBagConstraints.EAST;
		add(relFitBBLabel, c);

		// Mutation (0 to 1)
		mutLabel = new JLabel("Mutation (0 to 1): ");
		mutAtoBLabel = new JLabel("A to B: ");
		mutBtoALabel = new JLabel("B to A: ");
		mutAtoB = new JTextField(TEXT_LEN_SHORT);
		mutBtoA = new JTextField(TEXT_LEN_SHORT);
		
		c.gridx = 0; c.gridy = 180;
		c.anchor = GridBagConstraints.WEST;
		add(mutLabel, c);
		
		// add label then field x3
		c.gridx = 0; c.gridy = 190;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		add(mutAtoBLabel, c);
		c.gridx = 1; c.gridy = 190;
		c.anchor = GridBagConstraints.WEST;
		add(mutAtoB, c);
	
		c.gridx = 2; c.gridy = 190;
		//c.anchor = GridBagConstraints.EAST;
		add(mutBtoALabel, c);
		c.gridx = 2; c.gridy = 190;
		c.anchor = GridBagConstraints.EAST;
		add(mutBtoA, c);
		
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
		
		// **************submit button- to be deleted later ***************
		submit = new JButton(">> Submit <<");
		
		c.gridx = 999999; c.gridy = 999999;
		add(submit, c);		
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parms.setSeed(Integer.parseInt(seedField.getText()));
				parms.setPopSize(Integer.parseInt(popSizeField.getText()));
				
				System.out.println(parms.getSeed());
				System.out.println(parms.getPopSize());
				System.out.println(selectCheck.isSelected());
				System.out.println(popSizeCheck.isSelected());
			}
		});
		
	}

	public static void createAndShowGUI() {

		//make the panel
		GUI g = new GUI();
				
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