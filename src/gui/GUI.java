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
public class GUI extends EvoPane {

	// we'll put args here
	shared.SessionParameters parms;

	
	// eventually delete this for real time inputs
	JButton submit;
		
	JLabel seedLabel; 				// Seed
	JTextField seedField;
	JLabel initFreqALabel; 			// Initial frequencies
	JTextField initFreqA;
	JLabel calcFreqAA, calcFreqAB, 
		calcFreqBB;
	JLabel numGensLabel; 			// Number of Gens
	JTextField numGens;
	
	/* Evolutionary Forces Panes *********************************************/
	PopSizePane pp = new PopSizePane();
	SelectionPane sp = new SelectionPane();
	MutationPane mp = new MutationPane();
	MigrationPane mip = new MigrationPane();
	SexSelectPane ssp = new SexSelectPane();

	
	/** 
	 * This is the panel that will be added to the window (the frame)
	 */
	public GUI() {
		
		super();
		
		// our input will go in this 
		parms = new shared.SessionParameters();
		
		
		// left align
		c.anchor = GridBagConstraints.WEST;
		
		/* seed stuff *****************************************************************************/
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
		
		/* initial frequencies stuff ***********************************************************/
		
		initFreqALabel = new JLabel("Initial Frequency of Allele A: ");
		initFreqA = new JTextField(TEXT_LEN_SHORT);
		
		// add spacing
//		c.insets = new Insets(5, 10, 5, 0);
		
		c.gridx = 0; c.gridy = 4;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(initFreqALabel, c);
		
		c.gridx = 1; c.gridy = 4;
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = 1;
		add(initFreqA, c);
		
		calcFreqAA = new JLabel("AA: ___");
		calcFreqAB = new JLabel("AB: ___");
		calcFreqBB = new JLabel("BB: ___");
		c.gridwidth = 2;
		c.gridx = 1; c.gridy = 5;
		c.anchor = GridBagConstraints.WEST;
		add(calcFreqAA, c);
		c.gridx = 1; c.gridy = 5;
		c.anchor = GridBagConstraints.CENTER;
		add(calcFreqAB, c);
		c.gridx = 1; c.gridy = 5;
		c.anchor = GridBagConstraints.EAST;
		add(calcFreqBB, c);
		
		/* EVOLUTIONARY FORCES ***************************************************************/
		JLabel evoForces = new JLabel("Select active evolutionary forces:");
		
		JCheckBox popSizeCheck = new JCheckBox("Population Size", true);
		popSizeCheck.setEnabled(false);
		
		JCheckBox selectCheck = new JCheckBox("Natural Selection", true);
		selectCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				sp.setEnabled(selectCheck.isSelected());
			}
		});	
		
		JCheckBox mutationCheck = new JCheckBox("Mutation", true);
		mutationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				mp.setEnabled(mutationCheck.isSelected());
			}
		});

		
		JCheckBox migrationCheck = new JCheckBox("Migration", true);
		migrationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				mip.setEnabled(migrationCheck.isSelected());
			}
		});
		
		JCheckBox sexualSelectCheck = new JCheckBox("Non-Random Mating", true);
		sexualSelectCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				ssp.setEnabled(sexualSelectCheck.isSelected());
			}
		});
		
		c.gridx = 0; c.gridy = 7;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		add(evoForces, c);
		c.gridx = 0; c.gridy = 9;
		c.gridwidth = 1;
		add(popSizeCheck, c);
		c.gridx = 1; c.gridy = 9;
		add(selectCheck, c);
		c.gridx = 2; c.gridy = 9;
		add(mutationCheck, c);
		c.gridx = 3; c.gridy = 9;
		add(migrationCheck, c);
		c.gridx = 4; c.gridy = 9;
		add(sexualSelectCheck, c);
		
		/* Panes ****************************************************************************/
		c.gridwidth = 6;
		c.gridx = 0; c.gridy = 10;
		add(pp, c);

		c.gridx = 0; c.gridy = 20;
		add(sp, c);

		c.gridx = 0; c.gridy = 30;
		add(mp, c);
		
		c.gridx = 0; c.gridy = 40;
		add(mip, c);
		
		c.gridx = 0; c.gridy = 50;
		add(ssp, c);
		
		
		/* submit button***********************************************************/
		submit = new JButton(">> Submit <<");
		
		c.gridx = 5; c.gridy = 999999;
		add(submit, c);		
		
		/* Action Listener ********************************************************/
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parms.setSeed(Integer.parseInt(seedField.getText()));

				pp.submit(parms);
				sp.submit(parms);
				mp.submit(parms);
//				mip.submit(parms);
//				ssp.submit(parms);
				
//				System.out.println(parms.getSeed());
//				System.out.println(parms.getPopSize());
				
/*				System.out.println("here is the new stuff: ");
				System.out.println("AA repro: " + parms.getReproductionRate(shared.Genotype.AA)); */

			}
		});

		
	}

	public static void createAndShowGUI() {

		//make the GUI panel and add evo force panels to GUI
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