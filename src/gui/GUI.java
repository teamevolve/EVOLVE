package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import shared.DataManager;
import shared.EvolveDirector;
import shared.Genotype;



/**
 * @author linneasahlberg
 * @author jasonfortunato
 * Started 9/18/16
 */
public class GUI extends EvoPane {

	/**
	 * Unsure if we should change this -Jason
	 */
	private static final long serialVersionUID = 1L;


	// we'll put args here
	shared.SessionParameters parms;

	JButton submit;
		
	JLabel seedLabel; 				// Seed
	JTextField seedField;
	JLabel initFreqALabel; 			// Initial frequencies
	JTextField initFreqA;
	JLabel calcFreqAA, calcFreqAB, 
		calcFreqBB;
	JLabel numPopsLabel; 			// Number of Pops
	JTextField numPops;	
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
		
		
		// add spacing
//		c.insets = new Insets(5, 10, 5, 0);
		
		// left align
		c.anchor = GridBagConstraints.WEST;
		
		/* seed stuff *****************************************************************************/
		seedLabel = new JLabel("Seed: ");
		seedField = new JTextField(TEXT_LEN_LONG);
		seedField.setName(INT);
		seedField.setInputVerifier(new OurInputVerifier());
		
		c.gridx = 4; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(seedLabel, c);		
		c.gridx = 4; c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		add(seedField, c);	
		
		// num populations stuff **************************************************************/
		numPopsLabel = new JLabel("Number of Populations: ");
		numPops = new JTextField(TEXT_LEN_LONG);
		numPops.setName(INT);
		numPops.setInputVerifier(new OurInputVerifier());
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		add(numPopsLabel, c);
		c.gridx = 1; c.gridy = 1;
		add(numPops, c);

		// num generations stuff **************************************************************/
		numGensLabel = new JLabel("Number of Generations: ");
		numGens = new JTextField(TEXT_LEN_LONG);
		numGens.setName(INT);
		numGens.setInputVerifier(new OurInputVerifier());
		
		c.gridx = 0; c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		add(numGensLabel, c);
		c.gridx = 1; c.gridy = 2;
		add(numGens, c);
		
		
		/* initial frequencies stuff ***********************************************************/
		
		initFreqALabel = new JLabel("Initial Frequency of Allele A: ");
		initFreqA = new JTextField(TEXT_LEN_SHORT);
		initFreqA.setName(RATE);
		initFreqA.setInputVerifier(new OurInputVerifier());

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

				// Create a new, blank sesh parms object on each submit click 
				parms = new shared.SessionParameters();

				
				// Set SessionParameters NOT from the GUI pane
				parms.setNumPops(Integer.parseInt(numPops.getText()));
				parms.setSeed(Integer.parseInt(seedField.getText()));
				parms.setNumGens(Integer.parseInt(numGens.getText()));
				
				// Set Allele freqs --> maybe just move this into a new method?
				double alleleAfreq = Double.parseDouble(initFreqA.getText());
				double alleleBfreq = 1 - alleleAfreq;

				double AAfreq = alleleAfreq * alleleAfreq;
				double ABfreq = 2 * alleleAfreq * alleleBfreq;
				double BBfreq = alleleBfreq * alleleBfreq;
				
				calcFreqAA.setText(String.format("%.4f", AAfreq));
				calcFreqAB.setText(String.format("%.4f", ABfreq));
				calcFreqBB.setText(String.format("%.4f", BBfreq));
				
				parms.setGenotypeFrequency(Genotype.AA, AAfreq);
				parms.setGenotypeFrequency(Genotype.AB, ABfreq);
				parms.setGenotypeFrequency(Genotype.BB, BBfreq);
				
				parms.setPopSizeChecked(popSizeCheck.isSelected());
				parms.setSelectChecked(selectCheck.isSelected());
				parms.setMutationChecked(mutationCheck.isSelected());
				parms.setMigrationChecked(migrationCheck.isSelected());
				parms.setSexSelectChecked(sexualSelectCheck.isSelected());
				

				// Submit info from the EvoPanes if necessary
				if(parms.isPopSizeChecked())
					pp.submit(parms);
				if(parms.isSelectChecked())
					sp.submit(parms);
				if(parms.isMutationChecked())
					mp.submit(parms);
				if(parms.isMigrationChecked())
					mip.submit(parms);
				if(parms.isSexSelectChecked())
					ssp.submit(parms);
				
				DataManager.getInstance().setSessionParams(parms);
				EvolveDirector.getInstance().runSimulation();

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