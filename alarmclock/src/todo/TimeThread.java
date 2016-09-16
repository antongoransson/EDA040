package todo;

import todo.SharedData;

public class TimeThread extends Thread {
	private SharedData sd;

	public TimeThread(SharedData sd) {
		this.sd = sd;
	}

	public void run() {
		long t = System.currentTimeMillis();
		while (true) {
			t += 1000;
			sd.updateTime();
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
		}
	}
}
