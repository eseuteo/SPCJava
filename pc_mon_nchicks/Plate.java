package pc_mon_nchicks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Plate {
	private int maxBugs;
	private int bugsInPlate = 0;
	
	private Lock l = new ReentrantLock();
	private Condition food = l.newCondition();
	private Condition space = l.newCondition();

	public Plate(int i) {
		maxBugs = i;
	}

	public void putBug(int id) throws InterruptedException {
		try {
			l.lock();
			while (bugsInPlate == maxBugs)
				space.await();
			bugsInPlate++;
			System.out.println("Bug put by " + id + " Remaining: " + bugsInPlate);
			if (bugsInPlate == 1)
				food.signal();
		} finally {
			l.unlock();
		}
	}

	public void eatBug(int id) throws InterruptedException {
		try {
			l.lock();
			while (bugsInPlate == 0)
				food.await();
			bugsInPlate--;
			System.out.println("Bug eaten by " + id + " Remaining: " + bugsInPlate);
			if (bugsInPlate == maxBugs - 1)
				space.signal();
		} finally {
			l.unlock();
		}
	}

}
