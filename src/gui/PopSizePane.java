package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.util.ArrayList;

/**
 * 
 * @author jasonfortunato
 * @author linneasahlberg
 * 
 */
public class PopSizePane extends EvoPane {

	JLabel popLabel;			// Population size
	//JTextField popSizeField;
	ButtonGroup initPopVals;
	JRadioButton alleleFreqs;
	JRadioButton genotypeNums;
	JLabel initPopLabel;  			// Initial population
	JTextField initPop;

	ArrayList<Component> alleleFreqsList = new ArrayList<Component>();
	
	public PopSizePane() {

		super();
		
		popLabel = new JLabel("Initial Population: ");
		//popSizeField = new JTextField(TEXT_LEN_LONG);
		
		//popSizeField.setName(INT); popSizeField.setInputVerifier(iv);
		
		c.gridx = 0; c.gridy = 10;
		c.anchor = GridBagConstraints.WEST;
		add(popLabel, c);
		/*
		c.gridx = 1; c.gridy = 10;
		add(popSizeField, c);*/
		
		// population constant radio button stuff
		initPopVals = new ButtonGroup();
		alleleFreqs = new JRadioButton("Enter Allele Frequncies");
		genotypeNums = new JRadioButton("Enter Genotype Numbers");
		initPopVals.add(alleleFreqs);
		initPopVals.add(genotypeNums);
		
		c.gridx = 1; c.gridy = 10;
		add(alleleFreqs, c);
		c.gridx = 2; c.gridy = 10;
		add(genotypeNums, c);	

		// Set actions for the PopConstTrue/False radio buttons
		alleleFreqs.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeAlleleFreqs(true);
			}
		});
		
		genotypeNums.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeAlleleFreqs(false);
			}
		});
		
		}
	
		private void modeAlleleFreqs(boolean b){
			for(Component comp : alleleFreqsList) {
				comp.setEnabled(b);
			}
		/*	if(b == true) { // clear the two text fields
				carryCap.setText("");
				postCrash.setText("");
			}
		*/
		}

		public void submit(shared.SessionParameters p) {
			System.out.println("hi");
		/*	
		p.setPopSize(Integer.parseInt(popSizeField.getText()));
			p.setPopConst(popConstTrue.isSelected());
			if(popConstFalse.isSelected()){
				p.setPopCapacity(Integer.parseInt(carryCap.getText()));
				p.setCrashCapacity(Integer.parseInt(postCrash.getText()));
			*/
			}
		}
	
