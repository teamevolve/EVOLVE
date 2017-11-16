package graphing;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

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
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;

import importexport.ExportFormat;
import shared.Allele;
import shared.DataManager;
import shared.EvolveDirector;
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
	private static final int EXPORT_WIDTH = 960;
	private static final int EXPORT_HEIGHT = 540;
	/**
	 * Default value of bounds on x and y axis. In the case that a bound is
	 * default, the graphing library chooses best fit bounds.
	 */
	private static final String DEFAULT_BOUND = "";
	/** Default range-component, to show on first graph generation **/
	/** Holds cached versions of graphs to avoid extra computation **/
	private static final ArrayList<AxisType> DEFAULT_RANGE_METRIC = new ArrayList<AxisType>();
	private static final Class<?> DEFAULT_ACTIVE_SECTION = FrequencyType.class;
	private Container win = null;
	private ChartPanel panel = null;
	private boolean usingFrequencies = true;

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
	private _2DGraphingManager() {
		for (Allele a : Allele.getValues()) {
			DEFAULT_RANGE_METRIC.add(FrequencyType.toEnum(a));
		}
	}

	/**
	 * Resets the class to enable default axis types when
	 * switching between 2/3 alleles without restarting
	 * GUI.
	 */
	public static void destroyInstance() {
		instance = null;
		DEFAULT_RANGE_METRIC.clear();
	}

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
		updateSeries(DEFAULT_RANGE_METRIC, DEFAULT_BOUND, DEFAULT_BOUND, DEFAULT_BOUND, DEFAULT_BOUND);
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

		containerPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		ButtonGroup graphingTypes = new ButtonGroup();

		JRadioButton quantitiesButton = new JRadioButton("Graph Quantities");
		quantitiesButton.setSelected(!usingFrequencies);
		graphingTypes.add(quantitiesButton);


		JPanel quantitiesPanel = new JPanel();
		quantitiesPanel.setLayout(new BoxLayout(quantitiesPanel, BoxLayout.Y_AXIS));
		quantitiesPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		for (QuantityType qt : QuantityType.values()) {
			JCheckBox box = new JCheckBox(qt.toString());
			if (DEFAULT_RANGE_METRIC.contains(qt)) {
				box.setSelected(true);
			}
			if (DEFAULT_ACTIVE_SECTION != QuantityType.class) {
				box.setEnabled(false);
			}
			quantitiesPanel.add(box);
		}


		JRadioButton frequenciesButton = new JRadioButton("Graph Frequencies");
		frequenciesButton.setSelected(usingFrequencies);
		graphingTypes.add(frequenciesButton);


		JPanel frequenciesPanel = new JPanel();
		frequenciesPanel.setLayout(new BoxLayout(frequenciesPanel, BoxLayout.Y_AXIS));
		frequenciesPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		for (Allele a : Allele.getValues()) {
			AxisType at = FrequencyType.toEnum(a);
			JCheckBox box = new JCheckBox(at.toString());
			box.setForeground(FrequencyType.getColor(at));
			if (DEFAULT_RANGE_METRIC.contains(at))
				box.setSelected(true);
			if (DEFAULT_ACTIVE_SECTION != FrequencyType.class)
				box.setEnabled(false);
			frequenciesPanel.add(box);
		}
		for (Genotype gt : Genotype.getValues()) {
			AxisType at = FrequencyType.toEnum(gt);
			JCheckBox box = new JCheckBox(at.toString());
			box.setForeground(FrequencyType.getColor(at));
			if (DEFAULT_RANGE_METRIC.contains(at))
				box.setSelected(true);
			if (DEFAULT_ACTIVE_SECTION != FrequencyType.class)
				box.setEnabled(false);
			frequenciesPanel.add(box);
		}

		JPanel rangesPanel = new JPanel();
		rangesPanel.setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0; c1.gridy = 0;
		rangesPanel.add(new JLabel("X-Min: "), c1);
		JTextField domainLowerBound = new JTextField(BOUND_BOX_WIDTH);
		c1.gridx++;
		rangesPanel.add(domainLowerBound, c1);
		c1.gridx++;
		rangesPanel.add(new JLabel("X-Max: "), c1);
		JTextField domainUpperBound = new JTextField(BOUND_BOX_WIDTH);
		c1.gridx++;
		rangesPanel.add(domainUpperBound, c1);
		c1.gridx = 0; c1.gridy++;
		rangesPanel.add(new JLabel("Y-Min: "), c1);
		JTextField rangeLowerBound  = new JTextField(BOUND_BOX_WIDTH);
		c1.gridx++;
		rangesPanel.add(rangeLowerBound, c1);
		c1.gridx++;
		rangesPanel.add(new JLabel("Y-Max: "), c1);
		JTextField rangeUpperBound  = new JTextField(BOUND_BOX_WIDTH);
		c1.gridx++;
		rangesPanel.add(rangeUpperBound, c1);


		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton apply = new JButton("Apply");
		buttonPanel.add(apply);
		JButton exportImage = new JButton("Export Graph");
		buttonPanel.add(exportImage);
		JButton exportCSV = new JButton("Export Data");
		buttonPanel.add(exportCSV);

		quantitiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Component box : quantitiesPanel.getComponents()) {
					box.setEnabled(quantitiesButton.isSelected());
					// CHECK and disabled quantity buttons that cannot be plotted
					String text = ((JCheckBox) box).getText();
					if (!DataManager.getInstance().getSessionParams().isMigrationChecked())
						if (text.equals("Immigration")
									|| text.equals("Emigration")
									|| text.equals("Net Migration"))
							box.setEnabled(false);
					if (!DataManager.getInstance().getSessionParams().isMutationChecked() &&
							(text.equals("Num Mutations"))) {
						box.setEnabled(false);
					}
				}
				for (Component box : frequenciesPanel.getComponents()) {
					box.setEnabled(frequenciesButton.isSelected());
					((JCheckBox) box).setSelected(false);
				}
				usingFrequencies = false;
			}
		});

		frequenciesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Component box : frequenciesPanel.getComponents()) {
					box.setEnabled(frequenciesButton.isSelected());
				}
				for (Component box : quantitiesPanel.getComponents()) {
					box.setEnabled(quantitiesButton.isSelected());
					((JCheckBox) box).setSelected(false);
				}
				usingFrequencies = true;
			}
		});

		// Action listener to apply new range and/or axis bounds to chart
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<AxisType> axisTypes = new ArrayList<AxisType>();
				for (Component box : quantitiesButton.isSelected() ? quantitiesPanel.getComponents() : frequenciesPanel.getComponents()) {
					if (((JCheckBox)box).isSelected()) {
						axisTypes.add(AxisType.toEnum(((JCheckBox) box).getText()));
					}
				}
				updateSeries(axisTypes, rangeLowerBound.getText(), rangeUpperBound.getText(), domainLowerBound.getText(), domainUpperBound.getText());
			}
		});

		// Action listener to export graph as png
		exportImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File target = Utilities.generateSaveDialog(win, "png");
				if (target != null) {
					try {
						JFreeChart chart = panel.getChart();
						String question = "";
						String design = "";
						String prediction = "";
						String result = "";
						String discussion = "";
						if (DataManager.getInstance().getSessionParams().getQuestion().trim().length() > 0)
							question = "     Question: " + DataManager.getInstance().getSessionParams().getQuestion() + "\n";
						if (DataManager.getInstance().getSessionParams().getDesign().trim().length() > 0)
							design ="     Experimental Design: " +  DataManager.getInstance().getSessionParams().getDesign() + "\n";
						if (DataManager.getInstance().getSessionParams().getPrediction().trim().length() > 0)
							prediction = "     Predictions: " + DataManager.getInstance().getSessionParams().getPrediction() + "\n";
						if (DataManager.getInstance().getSessionParams().getResults().trim().length() > 0)
							result = "     Results: " + DataManager.getInstance().getSessionParams().getResults();
						if (DataManager.getInstance().getSessionParams().getDiscuss().trim().length() > 0)
							discussion = "     Notes: " + DataManager.getInstance().getSessionParams().getDiscuss();

						String labInfo = question + design  + prediction + result + discussion;
						TextTitle labinfo = new TextTitle(labInfo, new Font("Serif", Font.PLAIN, 14), Color.black,
							    RectangleEdge.BOTTOM, HorizontalAlignment.LEFT,
							    VerticalAlignment.CENTER, RectangleInsets.ZERO_INSETS);
						chart.addSubtitle(labinfo);

						// export the graph as a png
						ChartUtilities.saveChartAsPNG(target, chart, EXPORT_WIDTH, EXPORT_HEIGHT);

						// remove the lab information added
						chart.removeSubtitle(labinfo);
					}
					catch (IOException excpt) {
						excpt.printStackTrace();
					}
				}
			}
		});

		exportCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EvolveDirector.getInstance().export(ExportFormat.CSV);
			}
		});

		containerPanel.add(frequenciesButton, c);
		c.gridy++;
		containerPanel.add(frequenciesPanel,c);
		c.gridy++;
		containerPanel.add(quantitiesButton,c);
		c.gridy++;
		containerPanel.add(quantitiesPanel,c);
		c.gridy++;
		containerPanel.add(rangesPanel,c);
		c.gridy++;
		containerPanel.add(buttonPanel,c);

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
		JFreeChart chart = ChartFactory.createXYLineChart(DataManager.getInstance().getSessionParams().getTitle(), "Generation", "", seriesCollection);
		chart.setTitle(DataManager.getInstance().getSessionParams().getTitle());
		chart.removeLegend();

		for (AxisType at : types) {
			for (Population p : DataManager.getInstance().getSimulationData()) {
				XYSeries series = new XYSeries(at.toString() + " " + p.getPopID());
				for (GenerationRecord gr : p.getGenerationHistory()) {
					series.add(gr.getGenerationNumber(), getDataPoint(gr, at, p));
				}

				seriesCollection.addSeries(series);
				((XYPlot)chart.getPlot()).getRenderer().setBaseStroke(new BasicStroke(2.0f));
				// need to set up colors for quant types
				((XYPlot)chart.getPlot()).getRenderer().setSeriesPaint(seriesCollection.getSeriesIndex(at.toString() + " " + p.getPopID()), FrequencyType.getColor(at));
			}
		}

		// Adjust chart's axis bounds based on value of text fields in control panel
		NumberAxis domain = (NumberAxis) chart.getXYPlot().getDomainAxis();
		domain.setRange(0, DataManager.getInstance().getSimulationData().get(0).getLastGeneration().getGenerationNumber());
		Range domainRange = domain.getRange();
		double xMin = (xmin.equals("")) ? domainRange.getLowerBound() : Double.parseDouble(xmin);
		double xMax = (xmax.equals("")) ? domainRange.getUpperBound() : Double.parseDouble(xmax);
		domain.setRange(xMin, xMax);

		//set up tick unit
		long unit = Math.round(xMax/2000) * 200;
		if (unit == 0) unit = Math.round(xMax/200) * 10;
		if (unit == 0) unit = Math.round(xMax/100) * 5;
		if (unit == 0) unit = Math.round(xMax/50) * 2;
		if (unit == 0) unit = 1;

		domain.setTickUnit(new NumberTickUnit(unit));
		domain.setMinorTickCount(5);
		domain.setMinorTickMarksVisible(true);

		NumberAxis range = (NumberAxis)chart.getXYPlot().getRangeAxis();
		Range rangeRange = range.getRange();
		double yMin = (ymin.equals("")) ? (usingFrequencies) ? 0 : rangeRange.getLowerBound() : Double.parseDouble(ymin);
		double yMax = (ymax.equals("")) ? (usingFrequencies) ? 1 : rangeRange.getUpperBound() : Double.parseDouble(ymax);
		range.setRange(yMin,yMax);
		range.setMinorTickCount(5);
		range.setMinorTickMarksVisible(true);

		if (usingFrequencies) {
			range.setAttributedLabel("Frequency");
			range.setTickUnit(new NumberTickUnit(0.1));
		}
		else {
			// set auto tick unit selection on when using number as y-axis
			range.setAttributedLabel("Number");
			range.setAutoTickUnitSelection(!usingFrequencies);
		}


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
	private double getDataPoint(GenerationRecord gr, AxisType type, Population p) {
		if (type == QuantityType.POPSIZE)
			return gr.getPopulationSize();

//		else if (type == QuantityType.SUBPOPSIZE_AA)
//			return gr.getGenotypeSubpopulationSize(Genotype.AA);
//
//		else if (type == QuantityType.SUBPOPSIZE_AB)
//			return gr.getGenotypeSubpopulationSize(Genotype.AB);
//
//		else if (type == QuantityType.SUBPOPSIZE_BB)
//			return gr.getGenotypeSubpopulationSize(Genotype.BB);

		else if (type == QuantityType.IMMIGRATION) {
			double totalImmigrations = 0;
			for (Genotype gt : Genotype.getValues()) {
				totalImmigrations += gr.getImmigrationCount(gt);
			}
			return totalImmigrations;
		}

//		else if (type == QuantityType.IMMIGRATION_AA)
//			return gr.getImmigrationCount(Genotype.AA);
//
//		else if (type == QuantityType.IMMIGRATION_AB)
//			return gr.getImmigrationCount(Genotype.AB);
//
//		else if (type == QuantityType.IMMIGRATION_BB)
//			return gr.getImmigrationCount(Genotype.BB);

		else if (type == QuantityType.EMIGRATION) {
			double totalEmigrations = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalEmigrations += gr.getEmigrationCount(gt);
			}
			return totalEmigrations;
		}

//		else if (type == QuantityType.EMIGRATION_AA)
//			return gr.getEmigrationCount(Genotype.AA);
//
//		else if (type == QuantityType.EMIGRATION_AB)
//			return gr.getEmigrationCount(Genotype.AB);
//
//		else if (type == QuantityType.EMIGRATION_BB)
//			return gr.getEmigrationCount(Genotype.BB);
//
		else if (type == QuantityType.NETMIGRATION) {
			double totalMigrations = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalMigrations += gr.getImmigrationCount(gt);
				totalMigrations -= gr.getEmigrationCount(gt);
			}
			return totalMigrations;
		}

//		else if (type == QuantityType.NETMIGRATION_AA)
//			return gr.getImmigrationCount(Genotype.AA) - gr.getEmigrationCount(Genotype.AA);
//
//		else if (type == QuantityType.NETMIGRATION_AB)
//			return gr.getImmigrationCount(Genotype.AB) - gr.getEmigrationCount(Genotype.AB);
//
//		else if (type == QuantityType.NETMIGRATION_BB)
//			return gr.getImmigrationCount(Genotype.BB) - gr.getEmigrationCount(Genotype.BB);
//
		else if (type == QuantityType.MUTATION) {
			double totalMutations = 0;
			for(Genotype from : Genotype.getValues()) {
				for(Genotype to : Genotype.getValues()) {
					totalMutations += gr.getMutationCount(from, to);
				}
			}
			return totalMutations;
		}
//
//		else if (type == QuantityType.MUTATION_AA) {
//			double mutations = 0;
//			for (Genotype to : Genotype.getValues()) {
//				if (to == Genotype.AA) continue;
//				mutations += gr.getMutationCount(Genotype.AA, to);
//			}
//			return mutations;
//		}
//
//		else if (type == QuantityType.MUTATION_AB) {
//			double mutations = 0;
//			for (Genotype to : Genotype.getValues()) {
//				if (to == Genotype.AB) continue;
//				mutations += gr.getMutationCount(Genotype.AB, to);
//			}
//			return mutations;
//		}
//
//		else if (type == QuantityType.MUTATION_BB) {
//			double mutations = 0;
//			for (Genotype to : Genotype.getValues()) {
//				if (to == Genotype.BB) continue;
//				mutations += gr.getMutationCount(Genotype.BB, to);
//			}
//			return mutations;
//		}

		else if (type == QuantityType.BIRTHS) {
			double totalBirths = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalBirths += gr.getBirths(gt);
			}
			return totalBirths;
		}

//		else if (type == QuantityType.BIRTHS_AA)
//			return gr.getBirths(Genotype.AA);
//
//		else if (type == QuantityType.BIRTHS_AB)
//			return gr.getBirths(Genotype.AB);
//
//		else if (type == QuantityType.BIRTHS_BB)
//			return gr.getBirths(Genotype.BB);
//
		else if (type == QuantityType.DEATHS) {
			double totalDeaths = 0;
			for(Genotype gt : Genotype.getValues()) {
				totalDeaths += gr.getDeaths(gt);
			}
			return totalDeaths;
		}

//		else if (type == QuantityType.DEATHS_AA)
//			return gr.getDeaths(Genotype.AA);
//
//		else if (type == QuantityType.DEATHS_AB)
//			return gr.getDeaths(Genotype.AB);
//
//		else if (type == QuantityType.DEATHS_BB)
//			return gr.getDeaths(Genotype.BB);

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


		else if (((FrequencyType) type).isDelta_Allele()) {
			if (gr == p.getLastGeneration()) return 0;

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
			double old_freq = total / (gr.getPopulationSize() * 2);

			GenerationRecord gr2 = p.getGenerationHistory().get(p.getGenerationHistory().indexOf(gr) + 1);

			double total2 = 0.0;

			for (Genotype gt : Genotype.getValues()) {
				if (gt.getFirstAllele() == target) {
					total2 += gr2.getGenotypeSubpopulationSize(gt);
				}
				if (gt.getSecondAllele() == target) {
					total2 += gr2.getGenotypeSubpopulationSize(gt);
				}
			}
			return (total2 / (gr2.getPopulationSize() * 2)) - old_freq;
		}

		// expected heterozygosity
		else if (type == StatsType.EXP_HET) {
			double total = 1.0;
			for (Allele a1: Allele.getValues()) {
				total -= (DataManager.getInstance().getSessionParams().getAlleleFrequency(a1)) *
						 (DataManager.getInstance().getSessionParams().getAlleleFrequency(a1));
			}
			return total;
		}

		// observed heterozygosity
		else if (type == StatsType.OBS_HET) {
			double total = 0.0;
			for (Genotype gt: Genotype.getValues()) {
				if (gt.getFirstAllele() != gt.getSecondAllele())
					total += gr.getGenotypeFreq(gt);
			}
			return total;
		}

		// delta heterozygosity
		else if (type == StatsType.D_HET) {
			double total = 1.0;
			for (Allele a1: Allele.getValues()) {
				total -= (DataManager.getInstance().getSessionParams().getAlleleFrequency(a1)) *
						 (DataManager.getInstance().getSessionParams().getAlleleFrequency(a1));
			}
			for (Genotype gt: Genotype.getValues()) {
				if (gt.getFirstAllele() != gt.getSecondAllele())
					total -= gr.getGenotypeFreq(gt);
			}
			return total;
		}

		// Mean Absolute Fitness
		else if (type == StatsType.MEAN_FIT_ABS) {
			double total = 0;
			for (Genotype gt: Genotype.getValues()) {
				total += gr.getGenotypeFreq(gt) * DataManager.getInstance().getSessionParams().getAbsoluteFitness(gt);
			}
			return total;
		}

		// Mean Relative Fitness
		else if (type == StatsType.MEAN_FIT_REL) {
			double total = 0;
			for (Genotype gt: Genotype.getValues()) {
				total += gr.getGenotypeFreq(gt) * DataManager.getInstance().getSessionParams().getRelativeFitness(gt);
			}
			return total;
		}

		else {
			return Integer.MIN_VALUE;
		}


	}
}
