package lift;

public class Lift extends Thread {
	Monitor mon;
	public Lift(Monitor mon){
		this.mon = mon;
	}
	@Override
	public void run() {
		while (true){ 
			mon.moveLift();
			
		}
		
	}
}
