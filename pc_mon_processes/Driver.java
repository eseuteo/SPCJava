package pc_mon_processes;

public class Driver {
	public static void main(String[] args) {
		ControlUnit controlUnit = new ControlUnit(20);
		for (int i = 0; i < 30; i++) {
			new Process(controlUnit, i).start();
		}
	}
}
