package gui;

import java.awt.Component;

import javax.swing.JPanel;

/**
 * @author alexdennis
 */

public class EvoPanel extends JPanel {
  @Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		for(Component component : getComponents()) {
			component.setEnabled(enable);
		}
	}
}
