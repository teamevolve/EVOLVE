package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.border.EmptyBorder;

/**
 *
 * @author jasonfortunato
 * @author linneasahlberg
 * @author alexdennis
 *
 * The superclass for the evolutionary forces panes.
 * Allows for three allele mode.
 */

public class EvoMainPanel extends EvoPanel {
	final static int TEXT_LEN_LONG = 8;
	final static int TEXT_LEN_SHORT = 4;
  final static public Color COLOR1 = new Color(213, 218, 226);
	final static public Color COLOR2 = new Color(183, 210, 222);

	public boolean threeAlleles;
	public ArrayList<Component> threeAllelesList = new ArrayList<Component>();

	EvoMainPanel() {
		super();
		// initialize default settings
		threeAlleles = false;

    setBorder(new EmptyBorder(10, 10, 10, 10));
    setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	public void modeThreeAlleles(boolean b){
		threeAlleles = b;
		for(Component comp : this.threeAllelesList) {
			comp.setVisible(b);
		}
	}

  public void openHelp(String helpFile) throws Exception {
		InputStream pdfStream = GUI.class.getResourceAsStream(helpFile);

    if (pdfStream == null) {
      System.out.println("Resource does not exist!");
      System.out.println("Path used: " + helpFile);
      return;
    }

		File pdfTemp = File.createTempFile("help", ".pdf");
		pdfTemp.deleteOnExit();

		FileOutputStream fos = new FileOutputStream(pdfTemp);

		while(pdfStream.available() > 0) {
			fos.write(pdfStream.read());
		}

		fos.close();
		pdfStream.close();

		if (pdfTemp.exists()) {
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(pdfTemp);
			} else {
				System.out.println("Awt Desktop is not supported!");
			}
		} else {
			System.out.println("File does not exist!");
			System.out.println("Path used: " + pdfTemp.getAbsolutePath());
		}
	}
}
