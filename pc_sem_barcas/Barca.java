package pc_sem_barcas;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barca extends Thread{
	private final static int MAXPASSENGERS = 4;
	private int androidPassengers = 0;
	private int iphonePassengers = 0;
	
	private Lock l = new ReentrantLock();
	private Condition safety = l.newCondition();
	private Condition endTravel = l.newCondition();
	private Condition lastPassenger = l.newCondition();
	
	/**
	 * Un estudiante con móvil android llama a este método 
	 * cuando quiere cruzar el río. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * método
	 * @param id del estudiante android que llama al método
	 * @throws InterruptedException
	 */
	public void android(int id) throws InterruptedException{
		try {
			l.lock();
			while (androidPassengers == 2) {
				reportWaiting("Android", id);
				safety.await();
			}
			androidPassengers++;
			reportEnterBoat("Android", id);
			if (!theLastOneEntered()) {
				endTravel.await();
			} else {
				startTravel("android");
				endTravel.signalAll();
				lastPassenger.await();
			}
			androidPassengers--;
			reportExitBoat("Android", id);
			if (boatEmpty()) {
				System.out.println("Boat is empty");
				safety.signalAll();
			} else if (lastPassenger())
				lastPassenger.signal();
		} finally {
			l.unlock();
		}
	}
	
	private boolean lastPassenger() {
		return androidPassengers + iphonePassengers == 1;
	}

	private boolean boatEmpty() {
		return androidPassengers + iphonePassengers == 0;
	}

	private void reportExitBoat(String string, int id) {
		System.out.println(string + " passenger\t " + id + "\t exits the boat");
	}

	private void reportEnterBoat(String string, int id) {
		System.out.println(string + " passenger\t " + id + "\t enters into the boat");
	}

	private boolean theLastOneEntered() {
		return androidPassengers + iphonePassengers == MAXPASSENGERS;
	}

	private void reportWaiting(String string, int id) {
		System.out.println("\t" + string + " passenger\t " + id + "\t is waiting to enter the boat");
	}

	private void startTravel(String string) throws InterruptedException {
		System.out.println("Travel begins with an\t " + string + " \tdriver");
		Thread.sleep(3000);
		System.out.println("Travel ends");
	}

	/**
	 * Un estudiante con móvil android llama a este método 
	 * cuando quiere cruzar el río. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * método
	 * @param id del estudiante android que llama al método
	 * @throws InterruptedException
	 */

	public void iphone(int id) throws InterruptedException{
		try {
			l.lock();
			while (iphonePassengers == 2) {
				reportWaiting("iPhone", id);
				safety.await();
			}
			iphonePassengers++;
			reportEnterBoat("iPhone", id);
			if (!theLastOneEntered()) {
				endTravel.await();
			} else {
				startTravel("iPhone");
				endTravel.signalAll();
				lastPassenger.await();
			}
			iphonePassengers--;
			reportExitBoat("iPhone", id);
			if (boatEmpty()) {
				safety.signalAll();
				System.out.println("Boat is empty");
			} else if (lastPassenger())
				lastPassenger.signal();
		} finally {
			l.unlock();
		}
	}
	
	
	
	

}
