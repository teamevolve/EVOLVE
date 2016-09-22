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
	
	/** 
	 * This is the panel that will be added to the window (the frame)
	 */
	public GUI() {
		// our input will go in this guy/object <-- lowkey offensive
		parms = new shared.SessionParameters();
		
		setLayout(new GridBagLayout());
	
		// seed stuff
		seedLabel = new JLabel("Seed: ");
		seedField = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 999990; c.gridy = 0;
		add(seedLabel, c);		
		c.gridx = 999999; c.gridy = 0;
		add(seedField, c);	
		
		// population size stuff
		popSizeLabel = new JLabel("Population Size: ");
		popSizeField = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 0; c.gridy = 10;
		add(popSizeLabel, c);
		c.gridx = 10; c.gridy = 10;
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
		c.gridx = 10; c.gridy = 20;
		add(popConstTrue, c);
		c.gridx = 20; c.gridy = 20;
		add(popConstFalse, c);
		
		// initial population stuff - appears when popSize varying
		initPopLabel = new JLabel("Init Pop(if vary): ");
		initPop = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 10; c.gridy = 30;
		add(initPopLabel, c);
		c.gridx = 20; c.gridy = 30;
		add(initPop, c);
		
		// carrying capacity stuff - appears when popSize varying
		carryCapLabel = new JLabel("Carry Cap(if vary): ");
		carryCap = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 10; c.gridy = 40;
		add(carryCapLabel, c);
		c.gridx = 20; c.gridy = 40;
		add(carryCap, c);
		
		// post crash population size stuff - appears when popSize varying
		postCrashLabel = new JLabel("Post crash pop size(if vary): ");
		postCrash = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 10; c.gridy = 50;
		add(postCrashLabel, c);
		c.gridx = 20; c.gridy = 50;
		add(postCrash, c);
		
		// num generations stuff
		numGensLabel = new JLabel("Number of Generations: ");
		numGens = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 0; c.gridy = 60;
		add(numGensLabel, c);
		c.gridx = 10; c.gridy = 60;
		add(numGens, c);
		
		// initial frequencies stuff
		initFreqALabel = new JLabel("Initial Frequency of Allele A: ");
		initFreqA = new JTextField(TEXT_LEN_SHORT);
		
		c.gridx = 0; c.gridy = 70;
		add(initFreqALabel, c);
		c.gridx = 10; c.gridy = 70;
		add(initFreqA, c);
		
		calcFreqAA = new JLabel("AA: ");
		calcFreqAB = new JLabel("AB: ");
		calcFreqBB = new JLabel("BB: ");
		c.gridx = 10; c.gridy = 80;
		add(calcFreqAA, c);
		c.gridx = 20; c.gridy = 80;
		add(calcFreqAB, c);
		c.gridx = 30; c.gridy = 80;
		add(calcFreqBB, c);
		
		// Selection radio buttons
		selectLabel = new JLabel("Selection: ");
		selectGroup = new ButtonGroup();
		selectRandS = new JRadioButton("Reproduction and Survival");
		selectAbs = new JRadioButton("Absolute Fitness");
		selectGroup.add(selectRandS);
		selectGroup.add(selectAbs);
		
		c.gridx = 0; c.gridy = 90;
		add(selectLabel, c);
		c.gridx = 10; c.gridy = 90;
		add(selectRandS, c);
		c.gridx = 20; c.gridy = 90;
		add(selectAbs, c);
		
		// Reproduction Rates (visible if Repro and Surv is selected)
		reproRateLabel = new JLabel("Reproduction Rates (0 to 10, decimals allowed");
		reproAALabel = new JLabel("AA");
		reproABLabel = new JLabel("AB");
		reproBBLabel = new JLabel("BB");
		reproAA = new JTextField("AA: ");
		reproAB = new JTextField("AB: ");
		reproBB = new JTextField("BB: ");
		
		c.gridx = 10; c.gridy = 100;
		c.gridwidth = 3;
		add(reproRateLabel, c);
		c.gridx = 0; c.gridy = 100;
		c.gridwidth = 1;
		// add label then field x3
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
			}
		});
		
	}

	public static void createAndShowGUI() {

		//make the panel
		GUI g = new GUI();
		//g.setLayout(new GridBagLayout());
		
		//make the window
		JFrame frame = new JFrame();
		frame.setTitle("EVOLVE - v0.0");
		//frame.setSize(800, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		// add the panel to the window
		frame.add(g);
		frame.pack();
		frame.setVisible(true);
		//System.out.println("done");

	}
	
	public static void main(String[] args) {
		createAndShowGUI();
	}
}