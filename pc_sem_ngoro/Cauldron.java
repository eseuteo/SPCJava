package pc_sem_ngoro;

import java.util.concurrent.Semaphore;

public class Cauldron {
	private final int maxCapacity;
	private int rations = 0;
	private Semaphore hut = new Semaphore(1, true);
	private Semaphore food = new Semaphore(0, true);
	private Semaphore mutex = new Semaphore(1, true);

	public Cauldron(int cauldronCapacity) {
		this.maxCapacity = cauldronCapacity;
	}

	public void sleepAndCook() throws InterruptedException {
		hut.acquire();
		mutex.acquire();
		System.out.println("Cook is preparing the food");
		Thread.sleep(1000);
		rations = maxCapacity;
		System.out.println("Cauldron filled with " + maxCapacity + " rations");
		food.release();
		mutex.release();		
	}

	public void eat(int id) throws InterruptedException {
		food.acquire();
		mutex.acquire();
		rations--;
		System.out.println("Cannibal " + id + " eats a ration. \tRations remaining: " + rations);
		if (rations == 0)
			hut.release();
		else
			food.release();
		mutex.release();
	}

}
