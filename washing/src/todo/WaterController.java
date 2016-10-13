package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class WaterController extends PeriodicThread {
	// TODO: add suitable attributes

	private AbstractWashingMachine theMachine;
	private WaterEvent wEvent;

	public WaterController(AbstractWashingMachine theMachine, double speed) {
		super((long) (500 / speed)); // TODO: replace with suitable period
		this.theMachine = theMachine;
		// wEvent = (WaterEvent) mailbox.doFetch();

	}

	public void perform() {
		WaterEvent tempEvent = (WaterEvent) mailbox.tryFetch();
		if (tempEvent != null) {
			wEvent = tempEvent;
		}
		if (wEvent != null) {
			int mode = wEvent.getMode();
			if (mode == wEvent.WATER_FILL && theMachine.isLocked()) {
				if (theMachine.getWaterLevel() < wEvent.getLevel()) {
					theMachine.setDrain(false);
					theMachine.setFill(true);
				} else if (theMachine.getWaterLevel() > wEvent.getLevel()) {
					theMachine.setFill(false);
					((RTThread) wEvent.getSource()).putEvent(new RTEvent(this));
				}
			} else if (mode == wEvent.WATER_DRAIN) {
				theMachine.setDrain(true);
				if (theMachine.getWaterLevel() == 0) {
					((RTThread) wEvent.getSource()).putEvent(new RTEvent(this));
				} else {
					theMachine.setFill(false);
					theMachine.setDrain(true);
				}

			} else if (mode == wEvent.WATER_IDLE) {
				theMachine.setFill(false);
				theMachine.setDrain(false);
			}
		}
	}
}
