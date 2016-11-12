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

import shared.Allele;
import shared.Genotype;
import shared.SessionParameters;

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
	JLabel numPopsLabel;         // number of populations
	JTextField numPops; 
	JLabel numGensLabel;		// number of generations
	JTextField numGens;
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
		popSizeLabel = new JLabel("Population Size:"); alFreqList.add(popSizeLabel);
		popSizeField = new JTextField(TEXT_LEN_LONG); alFreqList.add(popSizeField);
		
		popSizeField.setName(INT); popSizeField.setInputVerifier(iv);
		
		// init pop size
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(popLabel, c);
	
		// Moved to afFreq table
//		c.gridx = 1;
//		add(popSizeLabel, c);
//		c.gridx = 2;
//		add(popSizeField, c);
		
		// num gens and pops
		numGensLabel = new JLabel("Number of Generations:");
		numGens = new JTextField(TEXT_LEN_LONG);
		numPopsLabel = new JLabel("Number of Populations:");
		numPops = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 1;
		add(numGensLabel, c);
		c.gridx++;
		add(numGens, c);
		c.gridx = 1; c.gridy++;
		add(numPopsLabel, c);
		c.gridx++;
		add(numPops, c);
		
		// population constant radio button stuff
		initPopVals = new ButtonGroup();
		alleleFreqs = new JRadioButton("Enter Allele Frequncies", true);
		genotypeNums = new JRadioButton("Enter Genotype Numbers");
		initPopVals.add(alleleFreqs);
		initPopVals.add(genotypeNums);

		
		alleleFreqsLabel = new JLabel("Allele frequencies (0-1):"); alFreqList.add(alleleFreqsLabel);
		initFreqALabel = new JLabel("A:"); alFreqList.add(initFreqALabel);
		initFreqBLabel = new JLabel("B:"); alFreqList.add(initFreqBLabel);
		initFreqCLabel = new JLabel("C:"); alFreqList.add(initFreqCLabel); threeAllelesList.add(initFreqCLabel);
		initFreqA = new JTextField(TEXT_LEN_SHORT);
		initFreqB = new JTextField(TEXT_LEN_SHORT);
		initFreqC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(initFreqC);
		
		initFreqA.setName(RATE);
		initFreqB.setName(RATE);
		initFreqC.setName(RATE);
		
		calcFreqAALabel = new JLabel("AA: ___");
		calcFreqABLabel = new JLabel("AB: ___");
		calcFreqBBLabel = new JLabel("BB: ___");
		calcFreqACLabel = new JLabel("AC: ___"); threeAllelesList.add(calcFreqACLabel);
		calcFreqBCLabel = new JLabel("BC: ___"); threeAllelesList.add(calcFreqBCLabel);
		calcFreqCCLabel = new JLabel("CC: ___"); threeAllelesList.add(calcFreqCCLabel);
		
		c.gridwidth = 2;
		c.gridx = 1; c.gridy++;
		add(alleleFreqs, c);
		c.gridx = 2;
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
		
		t.gridx = 12;
		afPane.add(popSizeLabel);
		t.gridx++;
		afPane.add(popSizeField);
		
		t.gridx = 3; t.gridy = 1;
		afPane.add(calcFreqAALabel, t);
		//t.gridx = 2; t.gridy = 2;
		//afPane.add(calcFreqAA, t);
		
		t.gridx = 4; t.gridy = 1;
		afPane.add(calcFreqABLabel, t);
		//t.gridx = 3; t.gridy = 2;
		//afPane.add(calcFreqAB, t);
		
		t.gridx = 5; t.gridy = 1;
		afPane.add(calcFreqBBLabel, t);
		//t.gridx = 4; t.gridy = 2;
		//afPane.add(calcFreqBB, t);
		
		t.gridx = 6; t.gridy = 1;
		afPane.add(calcFreqACLabel, t);
		//t.gridx = 5; t.gridy = 2;
		//afPane.add(calcFreqAC, t);
		
		t.gridx = 7; t.gridy = 1;
		afPane.add(calcFreqBCLabel, t);
		//t.gridx = 6; t.gridy = 2;
		//afPane.add(calcFreqBC, t);
		
		t.gridx = 8; t.gridy = 1;
		afPane.add(calcFreqCCLabel, t);
		//t.gridx = 7; t.gridy = 2;
		//afPane.add(calcFreqCC, t);
		
		c.gridx = 1; c.gridy++;
		c.gridwidth = 7;
		add(afPane, c);
		
		/*Genotype Number Pane********************************/
		genoNumsLabel = new JLabel("Genotype Numbers:"); gtNumList.add(genoNumsLabel);
		genoAALabel = new JLabel("AA"); gtNumList.add(genoAALabel);
		genoABLabel = new JLabel("AB"); gtNumList.add(genoABLabel);
		genoBBLabel = new JLabel("BB"); gtNumList.add(genoBBLabel);
		genoACLabel = new JLabel("AC"); gtNumList.add(genoACLabel); threeAllelesList.add(genoACLabel);
		genoBCLabel = new JLabel("BC"); gtNumList.add(genoBCLabel); threeAllelesList.add(genoBCLabel);
		genoCCLabel = new JLabel("CC"); gtNumList.add(genoCCLabel); threeAllelesList.add(genoCCLabel);
		
		genoAA = new JTextField(TEXT_LEN_SHORT); gtNumList.add(genoAA);
		genoAB = new JTextField(TEXT_LEN_SHORT); gtNumList.add(genoAB);
		genoBB = new JTextField(TEXT_LEN_SHORT); gtNumList.add(genoBB);
		genoAC = new JTextField(TEXT_LEN_SHORT); gtNumList.add(genoAC); threeAllelesList.add(genoAC);
		genoBC = new JTextField(TEXT_LEN_SHORT); gtNumList.add(genoBC); threeAllelesList.add(genoBC);
		genoCC = new JTextField(TEXT_LEN_SHORT); gtNumList.add(genoCC); threeAllelesList.add(genoCC);
		
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
		
		c.gridx = 1; c.gridy++;
		add(gnPane, c);
		
		
		addToLists();
		modeAlleleFreqs(true);
		
		// Set actions for the PopConstTrue/False radio buttons
		alleleFreqs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeAlleleFreqs(true);
				initFreqC.setEnabled(false);
				initFreqCLabel.setEnabled(false);
			}
		});
		
		genotypeNums.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeAlleleFreqs(false);
				initFreqC.setEnabled(false);
				initFreqCLabel.setEnabled(false);
			}
		});
		
		}

		//	adds components to the alFreqList and gtNumList
		private void addToLists() {
			
		}
	
		private void modeAlleleFreqs(boolean b) {

			for(Component comp : alFreqList)
				comp.setEnabled(b);
			
			for(Component comp : gtNumList) 
				comp.setEnabled(!b);
			if(!threeAlleles) {
				initFreqBLabel.setEnabled(false);
			}
		}
		
		@Override
		public void modeThreeAlleles(boolean b){
			super.modeThreeAlleles(b);
			initFreqB.setEnabled(b);
			initFreqBLabel.setEnabled(b);
			initFreqC.setEnabled(false);
			initFreqCLabel.setEnabled(false);
		}

		public void submit(shared.SessionParameters p) {
			double Afreq;
			double Bfreq;
			double Cfreq;

			p.setNumPops(Integer.parseInt(numPops.getText()));
			p.setNumGens(Integer.parseInt(numGens.getText()));			
			
			if(alleleFreqs.isSelected()) {

				p.setPopSize(Integer.parseInt(popSizeField.getText()));
				
				Afreq = Double.parseDouble(initFreqA.getText());
				if(threeAlleles) {
					Bfreq = Double.parseDouble(initFreqB.getText());
					Cfreq = 1 - (Afreq + Bfreq);
					p.setAlleleFrequency(Allele.C, Cfreq);
				}
				else { // two allele mode
					Bfreq = 1 - Afreq;
				}
				p.setAlleleFrequency(Allele.A, Afreq);
				p.setAlleleFrequency(Allele.B, Bfreq);

				updateGenotypeNums(p);			
			}
			
			
			else if(genotypeNums.isSelected()) {
				// Here we have to:
					// Given the hard numbers, calculate the frequencies
				double AA = 0;
				double AB = 0;
				double BB = 0;
				double AC = 0;
				double BC = 0;
				double CC = 0;
				int total = 0;

				AA = Double.parseDouble(genoAA.getText());
				AB = Double.parseDouble(genoAB.getText());
				BB = Double.parseDouble(genoBB.getText());				
				if(threeAlleles) {
					AC = Double.parseDouble(genoAC.getText());
					BC = Double.parseDouble(genoBC.getText());
					CC = Double.parseDouble(genoCC.getText());				
				}
				total = (int) (AA + AB + BB + AC + BC + CC);
				p.setPopSize(total);
				popSizeField.setText(Double.toString(total));

				// set init allele freqs
				// call updateGenotypeNums 
				int totalA = (int) (2 * AA + AB + AC);
				int totalB = (int) (2 * BB + AB + BC);
				int totalC = (int) (2 * CC + AC + BC);
			
				double totalAlleles = totalA + totalB + totalC;
				
				double fa = (double) totalA / totalAlleles;
				double fb = (double) totalB / totalAlleles;
				double fc = (double) totalC / totalAlleles;
				
				initFreqA.setText(String.format("%.3f", fa));
				initFreqB.setText(String.format("%.3f", fb));
				initFreqC.setText(String.format("%.3f", fc));
					
			}

			
			updateGenotypeNums(p);
			
		}
		
		// update the labels and boxes to reflect the A B and C allele freqs
		// update the sesh parms object too
		private void updateGenotypeNums(SessionParameters p) {
System.out.println("##########################");
			double freqA = Double.parseDouble(initFreqA.getText());
			double freqB = 0; 
			double freqC = 0;
			double AAfreq = 0;
			double ABfreq = 0;
			double BBfreq = 0;
			double ACfreq = 0;
			double BCfreq = 0;
			double CCfreq = 0;
			
			if(threeAlleles) {
				freqB = Double.parseDouble(initFreqB.getText());
				freqC = 1 - freqA - freqB;
						
				//calc 6 freqs
				AAfreq = freqA * freqA;
				ABfreq = 2 * freqA * freqB;
				BBfreq = freqB * freqB;
				ACfreq = 2 * freqA * freqC;
				BCfreq = 2 * freqB * freqC;
				CCfreq = freqC * freqC;
			
				p.setGenotypeFrequency(Genotype.AC, ACfreq);
				p.setGenotypeFrequency(Genotype.BC, BCfreq);
				p.setGenotypeFrequency(Genotype.CC, CCfreq);
			
			}

			else { // two allele mode
				freqB = 1 - freqA;
				AAfreq = freqA * freqA;
				ABfreq = 2 * freqA * freqB;
				BBfreq = freqB * freqB;

			}
			
			// Set the allele freq text fields
			initFreqB.setText(String.format("%.3f", freqB));
			initFreqC.setText(String.format("%.3f", freqC));
			
			calcFreqAALabel.setText("AA: " + String.format("%.3f",  AAfreq));
			calcFreqABLabel.setText("AB: " + String.format("%.3f",  ABfreq));
			calcFreqBBLabel.setText("BB: " + String.format("%.3f",  BBfreq));
			calcFreqACLabel.setText("AC: " + String.format("%.3f",  ACfreq));
			calcFreqBCLabel.setText("BC: " + String.format("%.3f",  BCfreq));
			calcFreqCCLabel.setText("CC: " + String.format("%.3f",  CCfreq));

			// Set the number text boxes
			if(!genotypeNums.isSelected()) {
				int totalPop = p.getPopSize();
				int numAA = (int) (AAfreq * totalPop);
				int numAB = (int) (ABfreq * totalPop);
				int numBB = (int) (BBfreq * totalPop);
				int numAC = (int) (ACfreq * totalPop);
				int numBC = (int) (BCfreq * totalPop);
				int numCC = (int) (CCfreq * totalPop);
			
				genoAA.setText(Integer.toString(numAA));
				genoAB.setText(Integer.toString(numAB));
				genoBB.setText(Integer.toString(numBB));
				genoAC.setText(Integer.toString(numAC));
				genoBC.setText(Integer.toString(numBC));
				genoCC.setText(Integer.toString(numCC));
			}
			
			p.setGenotypeFrequency(Genotype.AA, AAfreq);
			p.setGenotypeFrequency(Genotype.AB, ABfreq);
			p.setGenotypeFrequency(Genotype.BB, BBfreq);
			
		}

} // class
		

	
