package todo;

import done.ClockInput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ButtonThread extends Thread {
	private SharedData sd;
	private int prevState, currentState;
	private ClockInput input;
	private Semaphore sem;

	public ButtonThread(SharedData sd) {
		this.sd = sd;
		this.input = sd.getInput();
		this.sem = input.getSemaphoreInstance();
		prevState = 0;
	}

	public void run() {
		while (true) {
			sem.take();
			sd.upDateAlarmFlag(input.getAlarmFlag());
			currentState = input.getChoice();
			if (currentState != prevState) {
				// do

				if (prevState == 1) {
					sd.upDateAlarmTime(input.getValue());
				} else if (prevState == 2) {
					sd.updateTime(input.getValue());
				}
				prevState = currentState;
			}
		}
	}

}
