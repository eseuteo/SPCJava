package pc_sem_bank;

import java.util.concurrent.Semaphore;

public class Account {
	private int balance;
	private int needed;
	private boolean inRed = false;
	
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore withdraw = new Semaphore(1, true);
	private Semaphore deposit = new Semaphore(1, true);
	private Semaphore overWithdraw = new Semaphore(0, true);

	public void deposit(int id, int auxAmount) throws InterruptedException {
		deposit.acquire();
		mutex.acquire();
		balance += auxAmount;
		System.out.println("Customer\t " + id + "\t deposits\t " + auxAmount + " euros. Balance: " + balance);
		if (inRed) {
			if (auxAmount > needed){
				System.out.println("\tCustomer\t " + id + "\t deposits needed amount. Balance: " + balance);
				overWithdraw.release();
				inRed = false; 
			}
		}
		mutex.release();
		deposit.release();
	}

	public void withdraw(int id, int auxAmount) throws InterruptedException {
		withdraw.acquire();
		mutex.acquire();
		if (auxAmount > balance) {
			inRed = true;
			needed = auxAmount;
			System.out.println("Customer\t " + id + "\t waits to withdraw\t " + auxAmount + " euros. Balance: " + balance);
			mutex.release();
			overWithdraw.acquire();
			mutex.acquire();
		}
		balance-=auxAmount;
		System.out.println("Customer\t " + id + "\t withdraws\t " + auxAmount + " euros. Balance: " + balance);
		mutex.release();
		withdraw.release();
	}

}
