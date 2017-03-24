package kojonek2.tictactoe.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection extends Thread {

	@Override
	public void run() {
		super.run();
		
		try (
				Socket serverSocket = new Socket("localhost", 4554);
				PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))
			) {
				String inputLine;
				while((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
				}
				
			} catch (Exception e) {
				System.err.println("Error during creating socket or listening for conection");
				e.printStackTrace();
			}

	}
	
}
