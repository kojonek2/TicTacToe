package kojonek2.tictactoe.common;

import kojonek2.tictactoe.views.GameBoardPanel;

public abstract class GameController {
	protected GameBoardPanel gameBoardPanel;

	protected int sizeOfGameBoard;
	protected int fieldsNeededForWin;
	
	//initialization for window builder
	protected Field[][] gameBoard = new Field[0][0];
	
	protected boolean gameEnded = false;

	protected FieldState playerTurn = FieldState.BLANK;
	
	protected String crossPlayerName;
	protected String circlePlayerName;

	/**
	 * Create game Board
	 */
	public GameController(GameBoardPanel gameBoardPanel, int sizeOfBoard, int fieldsNeededForWin) {
		
		this.gameBoardPanel = gameBoardPanel;
		this.sizeOfGameBoard = sizeOfBoard;
		this.fieldsNeededForWin = fieldsNeededForWin;

		createGameBoard(sizeOfBoard);
	}

	public abstract void fieldClicked(Field clickedField);
	
	/**
	 * Sets player name.
	 * 
	 * @param playerFieldStatus
	 *            pass if player is playing as Field.CIRCLE or Field.CROSS
	 * @param name
	 *            player's name
	 */
	public void setPlayerName(FieldState playerFieldStatus, String name) {
		if (playerFieldStatus == FieldState.CIRCLE) {
			circlePlayerName = name;
			return;
		}
		if (playerFieldStatus == FieldState.CROSS) {
			crossPlayerName = name;
			return;
		}
		System.err.println("LocalGameController - setPlayerName: passed wrong playerFieldStatus");
	}

	public boolean getGameEnded() {
		return gameEnded;
	}
	
	public String getCrossPlayerName() {
		return crossPlayerName;
	}
	
	public String getCirclePlayerName() {
		return circlePlayerName;
	}

	public int getSizeOfGameBoard() {
		return sizeOfGameBoard;
	}

	public Field getField(int x, int y) {
		//check for window builder
		if(gameBoard.length == 0) {
			return null;
		}
		return gameBoard[x][y];
	}
	
	public FieldState getPlayerTurn() {
		return playerTurn;
	}

	protected void createGameBoard(int sizeOfGameBoard) {
		gameBoard = new Field[sizeOfGameBoard][sizeOfGameBoard];
		for (int x = 0; x < sizeOfGameBoard; x++) {
			for (int y = 0; y < sizeOfGameBoard; y++) {
				gameBoard[x][y] = new Field(this, x, y);
			}
		}
	}

	protected FieldState findWinner() {
		for (int x = 0; x < gameBoard.length; x++) {
			for (int y = 0; y < gameBoard[x].length; y++) {

				Field field = gameBoard[x][y];
				FieldState stateOfField = field.getState();
				if (stateOfField == FieldState.CIRCLE) {
					if (isFieldCreatingWinningRow(FieldState.CIRCLE, x, y)) {
						return FieldState.CIRCLE;
					}
				} else if (stateOfField == FieldState.CROSS) {
					if (isFieldCreatingWinningRow(FieldState.CROSS, x, y)) {
						return FieldState.CROSS;
					}
				}

			}
		}
		return FieldState.BLANK;
	}

	private boolean isFieldCreatingWinningRow(FieldState stateOfField, int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {

				// don't check this same spot (always generates true when i == 0
				// and j == 0)
				if (!(i == 0 && j == 0)) {
					boolean result = gameBoard[x][y].isWinningField(stateOfField, fieldsNeededForWin, i, j);
					if (result) {
						return true;
					}
				}

			}
		}
		return false;
	}

	protected int getNumberOfBlankField() {
		int result = 0;
		for (Field[] array : gameBoard) {
			for (Field field : array) {
				if (field.getState() == FieldState.BLANK) {
					result++;
				}
			}
		}
		return result;
	}
}
