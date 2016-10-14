package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine theMachine;
	private TemperatureEvent tEvent;

	public TemperatureController(AbstractWashingMachine theMachine, double speed) {
		super((long) (2000 / speed)); // TODO: replace with suitable period
		this.theMachine = theMachine;
		tEvent = null;
	}

	public void perform() {
		TemperatureEvent tempEvent = (TemperatureEvent) mailbox.tryFetch();
		if (tempEvent != null) {
			tEvent = tempEvent;
		}
		if (tEvent != null) {
			int mode = tEvent.getMode();
			if (mode == TemperatureEvent.TEMP_IDLE || theMachine.getWaterLevel() == 0) {
				theMachine.setHeating(false);
			//	 ((RTThread)tEvent.getSource()).putEvent(new RTEvent(this));
			} else if (mode == TemperatureEvent.TEMP_SET) {
				if (theMachine.getTemperature() < tEvent.getTemperature() -1.7 && (theMachine.getWaterLevel() > 0.5)) {
					theMachine.setHeating(true);
				} else if (theMachine.getTemperature() > tEvent.getTemperature()-0.4) {
				//	((RTThread)tEvent.getSource()).putEvent(new RTEvent(this));
					theMachine.setHeating(false);
				}
			}
		}
	}
}
