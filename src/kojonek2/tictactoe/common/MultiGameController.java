package kojonek2.tictactoe.common;

import java.awt.Toolkit;

import javax.swing.JOptionPane;

import kojonek2.tictactoe.views.GameBoardPanel;
import kojonek2.tictactoe.views.MultiGameBoardPanel;
import kojonek2.tictactoe.views.MultiWinnerAnnouncer;

public class MultiGameController extends GameController {

	private Object lock1 = new Object();
	private Object lock2 = new Object();
	private Object lock3 = new Object();
	private Object lock4 = new Object();
	private Object lock5 = new Object();
	
	public ConnectionToServer connection;
	
	private String clientPlayerName;
	private String opponentPlayerName;
	
	private FieldState clientState;
	private FieldState opponentState;
	
	private boolean isSynchronizing = false;
	private boolean jOptionPaneIsUp = false;
	private boolean hasPlayerLeft = false;
	private boolean hasOpponentLeft = false;
	private boolean hasOpponentWon = false; //TODO set to false when new game starts!!!!!!!!!!!
	private boolean winningPaneWasUp = false;
	
	public MultiGameController(GameBoardPanel gameBoardPanel, int sizeOfBoard, int fieldsNeededForWin, ConnectionToServer connection) {
		super(gameBoardPanel, sizeOfBoard, fieldsNeededForWin);
		this.connection = connection;
	}
	
	public FieldState getClientState() {
		return clientState;
	}
	
	public FieldState getOpponentState() {
		return opponentState;
	}
	
	public String getClientPlayerName() {
		return clientPlayerName;
	}
	
	public String getOpponentPlayerName() {
		return opponentPlayerName;
	}
	
	public void setOpponentWon(boolean b) {
		synchronized(lock3) {
			hasOpponentWon = b;
		}
	}
	
	public boolean hasOpponentWon() {
		synchronized(lock3) {
			return hasOpponentWon;
		}
	}
	
	public void setPlayerLeft(boolean b) {
		synchronized(lock4) {
			hasPlayerLeft = b;
		}
	}
	
	public boolean hasPlayerLeft() {
		synchronized(lock4) {
			return hasPlayerLeft;
		}
	}
	
	public void setOpponentleft(boolean b) {
		synchronized(lock2) {
			hasOpponentLeft = b;
		}
	}
	
	public boolean hasOpponentleft() {
		synchronized(lock2) {
			return hasOpponentLeft;
		}
	}
	
	public boolean isJOptionPaneUp() {
		synchronized(lock1) {
			return jOptionPaneIsUp;
		}
	}

	public void setJOptionPaneUp(boolean jOptionPaneIsUp) {
		synchronized(lock1) {
			this.jOptionPaneIsUp = jOptionPaneIsUp;
		}
	}
	
	public boolean wasWinningPaneUp() {
		synchronized(lock5) {
			return winningPaneWasUp;
		}
	}

	public void setWinningPaneUp(boolean winningPaneWasUp) {
		synchronized(lock5) {
			this.winningPaneWasUp = winningPaneWasUp;
		}
	}

	public void goBackToLobby(boolean opponentLeft) {
		if(hasPlayerLeft()) {
			//player has already left game
			return;
		}
		if(opponentLeft) {
			setOpponentleft(true);
			if(isJOptionPaneUp()) {
				//player is deciding if he want's to leave				
				return;
			}
			if(wasWinningPaneUp()) {
				//player had to chose that he want to leave
				return;
			}
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(gameBoardPanel, "Your opponent has left game.", "Opponent left", JOptionPane.INFORMATION_MESSAGE);
		}
		setPlayerLeft(true);
		((MultiGameBoardPanel) gameBoardPanel).parent.dispose();
		connection.lobbyFrame.setVisible(true);
		connection.toSendQueue.put("Connected:" + clientPlayerName);
	}

	@Override
	public void fieldClicked(Field field) {
		if(isSynchronizing || field.getState() != FieldState.BLANK || playerTurn != clientState) {
			return;
		}
		
		field.setState(clientState);
		gameBoardPanel.repaint();
		
		connection.toSendQueue.put("Game:MadeMove:" + field.getX() + ":" + field.getY() + ":" + clientState.getValue());
		
		FieldState winner = findWinner();
		if (!(winner == FieldState.BLANK) || getNumberOfBlankField() <= 0) {
			connection.toSendQueue.put("Game:Check");
			return;
		}
		
		playerTurn = opponentState;
		gameBoardPanel.updateInformationLabel();
	}
	
	public void processInput(String input) {
		String[] arguments = input.split(":");
		switch(arguments[0]) {
			case "Name":
				processSetPlayersNames(input);
				break;
			case "Info":
				if(arguments[1].equals("Basic")) {
					processGameInformations(arguments);
				} else if(arguments[1].equals("Fields")) {
					processFieldInfo(arguments);
				} else if(arguments[1].equals("Sending")) {
					isSynchronizing = true;
				} else if(arguments[1].equals("Sent")) {
					isSynchronizing = false;
					gameBoardPanel.updateInformationLabel();
					gameBoardPanel.repaint();
				} else if(arguments[1].equals("Updated")) {
					showUpdateMessage();
				} else if(arguments[1].equals("Turn")) {
					playerTurn = FieldState.fromInt(Integer.parseInt(arguments[2]));
				}
				break;
			case "Ended":
				if(arguments[1].equals("Quit")) {
					System.out.println("quit");
					goBackToLobby(true);
				} else if(arguments[1].equals("Winner") || arguments[1].equals("Draw")) {
					setOpponentWon(true);
					announceWinner(arguments);
				}
				break;
			default:
				System.out.println("GameController:     " + input);
		}
	}

	private void processSetPlayersNames(String input) {
		clientPlayerName = connection.getClientPlayerName();
		opponentPlayerName = input.replaceFirst("Name:", "");
	}
	
	private void processGameInformations(String[] arguments) {
		sizeOfGameBoard = Integer.parseInt(arguments[2]);
		createGameBoard(sizeOfGameBoard);
		fieldsNeededForWin = Integer.parseInt(arguments[3]);
		clientState = FieldState.fromInt(Integer.parseInt(arguments[4]));
		opponentState = FieldState.fromInt(Integer.parseInt(arguments[5]));
		playerTurn = FieldState.fromInt(Integer.parseInt(arguments[6]));
	}
	
	private void processFieldInfo(String[] arguments) {
		int x = Integer.parseInt(arguments[2]);
		int y = Integer.parseInt(arguments[3]);
		FieldState state = FieldState.fromInt(Integer.parseInt(arguments[4]));
		gameBoard[x][y].setState(state);
	}
	
	private void showUpdateMessage() {
		Toolkit.getDefaultToolkit().beep();
		String message = "Synchronzied information with server. \n" + (playerTurn == clientState ? "Your turn!" : "Opponent's turn!");
		JOptionPane.showMessageDialog(gameBoardPanel, message, "Synchronized", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void announceWinner(String[] arguments) {
		synchronized(this) {
			while(isJOptionPaneUp()) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.err.println("MultiGameContoller: announce winner");
					e.printStackTrace();
				}
				if(hasOpponentLeft) {
					//opponent left and player was redirected to lobby
					return;
				}
			}
			if(connection.lobbyFrame.isVisible()) {
				//already in lobby
				return;
			}
			
			String information = null;
			if(arguments[1].equals("Winner")) {
				FieldState winner = FieldState.fromInt(Integer.parseInt(arguments[2]));
				if(winner == clientState) {
					information = "You have won!";
				} else if(winner == opponentState) {
					information = "Opponent has won!";
				} else {
					System.err.println("MultiGameController wrong state");
					return;
				}
			}
			if(arguments[0].equals("Draw")) {
				information = "Draw!";
			}
			
			setWinningPaneUp(true);
			new MultiWinnerAnnouncer(this, information).setVisible(true);
		}
	}
}
