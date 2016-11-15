package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author jasonfortunato
 * @author linneasahlberg
 *
 * This is the superclass for the evolutionary forces panes
 */

public abstract class EvoPane extends JPanel {

	final static int TEXT_LEN_EXTRA_LONG = 48;
	final static int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 4;
	final static String INT = "int";
	final static String ANY_DOUBLE = "double";
	final static String RATE = "rate";
	final static String ANY_DOUBLE_ZERO_TO_TEN = "double zero to ten";
	final static String ANY_NUMBER = "any number";
	
	public boolean threeAlleles;
	
	public Font boldFont;
	
	public GridBagConstraints c;
	
	public OurInputVerifier iv = new OurInputVerifier();
	
	private boolean enabled;
	private boolean helpMode;
	
	public ArrayList<Component> threeAllelesList = new ArrayList<Component>();
	
	EvoPane() {
		super();
		JLabel dummy = new JLabel("we're getting the default font");
		Font font = dummy.getFont();
		boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
		
		threeAlleles = false;
		enabled = true;
		setHelpMode(false);
		
		// set layout
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();

		// add spacing
	//	c.insets = new Insets(1, 10, 0, 0);

		// standardize column widths
		for(int i = 0; i < 5; i++) {
			c.gridx = i; c.gridy = 0;
			c.anchor = GridBagConstraints.WEST;
			add(new JLabel("_______________________________"), c);
		}
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


	public boolean isHelpMode() {
		return helpMode;
	}


	public void setHelpMode(boolean helpMode) {
		this.helpMode = helpMode;
	}
	
	
	public void modeThreeAlleles(boolean b){
		threeAlleles = b;
		for(Component comp : this.threeAllelesList) {
			comp.setVisible(b);
		}
	}

	
	// Each pane must implement this
//	public abstract void flavorTextMode(boolean enabled);
	
}
