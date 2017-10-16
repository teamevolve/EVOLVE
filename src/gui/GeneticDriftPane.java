package gui;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.Box;

/**
 *
 * @author jasonfortunato
 * @author linneasahlberg
 * @author alexdennis
 *
 * A pane in the GUI that allows the user to enter information about a constant or varying population.
 *
 */

public class GeneticDriftPane extends EvoPane{
	JLabel genDriftLabel;			// Population constant
	JLabel carryCapLabel;			// Carrying Capacity
	EvoTextField carryCap;
	JLabel postCrashLabel; 			// Crash
	EvoTextField postCrash;

	public GeneticDriftPane() {
		super();

    // set layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    setBackground(COLOR1);

		/* pop const stuff *****************************************************************************/
		genDriftLabel = new JLabel("<html><span style='font-size:11px'><b>Genetic Drift</b> (Population Size): </span>");
    genDriftLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		// carrying capacity stuff - appears when popSize varying
		carryCapLabel = new JLabel("Carrying Capacity:");
		carryCap = new EvoTextField(TEXT_LEN_LONG);
		carryCap.setName(INT); carryCap.setInputVerifier(iv);

		// post crash population size stuff - appears when popSize varying
		postCrashLabel = new JLabel("Post Crash Population Size:");
		postCrash = new EvoTextField(TEXT_LEN_LONG);
		postCrash.setName(INT); postCrash.setInputVerifier(iv);

		// add carrying capacity and post crash stuff to separate panel for formatting
		JPanel fields = new JPanel();
		fields.setBackground(getBackground());
		fields.setLayout(new FlowLayout(FlowLayout.LEADING));
    fields.setAlignmentX(Component.LEFT_ALIGNMENT);
		fields.add(carryCapLabel);
		fields.add(carryCap);
    fields.add(Box.createHorizontalStrut(20));
		fields.add(postCrashLabel);
		fields.add(postCrash);

		add(genDriftLabel);
		add(fields);
	}

	public void submit(shared.SessionParameters p) {
		p.setPopCapacity(Integer.parseInt(carryCap.getText()));
		p.setCrashCapacity(Integer.parseInt(postCrash.getText()));
	}
}
