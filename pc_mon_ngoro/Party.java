package pc_mon_ngoro;

public class Party {
	private static final int CAULDRON_CAPACITY = 8;
	private static final int NUM_CANNIBALS = 20;
	public static void main(String[] args) {
		Cauldron cauldron = new Cauldron(CAULDRON_CAPACITY);
		Cook cook = new Cook(cauldron);
		cook.start();
		for (int i = 0; i < NUM_CANNIBALS; i++){
			new Cannibal(i, cauldron).start();
		}
	}
}
