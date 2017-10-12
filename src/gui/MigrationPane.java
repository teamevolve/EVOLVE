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

import shared.Genotype;

/**
 *
 * @author linneasahlberg
 * @author jasonfortunato
 * @author alexdennis
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
	EvoTextField fixedMigRate;
	JLabel varMigRateLabel;
	JLabel varMigRateAALabel, varMigRateABLabel, varMigRateBBLabel,
		varMigRateACLabel, varMigRateBCLabel, varMigRateCCLabel;
	EvoTextField varMigRateAA, varMigRateAB, varMigRateBB,
		varMigRateAC, varMigRateBC, varMigRateCC;

	ArrayList<Component> fixedList = new ArrayList<Component>();
	ArrayList<Component> varyList = new ArrayList<Component>();

	ArrayList<Component> labels = new ArrayList<Component>();
	ArrayList<Component> fields = new ArrayList<Component>();

	JPanel table;

	public MigrationPane() {
		super();
		color1List.add(getParent());
		// Migration radio buttons
		migLabel = new JLabel("<html><span style='font-size:11px'><b>Migration</b> (Gene Flow): </span>");
		migGroup = new ButtonGroup();
		fixedMig = new JRadioButton("Genotypes Equal (0.0-0.9): ", true);
		varMig = new JRadioButton("Genotypes Vary: ");
		migGroup.add(fixedMig);
		migGroup.add(varMig);


		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(migLabel, c);
		c.insets = new Insets(0, 20, 0, 0);
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth=3;
		add(fixedMig, c);
		c.gridwidth=1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		add(varMig, c);

		// Migration rate - if fixed
		fixedMigRateLabel = new JLabel("Migration Rate (0.0-0.9): ");
		fixedMigRate = new EvoTextField(TEXT_LEN_SHORT);

		fixedMigRate.setName(RATE); fixedMigRate.setInputVerifier(iv);

		//c.gridx = 1; c.gridy = 2;
		//c.anchor = GridBagConstraints.WEST;
		//add(fixedMigRateLabel, c);
		c.gridx = 1; c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		add(fixedMigRate, c);

		// Migration Rates by genotype - if varies
		varMigRateLabel = new JLabel("Migration Rate (by genotype): ");
		varMigRateAALabel = new JLabel("AA");
		varMigRateABLabel = new JLabel("AB");
		varMigRateBBLabel = new JLabel("BB");
		varMigRateACLabel = new JLabel("AC"); threeAllelesList.add(varMigRateACLabel);
		varMigRateBCLabel = new JLabel("BC"); threeAllelesList.add(varMigRateBCLabel);
		varMigRateCCLabel = new JLabel("CC"); threeAllelesList.add(varMigRateCCLabel);
		varMigRateAA = new EvoTextField(TEXT_LEN_SHORT);
		varMigRateAB = new EvoTextField(TEXT_LEN_SHORT);
		varMigRateBB = new EvoTextField(TEXT_LEN_SHORT);
		varMigRateAC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(varMigRateAC);
		varMigRateBC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(varMigRateBC);
		varMigRateCC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(varMigRateCC);

		varMigRateAA.setName(RATE); varMigRateAA.setInputVerifier(iv);
		varMigRateAB.setName(RATE); varMigRateAB.setInputVerifier(iv);
		varMigRateBB.setName(RATE); varMigRateBB.setInputVerifier(iv);

		table = new JPanel();
		table.setBackground(color1);
		table.setLayout(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints();
		//t.insets = new Insets(0, 0, 3, 15);

		addToLists();
		t.gridy = 1;
		int i = 0;
		for (Component comp : labels) {
			t.gridx = i; i++;
			table.add(comp, t);
		}

		t.gridy = 2;
		i = 0;
		for (Component comp : fields) {
			t.gridx = i; i++;
			table.add(comp, t);
		}
		c.gridheight = 10;
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

			if (threeAlleles) {
				p.setMigrationRate(Genotype.AC, Double.parseDouble(varMigRateAC.getText()));
				p.setMigrationRate(Genotype.BC, Double.parseDouble(varMigRateBC.getText()));
				p.setMigrationRate(Genotype.CC, Double.parseDouble(varMigRateCC.getText()));
			}
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
		for(Component c : table.getComponents()) {
			c.setEnabled(enabled);
		}
		if (fixedMig.isSelected() && enabled == true) {
			modeFixed(true);
		}
		else if (varMig.isSelected() && enabled == true){
			modeFixed(false);
		}
	}
}
