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
	
	// eventually delete this for real time inputs
	JButton submit;
	
	GridBagConstraints c = new GridBagConstraints();
	JLabel seedLabel;
	JTextField seedField;
	JLabel popSizeLabel;
	JTextField popSizeField;
	JLabel popConstLabel;
	ButtonGroup popConstGroup;
	JRadioButton popConstTrue;
	JRadioButton popConstFalse;
	JLabel initPopLabel;
	JTextField initPop;
	JLabel carryCapLabel;
	JTextField carryCap;
	JLabel numGensLabel;
	JTextField numGens;
	JLabel postCrashLabel;
	JTextField postCrash;
	JLabel initFreqALabel;
	JTextField initFreqA;
	JLabel calcFreqAA;
	JLabel calcFreqAB;
	JLabel calcFreqBB;
	
	
	/** 
	 * This is the panel that will be added to the window (the frame)
	 */
	public GUI() {
		setLayout(new GridBagLayout());
	
		// seed stuff
		seedLabel = new JLabel("Seed: ");
		seedField = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 1000; c.gridy = 0;
		add(seedLabel, c);		
		c.gridx = 100; c.gridy = 0;
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
		
		
		
		// **************submit button- to be deleted later ***************
		submit = new JButton(">> Submit <<");
		
		c.gridx = 0; c.gridy = 999999;
		add(submit, c);		
		
		/*submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});*/
		
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