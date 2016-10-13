package todo;

import done.*;

public class WashingController implements ButtonListener {
	private AbstractWashingMachine theMachine;
	private TemperatureController tempControl;
	private WaterController waterControl;
	private SpinController spinControl;
	private double theSpeed;
	// TODO: add suitable attributes

	public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		// TODO: implement this constructo
		this.theMachine = theMachine;
		this.theSpeed = 100;
		tempControl = new TemperatureController(theMachine, this.theSpeed);
		System.out.println("temp ");
		waterControl = new WaterController(theMachine, this.theSpeed);
		System.out.println("temp + water");
		spinControl = new SpinController(theMachine, this.theSpeed);
		tempControl.start();
		waterControl.start();
		spinControl.start();
	}

	public void processButton(int theButton) {
		System.out.println("iabidop");
		if (theButton == 1) {
			System.out.println("hej");
			new WashingProgram1(theMachine, theSpeed, tempControl, waterControl, spinControl).start();	
			// TODO: implement this method
		} else if (theButton == 2) {

		} else if (theButton == 3) {

		}
	}
}