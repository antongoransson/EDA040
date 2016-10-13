package todo;

import done.AbstractWashingMachine;

public class WashingProgram1 extends WashingProgram {

	public WashingProgram1(AbstractWashingMachine mach, double speed, TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		// TODO Auto-generated constructor stub
		super(mach, speed, tempController, waterController, spinController);
	}

	@Override
	protected void wash() throws InterruptedException {
		// Lås tvättmaskinen
		myMachine.setLock(true);

		// Fyll tvättmaskinentill 10 l
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.5));
		mailbox.doFetch();

		// Sätt temp till 58
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET, 0.58));
		// mailbox.doFetch();

		//Sätt igång spin
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));

		Thread.sleep(3000);
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		System.out.println("Tvätten är klar");
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0));
		mailbox.doFetch();
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0));
		for (int i = 0; i < 5; i++) {
			System.out.println("Dags för skölj");
			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.5));
			myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0));
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
			Thread.sleep(60*2*1000);
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0));
			mailbox.doFetch();
		}
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));
		Thread.sleep(5*60*1000);
		myMachine.setLock(false);
	}

}
