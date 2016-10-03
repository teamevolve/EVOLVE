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

		selectRandS.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				for(Component comp : rAndS) {
					comp.setEnabled(true);
				}
				for(Component comp : absFit) {
					comp.setEnabled(false);
				}			
			}
		});
		
		selectAbs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				for(Component comp : rAndS) {
					comp.setEnabled(false);
				}
				for(Component comp : absFit) {
					comp.setEnabled(true);
				}			
			}
		});

	}
	
	
	public void submit(shared.SessionParameters p){

		double relFitAA = 0;
		double relFitAB = 0;
		double relFitBB = 0;

		// if repro and surv is selected
		if(selectRandS.isSelected()) {
			p.setReproductionRate(Genotype.AA, Double.parseDouble(reproAA.getText()));
			p.setReproductionRate(Genotype.AB, Double.parseDouble(reproAB.getText()));
			p.setReproductionRate(Genotype.BB, Double.parseDouble(reproBB.getText()));
			
			p.setSurvivalRate(Genotype.AA, Double.parseDouble(survAA.getText()));
			p.setSurvivalRate(Genotype.AB, Double.parseDouble(survAB.getText()));
			p.setSurvivalRate(Genotype.BB, Double.parseDouble(survBB.getText()));

			
			// calculate this 
			relFitAA = 5.3;
			relFitAB = 1.7;
			relFitBB = 0.2;
			

		}
		else if(selectAbs.isSelected()) {

			double afAA = Double.parseDouble(absFitAA.getText());
			double afAB = Double.parseDouble(absFitAB.getText());
			double afBB = Double.parseDouble(absFitBB.getText());
			
			p.setAbsoluteFitness(Genotype.AA, afAA);
			p.setAbsoluteFitness(Genotype.AA, afAB);
			p.setAbsoluteFitness(Genotype.AA, afBB);

			relFitAA = afAA / (afAA + afAB + afBB);
			relFitAB = afAB / (afAA + afAB + afBB);
			relFitBB = afBB / (afAA + afAB + afBB);
		}


		relFitAALabel.setText("AA:   " + String.format("%.4f", relFitAA));
		relFitABLabel.setText("AB:   " + String.format("%.4f", relFitAB));
		relFitBBLabel.setText("BB:   " + String.format("%.4f", relFitBB));

		
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		SelectionPane test = new SelectionPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
	}

	
	
}
