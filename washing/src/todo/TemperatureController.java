package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine theMachine;
	private TemperatureEvent tEvent;

	public TemperatureController(AbstractWashingMachine theMachine, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.theMachine = theMachine;
		tEvent = null;
	}

	public void perform() {
		TemperatureEvent tempEvent = (TemperatureEvent) mailbox.tryFetch();
		if (tempEvent != null) {
			tEvent = tempEvent;
		}
		if (tEvent != null) {
			if (tEvent.getMode() == tEvent.TEMP_IDLE || theMachine.getWaterLevel() == 0) {
				theMachine.setHeating(false);
				// ((RTThread)msg.getSource()).putEvent(new RTEvent(this));
			} else if (tEvent.getMode() == tEvent.TEMP_SET) {
				if (theMachine.getTemperature() < 58.5) {
					theMachine.setHeating(true);
				} else if (theMachine.getTemperature() > 59.5) {
					theMachine.setHeating(false);

				}
			}
		}
	}
}
