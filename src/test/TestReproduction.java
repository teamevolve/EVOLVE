package test;

import shared.DataManager;
import shared.Genotype;
import shared.SessionParameters;
import simulation.Population;
import simulation.GenerationRecord;

public class TestReproduction {

	final static int SUBPOPSIZE_AA = 0;
	final static int SUBPOPSIZE_AB = 0;
	final static int SUBPOPSIZE_BB = 0;
		
	public static void main(String[] args) {
	
		TestResources.init();
		final SessionParameters sp = DataManager.getInstance().getSessionParams();
	
		GenerationRecord genRecord = new GenerationRecord(0, 0);
		
		genRecord.setGenotypeSubpopulationSize(Genotype.AA, SUBPOPSIZE_AA);
		genRecord.setGenotypeSubpopulationSize(Genotype.AB, SUBPOPSIZE_AB);
		genRecord.setGenotypeSubpopulationSize(Genotype.BB, SUBPOPSIZE_BB);
		
	}
	
}


