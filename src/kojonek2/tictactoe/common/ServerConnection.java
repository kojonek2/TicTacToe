package kojonek2.tictactoe.common;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection implements Runnable {

	private Socket serverSocket;
	
	public ServerConnection() {
		try {
			serverSocket = new Socket("localhost", 4554);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error during establishing connection!");
		}
	}
	
	@Override
	public void run() {		
		new Thread(new SocketReader(serverSocket)).start();
	}
	

}
