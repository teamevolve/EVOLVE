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
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import shared.Genotype;
import shared.SessionParameters;

/**
 *
 * @author jasonfortunato
 * @author linneasahlberg
 * @author alexdennis
 *
 */
public class SelectionPane extends EvoMainPanel {
  JButton help;
	JLabel selectLabel;
	ButtonGroup selectGroup;
	JRadioButton selectRandS,
		selectAbs;
	JLabel AALabel, ABLabel, BBLabel,
		ACLabel, BCLabel, CCLabel;
	JLabel reproLabel, survLabel, absFitLabel, relFitLabel;
	EvoTextField survAA, survAB, survBB,
	survAC, survBC, survCC;
	EvoTextField reproAA, reproAB, reproBB,
		reproAC, reproBC, reproCC;
	EvoTextField absFitAA, absFitAB, absFitBB,
		absFitAC, absFitBC, absFitCC;
	JLabel relFitAA, relFitAB, relFitBB,
		relFitAC, relFitBC, relFitCC;

	double REPRO_DEFAULT = 5;

	ArrayList<Component> labelsList = new ArrayList<Component>();
	ArrayList<Component> survList = new ArrayList<Component>();
	ArrayList<Component> reproList = new ArrayList<Component>();
	ArrayList<Component> absFitList = new ArrayList<Component>();
	ArrayList<Component> relFitList = new ArrayList<Component>();

	public SelectionPane() {
		super();

    // set layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    setBackground(COLOR2);

		// Selection radio buttons
		selectLabel = new JLabel("<html><b><span style='font-size:11px'>Natural Selection: </span> </b>");
    help = new JButton("Help");
		selectGroup = new ButtonGroup();
		selectRandS = new JRadioButton("Reproduction and Survival", true);
		selectAbs = new JRadioButton("Absolute Fitness");
		selectGroup.add(selectRandS);
		selectGroup.add(selectAbs);

    EvoPanel titlePane = new EvoPanel();
    titlePane.setLayout(new WrapLayout(WrapLayout.LEADING));
    titlePane.setBackground(getBackground());
    titlePane.setAlignmentX(Component.LEFT_ALIGNMENT);
    titlePane.add(selectLabel);
    titlePane.add(Box.createHorizontalStrut(20));
		titlePane.add(selectRandS);
    titlePane.add(Box.createHorizontalStrut(20));
		titlePane.add(selectAbs);

    EvoPanel helpPane = new EvoPanel();
    helpPane.setLayout(new BoxLayout(helpPane, BoxLayout.LINE_AXIS));
    helpPane.setBackground(getBackground());
    helpPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    helpPane.add(titlePane);
    helpPane.add(Box.createHorizontalGlue());
    helpPane.add(help);

    add(helpPane);

		AALabel = new JLabel("AA");
		ABLabel = new JLabel("AB");
		BBLabel = new JLabel("BB");
		ACLabel = new JLabel("AC"); threeAllelesList.add(ACLabel);
		BCLabel = new JLabel("BC"); threeAllelesList.add(BCLabel);
		CCLabel = new JLabel("CC"); threeAllelesList.add(CCLabel);
		survLabel = new JLabel("<html><b>Survival Rates: </b> (0.0-1.0)");
		reproLabel = new JLabel("<html><b>Reproductive Rates: </b> (0.0-10.0)");
		absFitLabel = new JLabel("<html><b>Absolute Fitness: </b> (0.0-5.0)");
		survAA = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survAA, reproAA, absFitAA);
        updateRelFit();
      }
    };
		survAB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survAB, reproAB, absFitAB);
        updateRelFit();
      }
    };
		survBB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survBB, reproBB, absFitBB);
        updateRelFit();
      }
    };
		survAC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survAC, reproAC, absFitAC);
        updateRelFit();
      }
    };
		survBC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survBC, reproBC, absFitBC);
        updateRelFit();
      }
    };
		survCC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survCC, reproCC, absFitCC);
        updateRelFit();
      }
    };
    threeAllelesList.add(survAC);
    threeAllelesList.add(survBC);
    threeAllelesList.add(survCC);

		reproAA = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survAA, reproAA, absFitAA);
        updateRelFit();
      }
    };
		reproAB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survAB, reproAB, absFitAB);
        updateRelFit();
      }
    };
		reproBB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survBB, reproBB, absFitBB);
        updateRelFit();
      }
    };
		reproAC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survAC, reproAC, absFitAC);
        updateRelFit();
      }
    };
		reproBC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survBC, reproBC, absFitBC);
        updateRelFit();
      }
    };
		reproCC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateAbsFit(survCC, reproCC, absFitCC);
        updateRelFit();
      }
    };
    threeAllelesList.add(reproAC);
    threeAllelesList.add(reproBC);
    threeAllelesList.add(reproCC);

		absFitAA = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateSurv(survAA, reproAA, absFitAA);
        updateRelFit();
      }
    };
		absFitAB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateSurv(survAB, reproAB, absFitAB);
        updateRelFit();
      }
    };
		absFitBB = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateSurv(survBB, reproBB, absFitBB);
        updateRelFit();
      }
    };
		absFitAC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateSurv(survAC, reproAC, absFitAC);
        updateRelFit();
      }
    };
		absFitBC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateSurv(survBC, reproBC, absFitBC);
        updateRelFit();
      }
    };
		absFitCC = new EvoTextField(TEXT_LEN_SHORT) {
      public void updateOnFocusLost() {
        updateSurv(survCC, reproCC, absFitCC);
        updateRelFit();
      }
    };
    threeAllelesList.add(absFitAC);
    threeAllelesList.add(absFitBC);
    threeAllelesList.add(absFitCC);

		relFitLabel = new JLabel("<html><b>Relative Fitness: ");
		relFitAA = new JLabel("___");
		relFitAB = new JLabel("___");
		relFitBB = new JLabel("___");
		relFitAC = new JLabel("___"); threeAllelesList.add(relFitAC);
		relFitBC = new JLabel("___"); threeAllelesList.add(relFitBC);
		relFitCC = new JLabel("___"); threeAllelesList.add(relFitCC);

		EvoPanel table = new EvoPanel();
		table.setBackground(getBackground());
		table.setLayout(new GridBagLayout());
    table.setAlignmentX(Component.LEFT_ALIGNMENT);
		GridBagConstraints t = new GridBagConstraints();
		t.insets = new Insets(0, 0, 3, 15);

		addToLists();

		int i = 1;
		t.gridy = 0;
		for (Component comp : labelsList) {
			t.gridx = i; i++;
			table.add(comp, t);
		}

		i = 0;
		t.anchor = GridBagConstraints.LINE_START;
		t.gridy++;
		for (Component comp : survList) {
			t.gridx = i; i++;
			table.add(comp, t);
		}

		i = 0;
		t.gridy++;
		for (Component comp : reproList) {
			t.gridx = i; i++;
			table.add(comp, t);
		}

		i = 0;
		t.gridy++;
		for (Component comp : absFitList) {
			t.gridx = i; i++;
			table.add(comp, t);
		}

		i = 0;
		t.gridy++;
		for (Component comp : relFitList) {
			t.gridx = i; i++;
			table.add(comp, t);
		}

    t.gridx = 7; t.gridy = 0;
    t.weightx = 1.0;
    t.fill = GridBagConstraints.HORIZONTAL;
    table.add(Box.createHorizontalGlue(), t);

		add(table);

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

		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openHelp("/help/Help05_Natural_Selection.pdf");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
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
