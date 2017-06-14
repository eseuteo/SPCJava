package sb_mon_printers;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ManagerSystem {
	private int aPrinters;
	private int bPrinters;
	private static Random rng = new Random();

	Lock l = new ReentrantLock();
	Condition printers = l.newCondition();

	public ManagerSystem(int i, int j) {
		aPrinters = i;
		bPrinters = j;
	}

	public void qPrinterA(int id) throws InterruptedException {
		try {
			l.lock();
			while (aPrinters == 0) {
				System.out.println("Type A document\t " + id + "\t waiting to be printed");
				System.out.println("\t Available A printers: " + aPrinters + ", Available B printers: " + bPrinters);
				printers.await();
			}
			aPrinters--;
			System.out.println("Type A document\t " + id + "\t started printing");
			System.out.println("\t Available A printers: " + aPrinters + ", Available B printers: " + bPrinters);
		} finally {
			l.unlock();
		}
	}

	public void qPrinterB(int id) throws InterruptedException {
		try {
			l.lock();
			while (bPrinters == 0) {
				System.out.println("Type B document\t " + id + "\t waiting to be printed");
				System.out.println("\t Available A printers: " + aPrinters + ", Available B printers: " + bPrinters);
				printers.await();
			}
			bPrinters--;
			System.out.println("Type B document\t " + id + "\t started printing");
			System.out.println("\t Available A printers: " + aPrinters + ", Available B printers: " + bPrinters);
		} finally {
			l.unlock();
		}
	}

	public char qPrinterAB(int id) throws InterruptedException {
		try {
			l.lock();
			char res;
			while (aPrinters == 0 && bPrinters == 0) {
				printers.await();
			}
			boolean choose = aPrinters != 0 && bPrinters != 0 ? rng.nextBoolean() : (aPrinters != 0 ? true : false);
			if (choose) {
				aPrinters--;
				res = 'A';
			} else {
				bPrinters--;
				res = 'B';
			}
			System.out.println("Type C document\t " + id + "\t started printing at printer " + res);
			System.out.println("\t Available A printers: " + aPrinters + ", Available B printers: " + bPrinters);
			return res;
		} finally {
			l.unlock();
		}
	}

	public void dPrinter(char typeUsed) throws InterruptedException {
		try {
			l.lock();
			if (typeUsed == 'A')
				aPrinters++;
			else
				bPrinters++;
			if (aPrinters == 1 || bPrinters == 1)
				printers.signal();
			System.out.println("Type " + typeUsed + " printer ended printing");
		} finally {
			l.unlock();
		}
	}

}
