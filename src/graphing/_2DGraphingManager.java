package graphing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

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
	private static final int CONTROL_PANEL_WIDTH = 150;
	/** Dimensions of exported image of chart **/
	private static final int EXPORT_WIDTH = 1920;
	private static final int EXPORT_HEIGHT = 1080;
	/** 
	 * Default value of bounds on x and y axis. In the case that a bound is 
	 * default, the graphing library chooses best fit bounds.
	 */
	private static final String DEFAULT_BOUND = "";
	/** Default range-component, to show on first graph generation **/
	/** Holds cached versions of graphs to avoid extra computation **/
	private static final AxisType DEFAULT_RANGE_METRIC = QuantityType.POPSIZE;
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
		win.setLayout(new BorderLayout());
		updateSeries(null, DEFAULT_BOUND, DEFAULT_BOUND, DEFAULT_BOUND, DEFAULT_BOUND);
		win.add(generateControlPanel(), BorderLayout.WEST);
	}
	
	
	/**
	 * Generates panel containing range switching mechanism and axis bound 
	 * manipulation.
	 * 
	 * @return panel containing control panel
	 * 
	 */
	private JPanel generateControlPanel() {
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		ButtonGroup graphingTypes = new ButtonGroup();
		
		JRadioButton quantitiesButton = new JRadioButton("Graph Quantities");
		quantitiesButton.setSelected(true);
		quantitiesButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		graphingTypes.add(quantitiesButton);
		
		
		JPanel quantitiesPanel = new JPanel();
		quantitiesPanel.setLayout(new BoxLayout(quantitiesPanel, BoxLayout.Y_AXIS));
		quantitiesPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		quantitiesPanel.setAlignmentX(0);
		
		for (QuantityType qt : QuantityType.values()) {
			JCheckBox box = new JCheckBox(qt.toString());
			if (qt == DEFAULT_RANGE_METRIC) {
				box.setSelected(true);
			}
			box.setAlignmentX(Component.LEFT_ALIGNMENT);
			quantitiesPanel.add(box);
		}
		
		
		JRadioButton frequenciesButton = new JRadioButton("Graph Frequencies");
		frequenciesButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		graphingTypes.add(frequenciesButton);

		
		JPanel frequenciesPanel = new JPanel();
		frequenciesPanel.setLayout(new BoxLayout(frequenciesPanel, BoxLayout.Y_AXIS));
		frequenciesPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		frequenciesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		for (Allele a : Allele.getValues()) {
			AxisType at = FrequencyType.toEnum(a);
			JCheckBox box = new JCheckBox(at.toString());
			if (at == DEFAULT_RANGE_METRIC)
				box.setSelected(true);
			box.setAlignmentX(Component.LEFT_ALIGNMENT);
			frequenciesPanel.add(box);
		}
		for (Genotype gt : Genotype.getValues()) {
			AxisType at = FrequencyType.toEnum(gt);
			JCheckBox box = new JCheckBox(at.toString());
			if (at == DEFAULT_RANGE_METRIC)
				box.setSelected(true);
			box.setAlignmentX(Component.LEFT_ALIGNMENT);
			frequenciesPanel.add(box);
		}
		
		
		
		
		JPanel rangesPanel = new JPanel(new GridLayout(2,4));
		rangesPanel.add(new JLabel("X-Min: "));
		JTextField domainLowerBound = new JTextField(BOUND_BOX_WIDTH); 
		rangesPanel.add(domainLowerBound);
		rangesPanel.add(new JLabel("X-Max: "));
		JTextField domainUpperBound = new JTextField(BOUND_BOX_WIDTH);
		rangesPanel.add(domainUpperBound);
		rangesPanel.add(new JLabel("Y-Min: "));
		JTextField rangeLowerBound  = new JTextField(BOUND_BOX_WIDTH);
		rangesPanel.add(rangeLowerBound);
		rangesPanel.add(new JLabel("Y-Max: "));
		JTextField rangeUpperBound  = new JTextField(BOUND_BOX_WIDTH);  
		rangesPanel.add(rangeUpperBound);
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton apply = new JButton("Apply");
		buttonPanel.add(apply);
		JButton export = new JButton("Export Graph");
		buttonPanel.add(export);
		
		quantitiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Component box : quantitiesPanel.getComponents()) {
					box.setEnabled(quantitiesButton.isSelected());
				}
			}
		});
		
		frequenciesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Component box : frequenciesPanel.getComponents()) {
					box.setEnabled(frequenciesButton.isSelected());
				}
			}
		});
		
		// Action listener to apply new range and/or axis bounds to chart
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<AxisType> axisTypes = new ArrayList<AxisType>();
				for (JCheckBox box : (JCheckBox[]) (quantitiesButton.isSelected() ? quantitiesPanel.getComponents() : frequenciesPanel.getComponents())) {
					if (box.isSelected()) axisTypes.add(AxisType.toEnum(box.getText()));
				}
				updateSeries(axisTypes, rangeLowerBound.getText(), rangeUpperBound.getText(), domainLowerBound.getText(), domainUpperBound.getText());	
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

		containerPanel.add(quantitiesButton);
		containerPanel.add(quantitiesPanel);
		containerPanel.add(frequenciesButton);
		containerPanel.add(frequenciesPanel);
		containerPanel.add(rangesPanel);
		containerPanel.add(buttonPanel);
		
		//containerPanel.setMaximumSize(new Dimension(CONTROL_PANEL_WIDTH, Integer.MAX_VALUE));

		return containerPanel;
	}
		
	
	/**
	 * Updates visual representation of chart to reflect changes in control 
	 * panel; also used on first graph generation. 
	 * 
	 * @param types what ranges to graph on
	 * @param ymin minimum value displayed on y-axis
	 * @param ymax maximum value displayed on y-axis
	 * @param xmin minimum value displayed on x-axis
	 * @param xmax maximum value displayed on x-axis
	 * 
	 */
	private void updateSeries(ArrayList<AxisType> types, String ymin, String ymax, String xmin, String xmax) {
		// Indicate to swing that container needs to be rerendered
		win.invalidate();
				
		XYSeriesCollection seriesCollection = new XYSeriesCollection();
		if (types == null) {
			for (Population p : DataManager.getInstance().getSimulationData()) {
				XYSeries series = new XYSeries(DEFAULT_RANGE_METRIC.toString() + " " + p.getPopID());
				for (GenerationRecord gr : p.getGenerationHistory()) {
					series.add(gr.getGenerationNumber(), getDataPoint(gr, DEFAULT_RANGE_METRIC));
				}
				seriesCollection.addSeries(series);
			}
		}
		else {
			for (AxisType at : types) {
				for (Population p : DataManager.getInstance().getSimulationData()) {
					XYSeries series = new XYSeries(at.toString() + " " + p.getPopID());
					for (GenerationRecord gr : p.getGenerationHistory()) {
						series.add(gr.getGenerationNumber(), getDataPoint(gr, at));
					}
					seriesCollection.addSeries(series);
				}
			}
		}

		JFreeChart chart = ChartFactory.createXYLineChart(DataManager.getInstance().getSessionParams().getTitle(), "Generation", "", seriesCollection);
			
		
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
		win.add(panel, BorderLayout.CENTER);
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
		if (type == QuantityType.POPSIZE)
			return gr.getPopulationSize();
		
		else if (type == QuantityType.IMMIGRATION) { 
			double totalImmigrations = 0;
			for (Genotype gt : Genotype.getValues()) {
				totalImmigrations += gr.getImmigrationCount(gt);
			}
			return totalImmigrations;
		}
		
		else if (type == QuantityType.EMIGRATION) {
			double totalEmigrations = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalEmigrations += gr.getEmigrationCount(gt);
			}
			return totalEmigrations;
		}
			
		else if (type == QuantityType.NETMIGRATION) {
			double totalMigrations = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalMigrations += gr.getImmigrationCount(gt);
				totalMigrations -= gr.getEmigrationCount(gt);
			}
			return totalMigrations;
		}
			
		else if (type == QuantityType.MUTATION) {
			double totalMutations = 0;
			for(Genotype from : Genotype.getValues()) {
				for(Genotype to : Genotype.getValues()) {
					totalMutations += gr.getMutationCount(from, to);
				}
			}
			return totalMutations;
		}
			
		else if (type == QuantityType.BIRTHS) {
			double totalBirths = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalBirths += gr.getBirths(gt);
			}
			return totalBirths;
		}
			
		else if (type == QuantityType.DEATHS) {
			double totalDeaths = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalDeaths += gr.getDeaths(gt);
			}
			return totalDeaths;
		}
		
		else if (((FrequencyType) type).isAllele()) {
			Allele target = ((FrequencyType)type).getAllele();
			
			double total = 0.0;
			
			for (Genotype gt : Genotype.getValues()) {
				if (gt.getFirstAllele() == target) {
					total += gr.getGenotypeSubpopulationSize(gt);
				}
				if (gt.getSecondAllele() == target) {
					total += gr.getGenotypeSubpopulationSize(gt);
				}
			}
			return total / (gr.getPopulationSize() * 2);
		}

	
		else if (((FrequencyType) type).isGenotype()) {
			return gr.getGenotypeFreq(((FrequencyType)type).getGenotype());
		}
		else {
			return Integer.MIN_VALUE;
		}
	
	}
}
