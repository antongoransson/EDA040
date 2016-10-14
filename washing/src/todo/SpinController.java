package todo;

import done.AbstractWashingMachine;
import se.lth.cs.realtime.PeriodicThread;

public class SpinController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine theMachine;
	private int spinDirection;
	private SpinEvent sEvent;

	public SpinController(AbstractWashingMachine theMachine, double speed) {
		super((long) (60*1000 / speed)); 
		this.theMachine = theMachine;
		spinDirection = AbstractWashingMachine.SPIN_LEFT;

	}

	public void perform() {
		SpinEvent tempEvent = (SpinEvent) mailbox.tryFetch();
		if (tempEvent != null) {
			sEvent = tempEvent;
		}
		if (sEvent != null) {
			int mode = sEvent.getMode();
			if (mode == SpinEvent.SPIN_SLOW) {
				if (spinDirection == AbstractWashingMachine.SPIN_LEFT) {
					spinDirection = AbstractWashingMachine.SPIN_RIGHT;
					theMachine.setSpin(spinDirection);
				} else {
					spinDirection = AbstractWashingMachine.SPIN_LEFT;
					theMachine.setSpin(spinDirection);
				}
			} else if (mode == SpinEvent.SPIN_FAST && (theMachine.getWaterLevel() == 0)) {
				spinDirection = AbstractWashingMachine.SPIN_FAST;
				theMachine.setSpin(spinDirection);
			} else {
				spinDirection = AbstractWashingMachine.SPIN_OFF;
				theMachine.setSpin(spinDirection);
			}
		}
	}
}
