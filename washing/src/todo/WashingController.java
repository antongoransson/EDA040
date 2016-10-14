package todo;

import done.*;

public class WashingController implements ButtonListener {
	private AbstractWashingMachine theMachine;
	private TemperatureController tempControl;
	private WaterController waterControl;
	private SpinController spinControl;
	private double theSpeed;
	WashingProgram w;

	public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		this.theMachine = theMachine;
		this.theSpeed = 10;
		tempControl = new TemperatureController(theMachine, this.theSpeed);
		waterControl = new WaterController(theMachine, this.theSpeed);
		spinControl = new SpinController(theMachine, this.theSpeed);
		tempControl.start();
		waterControl.start();
		spinControl.start();
		w = new WashingProgram1(theMachine, theSpeed, tempControl, waterControl, spinControl);
	}

	public void processButton(int theButton) {
		if (theButton == 1 && !(w.isAlive())) {
			w = new WashingProgram1(theMachine, theSpeed, tempControl, waterControl, spinControl);
			w.start();
		} else if (theButton == 2 && !(w.isAlive())) {
			w = new WashingProgram2(theMachine, theSpeed, tempControl, waterControl, spinControl);
			w.start();	
		} else if (theButton == 3 && !(w.isAlive())) {
			w = new WashingProgram3(theMachine, theSpeed, tempControl, waterControl, spinControl);
			w.start();	
		} else if(theButton == 0){
			w.interrupt();
			
		}
	}
}