package kojonek2.tictactoe.common;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;

import kojonek2.tictactoe.views.MultiGameOptionsFrame;

public class ConnectionToServer implements Runnable {

	private Socket serverSocket;
	private Timer pingTimer;
	
	WritingQueue toSendQueue;
	
	MultiGameOptionsFrame lobbyFrame;
	
	private int idOfConnection;
	private String playerName;
	
	private DefaultListModel<Player> modelInvite;
	private DefaultListModel<Player> modelPending;
	
	private List<Player> playersInLobby;
	
	public ConnectionToServer(String playerName, MultiGameOptionsFrame lobbyFrame) {
		this.playerName = playerName;
		this.lobbyFrame = lobbyFrame;
		
		lobbyFrame.clearPendingDetails();
		modelInvite = (DefaultListModel<Player>) lobbyFrame.getListModelInvite();
		modelPending = (DefaultListModel<Player>) lobbyFrame.getListModelPending();
		
		playersInLobby = new ArrayList<Player>();
		
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
			case "Player":
				if(arguments[1].equals("Add")) {
					processPlayerJoinedLobby(arguments, input);
				} else if(arguments[1].equals("Remove")) {
					proccesPlayerLeftLobby(arguments);
				} else if(arguments[1].equals("SendingAll")) {
					modelInvite.clear();
				} 
				break;
			default:
				System.out.println(input);
		}
	}
	
	synchronized void processPlayerJoinedLobby(String[] arguments, String input) {
		int idOfPlayer = Integer.parseInt(arguments[2]);
		String nickOfPlayer = input.replaceFirst("Player:Add:\\d:", "");
		Player player = new Player(idOfPlayer, nickOfPlayer);
		playersInLobby.add(player);
		modelInvite.addElement(player);
		//System.out.println("Connected player id: " + idOfPlayer + " name: " + nickOfPlayer);
	}
	
	synchronized void proccesPlayerLeftLobby(String[] arguments) {
		int id = Integer.parseInt(arguments[2]);
		Player removed = null;
		for(Player player : playersInLobby) {
			if(player.getIdOfConnection() == id) {
				modelInvite.removeElement(player);
				removed = player;
			}
		}
		if(removed == null) {
			System.err.println("ConnectionToServer - Tried to remove player from lobby who wasn't in lobby");
			return;
		}
		playersInLobby.remove(removed);
	}
	
	public void sendInvite() {
		String query = "Invite:Send:";
		
		Player player = lobbyFrame.getSelectedPlayerForInvite();
		if(player == null) {
			System.err.println("ConnectionToServer -- player must be selected to send invite");
		}
		query += player.getIdOfConnection() + ":";
		
		query += lobbyFrame.getSizeOfGameBoardInput() + ":" + lobbyFrame.getFieldsNeededForWinInput() + ":";
		query += lobbyFrame.getYourStateInput() + ":" + lobbyFrame.getInvitedStateInput();
		
		toSendQueue.put(query);
	}
}
