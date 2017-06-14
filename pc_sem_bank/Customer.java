package pc_sem_bank;

import java.util.Random;

public class Customer extends Thread {
	private Account account;
	private int id;
	private Type type;
	private static Random rng = new Random();

	public Customer(Account account, int id, Type type) {
		this.account = account;
		this.id = id;
		this.type = type;
	}
	
	@Override
	public void run() {
		int auxAmount;
		while (true) {
			try {
				auxAmount = rng.nextInt(100);
				sleep(rng.nextInt(3000));
				if (type.equals(Type.SAVER))
					account.deposit(id, auxAmount);
				else
					account.withdraw(id, auxAmount);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
