package gui;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class TitlePane extends EvoPane {
	JLabel titleLabel; JTextField title;
	JLabel questionLabel; JTextField question;
	JLabel experLabel; JTextField exper;
	JLabel resultsLabel; JTextField results;
	JLabel discussionLabel; JTextField discussion;

	
	public TitlePane() {
		super();
		titleLabel = new JLabel("Title:");
		title = new JTextField(TEXT_LEN_LONG);
		questionLabel = new JLabel("Question:"); 
		question = new JTextField(TEXT_LEN_LONG);
		experLabel = new JLabel("Experimental Design:"); 
		exper = new JTextField(TEXT_LEN_LONG);
		resultsLabel = new JLabel("Results:");
		results = new JTextField(TEXT_LEN_LONG);
		discussionLabel = new JLabel("Discussion:");
		discussion = new JTextField(TEXT_LEN_LONG);
		
		c.gridx = 0; c.gridy = 1;
		add(titleLabel, c);
		c.gridx = 1; c.gridy = 1;
		add(title, c);
		
		c.gridx = 0; c.gridy = 2;
		add(questionLabel, c);
		c.gridx = 1; c.gridy = 2;
		add(question, c);
		
		c.gridx = 0; c.gridy = 3;
		add(experLabel, c);
		c.gridx = 1; c.gridy = 3;
		add(exper, c);
		
		c.gridx = 0; c.gridy = 4;
		add(resultsLabel, c);
		c.gridx = 1; c.gridy = 4;
		add(results, c);
		
		c.gridx = 0; c.gridy = 5;
		add(discussionLabel, c);
		c.gridx = 1; c.gridy = 5;
		add(discussion, c);
	}
}
