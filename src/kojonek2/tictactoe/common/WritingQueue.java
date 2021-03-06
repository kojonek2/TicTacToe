package kojonek2.tictactoe.common;

import java.util.LinkedList;
import java.util.List;

public class WritingQueue {

	private List<String> queue;
	
	public WritingQueue() {
		queue = new LinkedList<String>();
	}
	
	synchronized String take() {
		while(queue.size() <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return queue.remove(0);
	}
	
	public synchronized void put(String toSend) {
		queue.add(toSend);
		notify();
	}
	
}
