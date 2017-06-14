package sb_sem_airport;

public class Controller extends Thread {
	private Runway runway;
	private Direction direction;

	public Controller(Runway runway, Direction direction) {
		this.runway = runway;
		this.direction = direction; 
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				runway.givePermission(direction);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
