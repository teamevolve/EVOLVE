package gui;

import javax.swing.SwingWorker;

import shared.EvolveDirector;

/**
 * Use an instance of this class to run the sim so you won't lock the GUI up
 * @author jasonfortunato
 * 11/27/17
 */
public class SimWorker extends SwingWorker<Object, Object> {

	/**
	 * PRE:	Session parameters in Evolve Director are set 
	 *		using EvolveDirector.getInstance().storeSessionParameters(parms);
	 * POST: Run Simulation button is re-enabled 
	 */
	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

        GUI.getInstance().submit.setText("<html><span style='font-size:13px'>Running. . . . .");
        GUI.getInstance().submit.setEnabled(false);
        EvolveDirector.getInstance().runSimulation();
		
		return null;
	}

	/**
	 * This will do run once doInBackground() is completed
	 */
    protected void done()
    {
        try
        {
            GUI.getInstance().submit.setText("<html><span style='font-size:13px'>Run Simulation");
			EvolveDirector.getInstance().graph();
	        GUI.getInstance().submit.setEnabled(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	
}
