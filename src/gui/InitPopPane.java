package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.util.ArrayList;

/**
 * 
 * @author jasonfortunato
 * @author linneasahlberg
 * 
 */
public class InitPopPane extends EvoPane {

	JLabel popLabel;			// Population size
	JLabel popSizeLabel;
	JTextField popSizeField;
	ButtonGroup initPopVals;     // radio buttons
	JRadioButton alleleFreqs;
	JRadioButton genotypeNums;
	JLabel initPopLabel;  			// Initial population
	JTextField initPop;
	JLabel initFreqALabel, initFreqBLabel,		// Initial frequencies
		initFreqCLabel; 			
	JTextField initFreqA, initFreqB, initFreqC;
	JLabel alleleFreqsLabel, genoNumsLabel;		//labels for subsections
	JLabel calcFreqAALabel, calcFreqABLabel, 
		calcFreqBBLabel, calcFreqACLabel, calcFreqBCLabel,
		calcFreqCCLabel;
	JLabel freqTotal;
	JTextField calcFreqAA, calcFreqAB, 
		calcFreqBB, calcFreqAC, calcFreqBC,
		calcFreqCC;
	JLabel genoAALabel, genoABLabel, 
		genoBBLabel, genoACLabel, genoBCLabel,
		genoCCLabel;
	JLabel genoTotal;
	JTextField genoAA, genoAB, 
		genoBB, genoAC, genoBC,
		genoCC;
	JPanel afPane, gnPane;

	ArrayList<Component> alFreqList = new ArrayList<Component>();
	ArrayList<Component> gtNumList = new ArrayList<Component>();
	
	public InitPopPane() {

		super();
		
		popLabel = new JLabel("Initial Population:");
		popSizeLabel = new JLabel("Population Size:");
		popSizeField = new JTextField(TEXT_LEN_LONG);
		
		popSizeField.setName(INT); popSizeField.setInputVerifier(iv);
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(popLabel, c);
		
		c.gridx = 1; c.gridy = 1;
		add(popSizeLabel, c);
		c.gridx = 2; c.gridy = 1;
		add(popSizeField, c);
		
		// population constant radio button stuff
		initPopVals = new ButtonGroup();
		alleleFreqs = new JRadioButton("Enter Allele Frequncies", true);
		genotypeNums = new JRadioButton("Enter Genotype Numbers");
		initPopVals.add(alleleFreqs);
		initPopVals.add(genotypeNums);

		
		alleleFreqsLabel = new JLabel("Allele frequencies (0-1):");
		initFreqALabel = new JLabel("A:");
		initFreqBLabel = new JLabel("B:");
		initFreqCLabel = new JLabel("C:"); threeAllelesList.add(initFreqCLabel);
		initFreqA = new JTextField(TEXT_LEN_SHORT);
		initFreqB = new JTextField(TEXT_LEN_SHORT);
		initFreqC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(initFreqC);
		
		initFreqA.setName(RATE);
		initFreqB.setName(RATE);
		initFreqC.setName(RATE);
		
		calcFreqAALabel = new JLabel("AA");
		calcFreqABLabel = new JLabel("AB");
		calcFreqBBLabel = new JLabel("BB");
		calcFreqACLabel = new JLabel("AC"); threeAllelesList.add(calcFreqACLabel);
		calcFreqBCLabel = new JLabel("BC"); threeAllelesList.add(calcFreqBCLabel);
		calcFreqCCLabel = new JLabel("CC"); threeAllelesList.add(calcFreqCCLabel);
		calcFreqAA = new JTextField(TEXT_LEN_SHORT);
		calcFreqAB = new JTextField(TEXT_LEN_SHORT);
		calcFreqBB = new JTextField(TEXT_LEN_SHORT);
		calcFreqAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(calcFreqAC);
		calcFreqBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(calcFreqBC);
		calcFreqCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(calcFreqCC);
		
		c.gridwidth = 2;
		c.gridx = 1; c.gridy = 2;
		add(alleleFreqs, c);
		c.gridx = 2; c.gridy = 2;
		add(genotypeNums, c);

		afPane = new JPanel();
		afPane.setLayout(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints(); 	// t for temp constraints
		t.insets = new Insets(0, 0, 3, 15);
				
		t.gridwidth = 6;
		t.gridx = 0; t.gridy = 0;
		afPane.add(alleleFreqsLabel, t);
		
		t.gridwidth = 1;
		t.gridx = 6; t.gridy = 0;
		afPane.add(initFreqALabel, t);
		t.gridx = 7; t.gridy = 0;
		afPane.add(initFreqA, t);
		
		t.gridx = 8; t.gridy = 0;
		afPane.add(initFreqBLabel, t);
		t.gridx = 9; t.gridy = 0;
		afPane.add(initFreqB, t);
		
		t.gridx = 10; t.gridy = 0;
		afPane.add(initFreqCLabel, t);
		t.gridx = 11; t.gridy = 0;
		afPane.add(initFreqC, t);
		
		t.gridx = 2; t.gridy = 1;
		afPane.add(calcFreqAALabel, t);
		t.gridx = 2; t.gridy = 2;
		afPane.add(calcFreqAA, t);
		
		t.gridx = 3; t.gridy = 1;
		afPane.add(calcFreqABLabel, t);
		t.gridx = 3; t.gridy = 2;
		afPane.add(calcFreqAB, t);
		
		t.gridx = 4; t.gridy = 1;
		afPane.add(calcFreqBBLabel, t);
		t.gridx = 4; t.gridy = 2;
		afPane.add(calcFreqBB, t);
		
		t.gridx = 5; t.gridy = 1;
		afPane.add(calcFreqACLabel, t);
		t.gridx = 5; t.gridy = 2;
		afPane.add(calcFreqAC, t);
		
		t.gridx = 6; t.gridy = 1;
		afPane.add(calcFreqBCLabel, t);
		t.gridx = 6; t.gridy = 2;
		afPane.add(calcFreqBC, t);
		
		t.gridx = 7; t.gridy = 1;
		afPane.add(calcFreqCCLabel, t);
		t.gridx = 7; t.gridy = 2;
		afPane.add(calcFreqCC, t);
		
		c.gridx = 1; c.gridy = 3;
		c.gridwidth = 7;
		add(afPane, c);
		
		/*Genotype Number Pane********************************/
		genoNumsLabel = new JLabel("Genotype Numbers:");
		genoAALabel = new JLabel("AA");
		genoABLabel = new JLabel("AB");
		genoBBLabel = new JLabel("BB");
		genoACLabel = new JLabel("AC"); threeAllelesList.add(genoACLabel);
		genoBCLabel = new JLabel("BC"); threeAllelesList.add(genoBCLabel);
		genoCCLabel = new JLabel("CC"); threeAllelesList.add(genoCCLabel);
		
		genoAA = new JTextField(TEXT_LEN_SHORT);
		genoAB = new JTextField(TEXT_LEN_SHORT);
		genoBB = new JTextField(TEXT_LEN_SHORT);
		genoAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(genoAC);
		genoBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(genoBC);
		genoCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(genoCC);
		
		gnPane = new JPanel();
		gnPane.setLayout(new GridBagLayout());
		t = new GridBagConstraints();
		t.insets = new Insets(0, 0, 0, 15);
		
		t.gridwidth = 4;
		t.gridx = 0; t.gridy = 0;
		gnPane.add(genoNumsLabel, t);
		
		t.gridwidth = 1;
		t.gridx = 1; t.gridy = 1;
		gnPane.add(genoAALabel, t);
		t.gridx = 1; t.gridy = 2;
		gnPane.add(genoAA, t);
		
		t.gridx = 2; t.gridy = 1;
		gnPane.add(genoABLabel, t);
		t.gridx = 2; t.gridy = 2;
		gnPane.add(genoAB, t);
		
		t.gridx = 3; t.gridy = 1;
		gnPane.add(genoBBLabel, t);
		t.gridx = 3; t.gridy = 2;
		gnPane.add(genoBB, t);
		
		t.gridx = 4; t.gridy = 1;
		gnPane.add(genoACLabel, t);
		t.gridx = 4; t.gridy = 2;
		gnPane.add(genoAC, t);
		
		t.gridx = 5; t.gridy = 1;
		gnPane.add(genoBCLabel, t);
		t.gridx = 5; t.gridy = 2;
		gnPane.add(genoBC, t);
		
		t.gridx = 6; t.gridy = 1;
		gnPane.add(genoCCLabel, t);
		t.gridx = 6; t.gridy = 2;
		gnPane.add(genoCC, t);
		
		c.gridx = 1; c.gridy = 4;
		add(gnPane, c);
		
		
		addToLists();
		
		// Set actions for the PopConstTrue/False radio buttons
		alleleFreqs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeAlleleFreqs(true);
			}
		});
		
		genotypeNums.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeAlleleFreqs(false);
			}
		});
		
		}

		//	adds components to the alFreqList and gtNumList
		private void addToLists() {
			
		}
	
		private void modeAlleleFreqs(boolean b){

			
			for(Component comp : alFreqList) {
				comp.setEnabled(b);
			}
			for(Component comp : gtNumList) {
				comp.setEnabled(!b);
			}
		/*	if(b == true) { // clear the two text fields
				carryCap.setText("");
				postCrash.setText("");
			}
		*/
		}

		public void submit(shared.SessionParameters p) {
		/*	
		p.setPopSize(Integer.parseInt(popSizeField.getText()));
			p.setPopConst(popConstTrue.isSelected());
			if(popConstFalse.isSelected()){
				p.setPopCapacity(Integer.parseInt(carryCap.getText()));
				p.setCrashCapacity(Integer.parseInt(postCrash.getText()));
			}
		*/

			
			
			
		}
}
	
