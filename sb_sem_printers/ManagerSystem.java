package sb_sem_printers;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ManagerSystem {
	private int aPrinters;
	private int bPrinters;
	private static Random rng = new Random();
	
	private Semaphore printerA = new Semaphore(1, true);
	private Semaphore printerB = new Semaphore(1, true);
	private Semaphore mutex = new Semaphore(1, true);

	public ManagerSystem(int i, int j) {
		aPrinters = i;
		bPrinters = j;
	}

	public void qPrinterA(int id) throws InterruptedException {
		printerA.acquire();
		mutex.acquire();
		aPrinters--;
		System.out.println("Type A document\t " + id + "\t started printing");
		System.out.println("\t Available A printers: " + aPrinters + ", Available B printers: " + bPrinters);
		if (aPrinters != 0)
			printerA.release();
		mutex.release();
	}

	public void qPrinterB(int id) throws InterruptedException {
		printerB.acquire();
		mutex.acquire();
		bPrinters--;
		System.out.println("Type B document\t " + id + "\t started printing");
		System.out.println("\t Available A printers: " + aPrinters + ", Available B printers: " + bPrinters);
		if (aPrinters != 0)
			printerB.release();
		mutex.release();
	}

	public char qPrinterAB(int id) throws InterruptedException {
		char res;
		mutex.acquire();
		boolean choose = aPrinters != 0 && bPrinters != 0 ? rng.nextBoolean() : (aPrinters != 0 ? true : false);
		mutex.release();
		if (choose) {
			printerA.acquire();
			mutex.acquire();
			aPrinters--;
			System.out.println("Type C document\t " + id + " \tstarted printing in a type A printer");
			if (aPrinters != 0)
				printerA.release();
			mutex.release();
			res = 'A';
		} else {
			printerB.acquire();
			mutex.acquire();
			bPrinters--;
			System.out.println("Type C document\t " + id + " \tstarted printing in a type B printer");
			if (aPrinters != 0)
				printerB.release();
			mutex.release();
			res = 'B';
		}
		System.out.println("\t Available A printers: " + aPrinters + ", Available B printers: " + bPrinters);
		return res;
	}

	public void dPrinter(char typeUsed) throws InterruptedException {
		mutex.acquire();
		if (typeUsed == 'A') {
			aPrinters++;
			if (aPrinters == 1)
				printerA.release();
		} else {
			bPrinters++;
			if (bPrinters == 1)
				printerB.release();
		}
		System.out.println("Type " + typeUsed + " printer ended printing");
		mutex.release();
	}

}
