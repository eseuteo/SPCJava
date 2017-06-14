package sb_sem_printers;

import java.util.Random;

public class Document extends Thread {
	private ManagerSystem manager;
	private int id;
	private Type type;
	private static Random rng = new Random();

	public Document(ManagerSystem managerSystem, int id, Type type) {
		this.manager = managerSystem;
		this.id = id;
		this.type = type;
	}
	
	@Override
	public void run() {
		char typeUsed;
		while (true) {
			try {
				sleep(rng.nextInt(10000));
				if (type.equals(Type.A)) {
					manager.qPrinterA(id);
					typeUsed = 'A';
				} else if (type.equals(Type.B)) {
					manager.qPrinterB(id);
					typeUsed = 'B';
				} else {
					typeUsed = manager.qPrinterAB(id);
				}
				sleep(rng.nextInt(1000));
				manager.dPrinter(typeUsed);
			} catch (InterruptedException e) {
				
			}
		}
	}

}
