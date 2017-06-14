package pc_mon_ngoro;

public class Cook extends Thread {
	private Cauldron cauldron;
		
	public Cook(Cauldron cauldron) {
		this.cauldron = cauldron;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				cauldron.sleepAndCook();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
