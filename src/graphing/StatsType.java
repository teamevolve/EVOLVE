package graphing;

import java.awt.Color;

import shared.Allele;
import shared.Genotype;

public enum StatsType implements AxisType {
	
	MEAN_FIT_ABS("Mean Absolute Fitness"),
	MEAN_FIT_REL("Mean Relative Fitness"),

	OBS_HET("Observed Heterozygosity"),
	EXP_HET("Expected Heterozygosity"),
	D_HET("Deviation from Exp. Heterozygosity");
	
	private String value;
	
	private static final Color MEAN_FIT_ABS_COLOR = Color.getHSBColor(166,206,227);
	private static final Color MEAN_FIT_REL_COLOR = Color.getHSBColor(31,120,180);
	private static final Color OBS_HET_COLOR = Color.getHSBColor(31,120,180);
	private static final Color EXP_HET_COLOR = Color.getHSBColor(31,120,180);
	private static final Color D_HET_COLOR = Color.getHSBColor(31,120,180);
	
	StatsType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}