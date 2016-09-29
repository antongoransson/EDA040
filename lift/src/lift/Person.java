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
		boolean inLift = false;
		while (true) {

			inLift = m.addPersonToLift(startFloor, goalFloor);
			while (inLift) {
				hasArrived = m.removePersonFromLift(goalFloor);
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
					inLift = false;
				}
			}

		}

	}
}
