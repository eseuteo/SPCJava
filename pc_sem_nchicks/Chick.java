package pc_sem_nchicks;

import java.util.Random;

public class Chick extends Thread {

	private Plate plate;
	private int id;
	private static Random rng = new Random();

	public Chick(Plate plate, int id) {
		this.plate = plate;
		this.id = id;
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(rng.nextInt(2000));
				plate.eatBug(id);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
