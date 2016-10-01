package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
		reproAALabel = new JLabel("AA: ");
		reproABLabel = new JLabel("AB: ");
		reproBBLabel = new JLabel("BB: ");
		reproAA = new JTextField(TEXT_LEN_LONG);
		reproAB = new JTextField(TEXT_LEN_LONG);
		reproBB = new JTextField(TEXT_LEN_LONG);
		
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
		survRateLabel = new JLabel("Survival Rates (0 to 1): ");
		survAALabel = new JLabel("AA: ");
		survABLabel = new JLabel("AB: ");
		survBBLabel = new JLabel("BB: ");
		survAA = new JTextField(TEXT_LEN_SHORT);
		survAB = new JTextField(TEXT_LEN_SHORT);
		survBB = new JTextField(TEXT_LEN_SHORT);
		
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
		absFitLabel = new JLabel("Absolute Fitness (any number): ");
		absFitAALabel = new JLabel("AA: ");
		absFitABLabel = new JLabel("AB: ");
		absFitBBLabel = new JLabel("BB: ");
		absFitAA = new JTextField(TEXT_LEN_LONG);
		absFitAB = new JTextField(TEXT_LEN_LONG);
		absFitBB = new JTextField(TEXT_LEN_LONG);
		
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
		
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		SelectionPane test = new SelectionPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
	}

	
	
}
