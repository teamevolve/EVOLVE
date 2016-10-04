package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 
 * @author linneasahlberg
 * @author jasonfortunato
 *
 */

public class MigrationPane extends EvoPane {
	
	JLabel migLabel;
	ButtonGroup migGroup;
	JRadioButton fixedMig, varMig;
	JLabel fixedMigRateLabel;
	JTextField fixedMigRate;
	JLabel varMigRateLabel;
	JLabel varMigRateAALabel, varMigRateABLabel, varMigRateBBLabel;
	JTextField varMigRateAA, varMigRateAB, varMigRateBB;
	
	ArrayList<Component> fixedList = new ArrayList<Component>();
	ArrayList<Component> varyList = new ArrayList<Component>();
	
	public MigrationPane() {
		
		// Migration radio buttons
		migLabel = new JLabel("Migration: ");
		migGroup = new ButtonGroup();
		fixedMig = new JRadioButton("Fixed");
		varMig = new JRadioButton("Varies by Genotype");
		migGroup.add(fixedMig);
		migGroup.add(varMig);
				
		c.gridx = 0; c.gridy = 200;
		c.anchor = GridBagConstraints.WEST;
		add(migLabel, c);
		c.gridx = 0; c.gridy = 200;
		c.anchor = GridBagConstraints.EAST;
		add(fixedMig, c);
		c.gridx = 1; c.gridy = 200;
		c.anchor = GridBagConstraints.WEST;
		add(varMig, c);
				
		// Migration rate - if fixed
		fixedMigRateLabel = new JLabel("Migration Rate: ");
		fixedMigRate = new JTextField(TEXT_LEN_SHORT);
				
		c.gridx = 1; c.gridy = 210;
		c.anchor = GridBagConstraints.WEST;
		add(fixedMigRateLabel, c);
		c.gridx = 1; c.gridy = 210;
		c.anchor = GridBagConstraints.EAST;
		add(fixedMigRate, c);
			
		// Migration Rates by genotype - if varies
		varMigRateLabel = new JLabel("Migration Rate (by genotype): ");
		varMigRateAALabel = new JLabel("AA: ");
		varMigRateABLabel = new JLabel("AB: ");
		varMigRateBBLabel = new JLabel("BB: ");
		varMigRateAA = new JTextField(TEXT_LEN_SHORT);
		varMigRateAB = new JTextField(TEXT_LEN_SHORT);
		varMigRateBB = new JTextField(TEXT_LEN_SHORT);
				
		c.gridwidth = 3;
		c.gridx = 1; c.gridy = 220;
		c.anchor = GridBagConstraints.WEST;
		add(varMigRateLabel, c);
			
		// add label then field x3
		c.gridx = 1; c.gridy = 230;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(varMigRateAALabel, c);
		c.gridx = 1; c.gridy = 230;
		c.anchor = GridBagConstraints.CENTER;
		add(varMigRateAA, c);
			
		c.gridx = 2; c.gridy = 230;
		c.anchor = GridBagConstraints.WEST;
		add(varMigRateABLabel, c);
		c.gridx = 2; c.gridy = 230;
		c.anchor = GridBagConstraints.CENTER;
		add(varMigRateAB, c);
			
		c.gridx = 3; c.gridy = 230;
		c.anchor = GridBagConstraints.WEST;
		add(varMigRateBBLabel, c);
		c.gridx = 3; c.gridy = 230;
		c.anchor = GridBagConstraints.CENTER;
		add(varMigRateBB, c);
		
		//add fixed elements to fixedList
		fixedList.add(fixedMigRateLabel);
		fixedList.add(fixedMigRate);
		
		//add vary-by-genotype to varyList
		varyList.add(varMigRateLabel);
		varyList.add(varMigRateAALabel);
		varyList.add(varMigRateABLabel);
		varyList.add(varMigRateBBLabel);
		varyList.add(varMigRateAA);
		varyList.add(varMigRateAB);
		varyList.add(varMigRateBB);
		
		// set radio buttons to grey out sections of panel
		fixedMig.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeFixed(true);
			}
		});
		
		varMig.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeFixed(false);
			}
		});

		
	}
	
	private void modeFixed(boolean b) {
		for(Component comp : fixedList) {
			comp.setEnabled(b);		
		}
		for(Component comp : varyList) {
			comp.setEnabled(!b);
		}
	}

	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if (fixedMig.isSelected() && enabled == true) {
			modeFixed(true);
		}
		else if (varMig.isSelected() && enabled == true){
			modeFixed(false);
		}
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		MigrationPane test = new MigrationPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
	}
}
