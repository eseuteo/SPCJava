package sb_mon_airport;

public class Airport {
	public static void main(String[] args) {
		Runway runway = new Runway();
		Controller northController = new Controller(runway, Direction.NORTH);
		Controller southController = new Controller(runway, Direction.SOUTH);
		northController.start();
		southController.start();
		for (int i = 0; i < 30; i++) {
			new Plane(runway, i).start();
		}
	}
}
