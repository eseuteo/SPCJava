package pc_mon_ngoro;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cauldron {
	private final int maxCapacity;
	private int rations = 0;
	
	private Lock l = new ReentrantLock();
	private Condition sleepAtHut = l.newCondition();
	private Condition waitForFood = l.newCondition();

	public Cauldron(int cauldronCapacity) {
		this.maxCapacity = cauldronCapacity;
	}

	public void sleepAndCook() throws InterruptedException {
		try {
			l.lock();
			while (rations != 0) {
				System.out.println("Cook sleeps at the hut");
				sleepAtHut.await();
			}
			System.out.println("Cook starts cooking");
			Thread.sleep(1000);
			rations = maxCapacity;
			System.out.println("Cook fills the cauldron with " + maxCapacity + " rations");
			waitForFood.signal();
		} finally {
			l.unlock();
		}
	}

	public void eat(int id) throws InterruptedException {
		try {
			l.lock();
			while (rations == 0) {
				System.out.println("Cannibal " + id + " waits for food");
				waitForFood.await();
			}
			rations--;
			System.out.println("Cannibal " + id + " \teats a ration. Remaining rations: " + rations);
			if (rations == 0)
				sleepAtHut.signal();
			else
				waitForFood.signal();
		} finally {
			l.unlock();
		}
	}

}
