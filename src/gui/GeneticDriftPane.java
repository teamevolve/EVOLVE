package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.util.ArrayList;

/**
 * 
 * @author jasonfortunato
 * @author linneasahlberg
 * 
 * A pane in the GUI that allows the user to enter information about a constant or varying population.
 * 
 */

public class GeneticDriftPane extends EvoPane{
	JLabel genDriftLabel;			// Population constant
//	ButtonGroup popConstGroup;
//	JRadioButton popConstTrue;
//	JRadioButton popConstFalse;
	JLabel initPopLabel;  			// Initial population
	JTextField initPop;
	JLabel carryCapLabel;			// Carrying Capacity
	JTextField carryCap;
	JLabel postCrashLabel; 			// Crash
	JTextField postCrash;

	ArrayList<Component> vPopList = new ArrayList<Component>();
	ArrayList<Component> cPopList = new ArrayList<Component>();
	
	public GeneticDriftPane() {

		super();
		color2List.add(getParent());
		
		/* pop const stuff *****************************************************************************/
		genDriftLabel = new JLabel("<html><span style='font-size:11px'><b>Genetic Drift: </span>");
		
//		// pop const and pop varying radio buttons
//		popConstGroup = new ButtonGroup();
//		popConstTrue = new JRadioButton("<html><b>Constant");
//		popConstFalse = new JRadioButton("<html><b>Varying: ", true);
//		popConstGroup.add(popConstTrue);
//		popConstGroup.add(popConstFalse);
//		
//		// add radio buttons to pane

//		c.insets = new Insets(0, 20, 0, 0);
//		c.gridx = 0; c.gridy++;
//		add(popConstFalse, c);
//		c.gridx = 0; c.gridy++;
//		add(popConstTrue, c);	

		// carrying capacity stuff - appears when popSize varying
		carryCapLabel = new JLabel("Carrying Capacity: "); 
		carryCap = new JTextField(TEXT_LEN_LONG);
		carryCap.setName(INT); carryCap.setInputVerifier(iv);

		// post crash population size stuff - appears when popSize varying
		postCrashLabel = new JLabel("Post Crash Population Size: ");
		postCrash = new JTextField(TEXT_LEN_LONG);
		postCrash.setName(INT); postCrash.setInputVerifier(iv);

		// add carrying capacity and post crash stuff to separate panel for formatting
		JPanel fields = new JPanel();
		fields.setBackground(color2);
		fields.setLayout(new FlowLayout());
		fields.add(carryCapLabel);
		fields.add(carryCap);
		fields.add(postCrashLabel);
		fields.add(postCrash);
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(genDriftLabel, c);
		
		// add new panel to the pane
		c.gridx = 1; c.gridy = 1;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		add(fields, c);
		
		// add all that will be disabled when PopSize is constant to the vPopList
		vPopList.add(carryCapLabel);
		vPopList.add(carryCap);
		vPopList.add(postCrashLabel);
		vPopList.add(postCrash);

		// add all that will be disabled when PopSize is varying to the cPopList
		cPopList.add(initPopLabel);
		cPopList.add(initPop);
//		modeConstPop(false);
		
		// set actions for the PopConstTrue radio button
//		popConstTrue.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				modeConstPop(true);
//			}
//		});
//		
//		// set actions for the PopConstFalse radio button
//		popConstFalse.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent e) {
//				modeConstPop(false);
//			}
//		});
	}
	
//	// for toggling between pop const and pop varying settings
//	private void modeConstPop(boolean b){
//		for(Component comp : vPopList) {
//			comp.setEnabled(!b);
//		}
//
//		if(b == true) { // clear the two text fields
//			carryCap.setText("");
//			postCrash.setText("");
//		}
//	}

	public void submit(shared.SessionParameters p) {
//		p.setPopConst(popConstTrue.isSelected());
//		if(popConstFalse.isSelected()){
		p.setPopCapacity(Integer.parseInt(carryCap.getText()));
		p.setCrashCapacity(Integer.parseInt(postCrash.getText()));
//		}
	}		
}
