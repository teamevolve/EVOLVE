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
		mutAtoBLabel, mutBtoALabel,
		mutAtoCLabel, mutCtoALabel,
		mutBtoCLabel, mutCtoBLabel;
	
	JTextField mutAtoB, mutBtoA,
		mutAtoC, mutCtoA,
		mutBtoC, mutCtoB;
	
	public MutationPane(){
		
		super();
		
		// Mutation (0 to 1)
		mutLabel = new JLabel("Mutation (0 to 1):");
		mutAtoBLabel = new JLabel("A to B:");
		mutBtoALabel = new JLabel("B to A:");
		mutAtoCLabel = new JLabel("A to C:"); threeAllelesList.add(mutAtoCLabel);
		mutCtoALabel = new JLabel("C to A:"); threeAllelesList.add(mutCtoALabel);
		mutBtoCLabel = new JLabel("B to C:"); threeAllelesList.add(mutBtoCLabel);
		mutCtoBLabel = new JLabel("C to B:"); threeAllelesList.add(mutCtoBLabel);
		mutAtoB = new JTextField(TEXT_LEN_LONG);
		mutBtoA = new JTextField(TEXT_LEN_LONG);
		mutAtoC = new JTextField(TEXT_LEN_LONG); threeAllelesList.add(mutAtoC);
		mutCtoA = new JTextField(TEXT_LEN_LONG); threeAllelesList.add(mutCtoA);
		mutBtoC = new JTextField(TEXT_LEN_LONG); threeAllelesList.add(mutBtoC);
		mutCtoB = new JTextField(TEXT_LEN_LONG); threeAllelesList.add(mutCtoB);

		// Set input verifiers
		
		mutAtoB.setName(RATE); mutAtoB.setInputVerifier(iv);
		mutBtoA.setName(RATE); mutBtoA.setInputVerifier(iv);
		mutAtoC.setName(RATE); mutBtoA.setInputVerifier(iv);
		mutCtoA.setName(RATE); mutBtoA.setInputVerifier(iv);
		mutBtoC.setName(RATE); mutBtoA.setInputVerifier(iv);
		mutCtoB.setName(RATE); mutBtoA.setInputVerifier(iv);
		
		c.gridx = 0; c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		add(mutLabel, c);
		
		// add label then field for each mutation possibility
		c.gridx = 1; c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		add(mutAtoBLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		add(mutAtoB, c);
	
		c.gridx = 2;
		c.anchor = GridBagConstraints.WEST;
		add(mutBtoALabel, c);
		c.anchor = GridBagConstraints.CENTER;
		add(mutBtoA, c);
		
		c.gridx = 3;
		c.anchor = GridBagConstraints.WEST;
		add(mutAtoCLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		add(mutAtoC, c);
		
		c.gridx = 4;
		c.anchor = GridBagConstraints.WEST;
		add(mutCtoALabel, c);
		c.anchor = GridBagConstraints.CENTER;
		add(mutCtoA, c);
		
		c.gridx = 5;
		c.anchor = GridBagConstraints.WEST;
		add(mutBtoCLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		add(mutBtoC, c);
		
		c.gridx = 6;
		c.anchor = GridBagConstraints.WEST;
		add(mutCtoBLabel, c);
		c.anchor = GridBagConstraints.CENTER;
		add(mutCtoB, c);
		
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
		double mutAA_AA = mutA_A * mutA_A;
		double mutBB_BB = mutB_B * mutB_B;
		double mutAB_AB = mutA_A * mutB_B + mutA_B * mutB_A;

		
		p.setMutationRate(Genotype.AA, Genotype.AB, mutAA_AB);
		p.setMutationRate(Genotype.AA, Genotype.BB, mutAA_BB);
		p.setMutationRate(Genotype.AB, Genotype.AA, mutAB_AA);
		p.setMutationRate(Genotype.AB, Genotype.BB, mutAB_BB);
		p.setMutationRate(Genotype.BB, Genotype.AA, mutBB_AA);
		p.setMutationRate(Genotype.BB, Genotype.AB, mutBB_AB);
		p.setMutationRate(Genotype.AA, Genotype.AA, mutAA_AA);
		p.setMutationRate(Genotype.AB, Genotype.AB, mutAB_AB);
		p.setMutationRate(Genotype.BB, Genotype.BB, mutBB_BB);
		
	}
	
	public static void main(String[] args){
		JFrame window = new JFrame();
		
		MutationPane test = new MutationPane();
		
		window.add(test);
		window.pack();
		window.setVisible(true);
		
	}

}
