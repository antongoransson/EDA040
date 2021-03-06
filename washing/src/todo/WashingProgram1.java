package todo;

import done.AbstractWashingMachine;

public class WashingProgram1 extends WashingProgram {
	private int washtime,rinsetime,centrifugetime;

	public WashingProgram1(AbstractWashingMachine mach, double speed, TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		// TODO Auto-generated constructor stub
		super(mach, speed, tempController, waterController, spinController);
		washtime= 30*60*1000/(int)speed/10;
		rinsetime = 2*60*1000/(int)speed;
		centrifugetime=5*60*1000/(int)speed;
	}

	@Override
	protected void wash() throws InterruptedException {
		System.out.println("Program 1");
		// Lås tvättmaskinen
		myMachine.setLock(true);

		// Fyll tvättmaskinentill 10 l
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.5));
		mailbox.doFetch();

		// Sätt temp till 58
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET, 60));
	
		//Sätt igång spin
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
		Thread.sleep(washtime);
		//Tvätt klar, stanna och töm vatten
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0.0));
		
		System.out.println("Tvätten är klar");
		
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
		mailbox.doFetch();
		
		
		//myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
		for (int i = 0; i < 5; i++) {
			System.out.println("Dags för skölj");
			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.5));
			mailbox.doFetch();
			myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0));
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
			Thread.sleep(rinsetime);
			mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0));
			mailbox.doFetch();
		}
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));
		System.out.println("Centrifugering pågår");
		Thread.sleep(centrifugetime);
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		
		myMachine.setLock(false);
		System.out.println("Du kan nu ta tvätten");
	}

}
