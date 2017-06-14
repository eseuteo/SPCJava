package sb_sem_airport;

import java.util.concurrent.Semaphore;

public class Runway {
	private int[] waitingPlanes = new int[2];
	private Semaphore sleepController[] = new Semaphore[2];
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore runway = new Semaphore(0, true);
	private Semaphore planeLanding = new Semaphore(1, true);
	private Semaphore finishLanding = new Semaphore(0, true);
	
	public Runway() {
		for (int i = 0; i < sleepController.length; i++)
			sleepController[i] = new Semaphore(0, true);
	}

	public void givePermission(Direction direction) throws InterruptedException {
		int index = direction == Direction.SOUTH ? 0 : 1;
		sleepController[index].acquire();
		reportAwake(direction);
		mutex.acquire();
		reportPermission(direction);
		runway.release();
		mutex.release();
		finishLanding.acquire();
	}

	public void askForPermission(Direction direction, int id) throws InterruptedException {
		int index = direction == Direction.SOUTH ? 0 : 1;
		reportPermission(direction, id);
		planeLanding.acquire();
		mutex.acquire();
		waitingPlanes[index]++;
		if (waitingPlanes[index] == 1) {
			mutex.release();
			sleepController[index].release();
			runway.acquire();
		}
	}
	
	public void land(Direction direction, int id) throws InterruptedException {
		int index = direction == Direction.SOUTH ? 0 : 1;
		mutex.acquire();
		waitingPlanes[index]--;
		reportLanding(direction, id);
		mutex.release();
		finishLanding.release();
		planeLanding.release();
	}

	private void reportPermission(Direction direction, int id) {
		System.out.println("Plane\t " + id + " \theading " + direction + " asks for permission to land");
	}

	private void reportLanding(Direction direction, int id) {
		System.out.println("Plane\t " + id + " \theading " + direction + " lands. Runway is free now");
	}
	
	private void reportPermission(Direction direction) {
		System.out.println("\t" + direction + " Controller gives permission to land");
	}

	private void reportAwake(Direction direction) {
		System.out.println("\t" + direction + " Controller is woken up");
	}

}
