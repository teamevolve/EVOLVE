package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import importexport.ExportFormat;
import shared.DataManager;
import shared.EvolveDirector;
import shared.Genotype;
import shared.SessionParameters;

public class ForcesPane extends EvoPane {
	JLabel seedLabel; 				// Seed
	JTextField seedField;
	JLabel numAllelesLabel;			// Number of Alleles
	ButtonGroup numAlleles;
	static JRadioButton alleles2, alleles3;
	
	public ForcesPane() {
		super();
		/* seed stuff *****************************************************************************/
		seedLabel = new JLabel("Seed: ");
		seedField = new JTextField(TEXT_LEN_LONG);
		seedField.setName(INT);
		seedField.setInputVerifier(iv);
		
		c.gridx = 4; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(seedLabel, c);		
		c.gridx = 4; c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		add(seedField, c);	
		
		/* num alleles stuff *****************************************************************************/
		numAllelesLabel = new JLabel("Number of Alleles: ");
		numAlleles = new ButtonGroup();
		alleles2 = new JRadioButton("2", true);
		alleles3 = new JRadioButton("3");
		numAlleles.add(alleles2);
		numAlleles.add(alleles3);
		
		c.gridx = 0; c.gridy = 1;
		add(numAllelesLabel, c);
		c.gridx = 1; c.gridy = 1;
		add(alleles2, c);
		c.gridx = 2; c.gridy = 1;
		add(alleles3, c);
	}
	
	void submit (shared.SessionParameters p) {
		p.setThreeAlleles(alleles3.isSelected());
		p.setSeed(Integer.parseInt(seedField.getText()));
	}
}