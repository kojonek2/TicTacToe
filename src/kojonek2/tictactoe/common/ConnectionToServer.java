package kojonek2.tictactoe.common;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import kojonek2.tictactoe.views.MultiGameMainFrame;
import kojonek2.tictactoe.views.MultiGameOptionsFrame;
import kojonek2.tictactoe.views.WelcomeFrame;

public class ConnectionToServer implements Runnable {

	private Socket serverSocket;
	private Timer pingTimer;
	
	WritingQueue toSendQueue;
	
	MultiGameOptionsFrame lobbyFrame;
	
	private int idOfConnection;
	private String playerName;
	
	private DefaultListModel<Player> modelInvite;
	private DefaultListModel<Invite> modelPending;
	
	private List<Player> playersInLobby;
	private List<Invite> invites;
	
	public ConnectionToServer(String playerName, MultiGameOptionsFrame lobbyFrame) {
		this.playerName = playerName;
		this.lobbyFrame = lobbyFrame;
		
		lobbyFrame.clearPendingDetails();
		modelInvite = (DefaultListModel<Player>) lobbyFrame.getListModelInvite();
		modelPending = (DefaultListModel<Invite>) lobbyFrame.getListModelPending();
		
		playersInLobby = new ArrayList<Player>();
		invites = new ArrayList<Invite>();
		
		try {
			serverSocket = new Socket("localhost", 4554);
			serverSocket.setSoTimeout(10000);
		} catch (IOException e) {
			System.err.println("Error during establishing connection!");
			e.printStackTrace();
			SwingUtilities.invokeLater(() -> {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(lobbyFrame, "Cannot establish connection with server!", "Error", JOptionPane.ERROR_MESSAGE);
				WelcomeFrame welcomeFrame = new WelcomeFrame();
				welcomeFrame.setVisible(true);
				lobbyFrame.dispose();
			});
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
					playersInLobby.clear();
					modelInvite.clear();
				} else if(arguments[1].equals("SentAll")) {
					validateInvites();
				}
				break;
			case "Invite":
				if(arguments[1].equals("Send")) {
					processNewPlayerInvite(arguments);
				} else if(arguments[1].equals("Cancel")) {
					processInviteCancellation(arguments);
				} else if(arguments[1].equals("Decline")) {
					processInviteDeclination(arguments);
				} else if(arguments[1].equals("RejectAcceptance")) {
					lobbyFrame.setVisible(true);
				}
				break;
			case "Game":
				if(input.equals("Game:Start")) {
					lobbyFrame.setVisible(false);
					new MultiGameMainFrame().setVisible(true);
				} else {
					//TODO: handle communication with server during game
				}
				break;
			default:
				System.out.println(input);
		}
	}
	
	synchronized void validateInvites() {
		for(int i = invites.size() - 1; i >= 0; i--) {
			if(!playersInLobby.contains(invites.get(i).getSender())) {
				System.err.println("ConeectionToServer: validateInvites - there was an invalid invite");
				Invite removedInvite = invites.remove(i);
				modelPending.removeElement(removedInvite);
			}
		}
	}
	
	synchronized private void processPlayerJoinedLobby(String[] arguments, String input) {
		int idOfPlayer = Integer.parseInt(arguments[2]);
		String nickOfPlayer = input.replaceFirst("Player:Add:\\d:", "");
		
		Player player = new Player(idOfPlayer, nickOfPlayer);
		playersInLobby.add(player);
		modelInvite.addElement(player);
		//System.out.println("Connected player id: " + idOfPlayer + " name: " + nickOfPlayer);
	}
	
	synchronized private void proccesPlayerLeftLobby(String[] arguments) {
		int idOfPlayer = Integer.parseInt(arguments[2]);
		
		Player removedPlayer = getPlayer(idOfPlayer);
		if(removedPlayer == null) {
			System.err.println("ConnectionToServer - Tried to remove player from lobby who wasn't in lobby");
			toSendQueue.put("Player:GetAll"); //refreshing player list
			return;
		}
		
		modelInvite.removeElement(removedPlayer);
		playersInLobby.remove(removedPlayer);
		
		removeInviteFrom(removedPlayer);
	}
	
	synchronized private void processNewPlayerInvite(String[] arguments) {
		int idOfSender = Integer.parseInt(arguments[2]);
		int sizeOfGameBoard = Integer.parseInt(arguments[3]);
		int fieldsNeededForWin = Integer.parseInt(arguments[4]);
		FieldState yourState = FieldState.fromInt(Integer.parseInt(arguments[5]));
		FieldState senderState = FieldState.fromInt(Integer.parseInt(arguments[6]));
		
		Player sender = getPlayer(idOfSender);
		
		if(sender == null) {
			System.err.println("ConnectionToServer: processNewPlayerInvite - got invite from player who isn't in lobby");
			return;
		}
		
		removeInviteFrom(sender);
		
		Invite invite = new Invite(sender, sizeOfGameBoard, fieldsNeededForWin, yourState, senderState);
		invites.add(invite);
		modelPending.addElement(invite);
	}
	
	synchronized private void processInviteCancellation(String[] arguments) {
		int idOfCancellingPlayer = Integer.parseInt(arguments[2]);
		Player player = getPlayer(idOfCancellingPlayer);
		if(player == null) {
			System.err.println("ConnectionToServer:processInviteCancellation - error");
		}
		removeInviteFrom(player);
	}
	
	//invite send query always has first your state and then invited player or player who invites you state
	synchronized public void sendInvite() {
		String query = "Invite:Send:";
		
		Player player = lobbyFrame.getSelectedPlayerForInvite();
		if(player == null) {
			System.err.println("ConnectionToServer -- player must be selected to send invite");
		}
		player.setInviteState(InviteState.SENT);
		
		query += player.getIdOfConnection() + ":";
		
		query += lobbyFrame.getSizeOfGameBoardInput() + ":" + lobbyFrame.getFieldsNeededForWinInput() + ":";
		query += lobbyFrame.getYourStateInput().getValue() + ":" + lobbyFrame.getInvitedStateInput().getValue();
		
		toSendQueue.put(query);
	}
	
	synchronized private void removeInviteFrom(Player player) {
		Invite removed = null;
		for(int i = invites.size() - 1; i >= 0; i--) {
			if(invites.get(i).getSender().equals(player)) {
				removed = invites.remove(i);
			}
		}
		if(removed == null) {
			return;
		}
		modelPending.removeElement(removed);
	}
	
	synchronized void processInviteDeclination(String[] arguments) {
		int idOfDecliner = Integer.parseInt(arguments[2]);
		Player decliner = getPlayer(idOfDecliner);
		if(decliner == null) {
			//already disconnected from lobby
			return;
		}
		decliner.setInviteState(InviteState.DECLINED);
		lobbyFrame.repaint();
	}
	
	synchronized Player getPlayer(int idOfPlayer) {
		for(Player player : playersInLobby) {
			if(player.getIdOfConnection() == idOfPlayer) {
				return player;
			}
		}
		return null;
	}
	
	synchronized public void declineInvite(Invite invite) {
		modelPending.removeElement(invite);
		invites.remove(invite);
		String query = "Invite:Decline:" + invite.getSender().getIdOfConnection() + ":";
		query += invite.getSizeOfGameBoard() + ":" + invite.getFieldsNeededForWin() + ":";
		query += invite.getYourState().getValue() + ":" + invite.getSenderState().getValue();
		toSendQueue.put(query);
	}
	
	synchronized public void acceptInvite(Invite invite) {
		modelPending.removeElement(invite);
		invites.remove(invite);
		String query = "Invite:Accept:" + invite.getSender().getIdOfConnection() + ":";
		query += invite.getSizeOfGameBoard() + ":" + invite.getFieldsNeededForWin() + ":";
		query += invite.getYourState().getValue() + ":" + invite.getSenderState().getValue();
		toSendQueue.put(query);
		lobbyFrame.setVisible(false);
	}
	
	public void cancelInviteFrom(Player player) {
		player.setInviteState(InviteState.CANCELED);
		toSendQueue.put("Invite:Cancel:" + player.getIdOfConnection());
	}
	

}
