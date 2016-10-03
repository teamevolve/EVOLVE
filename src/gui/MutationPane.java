package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import shared.Allele;
import shared.Genotype;


/**
 * 
 * @author jasonfortunato
 * @author linneasahlberg
 */
public class MutationPane extends EvoPane {

	JLabel mutLabel,					// Mutation (0 to 1)
		mutAtoBLabel, mutBtoALabel;
	JTextField mutAtoB, mutBtoA;
	
	public MutationPane(){
		
		super();
		
		// Mutation (0 to 1)
		mutLabel = new JLabel("Mutation (0 to 1): ");
		mutAtoBLabel = new JLabel("A to B: ");
		mutBtoALabel = new JLabel("B to A: ");
		mutAtoB = new JTextField(TEXT_LEN_SHORT);
		mutBtoA = new JTextField(TEXT_LEN_SHORT);
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(mutLabel, c);
		
		// add label then field x3
		c.gridx = 1; c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		add(mutAtoBLabel, c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		add(mutAtoB, c);
	
		c.gridx = 2;
		c.anchor = GridBagConstraints.CENTER;
		add(mutBtoALabel, c);
		c.gridx = 2;
		c.anchor = GridBagConstraints.EAST;
		add(mutBtoA, c);
		
	}

	public void submit(shared.SessionParameters p) {
		double mutA_B = Double.parseDouble(mutAtoB.getText());
		double mutB_A = Double.parseDouble(mutBtoA.getText());
		double mutA_A = 1 - mutA_B;
		double mutB_B = 1 - mutB_A;
		
		p.setAlleleMutationRate(Allele.A, Allele.B, mutA_B);
		p.setAlleleMutationRate(Allele.B, Allele.A, mutB_A);

		double mutAA_AB = 2 * mutA_B * mutA_A;
		double mutAA_BB = mutA_B * mutA_B;
		double mutAB_AA = mutB_A * mutA_A;
		double mutAB_BB = mutA_B * mutB_B;
		double mutBB_AA = mutB_A * mutB_A;
		double mutBB_AB = 2 * mutB_A * mutB_B;
		
		p.setMutationRate(Genotype.AA, Genotype.AB, mutAA_AB);
		p.setMutationRate(Genotype.AA, Genotype.BB, mutAA_BB);
		p.setMutationRate(Genotype.AB, Genotype.AA, mutAB_AA);
		p.setMutationRate(Genotype.AB, Genotype.BB, mutAB_BB);
		p.setMutationRate(Genotype.BB, Genotype.AA, mutBB_AA);
		p.setMutationRate(Genotype.BB, Genotype.AB, mutBB_AB);
		
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		MutationPane test = new MutationPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
		
	}

}
