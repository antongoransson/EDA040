package lift;

public class Monitor {
	private int here, next, load;
	private int[] waitEntry, waitExit;
	private boolean moveUp;
	private LiftView view;

	public Monitor(LiftView view) {
		moveUp = true;
		next = 0;
		here = 0;
		this.view = view;
		waitEntry = new int[7];
		waitExit = new int[7];

	}

	public synchronized int getHere() {
		return here;
	}

	public synchronized void updateHere() {
		here = next;
		notifyAll();
	}

	public synchronized int getNext() {
		return next;
	}

	private synchronized void setNext() {
		if (next + 1 > 6) {
			next -= 1;
			moveUp = false;
		} else if (next - 1 < 0) {
			next += 1;
			moveUp = true;
		} else if (moveUp) {
			next++;
		} else {
			next--;
		}
	}

	private synchronized void drawLevel(int floor) {
		view.drawLevel(floor, waitEntry[floor]);
	}

	private synchronized void drawLift() {
		view.drawLift(here, load);

	}

	public synchronized void addPersonAtFloor(int floor) {
		waitEntry[floor]++;
		drawLevel(floor);
		notifyAll();
	}

	public synchronized void addPersonToLift(int startFloor, int destFloor) throws InterruptedException {
		while (!((startFloor == here && load < 4))) {
			wait();
		}
		waitExit[destFloor]++;
		waitEntry[here]--;
		load++;
		drawLift();
		drawLevel(here);
		notifyAll();
	}

	public synchronized boolean removePersonFromLift(int floor) throws InterruptedException {
		while (!(floor == here)) {
			wait();
		}
		load--;
		waitExit[floor]--;
		drawLift();
		notifyAll();
		return true;
	}

	public synchronized boolean moveLift() throws InterruptedException {
		boolean personsInSystem = false;
		for (int i = 0; i < 7; i++) {
			if (waitExit[i] != 0 || waitEntry[i] != 0) {
				personsInSystem = true;
			}
		}
		while (!personsInSystem
				|| ((here == next && personsInSystem) && ((waitExit[here] > 0) || (waitEntry[here] > 0 && load < 4)))) {
			wait();
			for (int i = 0; i < 7; i++) {
				if (waitExit[i] != 0 || waitEntry[i] != 0) {
					personsInSystem = true;
				}
			}

		}
		setNext();
		notifyAll();
		return true;

	}
}
