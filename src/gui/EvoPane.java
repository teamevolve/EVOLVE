package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author jasonfortunato
 * @author linneasahlberg
 * @author alexdennis
 *
 * The superclass for the evolutionary forces panes. Allows for three allele mode.
 * Sets the layout and standardizes column widths.
 */

public abstract class EvoPane extends JPanel {
	final static int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 4;
	final static String INT = "int";
	final static String ANY_DOUBLE = "double";
	final static String RATE = "rate";
	final static String ANY_DOUBLE_ZERO_TO_TEN = "double zero to ten";
	final static String ANY_NUMBER = "any number";
  final static public Color COLOR1 = new Color(213, 218, 226);
	final static public Color COLOR2 = new Color(183, 210, 222);

	public boolean threeAlleles;

	public OurInputVerifier iv = new OurInputVerifier();

	private boolean enabled;

	public ArrayList<Component> threeAllelesList = new ArrayList<Component>();

	EvoPane() {
		super();
		// initialize default settings
		threeAlleles = false;
		enabled = true;

    setBorder(new EmptyBorder(10, 10, 10, 10));
    setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		for(Component component : getComponents()) {
			component.setEnabled(enable);
		}
		enabled = !enabled;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void modeThreeAlleles(boolean b){
		threeAlleles = b;
		for(Component comp : this.threeAllelesList) {
			comp.setVisible(b);
		}
	}
}
