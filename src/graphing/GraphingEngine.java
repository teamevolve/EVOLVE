package graphing;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import shared.DataManager;

/**
 * GraphingEngine is the top of level class of the graphing component of EVOLVE.
 * This class orchestrates all action of the graphing component, including 
 * drawing and manipulating 2d and 3d graphs, delegating all work to more 
 * specialized components. It is controlled by EvolveDirector
 * 
 * @see shared.EvolveDirector
 * 
 * @author ericscollins
 *
 */
public class GraphingEngine {
	
	private static GraphingEngine instance = null;
	
	public static GraphingEngine getInstance() {
		if (instance == null)
			instance = new GraphingEngine();
		return instance;
	}
	
	private GraphingEngine() {
		
	}
	
	public void generateGraph(GraphType type) {
		JFrame window = new JFrame();
		window.setTitle("PUT TITLE HERE, ASSHOLES");
		window.setSize(800, 600);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		switch(type) {
		case _2D : 
			_2DGraphingManager.getInstance().construct(window);
			break;
			
		default : 
			break;
		}
		
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		gui.GUI.main(args);
		while (DataManager.getInstance().getSimulationData() == null);
		System.out.println("you suck");
		GraphingEngine.getInstance().generateGraph(GraphType._2D);

	}
}
