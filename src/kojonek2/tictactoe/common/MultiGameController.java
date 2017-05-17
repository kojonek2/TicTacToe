package kojonek2.tictactoe.common;

import java.awt.Toolkit;

import javax.swing.JOptionPane;

import kojonek2.tictactoe.views.GameBoardPanel;

public class MultiGameController extends GameController {

	private ConnectionToServer connection;
	
	private String clientPlayerName;
	private String opponentPlayerName;
	
	private FieldState clientState;
	private FieldState opponentState;
	
	private boolean isSynchronizing = false;
	
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
		JOptionPane.showMessageDialog(gameBoardPanel, message, "Synchronized", JOptionPane.WARNING_MESSAGE);
	}
}
