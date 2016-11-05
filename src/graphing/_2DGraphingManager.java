package graphing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import shared.Allele;
import shared.DataManager;
import shared.Genotype;
import shared.Utilities;
import simulation.GenerationRecord;
import simulation.Population;

 /**
  * 
  * @author linneasahlberg
  * @author ericscollins
  */

public class _2DGraphingManager {
	private static final int TEXT_LEN = 3;
	private static final AxisType DEFAULT_AXIS_TYPE = AxisType.ALLELEFREQ;
	private XYSeriesCollection seriesCollection = new XYSeriesCollection();
	
	private static _2DGraphingManager instance = null;
	
	public static _2DGraphingManager getInstance() {
		if (instance == null)
			instance = new _2DGraphingManager();
		return instance;
	}
	
	private _2DGraphingManager() {
		
	}
	
	public void construct(JFrame window) {
		ChartPanel graphPanel = generateGraphPanel();
		window.add(graphPanel, BorderLayout.NORTH);
		JPanel controlPanel = generateControlPanel();
		window.add(controlPanel, BorderLayout.SOUTH);
		
		for (Population p : DataManager.getInstance().getSimulationData())
			seriesCollection.addSeries(new XYSeries(p.getPopID()));
			
		updateSeries(DEFAULT_AXIS_TYPE);
	}
	
	private JPanel generateControlPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		JLabel yaxisLabel = new JLabel("Y-Axis"); panel.add(yaxisLabel);
		JComboBox<String> yaxis = new JComboBox(); panel.add(yaxis);
		for (AxisType type : AxisType.values())
			yaxis.addItem(type.toString());
		yaxis.setSelectedItem(DEFAULT_AXIS_TYPE.toString());
		JLabel yminLabel = new JLabel("Y-Min: "); panel.add(yminLabel);
		JTextField ymin = new JTextField(TEXT_LEN); panel.add(ymin);
		JLabel ymaxLabel = new JLabel("Y-Max: "); panel.add(ymaxLabel);
		JTextField ymax = new JTextField(TEXT_LEN); panel.add(ymax);
		JLabel xminLabel = new JLabel("X-Min: "); panel.add(xminLabel);
		JTextField xmin = new JTextField(TEXT_LEN); panel.add(xmin);
		JLabel xmaxLabel = new JLabel("X-Max: "); panel.add(xmaxLabel);
		JTextField xmax = new JTextField(TEXT_LEN); panel.add(xmax);
		JButton submit = new JButton(">> Apply <<"); panel.add(submit);
		
		return panel;
	}
	
	private ChartPanel generateGraphPanel() {
		JFreeChart chart = ChartFactory.createXYLineChart("Title", "Generations", "yAxisLabel", seriesCollection);
		ChartPanel panel = new ChartPanel(chart);
		
		return panel;
	}
	
	private void updateSeries(AxisType type) {
		for (Population p : DataManager.getInstance().getSimulationData()) {
			XYSeries series = seriesCollection.getSeries(p.getPopID());
			series.clear();
			generateSeries(type, p, series);
		}
	}
	
	private void generateSeries(AxisType type, Population p, XYSeries series) {
		for (GenerationRecord gr : p.getGenerationHistory()) {
			series.add(gr.getGenerationNumber(), getDataPoint(gr, type));
		}
	}
	
	private double getDataPoint(GenerationRecord gr, AxisType type) {
		switch(type) {
		case POPSIZE: return gr.getPopulationSize();
		
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
			
			for (Allele a : Allele.getValues()) {
				System.out.printf("%.2f,", perAllele.get(a) / (gr.getPopulationSize() * 2));
			}	
			return (perAllele.get(Allele.A) - perAllele.get(Allele.B)) / (gr.getPopulationSize() * 2);
			
		default: return 0;		
		}
	}
}
