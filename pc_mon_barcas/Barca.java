package pc_mon_barcas;

import java.util.concurrent.Semaphore;

public class Barca extends Thread{
	private final static int MAXPASSENGERS = 4;
	private int androidPassengers = 0;
	private int iphonePassengers = 0;
	
	private Semaphore android = new Semaphore(1, true);
	private Semaphore iphone = new Semaphore(1, true);
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore lastPassenger = new Semaphore(0, true);
	private Semaphore regularPassenger = new Semaphore(0, true);
	
	/**
	 * Un estudiante con móvil android llama a este método 
	 * cuando quiere cruzar el río. Debe esperarse a montarse en la
	 * barca de forma segura, y a llegar a la otra orilla del antes de salir del
	 * método
	 * @param id del estudiante android que llama al método
	 * @throws InterruptedException
	 */
	public void android(int id) throws InterruptedException{
		android.acquire();
		mutex.acquire();
		androidPassengers++;
		System.out.println("Android passenger\t " + id + "\t enters the boat");
		if (androidPassengers < MAXPASSENGERS/2)
			android.release();
		if (androidPassengers + iphonePassengers == MAXPASSENGERS) {
			startTravel("android");
			for (int i = 0; i < MAXPASSENGERS-1; i++)
				regularPassenger.release();
			mutex.release();
			lastPassenger.acquire();
			mutex.acquire();
		} else {
			mutex.release();
			regularPassenger.acquire();
			mutex.acquire();
		}
		androidPassengers--;
		System.out.println("\tAndroid passenger\t " + id + "\t exits the boat");
		if (androidPassengers + iphonePassengers == 1) {
			lastPassenger.release();
		} else if (androidPassengers + iphonePassengers == 0) {
			android.release();
			iphone.release();
		}
		mutex.release();
	}
	
	private void startTravel(String string) throws InterruptedException {
		System.out.println("Travel begins with an " + string + " driver");
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
		iphone.acquire();
		mutex.acquire();
		iphonePassengers++;
		System.out.println("iPhone passenger\t " + id + "\t enters the boat");
		if (iphonePassengers < MAXPASSENGERS/2)
			iphone.release();
		if (androidPassengers + iphonePassengers == MAXPASSENGERS) {
			startTravel("iPhone");
			for (int i = 0; i < MAXPASSENGERS-1; i++)
				regularPassenger.release();
			mutex.release();
			lastPassenger.acquire();
			mutex.acquire();
		} else {
			mutex.release();
			regularPassenger.acquire();
			mutex.acquire();
		}
		iphonePassengers--;
		System.out.println("\tiPhone passenger\t " + id + "\t exits the boat");
		if (androidPassengers + iphonePassengers == 1) {
			lastPassenger.release();
		} else if (androidPassengers + iphonePassengers == 0) {
			android.release();
			iphone.release();
		}
		mutex.release();
	}
	
	
	
	

}
