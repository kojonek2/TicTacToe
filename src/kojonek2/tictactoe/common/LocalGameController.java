package kojonek2.tictactoe.common;

import java.awt.EventQueue;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kojonek2.tictactoe.views.GameLocalBoardPanel;
import kojonek2.tictactoe.views.WinnerAnnouncer;

public class LocalGameController {
	
	private GameLocalBoardPanel gameBoardPanel;

	private int sizeOfGameBoard;
	private int fieldsNeededForWin;
	
	//initialization for window builder
	private Field[][] gameBoard = new Field[0][0];
	
	private boolean randomPlayerSwaps = false;
	private boolean gameEnded = false;

	private int playerTurn = Field.BLANK;
	
	private String crossPlayerName;
	private String circlePlayerName;

	/**
	 * Create game Board
	 */
	public LocalGameController(GameLocalBoardPanel gameBoardPanel, int sizeOfBoard, int fieldsNeededForWin) {
		
		this.gameBoardPanel = gameBoardPanel;
		this.sizeOfGameBoard = sizeOfBoard;
		this.fieldsNeededForWin = fieldsNeededForWin;

		createGameBoard(sizeOfBoard);
	}

	public void setRandomPlayerSwaps(boolean b) {
		randomPlayerSwaps = b;
	}

	/**
	 * Sets player name.
	 * 
	 * @param playerFieldStatus
	 *            pass if player is playing as Field.CIRCLE or Field.CROSS
	 * @param name
	 *            player's name
	 */
	public void setPlayerName(int playerFieldStatus, String name) {
		if (playerFieldStatus == Field.CIRCLE) {
			circlePlayerName = name;
			return;
		}
		if (playerFieldStatus == Field.CROSS) {
			crossPlayerName = name;
			return;
		}
		System.err.println("LocalGameController - setPlayerName: passed wrong playerFieldStatus");
	}

	public void swapPlayers() {
		if (crossPlayerName != null && circlePlayerName != null) {
			String temp = crossPlayerName;
			crossPlayerName = circlePlayerName;
			circlePlayerName = temp;
		}
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
	
	public int getPlayerTurn() {
		return playerTurn;
	}
	
	public void fieldClicked(Field clickedField) {

		// do something only if clicked on blank field
		if (!(clickedField.getState() == Field.BLANK)) {
			return;
		}
		
		clickedField.setState(playerTurn);
		gameBoardPanel.repaint();

		int winner = findWinner();
		if (!(winner == Field.BLANK)) {
			// game ended announce winner
			announceWinner(winner);
			return;
		}
		
		if (getNumberOfBlankField() <= 0) {
			announceWinner(Field.DRAW);
			return;
		}

		nextTurn();
	}

	public void startNewGame() {
		// reverting state of the all variables to state from start of the game
		for (int x = 0; x < gameBoard.length; x++) {
			for (int y = 0; y < gameBoard.length; y++) {
				gameBoard[x][y].setState(Field.BLANK);
			}
		}
		
		playerTurn = Field.BLANK;

		if (randomPlayerSwaps) {
			if (ThreadLocalRandom.current().nextInt(2) == 0) {
				swapPlayers();
			}
		}

		// it needs to be done like that. Doesn't work in other way. Probably
		// because this is called from WinnerAnnouncer
		EventQueue.invokeLater(() -> gameEnded = false);
		nextTurn();
		gameBoardPanel.repaint();
	}

	private void nextTurn() {
		if (playerTurn == Field.CROSS) {
			playerTurn = Field.CIRCLE;
		} else if (playerTurn == Field.CIRCLE) {
			playerTurn = Field.CROSS;
		} else {
			playerTurn = ThreadLocalRandom.current().nextInt(1, 3);
		}

		gameBoardPanel.updateInformationLabel();
	}

	private void createGameBoard(int sizeOfGameBoard) {
		gameBoard = new Field[sizeOfGameBoard][sizeOfGameBoard];
		for (int x = 0; x < sizeOfGameBoard; x++) {
			for (int y = 0; y < sizeOfGameBoard; y++) {
				gameBoard[x][y] = new Field(this, x, y);
			}
		}
	}

	private int findWinner() {
		for (int x = 0; x < gameBoard.length; x++) {
			for (int y = 0; y < gameBoard[x].length; y++) {

				Field field = gameBoard[x][y];
				int stateOfField = field.getState();
				if (stateOfField == Field.CIRCLE) {
					if (isFieldCreatingWinningRow(Field.CIRCLE, x, y)) {
						return Field.CIRCLE;
					}
				} else if (stateOfField == Field.CROSS) {
					if (isFieldCreatingWinningRow(Field.CROSS, x, y)) {
						return Field.CROSS;
					}
				}

			}
		}
		return Field.BLANK;
	}

	private boolean isFieldCreatingWinningRow(int stateOfField, int x, int y) {
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

	private void announceWinner(int winner) {
		String winnerName = "";
		if (winner == Field.CROSS) {
			winnerName = crossPlayerName;
		} else if (winner == Field.CIRCLE) {
			winnerName = circlePlayerName;
		} else if (winner == Field.DRAW) {
			winnerName = null;
		} else {
			System.err.println("GameBoradPanel - announceWinner: invalid Winner");
		}
		
		JDialog dialog = new WinnerAnnouncer(this, winnerName);
		dialog.setVisible(true);
		gameEnded = true;
	}

	private int getNumberOfBlankField() {
		int result = 0;
		for (Field[] array : gameBoard) {
			for (Field field : array) {
				if (field.getState() == Field.BLANK) {
					result++;
				}
			}
		}
		return result;
	}

	public String saveGame() throws JSONException {
		JSONObject root = new JSONObject();

		saveGeneralInformations(root);
		saveFields(root);

		return root.toString();
	}

	private void saveGeneralInformations(JSONObject root) throws JSONException {
		root.put("sizeOfGameBoard", sizeOfGameBoard);
		root.put("fieldsNeededForWin", fieldsNeededForWin);
		root.put("crossPlayerName", crossPlayerName);
		root.put("circlePlayerName", circlePlayerName);
		root.put("randomPlayerSwaps", randomPlayerSwaps);
		root.put("playerTurn", playerTurn);
	}

	private void saveFields(JSONObject root) throws JSONException {
		JSONArray array = new JSONArray();

		for (int x = 0; x < sizeOfGameBoard; x++) {
			for (int y = 0; y < sizeOfGameBoard; y++) {
				Field field = gameBoard[x][y];

				JSONObject jsonField = new JSONObject();
				jsonField.put("state", field.getState());
				jsonField.put("x", x);
				jsonField.put("y", y);

				array.put(jsonField);
			}
		}

		root.put("fields", array);
	}

	public void loadGame(String jsonText) throws JSONException {
		JSONObject root = new JSONObject(jsonText);

		loadGeneralInformations(root);
		loadFields(root);

		gameBoardPanel.updateInformationLabel();
		gameBoardPanel.repaint();
	}

	private void loadGeneralInformations(JSONObject root) throws JSONException {
		sizeOfGameBoard = root.getInt("sizeOfGameBoard");
		fieldsNeededForWin = root.getInt("fieldsNeededForWin");
		crossPlayerName = root.getString("crossPlayerName");
		circlePlayerName = root.getString("circlePlayerName");
		randomPlayerSwaps = root.getBoolean("randomPlayerSwaps");
		playerTurn = root.getInt("playerTurn");

		// data needed to generate game board has already been set
		createGameBoard(sizeOfGameBoard);
	}

	private void loadFields(JSONObject root) throws JSONException {
		JSONArray array = root.getJSONArray("fields");

		for (int i = 0; i < array.length(); i++) {
			JSONObject field = array.getJSONObject(i);

			int x = field.getInt("x");
			int y = field.getInt("y");
			int state = field.getInt("state");

			gameBoard[x][y].setState(state);
		}
	}

}
