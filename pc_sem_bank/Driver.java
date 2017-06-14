package pc_sem_bank;

public class Driver {
	public static void main(String[] args) {
		Account account = new Account();
		for (int i = 0; i<10; i++){
			new Customer(account, i, (i%2 == 0 ? Type.SAVER : Type.SPENDER)).start();
		}
	}
}
