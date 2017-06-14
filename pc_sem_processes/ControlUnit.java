package pc_sem_processes;

import java.util.concurrent.Semaphore;

public class ControlUnit {
	private int globalResources;
	private int neededResources;
	private boolean processWaiting;
	
	private Semaphore allocate = new Semaphore(1, true);
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore enough = new Semaphore(0, true);

	public ControlUnit(int resources) {
		this.globalResources = resources;
	}

	public void allocate(int id, int resources) throws InterruptedException {
		System.out.println("Process\t " + id + "\t enters in the queue");
		allocate.acquire();
		mutex.acquire();
		if (globalResources < resources) {
			neededResources = resources;
			processWaiting = true;
			mutex.release();
			enough.acquire();
			mutex.acquire();
			processWaiting = false;
		}
		globalResources -= resources;
		System.out.println("Control unit allocates\t " + resources + "\t for process\t " + id);
		mutex.release();
		allocate.release();
	}

	public void deallocate(int id, int resources) throws InterruptedException {
		mutex.acquire();
		globalResources += resources;
		System.out.println("Control unit deallocates\t " + resources + "\t from process\t " + id);
		if (processWaiting && neededResources <= globalResources)
			enough.release();
		mutex.release();

	}

}
