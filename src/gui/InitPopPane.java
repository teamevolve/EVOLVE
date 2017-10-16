package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Box;

import shared.Allele;
import shared.Genotype;
import shared.SessionParameters;

import java.util.ArrayList;

/**
 *
 * @author jasonfortunato
 * @author linneasahlberg
 * @author alexdennis
 *
 * A pane in the GUI that allows the user to put in information about the initial population: number of generations,
 * number of populations, population size, and enter allele frequencies OR genotype numbers.
 *
 */
public class InitPopPane extends EvoPane {
	JLabel popLabel;			// Population size
	JLabel popSizeLabel;
	EvoTextField popSizeField;
	JLabel numPopsLabel;        // number of populations
	EvoTextField numPops;
	JLabel numGensLabel;		// number of generations
	EvoTextField numGens;
	ButtonGroup initPopVals;    // radio buttons
	JRadioButton alleleFreqs;
	JRadioButton genotypeNums;
	JLabel initPopLabel;  		// Initial population
	EvoTextField initPop;
	JLabel initFreqALabel, initFreqBLabel,		// Initial frequencies
		initFreqCLabel;
	EvoTextField initFreqA, initFreqB, initFreqC;
	JLabel alleleFreqsLabel, genoNumsLabel;		// labels for subsections
	// JLabel calcFreqAALabel, calcFreqABLabel, 	// calculated frequencies
	// 	calcFreqBBLabel, calcFreqACLabel,
	// 	calcFreqBCLabel, calcFreqCCLabel;
	JLabel freqTotal;
	JLabel genoAALabel, genoABLabel, 	// genotypes
		genoBBLabel, genoACLabel,
		genoBCLabel, genoCCLabel;
	JLabel genoTotal;
	EvoTextField genoAA, genoAB,
		genoBB, genoAC, genoBC,
		genoCC;
	JPanel afPane, gnPane;		// allele freqency and genotype panes
	JButton apply;				// apply button to see calculated values

	ArrayList<Component> alFreqList = new ArrayList<Component>();
	ArrayList<Component> gtNumList = new ArrayList<Component>();

	public InitPopPane() {
		super();

    // set layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

    setBackground(COLOR2);

		c.anchor = GridBagConstraints.LINE_START;

		popLabel = new JLabel("<html><b><span style='font-size:11px'>Initial Population:</span></b>");
		popSizeLabel = new JLabel("<html><b>Population Size:");
		popSizeField = new EvoTextField(TEXT_LEN_LONG) {
      public void updateOnFocusLost() {
        updateGenoNums();
      }
    };
		popSizeField.setName(INT); popSizeField.setInputVerifier(iv);

		// init pop size
		c.gridx = 0; c.gridy = 0;
    c.gridwidth = 3;
		add(popLabel, c);
    c.gridwidth = 1;

		// num gens and pops
		numGensLabel = new JLabel("<html><b>Number of Generations:");
		numGens = new EvoTextField(TEXT_LEN_LONG);
		numPopsLabel = new JLabel("<html><b>Number of Populations:");
		numPops = new EvoTextField(TEXT_LEN_LONG);

		// c.insets = new Insets(0, 20, 0, 0);
		c.gridx = 0; c.gridy++;
		add(numGensLabel, c);
		c.gridx = 1;
		add(numGens, c);
		c.gridx = 0; c.gridy++;
		add(numPopsLabel, c);
		c.gridx = 1;
		add(numPops, c);
		c.gridx = 0; c.gridy++;
		add(popSizeLabel, c);
		c.gridx = 1;
		add(popSizeField, c);

		// population constant radio button stuff
		initPopVals = new ButtonGroup();
		alleleFreqs = new JRadioButton("<html><b>Enter Allele Frequncies", true);
		genotypeNums = new JRadioButton("<html><b>Enter Genotype Numbers");
		initPopVals.add(alleleFreqs);
		initPopVals.add(genotypeNums);

		alleleFreqsLabel = new JLabel("<html><b>Allele frequencies</b> (0-1):");
		initFreqALabel = new JLabel("A:");
		initFreqBLabel = new JLabel("B:");
		initFreqCLabel = new JLabel("C:"); threeAllelesList.add(initFreqCLabel);
		initFreqA = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateFreq();
        updateGenoNums();
      }
    };
		initFreqB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateFreq();
        updateGenoNums();
      }
    };
		initFreqC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(initFreqC);

		initFreqA.setName(RATE);
		initFreqB.setName(RATE);
		initFreqC.setName(RATE);

		// calcFreqAALabel = new JLabel("AA: ___");
		// calcFreqABLabel = new JLabel("AB: ___");
		// calcFreqBBLabel = new JLabel("BB: ___");
		// calcFreqACLabel = new JLabel("AC: ___"); threeAllelesList.add(calcFreqACLabel);
		// calcFreqBCLabel = new JLabel("BC: ___"); threeAllelesList.add(calcFreqBCLabel);
		// calcFreqCCLabel = new JLabel("CC: ___"); threeAllelesList.add(calcFreqCCLabel);

		afPane = new JPanel();
		afPane.setBackground(getBackground());
		afPane.setLayout(new FlowLayout(FlowLayout.LEADING));

		afPane.add(alleleFreqs);
    afPane.add(Box.createHorizontalStrut(20));
    afPane.add(initFreqALabel);
		afPane.add(initFreqA);
    afPane.add(Box.createHorizontalStrut(20));
    afPane.add(initFreqBLabel);
		afPane.add(initFreqB);
    afPane.add(Box.createHorizontalStrut(20));
    afPane.add(initFreqCLabel);
		afPane.add(initFreqC);

		c.gridx = 0; c.gridy++;
		c.gridwidth = 3;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
		add(afPane, c);

		/*Genotype Number Pane********************************/
		genoNumsLabel = new JLabel("<html><b>Genotype Numbers:");
		genoAALabel = new JLabel("AA");
		genoABLabel = new JLabel("AB");
		genoBBLabel = new JLabel("BB");
		genoACLabel = new JLabel("AC"); threeAllelesList.add(genoACLabel);
		genoBCLabel = new JLabel("BC"); threeAllelesList.add(genoBCLabel);
		genoCCLabel = new JLabel("CC"); threeAllelesList.add(genoCCLabel);

		genoAA = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updatePopSizeAndFreq();
      }
    };
		genoAB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updatePopSizeAndFreq();
      }
    };
		genoBB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updatePopSizeAndFreq();
      }
    };
		genoAC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updatePopSizeAndFreq();
      }
    };
		genoBC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updatePopSizeAndFreq();
      }
    };
		genoCC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updatePopSizeAndFreq();
      }
    };
    threeAllelesList.add(genoAC);
    threeAllelesList.add(genoBC);
    threeAllelesList.add(genoCC);

		gnPane = new JPanel();
    gnPane.setBackground(getBackground());
		gnPane.setLayout(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints();
		t.insets = new Insets(0, 0, 0, 15);

		t.gridx = 0; t.gridy = 0;
		gnPane.add(genotypeNums, t);

		t.gridx = 1; t.gridy = 0;
		gnPane.add(genoAALabel, t);
		t.gridy = 1;
		gnPane.add(genoAA, t);

		t.gridx = 2; t.gridy = 0;
		gnPane.add(genoABLabel, t);
		t.gridx = 2; t.gridy = 1;
		gnPane.add(genoAB, t);

		t.gridx = 3; t.gridy = 0;
		gnPane.add(genoBBLabel, t);
		t.gridx = 3; t.gridy = 1;
		gnPane.add(genoBB, t);

		t.gridx = 4; t.gridy = 0;
		gnPane.add(genoACLabel, t);
		t.gridx = 4; t.gridy = 1;
		gnPane.add(genoAC, t);

		t.gridx = 5; t.gridy = 0;
		gnPane.add(genoBCLabel, t);
		t.gridx = 5; t.gridy = 1;
		gnPane.add(genoBC, t);

		t.gridx = 6; t.gridy = 0;
		gnPane.add(genoCCLabel, t);
		t.gridx = 6; t.gridy = 1;
		gnPane.add(genoCC, t);

    t.gridx = 7; t.gridy = 0;
    t.weightx = 1.0;
    t.fill = GridBagConstraints.HORIZONTAL;
    gnPane.add(Box.createHorizontalGlue(), t);

		c.gridx = 0; c.gridy++;
    c.gridwidth = 3;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
		add(gnPane, c);

		addToLists();
		modeAlleleFreqs(true);

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

	/**
	 * 	adds components to the alFreqList and gtNumList
	 */
	private void addToLists() {
		alFreqList.add(alleleFreqsLabel);
		alFreqList.add(initFreqALabel);
		alFreqList.add(initFreqBLabel);
		alFreqList.add(initFreqA);
		alFreqList.add(initFreqB);

		gtNumList.add(genoNumsLabel);
		gtNumList.add(genoAALabel);
		gtNumList.add(genoABLabel);
		gtNumList.add(genoBBLabel);
		gtNumList.add(genoACLabel);
		gtNumList.add(genoBCLabel);
		gtNumList.add(genoCCLabel);

		gtNumList.add(genoAA);
		gtNumList.add(genoAB);
		gtNumList.add(genoBB);
		gtNumList.add(genoAC);
		gtNumList.add(genoBC);
		gtNumList.add(genoCC);
	}

	private void modeAlleleFreqs(boolean b) {
		for(Component comp : alFreqList)
			comp.setEnabled(b);

		for(Component comp : gtNumList)
			comp.setEnabled(!b);

		if(!threeAlleles) {
      initFreqB.setEnabled(false);
			initFreqBLabel.setEnabled(false);
		}
	}

	@Override
	public void modeThreeAlleles(boolean b){
		super.modeThreeAlleles(b);
		initFreqB.setEnabled(b && alleleFreqs.isSelected());
		initFreqBLabel.setEnabled(b && alleleFreqs.isSelected());
		initFreqC.setEnabled(false);
		initFreqCLabel.setEnabled(false);
    updateAll();
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

		// calcFreqAALabel.setText("AA: " + String.format("%.3f",  AAfreq));
		// calcFreqABLabel.setText("AB: " + String.format("%.3f",  ABfreq));
		// calcFreqBBLabel.setText("BB: " + String.format("%.3f",  BBfreq));
		// calcFreqACLabel.setText("AC: " + String.format("%.3f",  ACfreq));
		// calcFreqBCLabel.setText("BC: " + String.format("%.3f",  BCfreq));
		// calcFreqCCLabel.setText("CC: " + String.format("%.3f",  CCfreq));

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

  public void updateAll() {
    if(alleleFreqs.isSelected()) {
      updateFreq();
      updateGenoNums();
    }
    else {
      updatePopSizeAndFreq();
    }
  }

  public void updateFreq() {
    if(!threeAlleles && initFreqA.getText().equals("")) {
      initFreqB.setText("");
      return;
    }
    if(threeAlleles && (initFreqA.getText().equals("") ||
       initFreqB.getText().equals(""))) {
      initFreqC.setText("");
      return;
    }

		double Afreq = Double.parseDouble(initFreqA.getText());
    double Bfreq;
		double Cfreq;

    if(!threeAlleles) { // two alleles
			Bfreq = 1 - Afreq;
      initFreqB.setText(String.format("%.3f", Bfreq));
		}
		else { // three alleles
			Bfreq = Double.parseDouble(initFreqB.getText());
			Cfreq = 1 - (Afreq + Bfreq);
      initFreqC.setText(String.format("%.3f", Cfreq));
		}
  }

  public void updateGenoNums() {
    if(popSizeField.getText().equals("") || initFreqA.getText().equals("") ||
       (threeAlleles && initFreqB.getText().equals(""))) {
      genoAA.setText("");
      genoAB.setText("");
      genoBB.setText("");
      genoAC.setText("");
      genoBC.setText("");
      genoCC.setText("");
      return;
    }

    int totalPop = Integer.parseInt(popSizeField.getText());
    double freqA = Double.parseDouble(initFreqA.getText());
		double freqB = Double.parseDouble(initFreqB.getText());
		double freqC = 0;
		double AAfreq = 0;
		double ABfreq = 0;
		double BBfreq = 0;
		double ACfreq = 0;
		double BCfreq = 0;
		double CCfreq = 0;

    if(!threeAlleles) { // two alleles
      AAfreq = freqA * freqA;
			ABfreq = 2 * freqA * freqB;
			BBfreq = freqB * freqB;
    }
    else { // three alleles
      freqC = Double.parseDouble(initFreqC.getText());

			AAfreq = freqA * freqA;
			ABfreq = 2 * freqA * freqB;
			BBfreq = freqB * freqB;
			ACfreq = 2 * freqA * freqC;
			BCfreq = 2 * freqB * freqC;
			CCfreq = freqC * freqC;
    }

    genoAA.setText(Integer.toString((int) (AAfreq * totalPop)));
    genoAB.setText(Integer.toString((int) (ABfreq * totalPop)));
    genoBB.setText(Integer.toString((int) (BBfreq * totalPop)));
    if(threeAlleles) {
      genoAC.setText(Integer.toString((int) (ACfreq * totalPop)));
      genoBC.setText(Integer.toString((int) (BCfreq * totalPop)));
      genoCC.setText(Integer.toString((int) (CCfreq * totalPop)));
    }
  }

  public void updatePopSizeAndFreq() {
    if(genoAA.getText().equals("") || genoAB.getText().equals("") ||
       genoBB.getText().equals("") || (threeAlleles && (
       genoAC.getText().equals("") || genoBC.getText().equals("") ||
       genoCC.getText().equals("")))) {
      popSizeField.setText("");
      initFreqA.setText("");
      initFreqB.setText("");
      initFreqC.setText("");
      return;
    }

    double AA = Double.parseDouble(genoAA.getText());;
    double AB = Double.parseDouble(genoAB.getText());;
    double BB = Double.parseDouble(genoBB.getText());;
    double AC = 0;
    double BC = 0;
    double CC = 0;
    if(threeAlleles) {
      AC = Double.parseDouble(genoAC.getText());
      BC = Double.parseDouble(genoBC.getText());
      CC = Double.parseDouble(genoCC.getText());
    }

    popSizeField.setText(Integer.toString((int) (AA + AB + BB + AC + BC + CC)));

    int totalA = (int) (2 * AA + AB + AC);
    int totalB = (int) (2 * BB + AB + BC);
    int totalC = (int) (2 * CC + AC + BC);

    double totalAlleles = totalA + totalB + totalC;

    initFreqA.setText(String.format("%.3f", (double) totalA / totalAlleles));
    initFreqB.setText(String.format("%.3f", (double) totalB / totalAlleles));
    initFreqC.setText(String.format("%.3f", (double) totalC / totalAlleles));
  }
} // class
