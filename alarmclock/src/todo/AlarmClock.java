package todo;

import done.*;

public class AlarmClock{

	private static ClockInput input;
	private static ClockOutput output;
	private SharedData sd;
	private TimeThread tt;
	private ButtonThread bt;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		sd = new SharedData(input, output);
		tt = new TimeThread(sd);
		bt = new ButtonThread(sd);
		tt.start();
		bt.start();
	}
}
