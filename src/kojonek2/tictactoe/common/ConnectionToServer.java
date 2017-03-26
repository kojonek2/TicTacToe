package kojonek2.tictactoe.common;

import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionToServer implements Runnable {

	private Socket serverSocket;
	private Timer pingTimer;
	
	WritingQueue toSendQueue;
	
	private int idOfConnection;
	private String playerName;
	
	public ConnectionToServer(String playerName) {
		this.playerName = playerName;
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
		String[] arguments = input.split(":");
		switch(arguments[0]) {
			case "ping": 
				//ignore
				break;
			case "Connected":
				idOfConnection = Integer.parseInt(arguments[1]);
				System.out.println(idOfConnection);
				toSendQueue.put("Connected:" + playerName);
				break;
			default:
				System.out.println(input);
		}
	}
}
