package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
 * @author linneasahlberg
 * @author jasonfortunato
 *
 */

public class MigrationPane extends EvoPane {
	
	/**
	 * auto gen'd
	 */
	private static final long serialVersionUID = 7447657572452485224L;

	JLabel migLabel;
	ButtonGroup migGroup;
	JRadioButton fixedMig, varMig;
	JLabel fixedMigRateLabel;
	JTextField fixedMigRate;
	JLabel varMigRateLabel;
	JLabel varMigRateAALabel, varMigRateABLabel, varMigRateBBLabel,
		varMigRateACLabel, varMigRateBCLabel, varMigRateCCLabel;
	JTextField varMigRateAA, varMigRateAB, varMigRateBB,
		varMigRateAC, varMigRateBC, varMigRateCC;
	
	ArrayList<Component> fixedList = new ArrayList<Component>();
	ArrayList<Component> varyList = new ArrayList<Component>();
	
	ArrayList<Component> labels = new ArrayList<Component>();
	ArrayList<Component> fields = new ArrayList<Component>();
	
	public MigrationPane() {
		
		// Migration radio buttons
		migLabel = new JLabel("Migration: ");
		migGroup = new ButtonGroup();
		fixedMig = new JRadioButton("Universal Proportion", true);
		varMig = new JRadioButton("Varies by Genotype");
		migGroup.add(fixedMig);
		migGroup.add(varMig);
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(migLabel, c);
		c.gridx = 1; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(fixedMig, c);
		c.gridx = 2; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(varMig, c);
				
		// Migration rate - if fixed
		fixedMigRateLabel = new JLabel("Migration Rate: ");
		fixedMigRate = new JTextField(TEXT_LEN_SHORT);
		
		fixedMigRate.setName(RATE); fixedMigRate.setInputVerifier(iv);
		
		c.gridx = 1; c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		add(fixedMigRateLabel, c);
		c.gridx = 1; c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		add(fixedMigRate, c);
			
		// Migration Rates by genotype - if varies
		varMigRateLabel = new JLabel("Migration Rate (by genotype): ");
		varMigRateAALabel = new JLabel("AA");
		varMigRateABLabel = new JLabel("AB");
		varMigRateBBLabel = new JLabel("BB");
		varMigRateACLabel = new JLabel("AC"); threeAllelesList.add(varMigRateACLabel);
		varMigRateBCLabel = new JLabel("BC"); threeAllelesList.add(varMigRateBCLabel);
		varMigRateCCLabel = new JLabel("CC"); threeAllelesList.add(varMigRateCCLabel);
		varMigRateAA = new JTextField(TEXT_LEN_SHORT);
		varMigRateAB = new JTextField(TEXT_LEN_SHORT);
		varMigRateBB = new JTextField(TEXT_LEN_SHORT);
		varMigRateAC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(varMigRateAC);
		varMigRateBC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(varMigRateBC);
		varMigRateCC = new JTextField(TEXT_LEN_SHORT); threeAllelesList.add(varMigRateCC);

		varMigRateAA.setName(RATE); varMigRateAA.setInputVerifier(iv);
		varMigRateAB.setName(RATE); varMigRateAB.setInputVerifier(iv);
		varMigRateBB.setName(RATE); varMigRateBB.setInputVerifier(iv);
		
		JPanel table = new JPanel();
		table.setLayout(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints();
		t.insets = new Insets(0, 0, 3, 15);
		
		addToLists();
		
		int i = 0;
		for (Component comp : labels) {
			t.gridx = i; i++;
			table.add(comp, t);
		}
		
		t.gridy = 1;
		i = 0;
		for (Component comp : fields) {
			t.gridx = i; i++;
			table.add(comp, t);
		}
		
		c.gridx = 1; c.gridy = 3;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(table, c);

		// grey out all the elements-- prevents submit w.o radio button selected
		for(Component c : fixedList) {
			c.setEnabled(true);
		}
		for(Component c : varyList) {
			c.setEnabled(false);
		}
		
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

	public void submit(shared.SessionParameters p){
		p.setFixedMig(fixedMig.isSelected());
		
		if(fixedMig.isSelected()){
			for(shared.Genotype gt : shared.Genotype.values()) {
				p.setMigrationRate(gt,Double.parseDouble(fixedMigRate.getText()));
			}
		}
		else {//varMig.isSelected()) 
			p.setMigrationRate(Genotype.AA, Double.parseDouble(varMigRateAA.getText()));
			p.setMigrationRate(Genotype.AB, Double.parseDouble(varMigRateAB.getText()));
			p.setMigrationRate(Genotype.BB, Double.parseDouble(varMigRateBB.getText()));
		}	
	
	}
	
	private void addToLists() {
		
		//add fixed elements to fixedList
		fixedList.add(fixedMigRateLabel);
		fixedList.add(fixedMigRate);
		
		//add vary-by-genotype to varyList
		varyList.add(varMigRateLabel);
		varyList.add(varMigRateAALabel);
		varyList.add(varMigRateABLabel);
		varyList.add(varMigRateBBLabel);
		varyList.add(varMigRateACLabel);
		varyList.add(varMigRateBCLabel);
		varyList.add(varMigRateCCLabel);
		
		varyList.add(varMigRateAA);
		varyList.add(varMigRateAB);
		varyList.add(varMigRateBB);
		varyList.add(varMigRateAC);
		varyList.add(varMigRateBC);
		varyList.add(varMigRateCC);
		
		// panel of migration labels and rates
		labels.add(varMigRateAALabel); 
		labels.add(varMigRateABLabel); 
		labels.add(varMigRateBBLabel);
		labels.add(varMigRateACLabel);
		labels.add(varMigRateBCLabel);
		labels.add(varMigRateCCLabel);
		
		fields.add(varMigRateAA);
		fields.add(varMigRateAB);
		fields.add(varMigRateBB);
		fields.add(varMigRateAC);
		fields.add(varMigRateBC);
		fields.add(varMigRateCC);
		
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
