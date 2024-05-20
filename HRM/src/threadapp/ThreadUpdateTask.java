package threadapp;

import dap.DAPTask;

/**
* @author Duc Linh
*/
public class ThreadUpdateTask extends Thread {
	@Override
	public void run() {
       DAPTask dap = new DAPTask();
       dap.update();
    }
}
