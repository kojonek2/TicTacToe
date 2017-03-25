package kojonek2.tictactoe.common;

import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionToServer implements Runnable {

	private Socket serverSocket;
	WritingQueue toSendQueue;
	Timer pingTimer;
	
	public ConnectionToServer() {
		try {
			serverSocket = new Socket("localhost", 4554);
			serverSocket.setSoTimeout(10000);
		} catch (IOException e) {
			System.err.println("Error during establishing connection!");
			e.printStackTrace();
		}
		toSendQueue = new WritingQueue();
		pingTimer = new Timer();
	}
	
	@Override
	public void run() {
		toSendQueue.put("Connected");
		
		new Thread(new SocketReaderClient(serverSocket, this)).start();
		new Thread(new SocketWriterClient(serverSocket, this)).start();
		
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				toSendQueue.put("ping");
			}
		};
		pingTimer.scheduleAtFixedRate(task, 4000, 4000);
	}
	
	synchronized void processInput(String input) {
		System.out.println(input);
	}
}
