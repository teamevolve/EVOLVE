package graphing;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

public class EvolveChart extends JFreeChart {

	private static final long serialVersionUID = -2828708566954359121L;
	private QuantityType axisType = null;
	
	public EvolveChart(Plot plot) {
		super(plot);
		// TODO Auto-generated constructor stub
	}

	public EvolveChart(String title, Plot plot) {
		super(title, plot);
		// TODO Auto-generated constructor stub
	}

	public EvolveChart(String arg0, Font arg1, Plot arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
	
	public void setAxisType(QuantityType at) {
		axisType = at;
	}
	
	public QuantityType getAxisType() {
		return axisType;
	}

}
