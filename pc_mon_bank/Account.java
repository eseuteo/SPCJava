package pc_mon_bank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	private int balance;
	private Lock l = new ReentrantLock();
	private Condition enoughMoney = l.newCondition();
	

	public void deposit(int id, int auxAmount) {
		try {
			l.lock();
			balance += auxAmount;
			System.out.println("Customer\t " + id + "\t deposits\t " + auxAmount + " euros. Balance: " + balance);
			enoughMoney.signal();
		} finally {
			l.unlock();
		}
	}

	public void withdraw(int id, int auxAmount) throws InterruptedException {
		try {
			l.lock();
			while(auxAmount > balance) {
				System.out.println("Customer\t " + id + "\t waits to withdraw\t " + auxAmount + " euros. Balance: " + balance);
				enoughMoney.await();
			}
			balance -= auxAmount;
			System.out.println("Customer\t " + id + "\t withdraws\t " + auxAmount + " euros. Balance: " + balance);
		} finally {
			l.unlock();
		}
	}

}
