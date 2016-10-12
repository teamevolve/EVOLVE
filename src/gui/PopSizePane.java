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

	JLabel popSizeLabel;			// Population size
	JTextField popSizeField;
	JLabel popConstLabel;			// Population constant
	ButtonGroup popConstGroup;
	JRadioButton popConstTrue;
	JRadioButton popConstFalse;
	JLabel initPopLabel;  			// Initial population
	JTextField initPop;
	JLabel carryCapLabel;			// Carrying Capacity
	JTextField carryCap;
	JLabel postCrashLabel; 			// Crash
	JTextField postCrash;

	ArrayList<Component> vPopList = new ArrayList<Component>();
	
	public PopSizePane() {

		super();
		
		popSizeLabel = new JLabel("Initial Population Size: ");
		popSizeField = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 0; c.gridy = 10;
		c.anchor = GridBagConstraints.WEST;
		add(popSizeLabel, c);
		c.gridx = 1; c.gridy = 10;
		add(popSizeField, c);
		
		// population constant radio button stuff
		popConstLabel = new JLabel("Population Size is: ");
		popConstGroup = new ButtonGroup();
		popConstTrue = new JRadioButton("Constant");
		popConstFalse = new JRadioButton("Varying");
		popConstGroup.add(popConstTrue);
		popConstGroup.add(popConstFalse);
		
		c.gridx = 0; c.gridy = 20;
		add(popConstLabel, c);
		c.gridx = 1; c.gridy = 20;
		add(popConstTrue, c);
		c.gridx = 2; c.gridy = 20;
		add(popConstFalse, c);

		
		
		// carrying capacity stuff - appears when popSize varying
		carryCapLabel = new JLabel("Carrying Capacity: "); 
		carryCap = new JTextField(TEXT_LEN_LONG);
		c.gridx = 1; c.gridy = 40;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(carryCapLabel, c);
		c.gridx = 2; c.gridy = 40;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		//c.ipadx = -55;
		add(carryCap, c);
		c.ipadx = 0;
		

		
		// post crash population size stuff - appears when popSize varying
		postCrashLabel = new JLabel("Post Crash Population Size: ");
		postCrash = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 1; c.gridy = 50;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		add(postCrashLabel, c);
		c.gridx = 2; c.gridy = 50;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		//c.ipadx = -55;
		add(postCrash, c);
		c.ipadx = 0;
		
		// Add all that will be disabled when PopSize is constant to the vPopList
		vPopList.add(carryCapLabel);
		vPopList.add(carryCap);
		vPopList.add(postCrashLabel);
		vPopList.add(postCrash);
		
		// Set actions for the PopConstTrue/False radio buttons
		popConstTrue.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeConstPop(true);
			}
		});
		
		popConstFalse.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				modeConstPop(false);
			}
		});
		
		}
	
		private void modeConstPop(boolean b){
			for(Component comp : vPopList) {
				comp.setEnabled(!b);
			}
			if(b == true) { // clear the two text fields
				carryCap.setText("");
				postCrash.setText("");
			}
		}

		public void submit(shared.SessionParameters p) {
			
			p.setPopSize(Integer.parseInt(popSizeField.getText()));
			p.setPopConst(popConstTrue.isSelected());
			if(popConstFalse.isSelected()){
				p.setPopCapacity(Integer.parseInt(carryCap.getText()));
				p.setCrashCapacity(Integer.parseInt(postCrash.getText()));
			}
		}
		
		/** 
		 * little testerino guy, dont use this, maybe remove this
		 * @param args
		 */
		public static void main(String[] args){
			JFrame window = new JFrame();
			
			PopSizePane test = new PopSizePane();
			
			window.add(test);
			window.pack();
			window.setVisible(true);
			
			
		}
	
	
}
