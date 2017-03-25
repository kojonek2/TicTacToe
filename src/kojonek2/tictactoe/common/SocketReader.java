package kojonek2.tictactoe.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketReader implements Runnable {
	
	private BufferedReader in;

	public SocketReader(Socket serverSocket) {
		try {
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error during creating SocketReader");
		}
	}
	
	@Override
	public void run() {
		
	}
	
}
