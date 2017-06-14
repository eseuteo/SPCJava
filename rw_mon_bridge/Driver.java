package rw_mon_bridge;

public class Driver {
	public static void main(String[] args) {
		Bridge bridge = new Bridge();
		for (int i = 0; i < 30; i++) {
			new Car(bridge, i, (i%2 == 0 ? Direction.left : Direction.right)).start();
		}
	}
}
