package kojonek2.tictactoe.common;

import kojonek2.tictactoe.views.GameBoardPanel;

public class MultiGameController extends GameController {

	private ConnectionToServer connection;
	
	private String clientPlayerName;
	private String opponentPlayerName;
	
	private FieldState clientState;
	private FieldState opponentState;
	
	public MultiGameController(GameBoardPanel gameBoardPanel, int sizeOfBoard, int fieldsNeededForWin, ConnectionToServer connection) {
		super(gameBoardPanel, sizeOfBoard, fieldsNeededForWin);
		this.connection = connection;
	}

	@Override
	public void fieldClicked(Field clickedField) {
		
	}
	
	public void processInput(String input) {
		String[] arguments = input.split(":");
		switch(arguments[0]) {
			case "Name":
				processSetPlayersNames(input);
				break;
			case "Info":
				processGameInformations(arguments);
				break;
			case "FieldsInfo":
				processFieldInfo(arguments);
				break;
			case "FieldsInfoSent":
				gameBoardPanel.repaint();
				break;
			default:
				System.out.println("Game Controller:" + input);
		}
	}
	
	private void processSetPlayersNames(String input) {
		clientPlayerName = connection.getClientPlayerName();
		opponentPlayerName = input.replaceFirst("Name:", "");
	}
	
	private void processGameInformations(String[] arguments) {
		sizeOfGameBoard = Integer.parseInt(arguments[1]);
		createGameBoard(sizeOfGameBoard);
		fieldsNeededForWin = Integer.parseInt(arguments[2]);
		clientState = FieldState.fromInt(Integer.parseInt(arguments[3]));
		opponentState = FieldState.fromInt(Integer.parseInt(arguments[4]));
		playerTurn = FieldState.fromInt(Integer.parseInt(arguments[5]));
	}
	
	private void processFieldInfo(String[] arguments) {
		int x = Integer.parseInt(arguments[1]);
		int y = Integer.parseInt(arguments[2]);
		FieldState state = FieldState.fromInt(Integer.parseInt(arguments[3]));
		gameBoard[x][y].setState(state);
	}
	
}
