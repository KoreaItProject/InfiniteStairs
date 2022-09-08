package swing.Sub;

import swing.GameStartFrame;

public class GaugeDown extends Thread{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(GameStartFrame.gauge<100&&GameStartFrame.gauge>0){
				GameStartFrame.gauge-=1;
			}
			 if(GameStartFrame.gameRunning==false)
                 break;
		}
		
		super.run();
	}
}