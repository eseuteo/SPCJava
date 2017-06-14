package sb_mon_airport;

import java.util.Random;

public class Plane extends Thread {
	private Runway runway;
	private int id;
	private Direction direction;
	private static Random rng = new Random();

	public Plane(Runway runway, int id) {
		this.runway = runway;
		this.direction = id % 2 == 0 ? Direction.SOUTH : Direction.NORTH;
		this.id = id;
	}
	
	@Override
	public void run() {
		try {
			sleep(rng.nextInt(30000));
			runway.askForPermission(direction, id);
			runway.land(direction, id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
