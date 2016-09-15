package todo;

import java.util.concurrent.Semaphore;

import done.ClockInput;
import se.lth.cs.realtime.semaphore.MutexSem;

public class SharedData {
	private int currentTime, alarmTime;
	private boolean alarmFlag;
	private ClockInput input;
	private MutexSem sem;

	public SharedData(ClockInput input) {
		this.input = input;
		sem = new MutexSem();
		currentTime = 55;
		alarmFlag = false;
	}

	public void updateTime(int time) {
		sem.take();
		System.out.println("Hold the door" + time);
		int tempTime = time;
		int sec = time % 100;
		if (sec > 59) {
			tempTime += 100;
			tempTime -= 60;
		}
		int min = tempTime % 10000;
		if (min > 5959) {
			tempTime += 10000;
			tempTime -= 6000;
		}
		if (tempTime > 235959) {
			tempTime = 0;
		}
		System.out.println("Blapblpop" + currentTime);
		currentTime = tempTime;
		sem.give();
	}

	public void upDateAlarmTime(int time) {
		sem.take();
		alarmTime = time;
		sem.give();
	}

	public void upDateAlarmFlag(boolean b) {
		alarmFlag = b;
	}

	public boolean getAlarmFlag() {
		return alarmFlag;
	}

	public ClockInput getInput() {
		return input;

	}

	public int getTime() {
		sem.take();
		sem.give();
		return currentTime;

	}

	public boolean doAlarm() {
		return (alarmTime == currentTime && alarmFlag);
	}

}
