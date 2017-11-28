package graphing;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

/**
 * @author alexdennis
 */

public class EvoChartPane extends ChartPanel {
  JFreeChart chart;
  XYPlot plot;

  public EvoChartPane(String title, GraphParameters params) {
    super(null);
    plot = new XYPlot();
    chart = new JFreeChart(title, plot);
    updateChart(params);
    setChart(chart);
  }

  public void updateChart(GraphParameters params) {
    
  }
}
