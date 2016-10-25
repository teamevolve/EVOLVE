package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

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
	
	JLabel selectLabel;				// Selection
	ButtonGroup selectGroup;
	JRadioButton selectRandS, 
		selectAbs;
	JLabel reproRateLabel, 					// Reproduction Rates (0 to 10, decimal allowed)
		reproAALabel, reproABLabel, reproBBLabel,
		reproACLabel, reproBCLabel, reproCCLabel;
	JTextField reproAA, reproAB, reproBB,
		reproAC, reproBC, reproCC;
	JLabel survRateLabel,					// Survival Rates (0 to 1)
		survAALabel, survABLabel, survBBLabel,
		survACLabel, survBCLabel, survCCLabel;
	JTextField survAA, survAB, survBB,
		survAC, survBC, survCC;
	JLabel absFitLabel,					// Absolute fitness (any num)
		absFitAALabel, absFitABLabel, absFitBBLabel,
		absFitACLabel, absFitBCLabel, absFitCCLabel;
	JTextField absFitAA, absFitAB, absFitBB,
		absFitAC, absFitBC, absFitCC;
	JLabel relFitLabel,					// Relative fitness (0 to 1)
		relFitAALabel, relFitABLabel, relFitBBLabel,
		relFitACLabel, relFitBCLabel, relFitCCLabel;
	
	ArrayList<Component> rAndS = new ArrayList<Component>();
	ArrayList<Component> absFit = new ArrayList<Component>();
	ArrayList<Component> threeAllelesList = new ArrayList<Component>();
		
	public SelectionPane() {
		
		// Selection radio buttons
		selectLabel = new JLabel("Selection: ");
		selectGroup = new ButtonGroup();
		selectRandS = new JRadioButton("Reproduction and Survival");
		selectAbs = new JRadioButton("Absolute Fitness");
		selectGroup.add(selectRandS);
		selectGroup.add(selectAbs);
		
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 90;
		c.anchor = GridBagConstraints.WEST;
		add(selectLabel, c);
		c.gridx = 1; c.gridy = 90;
		c.gridwidth = 2;
		add(selectRandS, c);
		c.gridx = 3; c.gridy = 90;
		c.gridwidth = 1;
		add(selectAbs, c);
		
		// Reproduction Rates (visible if Repro and Surv is selected)
		reproRateLabel = new JLabel("Reproduction Rates (0 to 10, decimals allowed): ");
		rAndS.add(reproRateLabel);
		reproAALabel = new JLabel("AA: "); rAndS.add(reproAALabel);
		reproABLabel = new JLabel("AB: "); rAndS.add(reproABLabel);
		reproBBLabel = new JLabel("BB: "); rAndS.add(reproBBLabel);
		reproACLabel = new JLabel("AC: "); rAndS.add(reproACLabel); threeAllelesList.add(reproACLabel); 
		reproBCLabel = new JLabel("BC: "); rAndS.add(reproBCLabel); threeAllelesList.add(reproBCLabel); 
		reproCCLabel = new JLabel("CC: "); rAndS.add(reproCCLabel); threeAllelesList.add(reproCCLabel); 
		reproAA = new JTextField(TEXT_LEN_LONG); rAndS.add(reproAA);
		reproAB = new JTextField(TEXT_LEN_LONG); rAndS.add(reproAB);
		reproBB = new JTextField(TEXT_LEN_LONG); rAndS.add(reproBB);
		reproAC = new JTextField(TEXT_LEN_LONG); rAndS.add(reproAC); threeAllelesList.add(reproAC); 
		reproBC = new JTextField(TEXT_LEN_LONG); rAndS.add(reproBC); threeAllelesList.add(reproBC); 
		reproCC = new JTextField(TEXT_LEN_LONG); rAndS.add(reproCC); threeAllelesList.add(reproCC); 

		// set input verifier
		reproAA.setName(ANY_DOUBLE_ZERO_TO_TEN); reproAA.setInputVerifier(iv);
		reproAB.setName(ANY_DOUBLE_ZERO_TO_TEN); reproAB.setInputVerifier(iv);
		reproBB.setName(ANY_DOUBLE_ZERO_TO_TEN); reproBB.setInputVerifier(iv);
		
		c.gridx = 1; c.gridy = 100;
		c.gridwidth = 3;
		add(reproRateLabel, c);
		
		// add label then field x3
		c.gridwidth = 1;
		c.gridx = 1; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproAALabel, c);
		c.gridx = 1; //c.gridy = 110;
		c.anchor = GridBagConstraints.CENTER;
		add(reproAA, c);
		
		c.gridx = 2; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproABLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 2; c.gridy = 110;
		add(reproAB, c);
		
		c.gridx = 3; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproBBLabel, c);
		c.gridx = 3; c.gridy = 110;
		c.anchor = GridBagConstraints.CENTER;
		add(reproBB, c);
		
		c.gridx = 4; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproACLabel, c);
		c.gridx = 4; c.gridy = 110;
		c.anchor = GridBagConstraints.CENTER;
		add(reproAC, c);
		
		c.gridx = 5; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproBCLabel, c);
		c.gridx = 5; c.gridy = 110;
		c.anchor = GridBagConstraints.CENTER;
		add(reproBC, c);
		
		c.gridx = 6; c.gridy = 110;
		c.anchor = GridBagConstraints.WEST;
		add(reproCCLabel, c);
		c.gridx = 6; c.gridy = 110;
		c.anchor = GridBagConstraints.CENTER;
		add(reproCC, c);
		
		// Survival Rates (visible if Repro and Surv is selected)
		survRateLabel = new JLabel("Survival Rates (0 to 1): "); rAndS.add(survRateLabel);
		survAALabel = new JLabel("AA: "); rAndS.add(survAALabel);
		survABLabel = new JLabel("AB: "); rAndS.add(survABLabel);
		survBBLabel = new JLabel("BB: "); rAndS.add(survBBLabel);
		survACLabel = new JLabel("AC: "); rAndS.add(survACLabel); threeAllelesList.add(survACLabel);
		survBCLabel = new JLabel("BC: "); rAndS.add(survBCLabel); threeAllelesList.add(survBCLabel);
		survCCLabel = new JLabel("CC: "); rAndS.add(survCCLabel); threeAllelesList.add(survCCLabel);
		survAA = new JTextField(TEXT_LEN_SHORT); rAndS.add(survAA);
		survAB = new JTextField(TEXT_LEN_SHORT); rAndS.add(survAB);
		survBB = new JTextField(TEXT_LEN_SHORT); rAndS.add(survBB);
		survAC = new JTextField(TEXT_LEN_SHORT); rAndS.add(survAC); threeAllelesList.add(survAC);
		survBC = new JTextField(TEXT_LEN_SHORT); rAndS.add(survBC); threeAllelesList.add(survBC);
		survCC = new JTextField(TEXT_LEN_SHORT); rAndS.add(survCC); threeAllelesList.add(survCC);
		
		// set input names for our verifier
		survAA.setName(RATE); survAA.setInputVerifier(iv);
		survAB.setName(RATE); survAB.setInputVerifier(iv);
		survBB.setName(RATE); survBB.setInputVerifier(iv);
		
		c.gridx = 1; c.gridy = 120;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(survRateLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 130;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(survAALabel, c);
		c.gridx = 1; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survAA, c);
		
		c.gridx = 2; c.gridy = 130;
		c.anchor = GridBagConstraints.WEST;
		add(survABLabel, c);
		c.gridx = 2; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survAB, c);
		
		c.gridx = 3; c.gridy = 130;
		c.anchor = GridBagConstraints.WEST;
		add(survBBLabel, c);
		c.gridx = 3; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survBB, c);
		
		c.gridx = 4; c.gridy = 130;
		c.anchor = GridBagConstraints.WEST;
		add(survACLabel, c);
		c.gridx = 4; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survAC, c);
		
		c.gridx = 5; c.gridy = 130;
		c.anchor = GridBagConstraints.WEST;
		add(survBCLabel, c);
		c.gridx = 5; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survBC, c);
		
		c.gridx = 6; c.gridy = 130;
		c.anchor = GridBagConstraints.WEST;
		add(survCCLabel, c);
		c.gridx = 6; c.gridy = 130;
		c.anchor = GridBagConstraints.CENTER;
		add(survCC, c);
		
		// Absolute Fitness Rates (any number)
		absFitLabel = new JLabel("Absolute Fitness (any number): "); absFit.add(absFitLabel);
		absFitAALabel = new JLabel("AA: "); absFit.add(absFitAALabel);
		absFitABLabel = new JLabel("AB: "); absFit.add(absFitABLabel);
		absFitBBLabel = new JLabel("BB: "); absFit.add(absFitBBLabel);
		absFitACLabel = new JLabel("AC: "); absFit.add(absFitAALabel); threeAllelesList.add(absFitACLabel);
		absFitBCLabel = new JLabel("BC: "); absFit.add(absFitABLabel); threeAllelesList.add(absFitBCLabel);
		absFitCCLabel = new JLabel("CC: "); absFit.add(absFitBBLabel); threeAllelesList.add(absFitCCLabel);
		absFitAA = new JTextField(TEXT_LEN_LONG); absFit.add(absFitAA);
		absFitAB = new JTextField(TEXT_LEN_LONG); absFit.add(absFitAB);
		absFitBB = new JTextField(TEXT_LEN_LONG); absFit.add(absFitBB);
		absFitAC = new JTextField(TEXT_LEN_LONG); absFit.add(absFitAC); threeAllelesList.add(absFitAC);
		absFitBC = new JTextField(TEXT_LEN_LONG); absFit.add(absFitBC); threeAllelesList.add(absFitBC);
		absFitCC = new JTextField(TEXT_LEN_LONG); absFit.add(absFitCC); threeAllelesList.add(absFitCC);
		
		// set names for our input verifier
		absFitAA.setName(ANY_NUMBER); absFitAA.setInputVerifier(iv);
		absFitAB.setName(ANY_NUMBER); absFitAB.setInputVerifier(iv);
		absFitBB.setName(ANY_NUMBER); absFitBB.setInputVerifier(iv);
		
		c.gridx = 1; c.gridy = 140;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(absFitLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 150;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(absFitAALabel, c);
		c.gridx = 1; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitAA, c);
	
		c.gridx = 2; c.gridy = 150;
		c.anchor = GridBagConstraints.WEST;
		add(absFitABLabel, c);
		c.gridx = 2; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitAB, c);
		
		c.gridx = 3; c.gridy = 150;
		c.anchor = GridBagConstraints.WEST;
		add(absFitBBLabel, c);
		c.gridx = 3; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitBB, c);
		
		c.gridx = 4; c.gridy = 150;
		c.anchor = GridBagConstraints.WEST;
		add(absFitACLabel, c);
		c.gridx = 4; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitAC, c);
		
		c.gridx = 5; c.gridy = 150;
		c.anchor = GridBagConstraints.WEST;
		add(absFitBCLabel, c);
		c.gridx = 5; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitBC, c);
		
		c.gridx = 6; c.gridy = 150;
		c.anchor = GridBagConstraints.WEST;
		add(absFitCCLabel, c);
		c.gridx = 6; c.gridy = 150;
		c.anchor = GridBagConstraints.CENTER;
		add(absFitCC, c);
		
		// Relative Fitness Rates (display only, 0 to 1)
		relFitLabel = new JLabel("Relative Fitness: ");
		relFitAALabel = new JLabel("AA: ___");
		relFitABLabel = new JLabel("AB: ___");
		relFitBBLabel = new JLabel("BB: ___");
		relFitACLabel = new JLabel("AC: ___"); threeAllelesList.add(relFitACLabel);
		relFitBCLabel = new JLabel("BC: ___"); threeAllelesList.add(relFitBCLabel);
		relFitCCLabel = new JLabel("CC: ___"); threeAllelesList.add(relFitCCLabel);
		
		c.gridx = 1; c.gridy = 160;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(relFitLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 170;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(relFitAALabel, c);
	
//		c.gridx = 2; c.gridy = 170;
		c.anchor = GridBagConstraints.CENTER;
		add(relFitABLabel, c);
		
//		c.gridx = 3; c.gridy = 170;
		c.anchor = GridBagConstraints.EAST;
		add(relFitBBLabel, c);
		
		c.gridx = 2;
		c.anchor = GridBagConstraints.WEST;
		add(relFitACLabel, c);
		
		c.anchor = GridBagConstraints.CENTER;
		add(relFitBCLabel, c);
		
		c.anchor = GridBagConstraints.EAST;
		add(relFitCCLabel, c);

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
	
	private void modeRandS(boolean b) {
		for(Component comp : rAndS) {
			comp.setEnabled(b);
		}
		for(Component comp : absFit) {
			comp.setEnabled(!b);
		}			
	}
	
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if (selectRandS.isSelected() && enabled == true) {
			modeRandS(true);
		}
		else if (selectAbs.isSelected() && enabled == true){
			modeRandS(false);
		}
	}
	
	/**
	 * Dumps absolute and relative fitnesses into session parameters.
	 * If Reproductioin and Survival radiobutton is checked, it also dumps Repro and Surv rates
	 * @param p = sesh parms
	 */
	public void submit(shared.SessionParameters p){

		double afAA = 0, afAB = 0, afBB = 0;

		double relFitAA, relFitAB, relFitBB;
		
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

			absFitAA.setText(String.format("%.4f", afAA));
			absFitAB.setText(String.format("%.4f", afAB));
			absFitBB.setText(String.format("%.4f", afBB));
			
		}
		else if(selectAbs.isSelected()) {
			afAA = Double.parseDouble(absFitAA.getText());
			afAB = Double.parseDouble(absFitAB.getText());
			afBB = Double.parseDouble(absFitBB.getText());
		}

		p.setAbsoluteFitness(Genotype.AA, afAA);
		p.setAbsoluteFitness(Genotype.AA, afAB);
		p.setAbsoluteFitness(Genotype.AA, afBB);
		
		relFitAA = afAA / (afAA + afAB + afBB);
		relFitAB = afAB / (afAA + afAB + afBB);
		relFitBB = afBB / (afAA + afAB + afBB);
		
		p.setRelativeFitness(Genotype.AA, relFitAA);
		p.setRelativeFitness(Genotype.AB, relFitAB);
		p.setRelativeFitness(Genotype.BB, relFitBB);
		
		relFitAALabel.setText("AA:   " + String.format("%.4f", relFitAA));
		relFitABLabel.setText("AB:   " + String.format("%.4f", relFitAB));
		relFitBBLabel.setText("BB:   " + String.format("%.4f", relFitBB));

		
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
