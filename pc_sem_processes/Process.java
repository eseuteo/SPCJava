package pc_sem_processes;

import java.util.Random;

public class Process extends Thread {
	private int id;
	private ControlUnit controlUnit;
	private static Random rng = new Random();

	public Process(ControlUnit controlUnit, int id) {
		this.id = id;
		this.controlUnit = controlUnit;
	}
	
	@Override
	public void run() {
		int resources;
		while (true) {
			try {
				sleep(rng.nextInt(10000));
				resources = rng.nextInt(10);
				controlUnit.allocate(id, resources);
				sleep(rng.nextInt(3000));
				controlUnit.deallocate(id,resources);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
