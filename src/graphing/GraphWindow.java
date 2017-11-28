package graphing;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;

import shared.SessionParameters;

/**
 * @author alexdennis
 */

public class GraphWindow extends JPanel {
  OptionsPane options;
  EvoChartPane graph;

  public GraphWindow(SessionParameters sess) {
    super();

    setLayout(new BorderLayout());

    options = new OptionsPane(sess);
    add(options, BorderLayout.LINE_START);

    graph = new EvoChartPane(sess.getTitle(), options.getGraphParams());
    add(graph, BorderLayout.CENTER);
  }

  public static void createGraphWindow(SessionParameters sess) {
    JFrame window = new JFrame();
    window.setTitle(sess.getTitle());
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    window.add(new GraphWindow(sess));

    window.pack();
    window.setVisible(true);
  }
}
