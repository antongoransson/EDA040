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

	public void setNext() {
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
		view.moveLift(here, next);
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
	}

	public synchronized boolean addPersonToLift(int startFloor, int destFloor) {
		if (startFloor == here && load < 4) {
			waitExit[destFloor]++;
			waitEntry[here]--;
			load++;
			drawLift();
			drawLevel(here);
			notifyAll();
			return true;
		}
		try {
			wait();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

	public synchronized boolean removePersonFromLift(int floor) {
		if (floor == here) {
			load--;
			waitExit[floor]--;
			drawLift();
			notifyAll();
			return true;
		}
		try {
			wait();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;

	}

	public synchronized void moveLift() {
		boolean pepesInSystem = false;
		for(int i = 0; i < 7; i++){
			if(waitExit[i] != 0 || waitEntry[i] != 0){
				pepesInSystem = true;
			}
		}
		if (here == next && pepesInSystem) {
			if (waitExit[here] > 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (waitEntry[here] > 0 && load < 4) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				setNext();
				notifyAll();
			}
		} else {
			here = next;
		}

	}
}
