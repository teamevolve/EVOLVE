package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.ArrayList;

import shared.Genotype;

/**
 * 
 * @author linneasahlberg
 * @author jasonfortunato
 *
 */

public class SexSelectPane extends EvoPane {

	static final int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 3;
	
	JLabel mateFreqLabel, AAPrefLabel,ABPrefLabel, BBPrefLabel, 
		ACPrefLabel, BCPrefLabel, CCPrefLabel;

	JLabel AALabel, ABLabel, BBLabel, ACLabel, BCLabel, CCLabel;
	
	JTextField freqAAxAA, freqAAxAB, freqAAxBB, freqAAxAC, freqAAxBC, freqAAxCC;
	JTextField freqABxAA, freqABxAB, freqABxBB, freqABxAC, freqABxBC, freqABxCC;
	JTextField freqBBxAA, freqBBxAB, freqBBxBB, freqBBxAC, freqBBxBC, freqBBxCC;
	JTextField freqACxAA, freqACxAB, freqACxBB, freqACxAC, freqACxBC, freqACxCC;
	JTextField freqBCxAA, freqBCxAB, freqBCxBB, freqBCxAC, freqBCxBC, freqBCxCC;
	JTextField freqCCxAA, freqCCxAB, freqCCxBB, freqCCxAC, freqCCxBC, freqCCxCC;

	ArrayList<Component> prefLabels = new ArrayList<Component>();	
	ArrayList<Component> labels = new ArrayList<Component>();
	ArrayList<Component> fields = new ArrayList<Component>();


	
	public SexSelectPane() {
		super();
		mateFreqLabel = new JLabel("Sexual Selection (Mating Preference): ");
		AAPrefLabel = new JLabel("% AA Preference for: ");
		ABPrefLabel = new JLabel("% AB Preference for: ");
		BBPrefLabel = new JLabel("% BB Preference for: ");
		ACPrefLabel = new JLabel("% AC Preference for: "); threeAllelesList.add(ACPrefLabel);
		BCPrefLabel = new JLabel("% BC Preference for: "); threeAllelesList.add(BCPrefLabel);
		CCPrefLabel = new JLabel("% CC Preference for: "); threeAllelesList.add(CCPrefLabel);

		AALabel = new JLabel("AA");
		ABLabel = new JLabel("AB");
		BBLabel = new JLabel("BB");
		ACLabel = new JLabel("AC"); threeAllelesList.add(ACLabel);
		BCLabel = new JLabel("BC"); threeAllelesList.add(BCLabel);
		CCLabel = new JLabel("CC"); threeAllelesList.add(CCLabel);
		
		freqAAxAA = new JTextField(TEXT_LEN_SHORT);
		freqAAxAB = new JTextField(TEXT_LEN_SHORT);
		freqAAxBB = new JTextField(TEXT_LEN_SHORT);
		freqAAxAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqAAxAC);
		freqAAxBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqAAxBC);
		freqAAxCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqAAxCC);
				
		freqABxAA = new JTextField(TEXT_LEN_SHORT);
		freqABxAB = new JTextField(TEXT_LEN_SHORT);
		freqABxBB = new JTextField(TEXT_LEN_SHORT);
		freqABxAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqABxAC);
		freqABxBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqABxBC);
		freqABxCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqABxCC);

		freqBBxAA = new JTextField(TEXT_LEN_SHORT);
		freqBBxAB = new JTextField(TEXT_LEN_SHORT);
		freqBBxBB = new JTextField(TEXT_LEN_SHORT);
		freqBBxAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBBxAC);
		freqBBxBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBBxBC);
		freqBBxCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBBxCC);

		freqACxAA = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxAA);
		freqACxAB = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxAB);
		freqACxBB = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxBB);
		freqACxAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxAC);
		freqACxBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxBC);
		freqACxCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqACxCC);

		freqBCxAA = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxAA);
		freqBCxAB = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxAB);
		freqBCxBB = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxBB);
		freqBCxAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxAC);
		freqBCxBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxBC);
		freqBCxCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqBCxCC);

		freqCCxAA = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxAA);
		freqCCxAB = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxAB);
		freqCCxBB = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxBB);
		freqCCxAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxAC);
		freqCCxBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxBC);
		freqCCxCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(freqCCxCC);

		addToLists();
		
		JPanel table = new JPanel();
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
		
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 3;
		add(mateFreqLabel, c);
			
		//AA x __
		c.gridx = 1; c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 6;
		//AAPrefLabel.setFont(boldFont);
		add(table, c);

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
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		SexSelectPane test = new SexSelectPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
	}

}
