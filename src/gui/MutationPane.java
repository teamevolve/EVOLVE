package gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;

import shared.Allele;
import shared.Genotype;


/**
 *
 * @author jasonfortunato
 * @author linneasahlberg
 * @author alexdennis
 *
 */
public class MutationPane extends EvoMainPanel {
  JButton help;

	JLabel mutLabel,					// Mutation (0 to 1)
		mutAtoBLabel, mutBtoALabel,
		mutAtoCLabel, mutCtoALabel,
		mutBtoCLabel, mutCtoBLabel;

	EvoTextField mutAtoB, mutBtoA,
		mutAtoC, mutCtoA,
		mutBtoC, mutCtoB;

	public MutationPane(){
		super();

    // set layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    setBackground(COLOR1);

		// Mutation (0 to 1)
		mutLabel = new JLabel("<html><span style='font-size:11px'><b>Mutation: </b></span>(0.0-0.01)");
    help = new JButton("Help");

		mutAtoBLabel = new JLabel("A to B:");
		mutBtoALabel = new JLabel("B to A:");
		mutAtoCLabel = new JLabel("A to C:"); threeAllelesList.add(mutAtoCLabel);
		mutCtoALabel = new JLabel("C to A:"); threeAllelesList.add(mutCtoALabel);
		mutBtoCLabel = new JLabel("B to C:"); threeAllelesList.add(mutBtoCLabel);
		mutCtoBLabel = new JLabel("C to B:"); threeAllelesList.add(mutCtoBLabel);
		mutAtoB = new EvoTextField(TEXT_LEN_SHORT);
		mutBtoA = new EvoTextField(TEXT_LEN_SHORT);
		mutAtoC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(mutAtoC);
		mutCtoA = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(mutCtoA);
		mutBtoC = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(mutBtoC);
		mutCtoB = new EvoTextField(TEXT_LEN_SHORT); threeAllelesList.add(mutCtoB);

    EvoPanel titlePane = new EvoPanel();
    titlePane.setBackground(getBackground());
    titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.LINE_AXIS));
    titlePane.setAlignmentX(Component.LEFT_ALIGNMENT);
		titlePane.add(mutLabel);
    titlePane.add(Box.createHorizontalGlue());
    titlePane.add(help);

    EvoPanel mutPane = new EvoPanel();
    mutPane.setBackground(getBackground());
    mutPane.setLayout(new WrapLayout(WrapLayout.LEADING));
    mutPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		mutPane.add(mutAtoBLabel);
		mutPane.add(mutAtoB);
    mutPane.add(Box.createHorizontalStrut(10));

		mutPane.add(mutBtoALabel);
		mutPane.add(mutBtoA);
    mutPane.add(Box.createHorizontalStrut(10));

		mutPane.add(mutAtoCLabel);
		mutPane.add(mutAtoC);
    mutPane.add(Box.createHorizontalStrut(10));

		mutPane.add(mutCtoALabel);
		mutPane.add(mutCtoA);
    mutPane.add(Box.createHorizontalStrut(10));

		mutPane.add(mutBtoCLabel);
		mutPane.add(mutBtoC);
    mutPane.add(Box.createHorizontalStrut(10));

		mutPane.add(mutCtoBLabel);
		mutPane.add(mutCtoB);

    add(titlePane);
    add(mutPane);

		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					openHelp("/help/Help06_Mutation.pdf");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
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

		if (threeAlleles) {
			double mutA_C = Double.parseDouble(mutAtoC.getText());
			double mutC_A = Double.parseDouble(mutCtoA.getText());
			double mutB_C = Double.parseDouble(mutBtoC.getText());
			double mutC_B = Double.parseDouble(mutCtoB.getText());

			double mutC_C = 1 - mutC_A - mutC_B;
			mutA_A -= mutA_C;
			mutB_B -= mutB_C;
			p.setAlleleMutationRate(Allele.A, Allele.C, mutA_C);
			p.setAlleleMutationRate(Allele.C, Allele.A, mutC_A);
			p.setAlleleMutationRate(Allele.C, Allele.B, mutC_B);
			p.setAlleleMutationRate(Allele.B, Allele.C, mutB_C);

			double mutAA_AC = 2 * mutA_C * mutA_A;
			double mutAA_CC = mutA_C * mutA_C;
			double mutAC_AA = mutC_A * mutA_A;
			double mutAC_CC = mutA_C * mutC_C;
			double mutCC_AA = mutC_A * mutC_A;
			double mutCC_AC = 2 * mutC_A * mutC_C;
			double mutCC_CC = mutC_C * mutC_C;
			double mutAC_AC = mutA_A * mutC_C + mutA_C * mutC_A;
			mutAA_AA = mutA_A * mutA_A;

			double mutCC_BC = 2 * mutC_B * mutC_C;
			double mutCC_BB = mutC_B * mutC_B;
			double mutBC_CC = mutB_C * mutC_C;
			double mutBC_BB = mutC_B * mutB_B;
			double mutBB_CC = mutB_C * mutB_C;
			double mutBB_BC = 2 * mutB_C * mutB_B;
			double mutBC_BC = mutC_C * mutB_B + mutC_B * mutB_C;
			mutBB_BB = mutB_B * mutB_B;

			p.setMutationRate(Genotype.AA, Genotype.AC, mutAA_AC);
			p.setMutationRate(Genotype.AA, Genotype.CC, mutAA_CC);
			p.setMutationRate(Genotype.AC, Genotype.AA, mutAC_AA);
			p.setMutationRate(Genotype.AC, Genotype.CC, mutAC_CC);
			p.setMutationRate(Genotype.CC, Genotype.AA, mutCC_AA);
			p.setMutationRate(Genotype.CC, Genotype.AC, mutCC_AC);
			p.setMutationRate(Genotype.AA, Genotype.AA, mutAA_AA);
			p.setMutationRate(Genotype.AC, Genotype.AC, mutAC_AC);
			p.setMutationRate(Genotype.CC, Genotype.CC, mutCC_CC);

			p.setMutationRate(Genotype.CC, Genotype.BC, mutCC_BC);
			p.setMutationRate(Genotype.CC, Genotype.BB, mutCC_BB);
			p.setMutationRate(Genotype.BC, Genotype.CC, mutBC_CC);
			p.setMutationRate(Genotype.BC, Genotype.BB, mutBC_BB);
			p.setMutationRate(Genotype.BB, Genotype.CC, mutBB_CC);
			p.setMutationRate(Genotype.BB, Genotype.BC, mutBB_BC);
			p.setMutationRate(Genotype.CC, Genotype.CC, mutCC_CC);
			p.setMutationRate(Genotype.BC, Genotype.BC, mutBC_BC);
			p.setMutationRate(Genotype.BB, Genotype.BB, mutBB_BB);

		}
	}

}
