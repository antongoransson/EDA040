package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.MutexSem;

public class SharedData {
	private int currentTime, alarmTime;
	private int alarmCounter = 0;
	private boolean alarmFlag;
	private ClockInput input;
	private MutexSem sem;
	private boolean alarmOn;
	private ClockOutput output;

	public SharedData(ClockInput input, ClockOutput output) {
		this.input = input;
		sem = new MutexSem();
		currentTime = 55;
		alarmFlag = false;
		alarmOn = false;
		this.output = output;
	}

	public void updateTime() {
		sem.take();
		currentTime++;
		int tempTime = currentTime;
		int sec = currentTime % 100;
		if (sec > 59) {
			tempTime += 40;
		}
		int min = tempTime % 10000;
		if (min > 5959) {
			tempTime += 4000;
		}
		if (tempTime > 235959) {
			tempTime = 0;
		}
		currentTime = tempTime;
		output.showTime(currentTime);
		checkIfAlarm();
		sem.give();
	}
	private void checkIfAlarm(){
		if (alarmTime == currentTime && alarmFlag || alarmIsOn()) {
			setAlarm(true);
			alarm();
		}
	}

	public void setTime(int time) {
		sem.take();
		int tempTime = time;
		int sec = time % 100;
		if (sec > 59) {
			tempTime += 40;
		}
		int min = tempTime % 10000;
		if (min > 5959) {
			tempTime += 4000;
		}
		if (tempTime > 235959) {
			tempTime = 0;
		}
		currentTime = tempTime;
		output.showTime(currentTime);
		sem.give();
		checkIfAlarm();
	}

	public void upDateAlarmTime(int time) {
		sem.take();
		alarmTime = time;
		sem.give();
	}

	public void upDateAlarmFlag(boolean b) {
		sem.take();
		alarmFlag = b;
		sem.give();
	}

	public ClockInput getInput() {
		return input;
	}
	
	public void setAlarm(boolean b) {
		sem.take();
		alarmOn = b;
		sem.give();
	}

	public boolean alarmIsOn() {
		boolean ans = alarmOn;
		return ans;
	}

	private void alarm() {
		if (alarmIsOn() && alarmCounter < 20){
			setAlarm(true);
			output.doAlarm();
			alarmCounter++;
		} else {
			alarmCounter = 0;
			setAlarm(false);
		}
	}

}
