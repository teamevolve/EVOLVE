package gui;

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
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import shared.Genotype;

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
	
	ArrayList<Component> labelsList = new ArrayList<Component>();
	ArrayList<Component> survList = new ArrayList<Component>();
	ArrayList<Component> reproList = new ArrayList<Component>();
	ArrayList<Component> absFitList = new ArrayList<Component>();
	ArrayList<Component> relFitList = new ArrayList<Component>();
	
		
	JPanel table;
	
	public SelectionPane() {
		
		// Selection radio buttons
		selectLabel = new JLabel("Natural Selection: ");
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
		survLabel = new JLabel("Survival Rates:");
		reproLabel = new JLabel("Reproductive Rates:");
		absFitLabel = new JLabel("Absolute Fitness:");
		relFitLabel = new JLabel("Relative Fitness:");
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
		relFitLabel = new JLabel("Relative Fitness: ");
		relFitAA = new JLabel("___");
		relFitAB = new JLabel("___");
		relFitBB = new JLabel("___");
		relFitAC = new JLabel("___"); threeAllelesList.add(relFitAC);
		relFitBC = new JLabel("___"); threeAllelesList.add(relFitBC);
		relFitCC = new JLabel("___"); threeAllelesList.add(relFitCC);
		
		table = new JPanel();
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
		
		c.gridx = 1; c.gridy = 2;
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
	
	private void modeRandS(boolean b) {
		for(Component comp : survList)
			comp.setEnabled(b);
		
		for(Component comp : reproList)
			comp.setEnabled(b);
		
		for(Component comp : absFitList)
			comp.setEnabled(!b);	
	}
	
/*	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if (selectRandS.isSelected() && enabled == true) {
			modeRandS(true);
		}
		else if (selectAbs.isSelected() && enabled == true){
			modeRandS(false);
		}
	}*/
	
	/**
	 * Dumps absolute and relative fitnesses into session parameters.
	 * If Reproductioin and Survival radiobutton is checked, it also dumps Repro and Surv rates
	 * @param p = sesh parms
	 */
	public void submit(shared.SessionParameters p){

		double afAA = 0, afAB = 0, afBB = 0;

		double rfAA, rfAB, rfBB;
		
		// if repro and surv is selected
		if(selectRandS.isSelected()) {
			double AArr = Double.parseDouble(reproAA.getText());
			double ABrr = Double.parseDouble(reproAB.getText());
			double BBrr = Double.parseDouble(reproBB.getText());

			double AAsr = Double.parseDouble(survAA.getText());
			double ABsr = Double.parseDouble(survAB.getText());
			double BBsr = Double.parseDouble(survBB.getText());
			
			p.setReproductionRate(Genotype.AA, AArr);
			p.setReproductionRate(Genotype.AB, ABrr);
			p.setReproductionRate(Genotype.BB, BBrr);

			p.setSurvivalRate(Genotype.AA, AAsr);
			p.setSurvivalRate(Genotype.AB, ABsr);
			p.setSurvivalRate(Genotype.BB, BBsr);

			// Calculate absolute fitness of each genotype
			afAA = AArr * AAsr;
			afAB = ABrr * ABsr;                 // ########## Ensure these are correct calculations??
			afBB = BBrr * BBsr;

			absFitAA.setText(String.format("%.3f", afAA));
			absFitAB.setText(String.format("%.3f", afAB));
			absFitBB.setText(String.format("%.3f", afBB));
			
		}
		else if(selectAbs.isSelected()) {
			afAA = Double.parseDouble(absFitAA.getText());
			afAB = Double.parseDouble(absFitAB.getText());
			afBB = Double.parseDouble(absFitBB.getText());
		}

		p.setAbsoluteFitness(Genotype.AA, afAA);
		p.setAbsoluteFitness(Genotype.AA, afAB);
		p.setAbsoluteFitness(Genotype.AA, afBB);
		
		rfAA = afAA / (afAA + afAB + afBB);
		rfAB = afAB / (afAA + afAB + afBB);
		rfBB = afBB / (afAA + afAB + afBB);
		
		p.setRelativeFitness(Genotype.AA, rfAA);
		p.setRelativeFitness(Genotype.AB, rfAB);
		p.setRelativeFitness(Genotype.BB, rfBB);
		
		relFitAA.setText(String.format("%.4f", rfAA));
		relFitAB.setText(String.format("%.4f", rfAB));
		relFitBB.setText(String.format("%.4f", rfBB));
	}
	
	/**
	 * 
	 * Little test guy 
	 */
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		SelectionPane test = new SelectionPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
	}

	
	
}
