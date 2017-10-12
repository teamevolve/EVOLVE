package gui;

import javax.swing.JTextField;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class EvoTextField extends JTextField {
  public EvoTextField(int columns) {
    super(columns);

    addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {
        selectAll();
      }

      public void focusLost(FocusEvent e) {
        select(0, 0);
        updateOnFocusLost();
      }
    });
  }

  public void updateOnFocusLost() {
  }
}
