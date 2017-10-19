package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import shared.EvolveDirector;

/**
 * @author linneasahlberg
 * @author jasonfortunato
 * @author alexdennis
 *
 * Started 9/18/16
 * The main GUI class. Combines all of the panes into one JFrame.
 *
 */

public class GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	public static boolean DEBUG_MATE = false;
	public static boolean DEBUG_REPRO = false;
	public static boolean DEBUG_MIGRATION = false;
	public static boolean DEBUG_SURVIVAL = false;
	public static boolean DEBUG_MUTATION = false;
	
	public static boolean DEBUG_SUMMARY = false;
	public static boolean MAT_SUM = false;
	public static boolean REP_SUM = false;
	public static boolean MIG_SUM = false;
	public static boolean SURV_SUM = false;
	public static boolean MUT_SUM = false;
	
	boolean firstRun = true;

	// we'll put args here
	shared.SessionParameters parms;

	// GUI buttons
	JButton submit;

	// Error stuff for invalid inputs
	JFrame errorFrame;
	JButton okay;

  HeaderPane hp = new HeaderPane();
  InitPopPane pp = new InitPopPane();
	GeneticDriftPane gd = new GeneticDriftPane();
	SelectionPane sp = new SelectionPane();
	MutationPane mp = new MutationPane();
	MigrationPane mip = new MigrationPane();
	SexSelectPane ssp = new SexSelectPane();

  JPanel mainPanel;

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

    setLayout(new BorderLayout());
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    mainPanel.add(hp);
    mainPanel.add(new JSeparator(JSeparator.HORIZONTAL));
    mainPanel.add(pp);
    mainPanel.add(new JSeparator(JSeparator.HORIZONTAL));
    mainPanel.add(gd);
    mainPanel.add(new JSeparator(JSeparator.HORIZONTAL));
    mainPanel.add(sp);
    mainPanel.add(new JSeparator(JSeparator.HORIZONTAL));
    mainPanel.add(mp);
    mainPanel.add(new JSeparator(JSeparator.HORIZONTAL));
    mainPanel.add(mip);
    mainPanel.add(new JSeparator(JSeparator.HORIZONTAL));
    mainPanel.add(ssp);

    JScrollPane mainScroll = new JScrollPane(mainPanel);
    mainScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    add(mainScroll, BorderLayout.CENTER);

		submit = new JButton("<html><span style='font-size:13px'>Run Simulation");
		Color buttonColor = new Color(115, 229, 103);
		submit.setBackground(buttonColor);
		add(submit, BorderLayout.PAGE_END);

    modeThreeAlleles(false);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a new, blank sesh parms object on each submit click
				parms = new shared.SessionParameters();
				try {
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
	} // end of constructor

	public void modeThreeAlleles(boolean b){
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
		// Submit info from the EvoPanes if necessary
		//fp.submit(parms);
    hp.submit(parms);
		pp.submit(parms);
		if(parms.isPopSizeChecked())
			gd.submit(parms);
    if(!parms.isSelectChecked())
      sp.fillWithOnes();
		sp.submit(parms); // Is not checked b/c is filled with ones if unchecked
		if(parms.isMutationChecked())
			mp.submit(parms);
		if(parms.isMigrationChecked())
			mip.submit(parms);
		if(parms.isSexSelectChecked())
			ssp.submit(parms);
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

		// add the scrollable pane to the window
		frame.add(g);
	    try {
	      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	        if ("Nimbus".equals(info.getName())) {
	          UIManager.setLookAndFeel(info.getClassName());
	          break;
	        }
	      }
	    } 
	    catch (Exception e) {
	      e.printStackTrace();
	    }

	    SwingUtilities.updateComponentTreeUI(frame);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
	  try {
		  String arg = args[0];
	      switch (arg.toLowerCase()) {
	    		case "mat":
	      		DEBUG_MATE = true;
	      		break;
	    		case "rep":
	      		DEBUG_REPRO = true;
	      		break;
	    		case "mig":
	    			DEBUG_MIGRATION = true;
	    			break;
	    		case "surv":
	    			DEBUG_SURVIVAL = true;
	    			break;
	    		case "mut":
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
	      String sum = args[1].toLowerCase();
	      if (sum.contains("all")) {
	      		DEBUG_SUMMARY = true;
	      		MAT_SUM = true;
	      		REP_SUM = true;
	      		MIG_SUM = true;
	      		SURV_SUM = true;
	      		MUT_SUM = true;
	      }
	      else {
	    	  	if (sum.contains("mat")) MAT_SUM = true;
	    	  	if (sum.contains("rep")) REP_SUM = true;
	    	  	if (sum.contains("mig")) MIG_SUM = true;
	    	  	if (sum.contains("surv")) SURV_SUM = true;
	    	  	if (sum.contains("mut")) MUT_SUM = true;
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
