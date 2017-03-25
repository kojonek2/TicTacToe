package kojonek2.tictactoe.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketWriter implements Runnable {
	
	PrintWriter out;

	public SocketWriter(Socket serverSocket) {
		try {
			out = new PrintWriter(serverSocket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error during creating SocketWriter");
		}
	}
	
	@Override
	public void run() {

	}

}
