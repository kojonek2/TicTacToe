package kojonek2.tictactoe.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketWriterClient implements Runnable {
	
	PrintWriter out;
	ConnectionToServer serverConnection;

	public SocketWriterClient(Socket serverSocket, ConnectionToServer serverConnection) {
		try {
			out = new PrintWriter(serverSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("Error during creating SocketWriter");
			e.printStackTrace();
		}
		this.serverConnection = serverConnection;
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			String toSend = serverConnection.toSendQueue.take();
			out.println(toSend);
		}
	}

}
