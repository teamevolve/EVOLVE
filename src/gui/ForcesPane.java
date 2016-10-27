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
	JLabel numAllelesLabel;			// Number of Alleles
	ButtonGroup numAlleles;
	JRadioButton alleles2, alleles3;
	
	public ForcesPane() {
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
		
		// Set actions for the NumAlleles radio buttons
		alleles2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeThreeAlleles(false);
			}
		});
		
		alleles3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeThreeAlleles(true);
			}
		});
	}
	
	void submit (shared.SessionParameters p) {
		p.setThreeAlleles(alleles3.isSelected());
	}
}