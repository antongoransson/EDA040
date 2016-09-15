package todo;

import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock extends Thread {

	private static ClockInput input;
	private static ClockOutput output;
	private static Semaphore sem;
	private SharedData sd;
	private TimeThread tt;
	private ButtonThread bt;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sem = input.getSemaphoreInstance();
		sd = new SharedData(input);
		tt = new TimeThread(sd);
		bt = new ButtonThread(sd);
		tt.start();
		bt.start();
	}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon
	// each keypress. To be modified in the lab.
	public void run() {

		while (true) {
			if (sd.doAlarm()) {
				sd.setAlarm(true);
				for (int i = 0; i < 20; i++) {
					if (sd.alarmIsOn()) {
					output.showTime(sd.getTime());
					output.doAlarm();
					long t = System.currentTimeMillis();
					t += 700;
					long diff = t - System.currentTimeMillis();
					if (diff > 0) {
						try {
							sleep(diff);
						} catch (InterruptedException e) {

							e.printStackTrace();
						}}
					}
				}
			}
			output.showTime(sd.getTime());
		}
	}

}
