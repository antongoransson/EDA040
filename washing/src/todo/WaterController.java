package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class WaterController extends PeriodicThread {
	private AbstractWashingMachine theMachine;
	private WaterEvent wEvent;
	private boolean isFull,isEmpty;

	public WaterController(AbstractWashingMachine theMachine, double speed) {
		super((long) (1000 / speed));
		this.theMachine = theMachine;
		isFull = false;
	}

	public void perform() {
		WaterEvent tempEvent = (WaterEvent) mailbox.tryFetch();
		if (tempEvent != null) {
			wEvent = tempEvent;
			isEmpty = false;
		}
		if (wEvent != null) {
			int mode = wEvent.getMode();
			if (mode == WaterEvent.WATER_FILL && theMachine.isLocked()) {
				if (theMachine.getWaterLevel() < wEvent.getLevel()) {
					isFull = false;
					isEmpty= false;
					theMachine.setDrain(false);
					theMachine.setFill(true);
				} else if (theMachine.getWaterLevel() > wEvent.getLevel() &&!isFull) {
					isFull = true;
					isEmpty= false;
					theMachine.setFill(false);
					((RTThread) wEvent.getSource()).putEvent(new RTEvent(this));
				}
			} else if (mode == WaterEvent.WATER_DRAIN) {
				theMachine.setFill(false);
				if (theMachine.getWaterLevel() == 0.0 && !isEmpty) {
					System.out.println("Tom");
					theMachine.setDrain(false);
					isFull = false;
					isEmpty = true;
					((RTThread) wEvent.getSource()).putEvent(new RTEvent(this));
				} else if(theMachine.getWaterLevel() > 0.0&& !isEmpty) {
					theMachine.setDrain(true);
				}

			} else if (mode == WaterEvent.WATER_IDLE) {
				theMachine.setFill(false);
				theMachine.setDrain(false);
			}
		}
	}
}
