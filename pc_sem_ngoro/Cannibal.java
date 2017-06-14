package pc_sem_ngoro;

import java.util.Random;

public class Cannibal extends Thread {
	private static Random rng = new Random();
	private Cauldron cauldron;
	private int id;

	public Cannibal(int id, Cauldron cauldron) {
		this.id = id;
		this.cauldron = cauldron;
	}

	@Override
	public void run() {
		try {
			while (true) {
				dance();
				cauldron.eat(id);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void dance() throws InterruptedException {
		System.out.println("Cannibal " + id + " is dancing.");
		sleep(rng.nextInt(10000));
	}

}
