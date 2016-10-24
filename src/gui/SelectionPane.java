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
		reproAALabel, reproABLabel, reproBBLabel;
	JTextField reproAA, reproAB, reproBB;
	JLabel survRateLabel,					// Survival Rates (0 to 1)
		survAALabel, survABLabel, survBBLabel;
	JTextField survAA, survAB, survBB;
	JLabel absFitLabel,					// Absolute fitness (any num)
		absFitAALabel, absFitABLabel, absFitBBLabel;
	JTextField absFitAA, absFitAB, absFitBB;
	JLabel relFitLabel,					// Relative fitness (0 to 1)
		relFitAALabel, relFitABLabel, relFitBBLabel;
	
	ArrayList<Component> rAndS = new ArrayList<Component>();
	ArrayList<Component> absFit = new ArrayList<Component>();
		
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
		reproAA = new JTextField(TEXT_LEN_LONG); rAndS.add(reproAA);
		reproAB = new JTextField(TEXT_LEN_LONG); rAndS.add(reproAB);
		reproBB = new JTextField(TEXT_LEN_LONG); rAndS.add(reproBB);

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
		
		// Survival Rates (visible if Repro and Surv is selected)
		survRateLabel = new JLabel("Survival Rates (0 to 1): "); rAndS.add(survRateLabel);
		survAALabel = new JLabel("AA: "); rAndS.add(survAALabel);
		survABLabel = new JLabel("AB: "); rAndS.add(survABLabel);
		survBBLabel = new JLabel("BB: "); rAndS.add(survBBLabel);
		survAA = new JTextField(TEXT_LEN_SHORT); rAndS.add(survAA);
		survAB = new JTextField(TEXT_LEN_SHORT); rAndS.add(survAB);
		survBB = new JTextField(TEXT_LEN_SHORT); rAndS.add(survBB);
		
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
		
		// Absolute Fitness Rates (any number)
		absFitLabel = new JLabel("Absolute Fitness (any number): "); absFit.add(absFitLabel);
		absFitAALabel = new JLabel("AA: "); absFit.add(absFitAALabel);
		absFitABLabel = new JLabel("AB: "); absFit.add(absFitABLabel);
		absFitBBLabel = new JLabel("BB: "); absFit.add(absFitBBLabel);
		absFitAA = new JTextField(TEXT_LEN_LONG); absFit.add(absFitAA);
		absFitAB = new JTextField(TEXT_LEN_LONG); absFit.add(absFitAB);
		absFitBB = new JTextField(TEXT_LEN_LONG); absFit.add(absFitBB);
		
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
		
		// Relative Fitness Rates (display only, 0 to 1)
		relFitLabel = new JLabel("Relative Fitness: ");
		relFitAALabel = new JLabel("AA: ___");
		relFitABLabel = new JLabel("AB: ___");
		relFitBBLabel = new JLabel("BB: ___");
		
		c.gridx = 1; c.gridy = 160;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(relFitLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 170;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(relFitAALabel, c);
	
//		c.gridx = 2; c.gridy = 170;
		c.anchor = GridBagConstraints.CENTER;
		add(relFitABLabel, c);
		
//		c.gridx = 3; c.gridy = 170;
		c.anchor = GridBagConstraints.EAST;
		add(relFitBBLabel, c);

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
