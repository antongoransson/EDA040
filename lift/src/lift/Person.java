package lift;

public class Person extends Thread {
	Monitor m;
	int startFloor, goalFloor;

	public Person(Monitor monitor) {
		this.m = monitor;

	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000 * ((int) (Math.random() * 46.0)));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startFloor = (int) (Math.random() * 7);
		goalFloor = (int) (Math.random() * 7);
		while (startFloor == goalFloor) {
			startFloor = (int) (Math.random() * 7);
		}
		m.addPersonAtFloor(startFloor);
		boolean hasArrived = false;

		while (true) {

			try {
				m.addPersonToLift(startFloor, goalFloor);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				hasArrived = m.removePersonFromLift(goalFloor);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (hasArrived) {
				try {
					Thread.sleep(1000 * ((int) (Math.random() * 46.0)));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startFloor = (int) (Math.random() * 7);
				goalFloor = (int) (Math.random() * 7);
				while (startFloor == goalFloor) {
					startFloor = (int) (Math.random() * 7);
				}
				m.addPersonAtFloor(startFloor);
				hasArrived = false;

			}

		}

	}
}
