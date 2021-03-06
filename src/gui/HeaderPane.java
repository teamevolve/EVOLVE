package gui;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.Box;

/**
 *
 * @author alexdennis
 *
 */
public class HeaderPane extends EvoMainPanel {
  // Lab report stuff
  JLabel headerLabel;
  JLabel titleLabel; EvoTextField title;
  JLabel questionLabel; JTextArea question; JScrollPane questionPane;
  JLabel experLabel; JTextArea exper; JScrollPane experPane;
  JLabel predictLabel; JTextArea predict; JScrollPane predictPane;
  JLabel resultsLabel; JTextArea results; JScrollPane resultsPane;
  JLabel discussionLabel; JTextArea discussion; JScrollPane discussionPane;
  JCheckBox showLabInfo;

  JButton intro;
  JButton help;

	// Evolutionary forces checkboxes
	JCheckBox popSizeCheck;
	JCheckBox selectCheck;
	JCheckBox mutationCheck;
	JCheckBox migrationCheck;
	JCheckBox sexualSelectCheck;

  /* Evolutionary Forces Panes *********************************************/
	JLabel seedLabel; 				// Seed
	EvoTextField seedField;
	JLabel numAllelesLabel;			// Number of Alleles
	ButtonGroup numAlleles;
	static JRadioButton alleles2, alleles3;

  public HeaderPane() {
    super();

    // set layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

    setBackground(COLOR1);

    c.anchor = GridBagConstraints.LINE_START;

    headerLabel = new JLabel("<html><b><span style='font-size:11px'>Experiment Parameters:</span></b>");
    c.gridx = 0; c.gridy = 0;
    c.gridwidth = 2;
    add(headerLabel, c);
    c.gridwidth = 1;

    /* help stuff *****************************************************************************/
		help = new JButton("Help");
    intro = new JButton("Introduction");

    c.gridx = 1;
    c.anchor = GridBagConstraints.CENTER;
    add(intro, c);

		c.gridx = 2;
		c.anchor = GridBagConstraints.LINE_END;
		add(help, c);
    c.anchor = GridBagConstraints.LINE_START;

		/* Lab report fields *******************************************************/
		titleLabel = new JLabel("<html><b>Title:</b>");
		title = new EvoTextField(TEXT_LEN_LONG);

		// // checkbox to show/hide lab report fields
		showLabInfo = new JCheckBox("Show lab report fields", true);

		questionLabel = new JLabel("<html><b>Question:</b>");
		question = new JTextArea(1, TEXT_LEN_LONG);
		questionPane = new JScrollPane(question);
		questionPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
    question.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
    question.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

		experLabel = new JLabel("<html><b>Experimental Design:</b>");
		exper = new JTextArea(1, TEXT_LEN_LONG);
		experPane = new JScrollPane(exper);
		experPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		exper.setLineWrap(true);
		exper.setWrapStyleWord(true);
    exper.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
    exper.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

    predictLabel = new JLabel("<html><b>Predictions:</b>");
    predict = new JTextArea(1, TEXT_LEN_LONG);
    predictPane = new JScrollPane(predict);
    predictPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    predict.setLineWrap(true);
    predict.setWrapStyleWord(true);
    predict.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
    predict.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

		resultsLabel = new JLabel("<html><b>Results:</b>");
		results = new JTextArea(1, TEXT_LEN_LONG);
		resultsPane = new JScrollPane(results);
		resultsPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		results.setLineWrap(true);
		results.setWrapStyleWord(true);
    results.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
    results.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

		discussionLabel = new JLabel("<html><b>Notes:</b>");
		discussion = new JTextArea(1, TEXT_LEN_LONG);
		discussionPane = new JScrollPane(discussion);
		discussionPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		discussion.setLineWrap(true);
		discussion.setWrapStyleWord(true);
    discussion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
    discussion.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

		c.gridx = 0; c.gridy = 1;
		add(titleLabel, c);
		c.gridx = 2;
		add(showLabInfo, c);
		c.gridx = 0; c.gridy++;
		add(questionLabel, c);
		c.gridx = 0; c.gridy++;
		add(experLabel, c);
    c.gridx = 0; c.gridy++;
    add(predictLabel, c);
		c.gridx = 0; c.gridy++;
		add(resultsLabel, c);
		c.gridx = 0; c.gridy++;
		add(discussionLabel, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1.0;
    c.gridx = 1; c.gridy = 1;
    add(title, c);
    c.gridwidth = 2;
    c.gridx = 1; c.gridy++;
    add(questionPane, c);
    c.gridx = 1; c.gridy++;
    add(experPane, c);
    c.gridx = 1; c.gridy++;
    add(predictPane, c);
    c.gridx = 1; c.gridy++;
    add(resultsPane, c);
    c.gridx = 1; c.gridy++;
    add(discussionPane, c);
    c.gridwidth = 1;
    c.weightx = 0.0;
    c.fill = GridBagConstraints.NONE;

		/* seed stuff ********************************************************/
		seedLabel = new JLabel("<html><b>Seed:</b>");
		seedField = new EvoTextField(TEXT_LEN_SHORT);

    EvoPanel seedPane = new EvoPanel();
    seedPane.setBackground(getBackground());
    seedPane.setLayout(new BoxLayout(seedPane, BoxLayout.LINE_AXIS));
    seedPane.add(seedLabel);
    seedPane.add(seedField);

    c.gridx = 2; c.gridy++;
    c.anchor = GridBagConstraints.LINE_END;
    add(seedPane, c);
    c.anchor = GridBagConstraints.LINE_START;

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

    EvoPanel allelesPane = new EvoPanel();
    allelesPane.setBackground(getBackground());
    allelesPane.setLayout(new WrapLayout(WrapLayout.LEADING));
    allelesPane.add(alleles2);
    allelesPane.add(Box.createHorizontalStrut(20));
    allelesPane.add(alleles3);

		// add radio buttons and labels to gui
		c.gridx = 0;
		add(numAllelesLabel, c);
		c.gridx = 1;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
		add(allelesPane, c);
    c.weightx = 0.0;
    c.fill = GridBagConstraints.NONE;

		/* EVOLUTIONARY FORCES ***************************************************************/
		JLabel evoForces = new JLabel("<html><b>Active Evolutionary Forces:");

		popSizeCheck = new JCheckBox("<html><b>Genetic Drift", true);
		popSizeCheck.setEnabled(false);
		popSizeCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.getInstance().gd.setEnabled(popSizeCheck.isSelected());
			}
		});

		selectCheck = new JCheckBox("<html><b>Natural Selection", true);
		selectCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.getInstance().sp.setEnabled(selectCheck.isSelected());
			}
		});

		mutationCheck = new JCheckBox("<html><b>Mutation", true);
		mutationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.getInstance().mp.setEnabled(mutationCheck.isSelected());
			}
		});


		migrationCheck = new JCheckBox("<html><b>Migration", true);
		migrationCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.getInstance().mip.setEnabled(migrationCheck.isSelected());
			}
		});

		sexualSelectCheck = new JCheckBox("<html><b>Nonrandom Mating", true);
		sexualSelectCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI.getInstance().ssp.setEnabled(sexualSelectCheck.isSelected());
			}
		});

		c.gridx = 0; c.gridy++;
		c.gridwidth = 3;
		add(evoForces, c);

    EvoPanel forcesPane = new EvoPanel();
    forcesPane.setBackground(getBackground());
    forcesPane.setLayout(new WrapLayout(WrapLayout.LEADING));
    forcesPane.add(popSizeCheck);
    forcesPane.add(Box.createHorizontalStrut(20));
    forcesPane.add(selectCheck);
    forcesPane.add(Box.createHorizontalStrut(20));
    forcesPane.add(mutationCheck);
    forcesPane.add(Box.createHorizontalStrut(20));
    forcesPane.add(migrationCheck);
    forcesPane.add(Box.createHorizontalStrut(20));
    forcesPane.add(sexualSelectCheck);

		c.gridx = 0; c.gridy++;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
		add(forcesPane, c);
    c.gridwidth = 1;
    c.weightx = 0.0;
    c.fill = GridBagConstraints.NONE;

		// Show additional lab info
		showLabInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideLabInfo(showLabInfo.isSelected());			}
		});

		// Set actions for the NumAlleles radio buttons
		alleles2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				GUI.getInstance().modeThreeAlleles(false);
			}
		});

		alleles3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				GUI.getInstance().modeThreeAlleles(true);
			}
		});

    intro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openHelp("/help/Help01_Introduction.pdf");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openHelp("/help/Help02_Exp_Config.pdf");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
  }

	private void hideLabInfo(boolean b) {
		questionLabel.setVisible(b);
		experLabel.setVisible(b);
    predictLabel.setVisible(b);
		resultsLabel.setVisible(b);
		discussionLabel.setVisible(b);
		questionPane.setVisible(b);
		experPane.setVisible(b);
    predictPane.setVisible(b);
		resultsPane.setVisible(b);
		discussionPane.setVisible(b);
	}

  public void submit(shared.SessionParameters p) {
    p.setTitle(title.getText());
		p.setQuestion(question.getText());
		p.setDesign(exper.getText());
    p.setPrediction(predict.getText());
		p.setResults(results.getText());
		p.setDiscuss(discussion.getText());

    p.setThreeAlleles(alleles3.isSelected());
    p.setSeed(Long.parseLong(seedField.getText()));

    // Set evolutionary force flags
    p.setPopSizeChecked(popSizeCheck.isSelected());
    p.setSelectChecked(selectCheck.isSelected());
    p.setMutationChecked(mutationCheck.isSelected());
    p.setMigrationChecked(migrationCheck.isSelected());
    p.setSexSelectChecked(sexualSelectCheck.isSelected());
  }
}
