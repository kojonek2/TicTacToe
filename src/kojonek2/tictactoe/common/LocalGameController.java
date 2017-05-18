package kojonek2.tictactoe.common;

import java.awt.EventQueue;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kojonek2.tictactoe.views.GameBoardPanel;
import kojonek2.tictactoe.views.LocalWinnerAnnouncer;

public class LocalGameController extends GameController {
	
	private boolean randomPlayerSwaps = false;
	
	private String crossPlayerName;
	private String circlePlayerName;

	/**
	 * Create game Board
	 */
	public LocalGameController(GameBoardPanel gameBoardPanel, int sizeOfBoard, int fieldsNeededForWin) {
		super(gameBoardPanel, sizeOfBoard, fieldsNeededForWin);
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
	
	public String getCrossPlayerName() {
		return crossPlayerName;
	}
	
	public String getCirclePlayerName() {
		return circlePlayerName;
	}
	
	public void swapPlayers() {
		if (crossPlayerName != null && circlePlayerName != null) {
			String temp = crossPlayerName;
			crossPlayerName = circlePlayerName;
			circlePlayerName = temp;
		}
	}
	
	@Override
	public void fieldClicked(Field clickedField) {

		// do something only if clicked on blank field
		if (!(clickedField.getState() == FieldState.BLANK)) {
			return;
		}
		
		clickedField.setState(playerTurn);
		gameBoardPanel.repaint();

		FieldState winner = findWinner();
		if (!(winner == FieldState.BLANK)) {
			// game ended announce winner
			announceWinner(winner);
			return;
		}
		
		if (getNumberOfBlankField() <= 0) {
			announceWinner(FieldState.DRAW);
			return;
		}

		nextTurn();
	}

	public void startNewGame() {
		// reverting state of the all variables to state from start of the game
		for (int x = 0; x < gameBoard.length; x++) {
			for (int y = 0; y < gameBoard.length; y++) {
				gameBoard[x][y].setState(FieldState.BLANK);
			}
		}
		
		playerTurn = FieldState.BLANK;

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
		if (playerTurn == FieldState.CROSS) {
			playerTurn = FieldState.CIRCLE;
		} else if (playerTurn == FieldState.CIRCLE) {
			playerTurn = FieldState.CROSS;
		} else {
			int random = ThreadLocalRandom.current().nextInt(1, 3);
			playerTurn = FieldState.fromInt(random);
		}

		gameBoardPanel.updateInformationLabel();
	}

	private void announceWinner(FieldState winner) {
		String winnerName = "";
		if (winner == FieldState.CROSS) {
			winnerName = crossPlayerName;
		} else if (winner == FieldState.CIRCLE) {
			winnerName = circlePlayerName;
		} else if (winner == FieldState.DRAW) {
			winnerName = null;
		} else {
			System.err.println("GameBoradPanel - announceWinner: invalid Winner");
		}
		
		JDialog dialog = new LocalWinnerAnnouncer(this, winnerName);
		dialog.setVisible(true);
		gameEnded = true;
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
		root.put("playerTurn", playerTurn.getValue());
	}

	private void saveFields(JSONObject root) throws JSONException {
		JSONArray array = new JSONArray();

		for (int x = 0; x < sizeOfGameBoard; x++) {
			for (int y = 0; y < sizeOfGameBoard; y++) {
				Field field = gameBoard[x][y];

				JSONObject jsonField = new JSONObject();
				jsonField.put("state", field.getState().getValue());
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
		playerTurn = FieldState.fromInt(root.getInt("playerTurn"));

		// data needed to generate game board has already been set
		createGameBoard(sizeOfGameBoard);
	}

	private void loadFields(JSONObject root) throws JSONException {
		JSONArray array = root.getJSONArray("fields");

		for (int i = 0; i < array.length(); i++) {
			JSONObject field = array.getJSONObject(i);

			int x = field.getInt("x");
			int y = field.getInt("y");
			FieldState state = FieldState.fromInt(field.getInt("state"));
			if(state == null) {
				System.err.println("LocalGameController:loadFields -- error during loading save");
			}

			gameBoard[x][y].setState(state);
		}
	}
}
