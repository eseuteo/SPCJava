package rw_sem_bridge;

import java.util.Random;

public class Car extends Thread {
	private Bridge bridge;
	private int id;
	private Direction direction;
	private static Random rng = new Random();

	public Car(Bridge bridge, int id, Direction direction) {
		this.bridge = bridge;
		this.id = id;
		this.direction = direction;
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(rng.nextInt(1500));
				bridge.startCrossing(direction, id);
				sleep(rng.nextInt(1000));
				bridge.endCrossing(direction, id);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
