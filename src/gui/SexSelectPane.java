package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.util.ArrayList;

import shared.Genotype;

/**
 *
 * @author linneasahlberg
 * @author jasonfortunato
 * @author alexdennis
 *
 */

public class SexSelectPane extends EvoPane {
	final static int TEXT_LEN_SHORT = 3;

  JButton help;
	JLabel mateFreqLabel, AAPrefLabel,ABPrefLabel, BBPrefLabel,
		ACPrefLabel, BCPrefLabel, CCPrefLabel;

	JLabel AALabel, ABLabel, BBLabel, ACLabel, BCLabel, CCLabel;

	EvoTextField freqAAxAA, freqAAxAB, freqAAxBB, freqAAxAC, freqAAxBC, freqAAxCC;
	EvoTextField freqABxAA, freqABxAB, freqABxBB, freqABxAC, freqABxBC, freqABxCC;
	EvoTextField freqBBxAA, freqBBxAB, freqBBxBB, freqBBxAC, freqBBxBC, freqBBxCC;
	EvoTextField freqACxAA, freqACxAB, freqACxBB, freqACxAC, freqACxBC, freqACxCC;
	EvoTextField freqBCxAA, freqBCxAB, freqBCxBB, freqBCxAC, freqBCxBC, freqBCxCC;
	EvoTextField freqCCxAA, freqCCxAB, freqCCxBB, freqCCxAC, freqCCxBC, freqCCxCC;

	ArrayList<Component> prefLabels = new ArrayList<Component>();
	ArrayList<Component> labels = new ArrayList<Component>();
	ArrayList<Component> fields = new ArrayList<Component>();

	JPanel table;

	public SexSelectPane() {
		super();

    // set layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    setBackground(COLOR1);

    mateFreqLabel = new JLabel("<html><span style='font-size:11px'><b>Sexual Selection </b>(Non-Random Mating): </span>(0.0-1.0)");
    help = new JButton("Help");

    JPanel titlePane = new JPanel();
    titlePane.setBackground(getBackground());
    titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.LINE_AXIS));
    titlePane.setAlignmentX(Component.LEFT_ALIGNMENT);
    titlePane.add(mateFreqLabel);
    titlePane.add(Box.createHorizontalGlue());
    titlePane.add(help);

		AAPrefLabel = new JLabel("AA Preference for:");
		ABPrefLabel = new JLabel("AB Preference for:");
		BBPrefLabel = new JLabel("BB Preference for:");
		ACPrefLabel = new JLabel("AC Preference for:"); threeAllelesList.add(ACPrefLabel);
		BCPrefLabel = new JLabel("BC Preference for:"); threeAllelesList.add(BCPrefLabel);
		CCPrefLabel = new JLabel("CC Preference for:"); threeAllelesList.add(CCPrefLabel);

		AALabel = new JLabel("AA");
		ABLabel = new JLabel("AB");
		BBLabel = new JLabel("BB");
		ACLabel = new JLabel("AC"); threeAllelesList.add(ACLabel);
		BCLabel = new JLabel("BC"); threeAllelesList.add(BCLabel);
		CCLabel = new JLabel("CC"); threeAllelesList.add(CCLabel);

		freqAAxAA = new EvoTextField(TEXT_LEN_SHORT);
		freqAAxAB = new EvoTextField(TEXT_LEN_SHORT);
		freqAAxBB = new EvoTextField(TEXT_LEN_SHORT);
		freqAAxAC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqAAxAC);
		freqAAxBC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqAAxBC);
		freqAAxCC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqAAxCC);

		freqABxAA = new EvoTextField(TEXT_LEN_SHORT);
		freqABxAB = new EvoTextField(TEXT_LEN_SHORT);
		freqABxBB = new EvoTextField(TEXT_LEN_SHORT);
		freqABxAC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqABxAC);
		freqABxBC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqABxBC);
		freqABxCC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqABxCC);

		freqBBxAA = new EvoTextField(TEXT_LEN_SHORT);
		freqBBxAB = new EvoTextField(TEXT_LEN_SHORT);
		freqBBxBB = new EvoTextField(TEXT_LEN_SHORT);
		freqBBxAC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBBxAC);
		freqBBxBC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBBxBC);
		freqBBxCC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBBxCC);

		freqACxAA = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxAA);
		freqACxAB = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxAB);
		freqACxBB = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxBB);
		freqACxAC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxAC);
		freqACxBC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxBC);
		freqACxCC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxCC);

		freqBCxAA = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxAA);
		freqBCxAB = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxAB);
		freqBCxBB = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxBB);
		freqBCxAC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxAC);
		freqBCxBC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxBC);
		freqBCxCC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxCC);

		freqCCxAA = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxAA);
		freqCCxAB = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxAB);
		freqCCxBB = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxBB);
		freqCCxAC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxAC);
		freqCCxBC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxBC);
		freqCCxCC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxCC);

		addToLists();

		table = new JPanel();
    table.setBackground(getBackground());
    table.setAlignmentX(Component.LEFT_ALIGNMENT);
		table.setLayout(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints(); // t for temp constraints
		t.insets = new Insets(0, 0, 3, 15);

		int k = 1;
		t.gridx = 0;
		t.gridy = 1;
		for(Component comp : prefLabels) {
			 t.gridy = k; k++;
			 table.add(comp, t);
		}

		int i = 1;
		t.gridy = 0;
		for(Component comp : labels) {
			t.gridx = i; i++;
			table.add(comp, t);
		}

		i = 1; int j = 1;
		for(Component comp : fields) {
			t.gridx = i; i++;
			t.gridy = j;
			table.add(comp, t);
			if(i % 7 == 0 ) {
				j++;
				i = 1;
			}
		}

    t.gridx = 8; t.gridy = 0;
    t.weightx = 1.0;
    t.fill = GridBagConstraints.HORIZONTAL;
    table.add(Box.createHorizontalGlue(), t);

		add(titlePane);
		add(table);

    help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openHelp("/help/Help08_Sexual_Selection.pdf");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	// does exactly what you'd think
	private void addToLists() {

		prefLabels.add(AAPrefLabel);
		prefLabels.add(ABPrefLabel);
		prefLabels.add(BBPrefLabel);
		prefLabels.add(ACPrefLabel);
		prefLabels.add(BCPrefLabel);
		prefLabels.add(CCPrefLabel);

		labels.add(AALabel);	labels.add(ABLabel);	labels.add(BBLabel);
		labels.add(ACLabel);	labels.add(BCLabel);	labels.add(CCLabel);

		fields.add(freqAAxAA); fields.add(freqAAxAB); fields.add(freqAAxBB);
		fields.add(freqAAxAC); fields.add(freqAAxBC); fields.add(freqAAxCC);

		fields.add(freqABxAA); fields.add(freqABxAB); fields.add(freqABxBB);
		fields.add(freqABxAC); fields.add(freqABxBC); fields.add(freqABxCC);

		fields.add(freqBBxAA); fields.add(freqBBxAB); fields.add(freqBBxBB);
		fields.add(freqBBxAC); fields.add(freqBBxBC); fields.add(freqBBxCC);

		fields.add(freqACxAA); fields.add(freqACxAB); fields.add(freqACxBB);
		fields.add(freqACxAC); fields.add(freqACxBC); fields.add(freqACxCC);

		fields.add(freqBCxAA); fields.add(freqBCxAB); fields.add(freqBCxBB);
		fields.add(freqBCxAC); fields.add(freqBCxBC); fields.add(freqBCxCC);

		fields.add(freqCCxAA); fields.add(freqCCxAB); fields.add(freqCCxBB);
		fields.add(freqCCxAC); fields.add(freqCCxBC); fields.add(freqCCxCC);

	}

	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		for(Component c : table.getComponents()) {
			c.setEnabled(enable);
		}
	}

	public void submit(shared.SessionParameters p) {
		// AA x __
		p.setSexualSelectionRate(Genotype.AA, Genotype.AA,
				Double.parseDouble(freqAAxAA.getText()));
		p.setSexualSelectionRate(Genotype.AA, Genotype.AB,
				Double.parseDouble(freqAAxAB.getText()));
		p.setSexualSelectionRate(Genotype.AA, Genotype.BB,
				Double.parseDouble(freqAAxBB.getText()));

		// AB x __
		p.setSexualSelectionRate(Genotype.AB, Genotype.AA,
				Double.parseDouble(freqABxAA.getText()));
		p.setSexualSelectionRate(Genotype.AB, Genotype.AB,
				Double.parseDouble(freqABxAB.getText()));
		p.setSexualSelectionRate(Genotype.AB, Genotype.BB,
				Double.parseDouble(freqABxBB.getText()));

		// BB x __
		p.setSexualSelectionRate(Genotype.BB, Genotype.AA,
				Double.parseDouble(freqBBxAA.getText()));
		p.setSexualSelectionRate(Genotype.BB, Genotype.AB,
				Double.parseDouble(freqBBxAB.getText()));
		p.setSexualSelectionRate(Genotype.BB, Genotype.BB,
				Double.parseDouble(freqBBxBB.getText()));

    if(threeAlleles) {
      // AA x C_
      p.setSexualSelectionRate(Genotype.AA, Genotype.AC,
  				Double.parseDouble(freqAAxAC.getText()));
  		p.setSexualSelectionRate(Genotype.AA, Genotype.BC,
  				Double.parseDouble(freqAAxBC.getText()));
  		p.setSexualSelectionRate(Genotype.AA, Genotype.CC,
  				Double.parseDouble(freqAAxCC.getText()));

      // AB x C_
      p.setSexualSelectionRate(Genotype.AB, Genotype.AC,
  				Double.parseDouble(freqABxAC.getText()));
  		p.setSexualSelectionRate(Genotype.AB, Genotype.BC,
  				Double.parseDouble(freqABxBC.getText()));
  		p.setSexualSelectionRate(Genotype.AB, Genotype.CC,
  				Double.parseDouble(freqABxCC.getText()));

      // BB x C_
      p.setSexualSelectionRate(Genotype.BB, Genotype.AC,
  				Double.parseDouble(freqBBxAC.getText()));
  		p.setSexualSelectionRate(Genotype.BB, Genotype.BC,
  				Double.parseDouble(freqBBxBC.getText()));
  		p.setSexualSelectionRate(Genotype.BB, Genotype.CC,
  				Double.parseDouble(freqBBxCC.getText()));

      // AC x __
      p.setSexualSelectionRate(Genotype.AC, Genotype.AA,
  				Double.parseDouble(freqACxAA.getText()));
  		p.setSexualSelectionRate(Genotype.AC, Genotype.AB,
  				Double.parseDouble(freqACxAB.getText()));
  		p.setSexualSelectionRate(Genotype.AC, Genotype.BB,
  				Double.parseDouble(freqACxBB.getText()));
      p.setSexualSelectionRate(Genotype.AC, Genotype.AC,
  				Double.parseDouble(freqACxAC.getText()));
  		p.setSexualSelectionRate(Genotype.AC, Genotype.BC,
  				Double.parseDouble(freqACxBC.getText()));
  		p.setSexualSelectionRate(Genotype.AC, Genotype.CC,
  				Double.parseDouble(freqACxCC.getText()));

      // BC x __
      p.setSexualSelectionRate(Genotype.BC, Genotype.AA,
  				Double.parseDouble(freqBCxAA.getText()));
  		p.setSexualSelectionRate(Genotype.BC, Genotype.AB,
  				Double.parseDouble(freqBCxAB.getText()));
  		p.setSexualSelectionRate(Genotype.BC, Genotype.BB,
  				Double.parseDouble(freqBCxBB.getText()));
      p.setSexualSelectionRate(Genotype.BC, Genotype.AC,
  				Double.parseDouble(freqBCxAC.getText()));
  		p.setSexualSelectionRate(Genotype.BC, Genotype.BC,
  				Double.parseDouble(freqBCxBC.getText()));
  		p.setSexualSelectionRate(Genotype.BC, Genotype.CC,
  				Double.parseDouble(freqBCxCC.getText()));

      // CC x __
      p.setSexualSelectionRate(Genotype.CC, Genotype.AA,
  				Double.parseDouble(freqCCxAA.getText()));
  		p.setSexualSelectionRate(Genotype.CC, Genotype.AB,
  				Double.parseDouble(freqCCxAB.getText()));
  		p.setSexualSelectionRate(Genotype.CC, Genotype.BB,
  				Double.parseDouble(freqCCxBB.getText()));
      p.setSexualSelectionRate(Genotype.CC, Genotype.AC,
  				Double.parseDouble(freqCCxAC.getText()));
  		p.setSexualSelectionRate(Genotype.CC, Genotype.BC,
  				Double.parseDouble(freqCCxBC.getText()));
  		p.setSexualSelectionRate(Genotype.CC, Genotype.CC,
  				Double.parseDouble(freqCCxCC.getText()));
    }
	}

	public static void main(String[] args){
		JFrame window = new JFrame();

		SexSelectPane test = new SexSelectPane();

		window.add(test);
		window.pack();
		window.setVisible(true);
	}

}
