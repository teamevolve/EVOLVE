package gui;

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
 */

public class GeneticDriftPane extends EvoPane{
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
	ArrayList<Component> cPopList = new ArrayList<Component>();
	
	public GeneticDriftPane() {

		super();
		
		// population constant radio button stuff
		popConstLabel = new JLabel("<html><span style='font-size:11px'><b>Genetic Drift: </span>");
		popConstGroup = new ButtonGroup();
		popConstTrue = new JRadioButton("<html><b>Constant");
		popConstFalse = new JRadioButton("<html><b>Varying: ", true);
		popConstGroup.add(popConstTrue);
		popConstGroup.add(popConstFalse);
		
		
		c.gridx = 0; c.gridy = 20;
		add(popConstLabel, c);
		
		c.insets = new Insets(0, 20, 0, 0);
		c.gridx = 0; c.gridy++;
		add(popConstFalse, c);
		c.gridx = 0; c.gridy++;
		add(popConstTrue, c);	

		// carrying capacity stuff - appears when popSize varying
		carryCapLabel = new JLabel("Carrying Capacity: "); 
		carryCap = new JTextField(TEXT_LEN_LONG);

		carryCap.setName(INT); carryCap.setInputVerifier(iv);

		// post crash population size stuff - appears when popSize varying
		postCrashLabel = new JLabel("Post Crash Population Size: ");
		postCrash = new JTextField(TEXT_LEN_LONG);
		
		postCrash.setName(INT); postCrash.setInputVerifier(iv);

		JPanel fields = new JPanel();
		fields.setLayout(new FlowLayout());
		fields.add(carryCapLabel);
		fields.add(carryCap);
		fields.add(postCrashLabel);
		fields.add(postCrash);
		
		c.gridx = 1; c.gridy = 21;
		c.gridwidth = 3;
		add(fields, c);
		
		// Add all that will be disabled when PopSize is constant to the vPopList
		vPopList.add(carryCapLabel);
		vPopList.add(carryCap);
		vPopList.add(postCrashLabel);
		vPopList.add(postCrash);
		
		c.gridx = 1; c.gridy = 22;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		cPopList.add(initPopLabel);
		cPopList.add(initPop);
		modeConstPop(false);
		
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
			
			//for(Component comp : cPopList) {
				//comp.setEnabled(b);
			//}
			
			if(b == true) { // clear the two text fields
				carryCap.setText("");
				postCrash.setText("");
			}
			//else
				//initPop.setText("");
		}

		public void submit(shared.SessionParameters p) {
			
			//p.setPopSize(Integer.parseInt(popSizeField.getText()));
			p.setPopConst(popConstTrue.isSelected());
			if(popConstFalse.isSelected()){
				p.setPopCapacity(Integer.parseInt(carryCap.getText()));
				p.setCrashCapacity(Integer.parseInt(postCrash.getText()));
			}
		}	
	
	}
