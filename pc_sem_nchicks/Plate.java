package pc_sem_nchicks;

import java.util.concurrent.Semaphore;

public class Plate {
	private int maxBugs;
	private int bugsInPlate;
	
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore food = new Semaphore(0, true);
	private Semaphore space = new Semaphore(1, true);

	public Plate(int i) {
		maxBugs = i;
	}

	public void putBug(int id) throws InterruptedException {
		space.acquire();
		mutex.acquire();
		bugsInPlate++;
		System.out.println("Bug put by " + id + " Remaining: " + bugsInPlate);
		if (bugsInPlate == 1)
			food.release();
		if (bugsInPlate < maxBugs)
			space.release();
		mutex.release();
	}

	public void eatBug(int id) throws InterruptedException {
		food.acquire();
		mutex.acquire();
		bugsInPlate--;
		System.out.println("Bug eaten by " + id + " Remaining: " + bugsInPlate);
		if (bugsInPlate == maxBugs-1)
			space.release();
		if (bugsInPlate > 0)
			food.release();
		mutex.release();
	}

}
