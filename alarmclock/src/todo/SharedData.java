package todo;

import java.util.concurrent.Semaphore;

import done.ClockInput;
import se.lth.cs.realtime.semaphore.MutexSem;

public class SharedData {
	private int currentTime, alarmTime;
	private boolean alarmFlag;
	private ClockInput input;
	private MutexSem sem;
	private boolean alarmOn;

	public SharedData(ClockInput input) {
		this.input = input;
		sem = new MutexSem();
		currentTime = 55;
		alarmFlag = false;
		alarmOn = false;
	}

	public void updateTime(int time) {
		sem.take();
		int tempTime = time;
		int sec = time % 100;
		if (sec > 59) {
			tempTime +=40;
		}
		int min = tempTime % 10000;
		if (min > 5959) {
			tempTime += 4000;
		}
		if (tempTime > 235959) {
			tempTime = 0;
		}
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

	public void setAlarm(boolean b) {
		alarmOn = b;

	}

	public boolean alarmIsOn() {
		return alarmOn;
	}

}
