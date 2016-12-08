package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
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
 * The superclass for the evolutionary forces panes. Allows for three allele mode. 
 * Sets the layout and standardizes column widths.
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
	public GridBagConstraints c;
	
	public OurInputVerifier iv = new OurInputVerifier();
	
	private boolean enabled;
	
	public ArrayList<Component> threeAllelesList = new ArrayList<Component>();
	public ArrayList<Component> color1List = new ArrayList<Component>();
	public ArrayList<Component> color2List = new ArrayList<Component>();
	public Color color1 = new Color(183, 210, 222);
	public Color color2 = new Color(213, 218, 226);//(173, 208, 212);
	
	EvoPane() {
		super();
		// initialize default settings
		threeAlleles = false;
		enabled = true;
		
		// set layout
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();

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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Component comp : this.color1List) {	
			g.setColor(color2);
			g.fillRect(0, 0, getWidth(), 15);
			g.setColor(color1);
			g.fillRect(0, 15, getWidth(), getHeight());
		}
		for(Component comp : this.color2List) {	
			g.setColor(color1);
			g.fillRect(0, 0, getWidth(), 15);
			g.setColor(color2);
			g.fillRect(0, 15, getWidth(), getHeight());
		}
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
