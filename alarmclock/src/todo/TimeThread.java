package todo;

import todo.SharedData;

public class TimeThread extends Thread {
	private SharedData sd;

	public TimeThread(SharedData sd) {
		this.sd = sd;
	}

	public void run() {
		
		//sd.updateTime(0);
		while (true) {
			int count = sd.getTime();
			long t = System.currentTimeMillis();
			t += 1000;
			long diff = t - System.currentTimeMillis();
			if (diff > 0) {
				try {
					Thread.sleep(diff);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("It's not your time to sleep");
				}
			}
			sd.updateTime(sd.getTime()+1);
		}
	}
}
