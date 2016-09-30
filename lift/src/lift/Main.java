package lift;

public class Main {

	public static void main(String[]args){
		LiftView v = new LiftView();
		Monitor m = new Monitor(v);
		for(int i =0; i < 20; i++){
			new Person(m).start();
		}
		Lift l = new Lift(m,v);
		l.start();
	}
}
