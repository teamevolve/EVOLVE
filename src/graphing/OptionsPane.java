package graphing;

import gui.WrapLayout;
import shared.SessionParameters;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.Box;

/**
 * @author alexdennis
 */

class OptionsPane extends JPanel {
  SessionParameters sess;

  JLabel hAxis, hAxisFreq;
  JRadioButton hAxisGen;
  JRadioButton hAxisFreqA, hAxisFreqB, hAxisFreqC,
    hAxisFreqAA, hAxisFreqAB, hAxisFreqBB,
    hAxisFreqAC, hAxisFreqBC, hAxisFreqCC;
  ButtonGroup hAxisGroup;

  JLabel vAxis, vAxisFreq;
  JCheckBox vAxisFreqA, vAxisFreqB, vAxisFreqC,
    vAxisFreqAA, vAxisFreqAB, vAxisFreqBB,
    vAxisFreqAC, vAxisFreqBC, vAxisFreqCC;
  JCheckBox vAxisPop;

  public OptionsPane(SessionParameters sess) {
    super();

    this.sess = sess;

    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    hAxis = new JLabel("<html><b><span style='font-size:11px'>Horizontal Axis:</span></b>");
    hAxis.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(hAxis);

    hAxisGroup = new ButtonGroup();

    hAxisGen = new JRadioButton("Generations", true);
    hAxisGen.setAlignmentX(Component.LEFT_ALIGNMENT);
    hAxisGroup.add(hAxisGen);
    add(hAxisGen);

    hAxisFreq = new JLabel("Frequency");
    hAxisFreq.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(hAxisFreq);

    JPanel hAxisFreqAllele = new JPanel();
    hAxisFreqAllele.setLayout(new WrapLayout(WrapLayout.LEADING));
    hAxisFreqAllele.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(hAxisFreqAllele);

    hAxisFreqA = new JRadioButton("A");
    hAxisGroup.add(hAxisFreqA);
    hAxisFreqAllele.add(hAxisFreqA);

    hAxisFreqAllele.add(Box.createHorizontalStrut(15));
    hAxisFreqB = new JRadioButton("B");
    hAxisGroup.add(hAxisFreqB);
    hAxisFreqAllele.add(hAxisFreqB);

    if (sess.isThreeAlleles()) {
      hAxisFreqAllele.add(Box.createHorizontalStrut(15));
      hAxisFreqC = new JRadioButton("C");
      hAxisGroup.add(hAxisFreqC);
      hAxisFreqAllele.add(hAxisFreqC);
    }

    JPanel hAxisFreqGeno = new JPanel();
    hAxisFreqGeno.setLayout(new WrapLayout(WrapLayout.LEADING));
    hAxisFreqGeno.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(hAxisFreqGeno);

    hAxisFreqAA = new JRadioButton("AA");
    hAxisGroup.add(hAxisFreqAA);
    hAxisFreqGeno.add(hAxisFreqAA);

    hAxisFreqGeno.add(Box.createHorizontalStrut(15));
    hAxisFreqAB = new JRadioButton("AB");
    hAxisGroup.add(hAxisFreqAB);
    hAxisFreqGeno.add(hAxisFreqAB);

    hAxisFreqGeno.add(Box.createHorizontalStrut(15));
    hAxisFreqBB = new JRadioButton("BB");
    hAxisGroup.add(hAxisFreqBB);
    hAxisFreqGeno.add(hAxisFreqBB);

    if (sess.isThreeAlleles()) {
      hAxisFreqGeno.add(Box.createHorizontalStrut(15));
      hAxisFreqAC = new JRadioButton("AC");
      hAxisGroup.add(hAxisFreqAC);
      hAxisFreqGeno.add(hAxisFreqAC);

      hAxisFreqGeno.add(Box.createHorizontalStrut(15));
      hAxisFreqBC = new JRadioButton("BC");
      hAxisGroup.add(hAxisFreqBC);
      hAxisFreqGeno.add(hAxisFreqBC);

      hAxisFreqGeno.add(Box.createHorizontalStrut(15));
      hAxisFreqCC = new JRadioButton("CC");
      hAxisGroup.add(hAxisFreqCC);
      hAxisFreqGeno.add(hAxisFreqCC);
    }

    add(Box.createVerticalStrut(15));

    vAxis = new JLabel("<html><b><span style='font-size:11px'>Vertical Axis:</span></b>");
    vAxis.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(vAxis);

    vAxisFreq = new JLabel("Frequencies");
    vAxisFreq.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(vAxisFreq);

    JPanel vAxisFreqAllele = new JPanel();
    vAxisFreqAllele.setLayout(new WrapLayout(WrapLayout.LEADING));
    vAxisFreqAllele.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(vAxisFreqAllele);

    vAxisFreqA = new JCheckBox("A", true);
    vAxisFreqAllele.add(vAxisFreqA);

    vAxisFreqAllele.add(Box.createHorizontalStrut(15));
    vAxisFreqB = new JCheckBox("B", true);
    vAxisFreqAllele.add(vAxisFreqB);

    if (sess.isThreeAlleles()) {
      vAxisFreqAllele.add(Box.createHorizontalStrut(15));
      vAxisFreqC = new JCheckBox("C", true);
      vAxisFreqAllele.add(vAxisFreqC);
    }

    JPanel vAxisFreqGeno = new JPanel();
    vAxisFreqGeno.setLayout(new WrapLayout(WrapLayout.LEADING));
    vAxisFreqGeno.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(vAxisFreqGeno);

    vAxisFreqAA = new JCheckBox("AA");
    vAxisFreqGeno.add(vAxisFreqAA);

    vAxisFreqGeno.add(Box.createHorizontalStrut(15));
    vAxisFreqAB = new JCheckBox("AB");
    vAxisFreqGeno.add(vAxisFreqAB);

    vAxisFreqGeno.add(Box.createHorizontalStrut(15));
    vAxisFreqBB = new JCheckBox("BB");
    vAxisFreqGeno.add(vAxisFreqBB);

    if (sess.isThreeAlleles()) {
      vAxisFreqGeno.add(Box.createHorizontalStrut(15));
      vAxisFreqAC = new JCheckBox("AC");
      vAxisFreqGeno.add(vAxisFreqAC);

      vAxisFreqGeno.add(Box.createHorizontalStrut(15));
      vAxisFreqBC = new JCheckBox("BC");
      vAxisFreqGeno.add(vAxisFreqBC);

      vAxisFreqGeno.add(Box.createHorizontalStrut(15));
      vAxisFreqAC = new JCheckBox("CC");
      vAxisFreqGeno.add(vAxisFreqCC);
    }

    vAxisPop = new JCheckBox("Population Size");
    vAxisPop.setAlignmentX(Component.LEFT_ALIGNMENT);
    add(vAxisPop);

    add(Box.createVerticalGlue());
  }

  public GraphParameters getGraphParams() {
    GraphParameters params = new GraphParameters();

    if (vAxisFreqA.isSelected()) {
      params.addVerticalAxis(FrequencyType.ALLELE_FREQ_A);
    }

    if (vAxisFreqB.isSelected()) {
      params.addVerticalAxis(FrequencyType.ALLELE_FREQ_B);
    }

    if (sess.isThreeAlleles() && vAxisFreqC.isSelected()) {
      params.addVerticalAxis(FrequencyType.ALLELE_FREQ_C);
    }

    if (vAxisFreqAA.isSelected()) {
      params.addVerticalAxis(FrequencyType.GT_FREQ_AA);
    }

    if (vAxisFreqAB.isSelected()) {
      params.addVerticalAxis(FrequencyType.GT_FREQ_AB);
    }

    if (vAxisFreqBB.isSelected()) {
      params.addVerticalAxis(FrequencyType.GT_FREQ_BB);
    }

    if (sess.isThreeAlleles()) {
      if (vAxisFreqAC.isSelected()) {
        params.addVerticalAxis(FrequencyType.GT_FREQ_AC);
      }

      if (vAxisFreqBC.isSelected()) {
        params.addVerticalAxis(FrequencyType.GT_FREQ_BC);
      }

      if (vAxisFreqCC.isSelected()) {
        params.addVerticalAxis(FrequencyType.GT_FREQ_CC);
      }
    }

    if (vAxisPop.isSelected()) {
      params.addVerticalAxis(QuantityType.POPSIZE);
    }

    return params;
  }
}
