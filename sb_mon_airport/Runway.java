package sb_mon_airport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runway {
	private int[] waitingPlanes = new int[2];
	private boolean planeAsking = false;

	private Lock l = new ReentrantLock();
	private Condition sleepController = l.newCondition();
	private Condition runway = l.newCondition();
	private Condition permission = l.newCondition();
	private Condition finishLanding = l.newCondition();

	public void givePermission(Direction direction) throws InterruptedException {
		try {
			l.lock();
			int index = direction == Direction.SOUTH ? 0 : 1;
			while (waitingPlanes[index] == 0){
				System.out.println("Controller " + direction + " is sleeping");
				sleepController.await();
			}
			System.out.println("Controller " + direction + " wakes up and gives permission to land");
			permission.signal();
			finishLanding.await();
		} finally {
			l.unlock();
		}
	}

	public void askForPermission(Direction direction, int id) throws InterruptedException {
		try {
			l.lock();
			int index = direction == Direction.SOUTH ? 0 : 1;
			waitingPlanes[index]++;
			while (planeAsking) {
				System.out.println("Plane\t " + id + "\t heading " + direction + " is waiting");
				runway.await();
			}
			planeAsking = true;
			System.out.println("Plane\t " + id + "\t heading " + direction + " asks for permission");
			sleepController.signalAll();
			permission.await();			
			System.out.println("Plane\t " + id + "\t heading " + direction + " is landing");
		} finally {
			l.unlock();
		}

	}

	public void land(Direction direction, int id) {
		try {
			l.lock();
			int index = direction == Direction.SOUTH ? 0 : 1;
			waitingPlanes[index]--;
			planeAsking = false;
			System.out.println("Plane\t " + id + "\t heading " + direction + " finished landing. Runway is now free");
			finishLanding.signal();
			runway.signal();
		} finally {
			l.unlock();
		}
	}

}
