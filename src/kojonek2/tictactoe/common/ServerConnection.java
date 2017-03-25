package kojonek2.tictactoe.common;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements Runnable {

	private Socket serverSocket;
	WritingQueue toSendQueue;
	
	public ServerConnection() {
		try {
			serverSocket = new Socket("192.168.1.150", 4554);
		} catch (IOException e) {
			System.err.println("Error during establishing connection!");
			e.printStackTrace();
		}
		toSendQueue = new WritingQueue();
	}
	
	@Override
	public void run() {
		new Thread(new SocketReaderClient(serverSocket, this)).start();
		new Thread(new SocketWriterClient(serverSocket, this)).start();
		toSendQueue.put("hello Server");
	}
	
	synchronized void processInput(String input) {
		System.out.println(input);
	}
}
