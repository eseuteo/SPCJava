package rw_mon_bridge;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bridge {
	private int carsCrossed;
	private int carsCrossingL;
	private int carsCrossingR;

	private Lock l = new ReentrantLock();
	private Condition crossingL = l.newCondition();
	private Condition crossingR = l.newCondition();

	public void startCrossing(Direction direction, int id) throws InterruptedException {
		try {
			l.lock();
			if (direction.equals(Direction.left)) {
				while (carsCrossingR > 0 || carsCrossed == 30) {
					System.out.println("\t\tCoche left esperando " + id);
					crossingL.await();
				}
				carsCrossingL++;
				carsCrossed++;
				System.out.println("\tCoche left cruza " + id);
				System.out.println("Cars crossed: " + carsCrossed + ", Cars crossing: " + carsCrossingL);
			} else {
				while (carsCrossingL > 0 || carsCrossed == 30) {
					System.out.println("\t\tCoche right esperando " + id);
					crossingR.await();
				}
				carsCrossingR++;
				carsCrossed++;
				System.out.println("\tCoche right cruza " + id);
				System.out.println("Cars crossed: " + carsCrossed + ", Cars crossing: " + carsCrossingR);
			}
		} finally {
			l.unlock();
		}
	}

	public void endCrossing(Direction direction, int id) throws InterruptedException {
		try {
			l.lock();
			if (direction.equals(Direction.left)) {
				carsCrossingL--;
				System.out.println("\t\tCoche left termina " + id);
				if (carsCrossingL == 0) {
					carsCrossed = 0;
					crossingR.signalAll();
				}
			} else {
				carsCrossingR--;
				System.out.println("\t\tCoche right termina " + id);
				if (carsCrossingR == 0) {
					carsCrossed = 0;
					crossingL.signalAll();
				}
			}
		} finally {
			l.unlock();
		}
	}

}
