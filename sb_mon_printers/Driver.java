package sb_mon_printers;

public class Driver {
	public static void main(String[] args) {
		ManagerSystem managerSystem = new ManagerSystem(2, 2);
		for (int i = 0; i < 30; i++){
			new Document(managerSystem, i, (i%3 == 0 ? Type.A : i%3 == 1 ? Type.B : Type.C)).start();
		}
	}
}
