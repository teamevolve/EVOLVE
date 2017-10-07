package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import shared.Genotype;
import shared.SessionParameters;

/**
 *
 * @author jasonfortunato
 * @author linneasahlberg
 *
 */
public class SelectionPane extends EvoPane {
	JLabel selectLabel;
	ButtonGroup selectGroup;
	JRadioButton selectRandS,
		selectAbs;
	JLabel AALabel, ABLabel, BBLabel,
		ACLabel, BCLabel, CCLabel;
	JLabel reproLabel, survLabel, absFitLabel, relFitLabel;
	JTextField survAA, survAB, survBB,
	survAC, survBC, survCC;
	JTextField reproAA, reproAB, reproBB,
		reproAC, reproBC, reproCC;
	JTextField absFitAA, absFitAB, absFitBB,
		absFitAC, absFitBC, absFitCC;
	JLabel relFitAA, relFitAB, relFitBB,
		relFitAC, relFitBC, relFitCC;
	JButton apply;

	double REPRO_DEFAULT = 5;

	ArrayList<Component> labelsList = new ArrayList<Component>();
	ArrayList<Component> survList = new ArrayList<Component>();
	ArrayList<Component> reproList = new ArrayList<Component>();
	ArrayList<Component> absFitList = new ArrayList<Component>();
	ArrayList<Component> relFitList = new ArrayList<Component>();

	JPanel table;

	public SelectionPane() {
		super();
		color1List.add(getParent());
		// Selection radio buttons
		selectLabel = new JLabel("<html><b><span style='font-size:11px'>Natural Selection: </span> </b>");
		selectGroup = new ButtonGroup();
		selectRandS = new JRadioButton("Reproduction and Survival", true);
		selectAbs = new JRadioButton("Absolute Fitness");
		selectGroup.add(selectRandS);
		selectGroup.add(selectAbs);

		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(selectLabel, c);

		c.gridx = 1; c.gridy = 1;
		c.gridwidth = 2;
		add(selectRandS, c);
		c.gridx = 2; c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(selectAbs, c);

		AALabel = new JLabel("AA");
		ABLabel = new JLabel("AB");
		BBLabel = new JLabel("BB");
		ACLabel = new JLabel("AC"); threeAllelesList.add(ACLabel);
		BCLabel = new JLabel("BC"); threeAllelesList.add(BCLabel);
		CCLabel = new JLabel("CC"); threeAllelesList.add(CCLabel);
		survLabel = new JLabel("<html><b>Survival Rates </b> (0.0-1.0):");
		reproLabel = new JLabel("<html><b>Reproductive Rates </b> (0.0-10.0):");
		absFitLabel = new JLabel("<html><b>Absolute Fitness </b> (0.0-5.0):");
		survAA = new JTextField(TEXT_LEN_SHORT);
		survAB = new JTextField(TEXT_LEN_SHORT);
		survBB = new JTextField(TEXT_LEN_SHORT);
		survAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(survAC);
		survBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(survBC);
		survCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(survCC);
		reproAA = new JTextField(TEXT_LEN_SHORT);
		reproAB = new JTextField(TEXT_LEN_SHORT);
		reproBB = new JTextField(TEXT_LEN_SHORT);
		reproAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(reproAC);
		reproBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(reproBC);
		reproCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(reproCC);
		absFitAA = new JTextField(TEXT_LEN_SHORT);
		absFitAB = new JTextField(TEXT_LEN_SHORT);
		absFitBB = new JTextField(TEXT_LEN_SHORT);
		absFitAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(absFitAC);
		absFitBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(absFitBC);
		absFitCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(absFitCC);
		relFitLabel = new JLabel("<html><b>Relative Fitness: ");
		relFitAA = new JLabel("___");
		relFitAB = new JLabel("___");
		relFitBB = new JLabel("___");
		relFitAC = new JLabel("___"); threeAllelesList.add(relFitAC);
		relFitBC = new JLabel("___"); threeAllelesList.add(relFitBC);
		relFitCC = new JLabel("___"); threeAllelesList.add(relFitCC);

		table = new JPanel();
		table.setBackground(color1);
		table.setLayout(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints();
		t.insets = new Insets(0, 0, 3, 15);

		addToLists();

		int i = 1;
		int y = 0;
		t.gridy = y;
		for (Component c : labelsList) {
			t.gridx = i; i++;
			table.add(c, t);
		}

		i = 0;
		t.anchor = GridBagConstraints.WEST;
		t.gridy++;
		for (Component c : survList) {
			t.gridx = i; i++;
			table.add(c, t);
		}

		i = 0;
		t.gridy++;
		for (Component c : reproList) {
			t.gridx = i; i++;
			table.add(c, t);
		}

		i = 0;
		t.gridy++;
		for (Component c : absFitList) {
			t.gridx = i; i++;
			table.add(c, t);
		}

		i = 0;
		t.gridy++;
		for (Component c : relFitList) {
			t.gridx = i; i++;
			table.add(c, t);
		}

		c.insets = new Insets(0, 20, 0, 0);
		c.gridx = 0; c.gridy = 2;
		c.gridwidth = 7;
		c.anchor = GridBagConstraints.WEST;
		add(table, c);


		// default is rAndS
		modeRandS(true);

		// Set listeners for real-time disabling of fields
		selectRandS.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeRandS(true);
			}
		});

		selectAbs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeRandS(false);
			}
		});


    survAA.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        survAA.selectAll();
      }

      public void focusLost(FocusEvent e) {
        survAA.select(0, 0);
        updateAbsFit(survAA, reproAA, absFitAA);
        updateRelFit();
      }
    });

		survAB.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        survAB.selectAll();
      }

      public void focusLost(FocusEvent e) {
        survAB.select(0, 0);
        updateAbsFit(survAB, reproAB, absFitAB);
        updateRelFit();
      }
    });

		survBB.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        survBB.selectAll();
      }

      public void focusLost(FocusEvent e) {
        survBB.select(0, 0);
        updateAbsFit(survBB, reproBB, absFitBB);
        updateRelFit();
      }
    });

		survAC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        survAC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        survAC.select(0, 0);
        updateAbsFit(survAC, reproAC, absFitAC);
        updateRelFit();
      }
    });

		survBC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        survBC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        survBC.select(0, 0);
        updateAbsFit(survBC, reproBC, absFitBC);
        updateRelFit();
      }
    });

		survCC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        survCC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        survCC.select(0, 0);
        updateAbsFit(survCC, reproCC, absFitCC);
        updateRelFit();
      }
    });

		reproAA.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        reproAA.selectAll();
      }

      public void focusLost(FocusEvent e) {
        reproAA.select(0, 0);
        updateAbsFit(survAA, reproAA, absFitAA);
        updateRelFit();
      }
    });

		reproAB.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        reproAB.selectAll();
      }

      public void focusLost(FocusEvent e) {
        reproAB.select(0, 0);
        updateAbsFit(survAB, reproAB, absFitAB);
        updateRelFit();
      }
    });

		reproBB.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        reproBB.selectAll();
      }

      public void focusLost(FocusEvent e) {
        reproAB.select(0, 0);
        updateAbsFit(survBB, reproBB, absFitBB);
        updateRelFit();
      }
    });

		reproAC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        reproAC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        reproAC.select(0, 0);
        updateAbsFit(survAC, reproAC, absFitAC);
        updateRelFit();
      }
    });

		reproBC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        reproBC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        reproBC.select(0, 0);
        updateAbsFit(survBC, reproBC, absFitBC);
        updateRelFit();
      }
    });

		reproCC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        reproCC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        reproCC.select(0, 0);
        updateAbsFit(survCC, reproCC, absFitCC);
        updateRelFit();
      }
    });

		absFitAA.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        absFitAA.selectAll();
      }

      public void focusLost(FocusEvent e) {
        absFitAA.select(0, 0);
        updateSurv(survAA, reproAA, absFitAA);
        updateRelFit();
      }
    });

		absFitAB.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        absFitAB.selectAll();
      }

      public void focusLost(FocusEvent e) {
        absFitAB.select(0, 0);
        updateSurv(survAB, reproAB, absFitAB);
        updateRelFit();
      }
    });

		absFitBB.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        absFitBB.selectAll();
      }

      public void focusLost(FocusEvent e) {
        absFitBB.select(0, 0);
        updateSurv(survBB, reproBB, absFitBB);
        updateRelFit();
      }
    });

		absFitAC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        absFitAC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        absFitAC.select(0, 0);
        updateSurv(survAC, reproAC, absFitAC);
        updateRelFit();
      }
    });

		absFitBC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        absFitBC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        absFitBC.select(0, 0);
        updateSurv(survBC, reproBC, absFitBC);
        updateRelFit();
      }
    });

		absFitCC.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        absFitCC.selectAll();
      }

      public void focusLost(FocusEvent e) {
        absFitCC.select(0, 0);
        updateSurv(survCC, reproCC, absFitCC);
        updateRelFit();
      }
    });
	}


	private void addToLists() {
		labelsList.add(AALabel);
		labelsList.add(ABLabel);
		labelsList.add(BBLabel);
		labelsList.add(ACLabel);
		labelsList.add(BCLabel);
		labelsList.add(CCLabel);

		survList.add(survLabel);
		survList.add(survAA);
		survList.add(survAB);
		survList.add(survBB);
		survList.add(survAC);
		survList.add(survBC);
		survList.add(survCC);

		reproList.add(reproLabel);
		reproList.add(reproAA);
		reproList.add(reproAB);
		reproList.add(reproBB);
		reproList.add(reproAC);
		reproList.add(reproBC);
		reproList.add(reproCC);

		absFitList.add(absFitLabel);
		absFitList.add(absFitAA);
		absFitList.add(absFitAB);
		absFitList.add(absFitBB);
		absFitList.add(absFitAC);
		absFitList.add(absFitBC);
		absFitList.add(absFitCC);

		relFitList.add(relFitLabel);
		relFitList.add(relFitAA);
		relFitList.add(relFitAB);
		relFitList.add(relFitBB);
		relFitList.add(relFitAC);
		relFitList.add(relFitBC);
		relFitList.add(relFitCC);
	}

  public void modeThreeAlleles(boolean b) {
    super.modeThreeAlleles(b);
    updateAll();
	}

	private void modeRandS(boolean b) {
		for(Component comp : survList)
			comp.setEnabled(b);

		for(Component comp : reproList)
			comp.setEnabled(b);

		for(Component comp : absFitList)
			comp.setEnabled(!b);
	}

	@Override
	public void setEnabled(boolean enable){
		super.setEnabled(enable);
		if (!enable) {
			fillWithOnes();
		}
		for(Component comp : table.getComponents()){
			comp.setEnabled(enable);
		}
		if (selectRandS.isSelected() && enable) {
			modeRandS(true);
		}
		else if (selectAbs.isSelected() && enable){
			modeRandS(false);
		}
	}

	/**
	 * @author jason
	 * PRE: this pane is disabled in the GUI
	 */
	public void fillWithOnes() {
		survAA.setText("1");
		survAB.setText("1");
		survBB.setText("1");
		survAC.setText("1");
		survBC.setText("1");
		survCC.setText("1");

		reproAA.setText("1.02");
		reproAB.setText("1.02");
		reproBB.setText("1.02");
		reproAC.setText("1.02");
		reproBC.setText("1.02");
		reproCC.setText("1.02");

		JLabel[] relFits = {relFitAA, relFitAB, relFitBB,
				relFitAC, relFitBC, relFitCC};

		selectRandS.setSelected(true);
		updateAll();
		for(JLabel rf : relFits) {
			rf.setText("1.000");
		}
	}

	/**
	 * Dumps absolute and relative fitnesses into session parameters.
	 * If Reproductioin and Survival radiobutton is checked, it also dumps Repro and Surv rates
	 * @param p = sesh parms
	 */
	public void submit(shared.SessionParameters p){

		double afAA = 0; double afAB = 0; double afBB = 0;
		double afAC = 0; double afBC = 0; double afCC = 0;

		double rfAA, rfAB, rfBB, rfAC, rfBC, rfCC;

		// if repro and surv is selected
		if(selectRandS.isSelected()) {
			submitRandS(p);
			calcAbsFit();

			afAA = Double.parseDouble(absFitAA.getText());
			afAB = Double.parseDouble(absFitAB.getText());
			afBB = Double.parseDouble(absFitBB.getText());
			if(threeAlleles) {
				afAC = Double.parseDouble(absFitAC.getText());
				afBC = Double.parseDouble(absFitBC.getText());
				afCC = Double.parseDouble(absFitCC.getText());
			}
		}
		else if(selectAbs.isSelected()) {
			afAA = Double.parseDouble(absFitAA.getText());
			afAB = Double.parseDouble(absFitAB.getText());
			afBB = Double.parseDouble(absFitBB.getText());

			reproAA.setText(Double.toString(REPRO_DEFAULT));
			reproAB.setText(Double.toString(REPRO_DEFAULT));
			reproBB.setText(Double.toString(REPRO_DEFAULT));

			if(threeAlleles) {
				reproAC.setText(Double.toString(REPRO_DEFAULT));
				reproBC.setText(Double.toString(REPRO_DEFAULT));
				reproCC.setText(Double.toString(REPRO_DEFAULT));

				afAC = Double.parseDouble(absFitAC.getText());
				afBC = Double.parseDouble(absFitBC.getText());
				afCC = Double.parseDouble(absFitCC.getText());
			}

			calcSurvRates(afAA, afAB, afBB, afAC, afBC, afCC);
			submitRandS(p);
		}

		p.setAbsoluteFitness(Genotype.AA, afAA);
		p.setAbsoluteFitness(Genotype.AB, afAB);
		p.setAbsoluteFitness(Genotype.BB, afBB);

    if(threeAlleles) {
      p.setAbsoluteFitness(Genotype.AC, afAC);
      p.setAbsoluteFitness(Genotype.BC, afBC);
      p.setAbsoluteFitness(Genotype.CC, afCC);
    }

		// get the highest absolute fitness
		double afMax = 0;

		double[] afArray = {afAA, afAB, afBB, afAC, afBC, afCC};

		for(double af : afArray) {
			afMax = Math.max(afMax, af);
		}


		rfAA = afAA / afMax;
		rfAB = afAB / afMax;
		rfBB = afBB / afMax;

		p.setRelativeFitness(Genotype.AA, rfAA);
		p.setRelativeFitness(Genotype.AB, rfAB);
		p.setRelativeFitness(Genotype.BB, rfBB);

		relFitAA.setText(String.format("%.4f", rfAA));
		relFitAB.setText(String.format("%.4f", rfAB));
		relFitBB.setText(String.format("%.4f", rfBB));
		if(threeAlleles) {
			rfAC = afAC / afMax;
			rfBC = afBC / afMax;
			rfCC = afCC / afMax;

			p.setRelativeFitness(Genotype.AC, rfAC);
			p.setRelativeFitness(Genotype.BC, rfBC);
			p.setRelativeFitness(Genotype.CC, rfCC);

			relFitAC.setText(String.format("%.4f", rfAC));
			relFitBC.setText(String.format("%.4f", rfBC));
			relFitCC.setText(String.format("%.4f", rfCC));
		}
	}

	private void submitRandS(SessionParameters p) {
		double AArr = Double.parseDouble(reproAA.getText());
		double ABrr = Double.parseDouble(reproAB.getText());
		double BBrr = Double.parseDouble(reproBB.getText());
		double ACrr = 0;
		double BCrr = 0;
		double CCrr = 0;

		double AAsr = Double.parseDouble(survAA.getText());
		double ABsr = Double.parseDouble(survAB.getText());
		double BBsr = Double.parseDouble(survBB.getText());
		double ACsr = 0;
		double BCsr = 0;
		double CCsr = 0;

		if(threeAlleles) {
			ACrr = Double.parseDouble(reproAC.getText());
			BCrr = Double.parseDouble(reproBC.getText());
			CCrr = Double.parseDouble(reproCC.getText());

			ACsr = Double.parseDouble(survAC.getText());
			BCsr = Double.parseDouble(survBC.getText());
			CCsr = Double.parseDouble(survCC.getText());
		}

		p.setReproductionRate(Genotype.AA, AArr);
		p.setReproductionRate(Genotype.AB, ABrr);
		p.setReproductionRate(Genotype.BB, BBrr);
		p.setReproductionRate(Genotype.AC, ACrr);
		p.setReproductionRate(Genotype.BC, BCrr);
		p.setReproductionRate(Genotype.CC, CCrr);

		p.setSurvivalRate(Genotype.AA, AAsr);
		p.setSurvivalRate(Genotype.AB, ABsr);
		p.setSurvivalRate(Genotype.BB, BBsr);
		p.setSurvivalRate(Genotype.AC, ACsr);
		p.setSurvivalRate(Genotype.BC, BCsr);
		p.setSurvivalRate(Genotype.CC, CCsr);

	}

	private void calcAbsFit() {
		double AArr = Double.parseDouble(reproAA.getText());
		double ABrr = Double.parseDouble(reproAB.getText());
		double BBrr = Double.parseDouble(reproBB.getText());
		double ACrr = 0;
		double BCrr = 0;
		double CCrr = 0;

		double AAsr = Double.parseDouble(survAA.getText());
		double ABsr = Double.parseDouble(survAB.getText());
		double BBsr = Double.parseDouble(survBB.getText());
		double ACsr = 0;
		double BCsr = 0;
		double CCsr = 0;

		if(threeAlleles) {
			ACrr = Double.parseDouble(reproAC.getText());
			BCrr = Double.parseDouble(reproBC.getText());
			CCrr = Double.parseDouble(reproCC.getText());

			ACsr = Double.parseDouble(survAC.getText());
			BCsr = Double.parseDouble(survBC.getText());
			CCsr = Double.parseDouble(survCC.getText());
		}
		// Calculate absolute fitness of each genotype
		double afAA = AArr * AAsr;
		double afAB = ABrr * ABsr;
		double afBB = BBrr * BBsr;
		double afAC = ACrr * ACsr;
		double afBC = BCrr * BCsr;
		double afCC = CCrr * CCsr;

		absFitAA.setText(String.format("%.3f", afAA));
		absFitAB.setText(String.format("%.3f", afAB));
		absFitBB.setText(String.format("%.3f", afBB));
		if(threeAlleles) {
			absFitAC.setText(String.format("%.3f", afAC));
			absFitBC.setText(String.format("%.3f", afBC));
			absFitCC.setText(String.format("%.3f", afCC));
		}
	}

	private void calcSurvRates(double aa, double ab, double bb, double ac, double bc, double cc) {

		survAA.setText(String.format("%.4f", aa/REPRO_DEFAULT));
		survAB.setText(String.format("%.4f", ab/REPRO_DEFAULT));
		survBB.setText(String.format("%.4f", bb/REPRO_DEFAULT));

		if(threeAlleles){
			survAC.setText(String.format("%.4f", ac/REPRO_DEFAULT));
			survBC.setText(String.format("%.4f", bc/REPRO_DEFAULT));
			survCC.setText(String.format("%.4f", cc/REPRO_DEFAULT));
		}
	}

  private void updateAll() {
    if(selectRandS.isSelected()) {
      updateAbsFit(survAA, reproAA, absFitAA);
      updateAbsFit(survAB, reproAB, absFitAB);
      updateAbsFit(survBB, reproBB, absFitBB);
      if(threeAlleles) {
        updateAbsFit(survAC, reproAC, absFitAC);
        updateAbsFit(survBC, reproBC, absFitBC);
        updateAbsFit(survCC, reproCC, absFitCC);
      }
    }
    else {
      updateSurv(survAA, reproAA, absFitAA);
      updateSurv(survAB, reproAB, absFitAB);
      updateSurv(survBB, reproBB, absFitBB);
      if(threeAlleles) {
        updateSurv(survAC, reproAC, absFitAC);
        updateSurv(survBC, reproBC, absFitBC);
        updateSurv(survCC, reproCC, absFitCC);
      }
    }
    updateRelFit();
  }

  private void updateAbsFit(JTextField surv, JTextField repro, JTextField absFit) {
    if(surv.getText().equals("") || repro.getText().equals("")) {
      absFit.setText("");
      return;
    }

    double sr = Double.parseDouble(surv.getText());
    double rr = Double.parseDouble(repro.getText());
    double af = sr * rr;
    absFit.setText(String.format("%.3f", af));
  }

  private void updateSurv(JTextField surv, JTextField repro, JTextField absFit) {
    if(absFit.getText().equals("")) {
      surv.setText("");
      repro.setText("");
      return;
    }

    repro.setText(Double.toString(REPRO_DEFAULT));
    double af = Double.parseDouble(absFit.getText());
    surv.setText(String.format("%.4f", af/REPRO_DEFAULT));
  }

  private void updateRelFit() {
    if(absFitAA.getText().equals("") || absFitAB.getText().equals("") ||
       absFitBB.getText().equals("") || (threeAlleles && (
       absFitAC.getText().equals("") || absFitBC.getText().equals("") ||
       absFitCC.getText().equals("")))) {
      relFitAA.setText("___");
      relFitAB.setText("___");
      relFitBB.setText("___");
      relFitAC.setText("___");
      relFitBC.setText("___");
      relFitCC.setText("___");
      return;
    }

    double afAA = 0; double afAB = 0; double afBB = 0;
		double afAC = 0; double afBC = 0; double afCC = 0;

		double rfAA, rfAB, rfBB, rfAC, rfBC, rfCC;

		afAA = Double.parseDouble(absFitAA.getText());
		afAB = Double.parseDouble(absFitAB.getText());
		afBB = Double.parseDouble(absFitBB.getText());
		if(threeAlleles) {
			afAC = Double.parseDouble(absFitAC.getText());
			afBC = Double.parseDouble(absFitBC.getText());
			afCC = Double.parseDouble(absFitCC.getText());
		}

		// get the highest absolute fitness
		double afMax = 0;

		double[] afArray = {afAA, afAB, afBB, afAC, afBC, afCC};

		for(double af : afArray) {
			afMax = Math.max(afMax, af);
		}

		rfAA = afAA / afMax;
		rfAB = afAB / afMax;
		rfBB = afBB / afMax;

		relFitAA.setText(String.format("%.4f", rfAA));
		relFitAB.setText(String.format("%.4f", rfAB));
		relFitBB.setText(String.format("%.4f", rfBB));
		if(threeAlleles) {
			rfAC = afAC / afMax;
			rfBC = afBC / afMax;
			rfCC = afCC / afMax;

			relFitAC.setText(String.format("%.4f", rfAC));
			relFitBC.setText(String.format("%.4f", rfBC));
			relFitCC.setText(String.format("%.4f", rfCC));
		}
  }
}
