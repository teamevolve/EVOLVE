package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import importexport.ExportFormat;
import shared.DataManager;
import shared.EvolveDirector;
import shared.Genotype;
import shared.SessionParameters;



/**
 * @author linneasahlberg
 * @author jasonfortunato
 * @author richwenner
 * 
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
	JToggleButton help;
	JLabel numPopsLabel; 			// Number of Pops
	JTextField numPops;	
	JLabel numGensLabel; 			// Number of Gens
	JTextField numGens;
	
	JCheckBox popSizeCheck;
	JCheckBox selectCheck;
	JCheckBox mutationCheck;
	JCheckBox migrationCheck;
	JCheckBox sexualSelectCheck;
	
	JLabel titleLabel; JTextField title;
	JLabel questionLabel; JTextField question;
	JLabel experLabel; JTextField exper;
	JLabel resultsLabel; JTextField results;
	JLabel discussionLabel; JTextField discussion;
		
	/* Evolutionary Forces Panes *********************************************/
	TitlePane tp = new TitlePane();
	ForcesPane fp = new ForcesPane();
	InitPopPane pp = new InitPopPane();
	GeneticDriftPane gd = new GeneticDriftPane();
	SelectionPane sp = new SelectionPane();
	MutationPane mp = new MutationPane();
	MigrationPane mip = new MigrationPane();
	SexSelectPane ssp = new SexSelectPane();
	
	ArrayList<EvoPane> forces = new ArrayList<EvoPane>();
	
	/** 
	 * This is the panel that will be added to the window (the frame)
	 */
	public GUI() {
		
		super();
		
		
		// add spacing
//		c.insets = new Insets(5, 10, 5, 0);
		
		// left align
		c.anchor = GridBagConstraints.WEST;
		
		titleLabel = new JLabel("Title:");
		title = new JTextField(TEXT_LEN_LONG);
		questionLabel = new JLabel("Question:"); 
		question = new JTextField(TEXT_LEN_LONG);
		experLabel = new JLabel("Experimental Design:"); 
		exper = new JTextField(TEXT_LEN_LONG);
		resultsLabel = new JLabel("Results:");
		results = new JTextField(TEXT_LEN_LONG);
		discussionLabel = new JLabel("Discussion:");
		discussion = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 0; c.gridy = 1;
		add(titleLabel, c);
		c.gridx = 1; c.gridy = 1;
		add(title, c);
		
		c.gridx = 0; c.gridy = 2;
		add(questionLabel, c);
		c.gridx = 1; c.gridy = 2;
		add(question, c);
		
		c.gridx = 0; c.gridy = 3;
		add(experLabel, c);
		c.gridx = 1; c.gridy = 3;
		add(exper, c);
		
		c.gridx = 0; c.gridy = 4;
		add(resultsLabel, c);
		c.gridx = 1; c.gridy = 4;
		add(results, c);
		
		c.gridx = 0; c.gridy = 5;
		add(discussionLabel, c);
		c.gridx = 1; c.gridy = 5;
		add(discussion, c);

		/* help stuff *****************************************************************************/
		help = new JToggleButton(">> Help!? <<");
		c.gridx = 6; c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(help, c);


		/* EVOLUTIONARY FORCES ***************************************************************/
		JLabel evoForces = new JLabel("Select active evolutionary forces:");
		
		popSizeCheck = new JCheckBox("Drift (Population Size)", true);
		//popSizeCheck.setEnabled(false);
		popSizeCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				gd.setEnabled(popSizeCheck.isSelected());
			}
		});	
		
		selectCheck = new JCheckBox("Natural Selection", true);
		selectCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				sp.setEnabled(selectCheck.isSelected());
			}
		});	
		
		mutationCheck = new JCheckBox("Mutation", true);
		mutationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				mp.setEnabled(mutationCheck.isSelected());
			}
		});

		
		migrationCheck = new JCheckBox("Migration", true);
		migrationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				mip.setEnabled(migrationCheck.isSelected());
			}
		});
		
		sexualSelectCheck = new JCheckBox("Non-Random Mating", true);
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
		c.gridwidth = 7;
		//c.gridx = 0; c.gridy = 3;
		//add(tp, c);
		
		c.gridx = 0; c.gridy = 6;
		add(fp, c);
		
		c.gridx = 0; c.gridy = 10;
		add(pp, c);
		
		c.gridx = 0; c.gridy = 15;
		add(gd, c);

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
		
		c.gridx = 6; c.gridy = 999999;
		add(submit, c);		
		
		/* Set to 2 alleles mode on startup ***************************************/
		modeThreeAlleles(false);
		
		/* Action Listeners ********************************************************/
		// Set actions for the NumAlleles radio buttons
		gui.ForcesPane.alleles2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeThreeAlleles(false);
			}
		});
		
		gui.ForcesPane.alleles3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeThreeAlleles(true);
			}
		});
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a new, blank sesh parms object on each submit click 
				parms = new shared.SessionParameters();

				submitInfo(parms);

			}
		});
	}

/*		help.addActionListener(new ActionListener() {              // HELP MODE !@#@!@!#@!!@##@!!@#
			public void actionPerformed(ActionEvent e) {
				
				setHelpMode(help.isSelected());
				this.flavorTextMode(help.isSelected());
				for(EvoPane pane : forces) {
					pane.setHelpMode(help.isSelected());
					pane.flavorTextMode(help.isSelected());
				}
				
			}
		}); */
		public void modeThreeAlleles(boolean b){
			super.modeThreeAlleles(b);
			pp.modeThreeAlleles(b);
			sp.modeThreeAlleles(b);
			mp.modeThreeAlleles(b);
			mip.modeThreeAlleles(b);
			ssp.modeThreeAlleles(b);
			
		}

	public void submitInfo(SessionParameters parms) {

		// Set SessionParameters NOT from the GUI pane
		parms.setNumPops(Integer.parseInt(numPops.getText()));
		parms.setNumGens(Integer.parseInt(numGens.getText()));
		
		/*// Set Allele freqs
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
		*/
		
		// Set evolutionary force flags
		parms.setPopSizeChecked(popSizeCheck.isSelected());
		parms.setSelectChecked(selectCheck.isSelected());
		parms.setMutationChecked(mutationCheck.isSelected());
		parms.setMigrationChecked(migrationCheck.isSelected());
		parms.setSexSelectChecked(sexualSelectCheck.isSelected());

		// Submit info from the EvoPanes if necessary
		fp.submit(parms);
		if(parms.isPopSizeChecked())
			gd.submit(parms);
		if(parms.isSelectChecked())
			sp.submit(parms);
		if(parms.isMutationChecked())
			mp.submit(parms);
		if(parms.isMigrationChecked())
			mip.submit(parms);
		if(parms.isSexSelectChecked())
			ssp.submit(parms);
		
		// Link datamanger to sesh parms, run sim, export
		DataManager.getInstance().setSessionParams(parms);
		EvolveDirector.getInstance().runSimulation();
		EvolveDirector.getInstance().export(ExportFormat.CSV);

		
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
	
	/**
	 * Set up clear panes that are clickable over the GUI 
	 */
	public void flavorTextMode(boolean enabled) {
		if(enabled) {
			
		}
		else {
			
		}
	}
	
	public static void main(String[] args) {
		createAndShowGUI();
	}
}