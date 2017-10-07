package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

import shared.EvolveDirector;

/**
 * @author linneasahlberg
 * @author jasonfortunato
 *
 * Started 9/18/16
 * The main GUI class. Combines all of the panes into one JFrame.
 *
 */

public class GUI extends EvoPane {

	private static final long serialVersionUID = 1L;
	public static boolean DEBUG_MATE = false;
	public static boolean DEBUG_REPRO = false;
	public static boolean DEBUG_MIGRATION = false;
	public static boolean DEBUG_SURVIVAL = false;
	public static boolean DEBUG_MUTATION = false;
	
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

	// Number of Pops
	JLabel numPopsLabel;
	JTextField numPops;

	// Number of Gens
	JLabel numGensLabel;
	JTextField numGens;

	// Evolutionary forces checkboxes
	JCheckBox popSizeCheck;
	JCheckBox selectCheck;
	JCheckBox mutationCheck;
	JCheckBox migrationCheck;
	JCheckBox sexualSelectCheck;

	// Error stuff for invalid inputs
	JFrame errorFrame;
	JButton okay;

	/* Evolutionary Forces Panes *********************************************/
	JLabel seedLabel; 				// Seed
	JTextField seedField;
	JLabel numAllelesLabel;			// Number of Alleles
	ButtonGroup numAlleles;
	static JRadioButton alleles2, alleles3;

	//ForcesPane fp = new ForcesPane();
	InitPopPane pp = new InitPopPane();
	GeneticDriftPane gd = new GeneticDriftPane();
	SelectionPane sp = new SelectionPane();
	MutationPane mp = new MutationPane();
	MigrationPane mip = new MigrationPane();
	SexSelectPane ssp = new SexSelectPane();

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
		c.anchor = GridBagConstraints.WEST;

		/* Lab report fields *******************************************************/
		color2List.add(getParent());
		titleLabel = new JLabel("<html><b>Title:</b>");
		title = new JTextField(TEXT_LEN_EXTRA_LONG / 2);

		// checkbox to show/hide lab report fields
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
		help = new JButton("Help");
		c.gridx = 4; c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		add(help, c);

		/* Here's a bar for "aesthetics" ****************************************************/
		c.gridy = 6;
		/*for(int i = 0; i < 5; i++) {
			c.gridx = i;
			c.anchor = GridBagConstraints.WEST;
			add(new JLabel("_______________________________"), c);
		}*/

		/* seed stuff ********************************************************/
		seedLabel = new JLabel("<html><b>Seed: </b>");
		seedField = new JTextField(TEXT_LEN_LONG);

		// add seed label and field to frame
		c.gridx = 4; c.gridy++;
		c.anchor = GridBagConstraints.WEST;
		add(seedLabel, c);
		c.gridx = 4;
		c.anchor = GridBagConstraints.CENTER;
		add(seedField, c);

		// Fill in a random seed
    // range of seed should be small enough for students to write down, we
    // don't mind sacrificing some "randomness" for convenience
		Integer randomNum = (new Random()).nextInt(100000);
		seedField.setText(randomNum.toString());

		/* num alleles stuff ****************************************************/
		numAllelesLabel = new JLabel("<html><b>Number of Alleles: </b>");

		// 2 and 3 allele radio buttons
		numAlleles = new ButtonGroup();
		alleles2 = new JRadioButton("2", true);
		alleles3 = new JRadioButton("3");

		// add radio buttons to button groups
		numAlleles.add(alleles2);
		numAlleles.add(alleles3);

		// add radio buttons and labels to gui
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		add(numAllelesLabel, c);
		c.gridx = 1;
		add(alleles2, c);
		c.gridx = 2;
		add(alleles3, c);

		/* EVOLUTIONARY FORCES ***************************************************************/
		JLabel evoForces = new JLabel("<html><b>Active Evolutionary Forces:");

		popSizeCheck = new JCheckBox("<html><b>Genetic Drift", true);
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


		migrationCheck = new JCheckBox("<html><b>Migration", true);
		migrationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mip.setEnabled(migrationCheck.isSelected());
			}
		});

		sexualSelectCheck = new JCheckBox("<html><b>Sexual Selection", true);
		sexualSelectCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ssp.setEnabled(sexualSelectCheck.isSelected());
			}
		});

		c.gridx = 0; c.gridy++;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		add(evoForces, c);
		c.gridx = 0; c.gridy++;
		c.gridwidth = 1;
		add(popSizeCheck, c);
		c.gridx = 1;
		add(selectCheck, c);
		c.gridx = 2;
		add(mutationCheck, c);
		c.gridx = 3;
		add(migrationCheck, c);
		c.gridx = 4;
		add(sexualSelectCheck, c);


		/* Panes ****************************************************************************/
		c.gridwidth = 7;

//		c.gridx = 0; c.gridy = 6;
	//	add(fp, c);

		c.gridx = 0; c.gridy++;
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

		submit = new JButton("<html><span style='font-size:13px'>Run Simulation");
		Color buttonColor = new Color(115, 229, 103);
		submit.setBackground(buttonColor);
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
		alleles2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeThreeAlleles(false);

			}
		});

		alleles3.addItemListener(new ItemListener() {
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
					EvolveDirector.getInstance().resetSimulationEngine();
					EvolveDirector.getInstance().storeSessionParameters(parms);
					new SimWorker().execute();

				} catch (Exception e1) {
					errorFrame = new JFrame();
					JLabel errorText = new JLabel("<html><font color = red> <b> One or more inputs are incorrect.  Verify every active box has the appropriate input.");
					//errorText.setFont(new Font("Serif", Font.PLAIN, 32));
					errorFrame.setTitle("Invalid input");
					errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

					Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
					errorText.setBorder(padding);
					errorFrame.add(errorText, BorderLayout.NORTH);
					okay = new JButton("Okay");
					errorFrame.add(okay, BorderLayout.EAST);
					errorFrame.pack();
					errorFrame.setVisible(true);
					okay.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							errorFrame.dispatchEvent(new WindowEvent(errorFrame, WindowEvent.WINDOW_CLOSING));
						}
					});
					e1.printStackTrace();
				}
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
					openHelp();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	} // end of constructor

	private void openHelp() throws Exception {

		InputStream pdfStream = GUI.class.getResourceAsStream("evolveInfo.pdf");

		File pdfTemp = new File("evolveTempManual.pdf");
		pdfTemp.deleteOnExit();

		FileOutputStream fos = new FileOutputStream(pdfTemp);

		while(pdfStream.available() > 0) {
			fos.write(pdfStream.read());
		}

		fos.close();
		pdfStream.close();

		if (pdfTemp.exists()) {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(pdfTemp);
			} else {
				System.out.println("Awt Desktop is not supported!");
			}
		} else {
			System.out.println("File does not exist!");
			System.out.println("Path used: " + pdfStream);
		}

	}

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
		parms.setThreeAlleles(alleles3.isSelected());
		parms.setSeed(Long.parseLong(seedField.getText()));

		// Set evolutionary force flags
		parms.setPopSizeChecked(popSizeCheck.isSelected());
		parms.setSelectChecked(selectCheck.isSelected());
		parms.setMutationChecked(mutationCheck.isSelected());
		parms.setMigrationChecked(migrationCheck.isSelected());
		parms.setSexSelectChecked(sexualSelectCheck.isSelected());

		submitTitle();

		// Submit info from the EvoPanes if necessary
		//fp.submit(parms);
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
		frame.setTitle("EVOLVE - Hamilton College Computer Science");
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
		  try {
		        String one = args[0];
		        switch (one.toLowerCase()) {
		        		case "mate":
			        		DEBUG_MATE = true;
			        		break;
		        		case "reproduction":
			        		DEBUG_REPRO = true;
			        		break;
		        		case "migration":
		        			DEBUG_MIGRATION = true;
		        			break;
		        		case "survival":
		        			DEBUG_SURVIVAL = true;
		        			break;
		        		case "mutation":
		        			DEBUG_MUTATION = true;
		        			break;
		        		case "all":
		        			DEBUG_MATE = true;
		        			DEBUG_REPRO = true;
		        			DEBUG_MIGRATION = true;
		        			DEBUG_SURVIVAL = true;
		        			DEBUG_MUTATION = true;
		        			break;
		        		default:
		        			break;
		        }
		    }
		    catch (ArrayIndexOutOfBoundsException e){
		        //System.out.println("ArrayIndexOutOfBoundsException caught");
		    	}
		    finally {
				createAndShowGUI();
		    }
	}
}
