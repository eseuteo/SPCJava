package pc_mon_nchicks;

public class Nest {
	private final static int NUM_PARENTS = 2;
	private final static int NUM_CHICKS = 10;

	public static void main(String[] args) {
		Plate plate = new Plate(8);
		Bird[] parents = new Bird[NUM_PARENTS];
		Chick[] chicks = new Chick[NUM_CHICKS];
		for (int i = 0; i < NUM_PARENTS; i++) {
			parents[i] = new Bird(plate, i);
			parents[i].start();
		}
		for (int i = 0; i < NUM_CHICKS; i++) {
			chicks[i] = new Chick(plate, i);
			chicks[i].start();
		}

	}

}
