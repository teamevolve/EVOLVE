package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.Box;

import shared.Genotype;

/**
 *
 * @author linneasahlberg
 * @author jasonfortunato
 * @author alexdennis
 *
 */

public class MigrationPane extends EvoMainPanel {

	/**
	 * auto gen'd
	 */
	private static final long serialVersionUID = 7447657572452485224L;

  JButton help;
	JLabel migLabel;
	ButtonGroup migGroup;
	JRadioButton fixedMig, varMig;
	EvoTextField fixedMigRate;
	JLabel varMigRateAALabel, varMigRateABLabel, varMigRateBBLabel,
		varMigRateACLabel, varMigRateBCLabel, varMigRateCCLabel;
	EvoTextField varMigRateAA, varMigRateAB, varMigRateBB,
		varMigRateAC, varMigRateBC, varMigRateCC;

	ArrayList<Component> fixedList = new ArrayList<Component>();
	ArrayList<Component> varyList = new ArrayList<Component>();

	ArrayList<Component> labels = new ArrayList<Component>();
	ArrayList<Component> fields = new ArrayList<Component>();

	public MigrationPane() {
		super();

    // set layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
    c.anchor = GridBagConstraints.LINE_START;

    setBackground(COLOR2);

		// Migration radio buttons
		migLabel = new JLabel("<html><span style='font-size:11px'><b>Migration</b> (Gene Flow): </span>");
    help = new JButton("Help");
		migGroup = new ButtonGroup();
		fixedMig = new JRadioButton("Genotypes Equal: (0.0-0.9)", true);
		varMig = new JRadioButton("Genotypes Vary: ");
		migGroup.add(fixedMig);
		migGroup.add(varMig);


		c.gridx = 0; c.gridy = 0;
    c.gridwidth = 2;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
		add(migLabel, c);
    c.gridwidth = 1;
    c.weightx = 0.0;
    c.fill = GridBagConstraints.NONE;
    c.gridx = 2;
    c.anchor = GridBagConstraints.LINE_END;
    add(help, c);
    c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0; c.gridy++;
		add(fixedMig, c);
		c.gridy++;
		add(varMig, c);

    // Migration rate - if fixed
		fixedMigRate = new EvoTextField(TEXT_LEN_SHORT);

		c.gridx = 1; c.gridy = 1;
		add(fixedMigRate, c);

		// Migration Rates by genotype - if varies
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

		EvoPanel table = new EvoPanel();
		table.setBackground(getBackground());
		table.setLayout(new GridBagLayout());
		GridBagConstraints t = new GridBagConstraints();

		addToLists();
		t.gridy = 0;
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

    c.gridx = 1; c.gridy = 2;
		c.gridheight = 2;
		add(table, c);
    c.gridheight = 1;

    c.gridx = 2; c.gridy = 1;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
    add(Box.createHorizontalGlue());

		// grey out all the elements-- prevents submit w.o radio button selected
		for(Component comp : fixedList) {
			comp.setEnabled(true);
		}
		for(Component comp : varyList) {
			comp.setEnabled(false);
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

    help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openHelp("/help/Help07_Migration.pdf");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
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
		fixedList.add(fixedMigRate);

		//add vary-by-genotype to varyList
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
}
