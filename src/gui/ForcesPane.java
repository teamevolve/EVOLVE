package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import importexport.ExportFormat;
import shared.DataManager;
import shared.EvolveDirector;
import shared.Genotype;
import shared.SessionParameters;

/**
 * 
 * @author linneasahlberg
 * @author jasonfortunato
 * 
 * The first panel in the GUI. Randomly assigns a seed or allows the user to enter a seed value. Allows user to
 * choose whether to run simulation on 2 alleles or 3 allels.
 * 
 */
public class ForcesPane extends EvoPane {
	JLabel seedLabel; 				// Seed
	JTextField seedField;
	JLabel numAllelesLabel;			// Number of Alleles
	ButtonGroup numAlleles;
	static JRadioButton alleles2, alleles3;
	
	public ForcesPane() {
		super();
		/* seed stuff ********************************************************/
		seedLabel = new JLabel("<html><b>Seed: </b>");
		seedField = new JTextField(TEXT_LEN_LONG);
		seedField.setName(INT);
		seedField.setInputVerifier(iv);
		
		// add seed label and field to frame
		c.gridx = 4; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(seedLabel, c);		
		c.gridx = 4; c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(seedField, c);	

		// Fill in a random seed
		Long randomNum = (new Random()).nextLong();
		seedField.setText(randomNum.toString());
		
		/* num alleles stuff ****************************************************/
		numAllelesLabel = new JLabel("<html><b>Number of Alleles: </b>");
		
		// 2 and 3 allele radio buttons
		numAlleles = new ButtonGroup();
		alleles2 = new JRadioButton("2", true);
		alleles3 = new JRadioButton("3");
		
		// add radio buttons to button groups
		numAlleles.add(alleles2);
		numAlleles.add(alleles3);
		
		// add radio buttons and labels to pane
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0; c.gridy = 1;
		add(numAllelesLabel, c);
		c.gridx = 1; c.gridy = 1;
		add(alleles2, c);
		c.gridx = 2; c.gridy = 1;
		add(alleles3, c);
	}
	
	void submit (shared.SessionParameters p) {
		p.setThreeAlleles(alleles3.isSelected());
		p.setSeed(Long.parseLong(seedField.getText()));
	}
}
