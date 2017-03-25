package kojonek2.tictactoe.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

public class SocketReaderClient implements Runnable {
	
	ConnectionToServer serverConnection;
	private BufferedReader in;

	public SocketReaderClient(Socket serverSocket, ConnectionToServer serverConnection) {
		try {
			in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Error during creating SocketReader");
			e.printStackTrace();
		}
		this.serverConnection = serverConnection;
	}
	
	@Override
	public void run() {
		String inputLine;
		try {
			while((inputLine = in.readLine()) != null) {
				serverConnection.processInput(inputLine);
			}
		} catch (IOException e) {
			System.err.println("Error during reading inputStream");
			//e.printStackTrace();
			//probably timeout
			JOptionPane.showMessageDialog(null, "Lost connection!", "Warning" , JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
}
