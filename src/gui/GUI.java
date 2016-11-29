package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import shared.EvolveDirector;

/**
 * @author linneasahlberg
 * @author jasonfortunato
 * 
 * Started 9/18/16
 */
public class GUI extends EvoPane {

	private static final long serialVersionUID = 1L;

	boolean firstRun = true;
	
	// we'll put args here
	shared.SessionParameters parms;
	
	// Lab report stuff
	JLabel titleLabel; JTextField title;
	JLabel questionLabel; JTextArea question; JScrollPane questionPane;
	JLabel experLabel; JTextArea exper; JScrollPane experPane;
	JLabel resultsLabel; JTextArea results; JScrollPane resultsPane;
	JLabel discussionLabel; JTextArea discussion; JScrollPane discussionPane;
	JCheckBox showLabInfo;
	// GUI buttons
	JButton apply;
	JButton submit;
	JButton help;
	
	JLabel numPopsLabel; 			// Number of Pops
	JTextField numPops;	
	JLabel numGensLabel; 			// Number of Gens
	JTextField numGens;
	
	JCheckBox popSizeCheck;
	JCheckBox selectCheck;
	JCheckBox mutationCheck;
	JCheckBox migrationCheck;
	JCheckBox sexualSelectCheck;
	
	/* Evolutionary Forces Panes *********************************************/
	TitlePane tp = new TitlePane();
	ForcesPane fp = new ForcesPane();
	InitPopPane pp = new InitPopPane();
	GeneticDriftPane gd = new GeneticDriftPane();
	SelectionPane sp = new SelectionPane();
	MutationPane mp = new MutationPane();
	MigrationPane mip = new MigrationPane();
	SexSelectPane ssp = new SexSelectPane();
	NextPane np = new NextPane();
	
	ArrayList<EvoPane> forces = new ArrayList<EvoPane>();

	/**
	 * Member to enable singleton class
	 */
	public static GUI instance = null;

	/**
	 * Returns singleton instance of SimulationEngine
	 * @return singleton instance of SimulationEngine
	 */
	public static GUI getInstance() {
		if (instance == null) {
			instance  = new GUI();
		}
		return instance;
	}
	
	/** 
	 * This is the panel that will be added to the window (the frame)
	 */
	private GUI() {
		
		super();
		
		// left align
		c.anchor = GridBagConstraints.WEST;
		
		titleLabel = new JLabel("<html><b>Title:</b>");
		title = new JTextField(TEXT_LEN_EXTRA_LONG / 2);
		
		showLabInfo = new JCheckBox("Show lab report fields", true);
		
		questionLabel = new JLabel("<html><b>Question:</b>"); 
		question = new JTextArea(1, TEXT_LEN_EXTRA_LONG);
		questionPane = new JScrollPane(question);
		questionPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		
		experLabel = new JLabel("<html><b>Experimental Design:</b>"); 
		exper = new JTextArea(1, TEXT_LEN_EXTRA_LONG);
		experPane = new JScrollPane(exper);
		experPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		exper.setLineWrap(true);
		exper.setWrapStyleWord(true);
		
		resultsLabel = new JLabel("<html><b>Results:</b>");
		results = new JTextArea(1, TEXT_LEN_EXTRA_LONG);
		resultsPane = new JScrollPane(results);
		resultsPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		results.setLineWrap(true);
		results.setWrapStyleWord(true);
		
		discussionLabel = new JLabel("<html><b>Notes:</b>");
		discussion = new JTextArea(1, TEXT_LEN_EXTRA_LONG);
		discussionPane = new JScrollPane(discussion);
		discussionPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		discussion.setLineWrap(true);
		discussion.setWrapStyleWord(true);
		
		c.gridwidth = 5;
		c.gridx = 0; c.gridy = 1;
		add(titleLabel, c);
		c.gridx = 1; c.gridy = 1;
		add(title, c);
		c.gridx = 4; c.gridy = 1;
		add(showLabInfo, c);
		
		c.gridx = 0; c.gridy = 2;
		add(questionLabel, c);
		c.gridx = 1; c.gridy = 2;
		add(questionPane, c);
		
		c.gridx = 0; c.gridy = 3;
		add(experLabel, c);
		c.gridx = 1; c.gridy = 3;
		add(experPane, c);
		
		c.gridx = 0; c.gridy = 4;
		add(resultsLabel, c);
		c.gridx = 1; c.gridy = 4;
		add(resultsPane, c);
		
		c.gridx = 0; c.gridy = 5;
		add(discussionLabel, c);
		c.gridx = 1; c.gridy = 5;
		add(discussionPane, c);

		/* help stuff *****************************************************************************/
		help = new JButton("Info");
		c.gridx = 4; c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(help, c);


		/* EVOLUTIONARY FORCES ***************************************************************/
		JLabel evoForces = new JLabel("<html><b>Active Evolutionary Forces:");
		
		popSizeCheck = new JCheckBox("<html><b>Drift</b> (Population Size)", true);
		popSizeCheck.setEnabled(false);

		popSizeCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				gd.setEnabled(popSizeCheck.isSelected());
			}
		});	
		
		selectCheck = new JCheckBox("<html><b>Natural Selection", true);
		selectCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				sp.setEnabled(selectCheck.isSelected());
			}
		});	
		
		mutationCheck = new JCheckBox("<html><b>Mutation", true);
		mutationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				mp.setEnabled(mutationCheck.isSelected());
			}
		});

		
		migrationCheck = new JCheckBox("<html><b>Migration </b>(Gene Flow)", true);
		migrationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				mip.setEnabled(migrationCheck.isSelected());
			}
		});
		
		sexualSelectCheck = new JCheckBox("<html><b>Non-Random Mating", true);
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
		
		c.gridx = 0; c.gridy = 70;
		//add(np, c);
		
		/* apply and submit buttons ***********************************************/
		apply = new JButton("Apply");
		c.gridx = 3; c.gridy = 60;
		//add(apply, c);
		
		submit = new JButton("Run Simulation");	
		c.gridx = 4; c.gridy = 60;
		add(submit, c);		
				
		/* Set to 2 alleles mode on startup ***************************************/
		modeThreeAlleles(false);
		
		/* Action Listeners ********************************************************/
		
		// Show additional lab info
		showLabInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideLabInfo(showLabInfo.isSelected());			}
		});
		
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

				
				
				
				try {
					if(!selectCheck.isSelected()) {
						sp.fillWithOnes();
					}
					applyInfo();				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Exception in submission! Check your inputs!");
					e1.printStackTrace();
				}
				
				// Link datamanger to sesh parms, run sim, export
				EvolveDirector.getInstance().resetSimulationEngine();
				EvolveDirector.getInstance().storeSessionParameters(parms);
				new SimWorker().execute();
			}
			
			
			
			
			
		});

		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parms = new shared.SessionParameters();
				if(!selectCheck.isSelected()) {
					sp.fillWithOnes();
				}
				applyInfo();
			}
		});


		/**
		 * @author jasonfortunato
		 * @author linneasahlberg
		 * 
		 * help mode opens a pdf to be provided by frank
		 * 
		 * This does not work! This relies on the jar being run from the /EVOLVE/ directory
		 */
		help.addActionListener(new ActionListener() {              
			public void actionPerformed(ActionEvent e) {
				
				try {
					String absolutePath = new File("src/gui/evolveInfo.pdf").getAbsolutePath();
					File pdfFile = new File(absolutePath);
					if (pdfFile.exists()) {
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(pdfFile);
						} else {
							System.out.println("Awt Desktop is not supported!");
						}
					} else {
						System.out.println("File does not exist!");
						System.out.println("Path used: " + absolutePath);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}		
			}
		}); 

	} // end of constructor

	
	private void hideLabInfo(boolean b) {	
		//setLayout(null);
		questionLabel.setVisible(b);
		experLabel.setVisible(b);
		resultsLabel.setVisible(b);
		discussionLabel.setVisible(b);
		questionPane.setVisible(b);
		experPane.setVisible(b);		
		resultsPane.setVisible(b);
		discussionPane.setVisible(b);		
	}
	
	
	public void modeThreeAlleles(boolean b){
		super.modeThreeAlleles(b);
		pp.modeThreeAlleles(b);
		sp.modeThreeAlleles(b);
		mp.modeThreeAlleles(b);
		mip.modeThreeAlleles(b);
		ssp.modeThreeAlleles(b);
		
	}

	
	/**
	 *  pushes data to sesh parms
	 */
	public void applyInfo() { //throws Exception {
		
		// Set evolutionary force flags
		parms.setPopSizeChecked(popSizeCheck.isSelected());
		parms.setSelectChecked(selectCheck.isSelected());
		parms.setMutationChecked(mutationCheck.isSelected());
		parms.setMigrationChecked(migrationCheck.isSelected());
		parms.setSexSelectChecked(sexualSelectCheck.isSelected());

		submitTitle();
		
		// Submit info from the EvoPanes if necessary
		fp.submit(parms);
		pp.submit(parms);
		if(parms.isPopSizeChecked())
			gd.submit(parms);
		sp.submit(parms); // Is not checked b/c is filled with ones if unchecked
		if(parms.isMutationChecked())
			mp.submit(parms);
		if(parms.isMigrationChecked())
			mip.submit(parms);
		if(parms.isSexSelectChecked())
			ssp.submit(parms);
	}

	
	/**
	 * Submits title and lab info
	 */
	private void submitTitle() {
		parms.setTitle(title.getText());
		parms.setQuestion(question.getText());
		parms.setDesign(exper.getText());
		parms.setResults(results.getText());
		parms.setDiscuss(discussion.getText());
	}
	
	
	/**
	 * Called in main
	 */
	public static void createAndShowGUI() {

		//make the GUI panel and add evo force panels to GUI
		GUI g = GUI.getInstance();		
		
		//make the window
		JFrame frame = new JFrame();
		frame.setTitle("EVOLVE - v0.2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add the GUI to a scrollable pane
		JScrollPane scrPane = new JScrollPane(g);

		// add the scrollable pane to the window
		frame.add(scrPane);
		
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        SwingUtilities.updateComponentTreeUI(frame);
		
		frame.pack();
		frame.setVisible(true);

	}
	
	public static void main(String[] args) {
		createAndShowGUI();
	}
}