package graphing;

import java.awt.Color;

import shared.Allele;
import shared.Genotype;

public enum StatsType implements AxisType {
	MEAN_FIT_ABS_AA("Mean Absolute Fitness AA"),
	MEAN_FIT_ABS_AB("Mean Absolute Fitness AB"),
	MEAN_FIT_ABS_AC("Mean Absolute Fitness AC"),
	MEAN_FIT_ABS_BB("Mean Absolute Fitness BB"),
	MEAN_FIT_ABS_BC("Mean Absolute Fitness BC"),
	MEAN_FIT_ABS_CC("Mean Absolute Fitness CC"),
	MEAN_FIT_ABS("Mean Absolute Fitness"),

	MEAN_FIT_REL_AA("Mean Relative Fitness AA"),
	MEAN_FIT_REL_AB("Mean Relative Fitness AB"),
	MEAN_FIT_REL_AC("Mean Relative Fitness AC"),
	MEAN_FIT_REL_BB("Mean Relative Fitness BB"),
	MEAN_FIT_REL_BC("Mean Relative Fitness BC"),
	MEAN_FIT_REL_CC("Mean Relative Fitness CC"),
	MEAN_FIT_REL("Mean Relative Fitness"),

	OBS_HET("Observed Heterozygosity"),
	EXP_HET("Expected Heterozygosity"),
	D_HET("Deviation from Exp. Heterozygosity");

	
	private String value;
	
	StatsType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}