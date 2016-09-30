package lift;

public class Lift extends Thread {
	Monitor mon;
	LiftView v;

	public Lift(Monitor mon, LiftView v) {
		this.mon = mon;
		this.v = v;
	}

	@Override
	public void run() {
		while (true) {
			try {
				boolean m = mon.moveLift();
				if (m) {
					v.moveLift(mon.getHere(), mon.getNext());
					mon.updateHere();

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
