package graphing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import shared.Allele;
import shared.DataManager;
import shared.Genotype;
import shared.Utilities;
import simulation.GenerationRecord;
import simulation.Population;

 /**
  * _2DGraphingManager handles all graphing of two-dimensional charts on a
  * Cartesian plane. This manager does not create the window containing the
  * graph, but, rather, populates it with the chart itself and a control panel
  * to manipulate the chart. All data used to construct the graph is pulled 
  * from DataManager after a simulation has been run._2DGraphingManager is used
  * directly by the GraphingEngine.
  * 
  * @see GraphingEngine
  * @see DataManager
  * 
  * @author linneasahlberg
  * @author ericscollins
  */

public class _2DGraphingManager {
	/** Width of the input text boxes for controlling the range of x/y values **/
	private static final int BOUND_BOX_WIDTH = 3;
	/** Resolution of resulting graph. Higher resolution = more memory + time **/
	private static final int GRAPH_RENDER_HEIGHT = 1080;
	private static final int GRAPH_RENDER_WIDTH  = 1920;
	/** Maximum height of control panel **/
	private static final int CONTROL_PANEL_HEIGHT = 50;
	/** Dimensions of exported image of chart **/
	private static final int EXPORT_WIDTH = 1920;
	private static final int EXPORT_HEIGHT = 1080;
	/** 
	 * Default value of bounds on x and y axis. In the case that a bound is 
	 * default, the graphing library chooses best fit bounds.
	 */
	private static final String DEFAULT_BOUND = "";
	/** Default range-component, to show on first graph generation **/
	private static final AxisType DEFAULT_AXIS_TYPE = AxisType.ALLELEFREQ;
	/** Holds cached versions of graphs to avoid extra computation **/
	private static final HashMap<AxisType, JFreeChart> cachedCharts = new HashMap<AxisType, JFreeChart>();
	
	private Container win = null;
	private ChartPanel panel = null;
	
	/** Enables singelton class **/
	private static _2DGraphingManager instance = null;
	
	
	/**
	 * Enable singelton class.
	 * 
	 * @return singelton instance of _2DGraphingManager.
	 * 
	 */
	public static _2DGraphingManager getInstance() {
		if (instance == null)
			instance = new _2DGraphingManager();
		return instance;
	}
	
	
	/**
	 * Private constructor enables singleton class.
	 */
	private _2DGraphingManager() {}
	 
	
	/**
	 * Constructs the graph and control panel and adds them to a given
	 * container.
	 * 
	 * @param container container to build graph and control panel in
	 * 
	 */
	public void construct(Container container) {
		win = container;
		win.setLayout(new BoxLayout(win, BoxLayout.Y_AXIS));
		updateSeries(DEFAULT_AXIS_TYPE, DEFAULT_BOUND, DEFAULT_BOUND, DEFAULT_BOUND, DEFAULT_BOUND);
		win.add(generateControlPanel());
	}
	
	
	/**
	 * Generates panel containing range switching mechanism and axis bound 
	 * manipulation.
	 * 
	 * @return panel containing control panel
	 * 
	 */
	private JPanel generateControlPanel() {
		// Non-static components of control panel 
		JComboBox<String> rangeComboBox = new JComboBox<String>(); 
		JTextField rangeLowerBound  = new JTextField(BOUND_BOX_WIDTH);
		JTextField rangeUpperBound  = new JTextField(BOUND_BOX_WIDTH);  
		JTextField domainLowerBound = new JTextField(BOUND_BOX_WIDTH); 
		JTextField domainUpperBound = new JTextField(BOUND_BOX_WIDTH);
		JButton apply = new JButton("Apply");
		JButton export = new JButton("Export Graph");

		// Populate rangeComboBox dropdown
		for (AxisType type : AxisType.values())
			rangeComboBox.addItem(type.toString());
		rangeComboBox.setSelectedItem(DEFAULT_AXIS_TYPE.toString());

		// Action listener to reset axis bounds on range change
		rangeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rangeLowerBound.setText("");
				rangeUpperBound.setText("");
				domainLowerBound.setText("");
				domainUpperBound.setText("");
			}
		});
		
		// Action listener to apply new range and/or axis bounds to chart
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSeries(AxisType.toEnum((String)rangeComboBox.getSelectedItem()), rangeLowerBound.getText(), rangeUpperBound.getText(), domainLowerBound.getText(), domainUpperBound.getText());	
			}
		});
		
		// Action listener to export graph as png
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File target = Utilities.generateSaveDialog(win, "png");
				if (target != null) {
					try {
						ChartUtilities.saveChartAsPNG(target, panel.getChart(), EXPORT_WIDTH, EXPORT_HEIGHT);
					}
					catch (IOException excpt) {
						excpt.printStackTrace();
					}
				}
			}
		});
		
		// Construct panel
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel("Y-Axis: "));
		panel.add(rangeComboBox);
		panel.add(new JLabel("Y-Min: "));
		panel.add(rangeLowerBound);
		panel.add(new JLabel("Y-Max: "));
		panel.add(rangeUpperBound);
		panel.add(new JLabel("X-Min: "));
		panel.add(domainLowerBound);
		panel.add(new JLabel("X-Max: "));
		panel.add(domainUpperBound);
		panel.add(apply);
		panel.add(export);
		
		// Force graph to resize rather than control panel
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, CONTROL_PANEL_HEIGHT));
		
		return panel;
	}
	
	
	/**
	 * Updates visual representation of chart to reflect changes in control 
	 * panel; also used on first graph generation. Caches charts after 
	 * their generation to avoid duplicating computation.
	 * 
	 * @param type what range to graph on
	 * @param ymin minimum value displayed on y-axis
	 * @param ymax maximum value displayed on y-axis
	 * @param xmin minimum value displayed on x-axis
	 * @param xmax maximum value displayed on x-axis
	 * 
	 */
	private void updateSeries(AxisType type, String ymin, String ymax, String xmin, String xmax) {
		// Indicate to swing that container needs to be rerendered
		win.invalidate();
		
		JFreeChart chart;
		
		// Pull chart from cache if available
		if (cachedCharts.containsKey(type))
			chart = cachedCharts.get(type);
		// Otherwise generate a new chart
		else {			
			XYSeriesCollection seriesCollection = new XYSeriesCollection();
			
			for (Population p : DataManager.getInstance().getSimulationData()) {
				XYSeries series = new XYSeries(p.getPopID());
				for (GenerationRecord gr : p.getGenerationHistory()) {
					series.add(gr.getGenerationNumber(), getDataPoint(gr, type));
				}
				seriesCollection.addSeries(series);
			}
			
			chart = ChartFactory.createXYLineChart(type.toString() + " vs. Generation", "Generation", type.toString(), seriesCollection);
			chart.removeLegend();
			chart.setTitle(DataManager.getInstance().getSessionParams().getTitle());
			
			cachedCharts.put(type, chart);
		}
		
		// Adjust chart's axis bounds based on value of text fields in conrtol panel
		ValueAxis domain = chart.getXYPlot().getDomainAxis();
		Range domainRange = domain.getRange();
		double xMin = (xmin.equals("")) ? ((domainRange.getLowerBound() < 0) ? 0 : domainRange.getLowerBound()) : Double.parseDouble(xmin);
		double xMax = (xmax.equals("")) ? domainRange.getUpperBound() : Double.parseDouble(xmax);
		domain.setRange(xMin, xMax);

		ValueAxis range = chart.getXYPlot().getRangeAxis();
		Range rangeRange = range.getRange();
		double yMin = (ymin.equals("")) ? ((rangeRange.getLowerBound() < 0) ? 0 : rangeRange.getLowerBound()) : Double.parseDouble(ymin);
		double yMax = (ymax.equals("")) ? rangeRange.getUpperBound() : Double.parseDouble(ymax);
		range.setRange(yMin,yMax);

	
		// Create panel and set its dimensions to avoid text stretching on labels
		ChartPanel newPanel = new ChartPanel(chart);
		newPanel.setMaximumDrawHeight(GRAPH_RENDER_HEIGHT);
		newPanel.setMaximumDrawWidth(GRAPH_RENDER_WIDTH);
		
		// Remove existing panel from screen
		if (panel != null) win.remove(panel);

		// Add new panel to screen
		panel = newPanel;
		win.add(panel, 0);
		win.validate();
	}

	
	/**
	 * Retrieves a single datapoint from a generation record based on a given
	 * range component being plotted on.
	 * 
	 * @param gr   generation record to pull info from
	 * @param type determines what sort of info is returned
	 * 
	 * @return datapoint relating to given AxisType 
	 * 
	 */
	private double getDataPoint(GenerationRecord gr, AxisType type) {
		switch(type) {
		case POPSIZE: 
			return gr.getPopulationSize();
		
		case IMMIGRATION: 
			double totalImmigrations = 0;
			for (Genotype gt : Genotype.getValues()) {
				totalImmigrations += gr.getImmigrationCount(gt);
			}
			return totalImmigrations;
			
		case EMIGRATION:
			double totalEmigrations = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalEmigrations += gr.getEmigrationCount(gt);
			}
			return totalEmigrations;
			
		case NETMIGRATION:
			double totalMigrations = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalMigrations += gr.getImmigrationCount(gt);
				totalMigrations -= gr.getEmigrationCount(gt);
			}
			return totalMigrations;
			
		case MUTATION:
			double totalMutations = 0;
			for(Genotype from : Genotype.getValues()) {
				for(Genotype to : Genotype.getValues()) {
					totalMutations += gr.getMutationCount(from, to);
				}
			}
			return totalMutations;
			
		case BIRTHS:
			double totalBirths = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalBirths += gr.getBirths(gt);
			}
			return totalBirths;
			
		case DEATHS:
			double totalDeaths = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalDeaths += gr.getDeaths(gt);
			}
			return totalDeaths;
		
		case ALLELEFREQ:
			HashMap<Allele, Double> perAllele = new HashMap<Allele, Double>();
			for (Allele a : Allele.getValues()) {
				perAllele.put(a,  0.0);
			}
			
			for (Genotype gt : Genotype.getValues()) {
				perAllele.put(gt.getFirstAllele(), perAllele.get(gt.getFirstAllele()) + gr.getGenotypeSubpopulationSize(gt));
				perAllele.put(gt.getSecondAllele(), perAllele.get(gt.getSecondAllele()) + gr.getGenotypeSubpopulationSize(gt));			
			}
			
			return (perAllele.get(Allele.A) - perAllele.get(Allele.B)) / (gr.getPopulationSize() * 2);
			
		default: 
			return 0;		
		}
	}
}
