package rw_sem_bridge;

import java.util.concurrent.Semaphore;

public class Bridge {
	private int carsCrossed;
	private int carsCrossing;
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore permission[] = new Semaphore[2];
	private Semaphore checkSemaphores = new Semaphore(1, true);

	public Bridge() {
		for (int i = 0; i < permission.length; i++) {
			permission[i] = new Semaphore(1, true);
		}
	}

	public void startCrossing(Direction direction, int id) throws InterruptedException {
		int index = direction.equals(Direction.left) ? 0 : 1;
		int antiIndex = (index + 1) % 2;
		checkSemaphores.acquire();
		if (permission[antiIndex].availablePermits() == 1)
			permission[antiIndex].acquire();
		permission[index].acquire();
		mutex.acquire();
		carsCrossing++;
		carsCrossed++;
		System.out.println(id + ",\t\t\t\t" + (direction.equals(Direction.left) ? "<<---" : "--->>"));
		System.out.println("Cars crossed: " + carsCrossed + ", Cars crossing: " + carsCrossing);
		mutex.release();
		permission[index].release();
		checkSemaphores.release();
	}

	public void endCrossing(Direction direction, int id) throws InterruptedException {
		int index = direction.equals(Direction.left) ? 0 : 1;
		int antiIndex = (index + 1) % 2;
		mutex.acquire();
		carsCrossing--;
		System.out.println("Car " + id + " crossed " + direction);
		System.out.println("Cars crossed: " + carsCrossed + ", Cars crossing: " + carsCrossing);
		if (carsCrossing == 0) {
			permission[antiIndex].release();
			carsCrossed = 0;
		}
		mutex.release();
	}

}
