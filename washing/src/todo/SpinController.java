package todo;

import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {
	// TODO: add suitable attributes
	private AbstractWashingMachine theMachine;
	private int spinDirection;
	private SpinEvent sEvent;

	public SpinController(AbstractWashingMachine theMachine, double speed) {
		super((long) (600000 / speed)); // TODO: replace with suitable period
		this.theMachine = theMachine;
		spinDirection = theMachine.SPIN_LEFT;

	}

	public void perform() {
		SpinEvent tempEvent = (SpinEvent) mailbox.tryFetch();
		if (tempEvent != null) {
			sEvent = tempEvent;
		}
		if (sEvent != null) {
			int mode = sEvent.getMode();
			if (mode == sEvent.SPIN_SLOW) {
				if (spinDirection == theMachine.SPIN_LEFT) {
					spinDirection = theMachine.SPIN_RIGHT;
					theMachine.setSpin(spinDirection);
					System.out.println("Höger");
				} else {
					spinDirection = theMachine.SPIN_LEFT;
					theMachine.setSpin(spinDirection);
					System.out.println("Vänster");
				}
			} else if (mode == sEvent.SPIN_FAST) {
				spinDirection = theMachine.SPIN_FAST;
				theMachine.setSpin(spinDirection);
			} else {
				spinDirection = theMachine.SPIN_OFF;
				theMachine.setSpin(spinDirection);
			}

			// ((RTThread)msg.getSource()).putEvent(new RTEvent(this))

		}
	}
}
