package gui;

import javax.swing.SwingWorker;

import shared.EvolveDirector;

public class SimWorker extends SwingWorker<Object, Object> {

	@Override
	protected Object doInBackground() throws Exception {
		// TODO Auto-generated method stub

        GUI.getInstance().submit.setText("Running. . . . .");
        GUI.getInstance().submit.setEnabled(false);
        EvolveDirector.getInstance().runSimulation();
		
		return null;
	}

    protected void done()
    {
        try
        {
            GUI.getInstance().submit.setText("Run Simulation");
			EvolveDirector.getInstance().graph();
	        GUI.getInstance().submit.setEnabled(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	
}
