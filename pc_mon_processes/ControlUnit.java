package pc_mon_processes;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControlUnit {
	private int globalResources;
	private boolean processWaiting;
	
	private Lock l = new ReentrantLock();
	private Condition enoughResources = l.newCondition();
	private Condition firstProcess = l.newCondition();

	public ControlUnit(int resources) {
		this.globalResources = resources;
	}

	public void allocate(int id, int resources) throws InterruptedException {
		try {
			l.lock();
			while (processWaiting) {
				System.out.println("Process\t " + id + "\t enters the queue ");
				firstProcess.await();
			}
			processWaiting = true;
			while (globalResources < resources) {
				System.out.println("Process\t " + id + "\t waiting for\t " + resources + "\t resources");
				enoughResources.await();
			}
			globalResources -= resources;
			System.out.println("Control unit allocated\t " + resources + "\t resources for process " + id);
			processWaiting = false;
			firstProcess.signal();
		} finally {
			l.unlock();
		}
	}

	public void deallocate(int id, int resources) throws InterruptedException {
		try {
			l.lock();
			globalResources += resources;
			System.out.println("Control unit deallocates\t " + resources + "\t from process\t " + id);
		} finally {
			l.unlock();
		}
	}

}
