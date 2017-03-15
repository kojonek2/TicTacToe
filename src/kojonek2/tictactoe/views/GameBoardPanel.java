package kojonek2.tictactoe.views;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kojonek2.tictactoe.common.Field;

@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel implements ComponentListener, MouseListener {

	private JLabel informationLabel;

	private int sizeOfGameBoard;
	private Field[][] gameBoard;
	private int width, height;
	private int fieldsNeededForWin;
	private boolean gameEnded = false;
	private Field startedDragAtField = null;

	private String crossPlayerName;
	private String circlePlayerName;
	private boolean randomPlayerSwaps = false;

	private int playerTurn = Field.BLANK;

	/**
	 * Create game Board
	 */
	public GameBoardPanel(JLabel jLabel, int sizeOfBoard, int fieldsNeededForWin) {
		super();
		addComponentListener(this);
		addMouseListener(this);

		this.sizeOfGameBoard = sizeOfBoard;
		this.fieldsNeededForWin = fieldsNeededForWin;
		this.informationLabel = jLabel;

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
		System.err.println("GameBoardPnale - setPlayerName: passed wrong playerFieldStatus");
	}

	public void swapPlayers() {
		if (crossPlayerName != null && circlePlayerName != null) {
			String temp = crossPlayerName;
			crossPlayerName = circlePlayerName;
			circlePlayerName = temp;
		}
	}

	public int getSizeOfGameBoard() {
		return sizeOfGameBoard;
	}

	public Field getFieldFromGameBoard(int x, int y) {
		return gameBoard[x][y];
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
		repaint();
	}

	private void nextTurn() {
		if (playerTurn == Field.CROSS) {
			playerTurn = Field.CIRCLE;
		} else if (playerTurn == Field.CIRCLE) {
			playerTurn = Field.CROSS;
		} else {
			playerTurn = ThreadLocalRandom.current().nextInt(1, 3);
		}

		updateInformationLabel();
	}

	private void createGameBoard(int sizeOfGameBoard) {
		gameBoard = new Field[sizeOfGameBoard][sizeOfGameBoard];
		for (int x = 0; x < sizeOfGameBoard; x++) {
			for (int y = 0; y < sizeOfGameBoard; y++) {
				gameBoard[x][y] = new Field(this, x, y);
			}
		}
	}

	private void updateInformationLabel() {
		if (playerTurn == Field.CROSS) {
			SwingUtilities.invokeLater(() -> informationLabel.setText("Turn: " + crossPlayerName));
		} else if (playerTurn == Field.CIRCLE) {
			SwingUtilities.invokeLater(() -> informationLabel.setText("Turn: " + circlePlayerName));
		} else {
			SwingUtilities.invokeLater(() -> informationLabel.setText("Error"));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// this is there because window builder showed divided by 0 error
		/////////////////////////////////////////////////////////////////////////////////
		int divisor = sizeOfGameBoard;
		if (sizeOfGameBoard == 0) {
			divisor = 3;
			System.err.println("GameBoardPanel - paintComponent: this is only for window builder purpose");
		}
		////////////////////////////////////////////////////////////////////////////////////

		if (width > height) {
			Field.lengthOfSide = height / divisor;
		} else {
			Field.lengthOfSide = width / divisor;
		}

		for (int i = 0; i < sizeOfGameBoard; i++) {
			for (int j = 0; j < sizeOfGameBoard; j++) {
				drawImageOfField(g2d, i, j);
				drawLinesOfField(g2d, i, j);
			}
		}
	}

	private void drawImageOfField(Graphics2D g2d, int column, int row) {
		Image imgCross = Toolkit.getDefaultToolkit()
				.getImage(GameBoardPanel.class.getResource("/kojonek2/tictactoe/resources/cross_512.png"));
		Image imgCircle = Toolkit.getDefaultToolkit()
				.getImage(GameBoardPanel.class.getResource("/kojonek2/tictactoe/resources/circle_512.png"));

		int offsetFromCorner = (int) (0.05 * Field.lengthOfSide);
		int dx1 = column * Field.lengthOfSide + offsetFromCorner;
		int dx2 = (column + 1) * Field.lengthOfSide - offsetFromCorner;
		int dy1 = row * Field.lengthOfSide + offsetFromCorner;
		int dy2 = (row + 1) * Field.lengthOfSide - offsetFromCorner;
		int sx1 = 0;
		int sx2 = 512;
		int sy1 = 0;
		int sy2 = 512;

		if (gameBoard[column][row].getState() == Field.CIRCLE) {
			g2d.drawImage(imgCircle, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
		} else if (gameBoard[column][row].getState() == Field.CROSS) {
			g2d.drawImage(imgCross, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
		}
	}

	private void drawLinesOfField(Graphics2D g2d, int column, int row) {
		if (column < sizeOfGameBoard - 1) {
			drawVerticalLine(g2d, column, row);
		}
		if (row < sizeOfGameBoard - 1) {
			drawHorizontalLine(g2d, column, row);
		}
	}

	private void drawVerticalLine(Graphics2D g2d, int column, int row) {
		Image imgLineVertical = Toolkit.getDefaultToolkit()
				.getImage(GameBoardPanel.class.getResource("/kojonek2/tictactoe/resources/line_vertical_512.png"));

		int offsetFromCorner = (int) (0.1 * Field.lengthOfSide);
		int dx1 = column * Field.lengthOfSide + Field.lengthOfSide / 2 + offsetFromCorner;
		int dx2 = (column + 1) * Field.lengthOfSide + Field.lengthOfSide / 2 - offsetFromCorner;
		int dy1 = row * Field.lengthOfSide;
		int dy2 = (row + 1) * Field.lengthOfSide;
		int sx1 = 0;
		int sx2 = 512;
		int sy1 = 0;
		int sy2 = 512;

		g2d.drawImage(imgLineVertical, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
	}

	private void drawHorizontalLine(Graphics2D g2d, int column, int row) {
		Image imgLineHorizontal = Toolkit.getDefaultToolkit()
				.getImage(GameBoardPanel.class.getResource("/kojonek2/tictactoe/resources/line_horizontal_512.png"));

		int offsetFromCorner = (int) (0.1 * Field.lengthOfSide);
		int dx1 = column * Field.lengthOfSide;
		int dx2 = (column + 1) * Field.lengthOfSide;
		int dy1 = row * Field.lengthOfSide + Field.lengthOfSide / 2 + offsetFromCorner;
		int dy2 = (row + 1) * Field.lengthOfSide + Field.lengthOfSide / 2 - offsetFromCorner;
		int sx1 = 0;
		int sx2 = 512;
		int sy1 = 0;
		int sy2 = 512;

		g2d.drawImage(imgLineHorizontal, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
	}

	private int checkForWinner() {
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

				// don't check this same spot (generates always true when i == 0
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

	private void fieldClicked(Field clickedField) {

		// work only if clicked on blank field
		if (!(clickedField.getState() == Field.BLANK)) {
			return;
		}
		clickedField.setState(playerTurn);

		// repaint after changing one of fields
		repaint();

		int winner = checkForWinner();
		if (!(winner == Field.BLANK)) {
			// game ended announce winner
			announceWinner(winner);
			return;
		}
		if (getNumberOfBlankField() <= 0) {
			announceWinner(Field.DRAW);
			return;
		}

		// start next turn
		nextTurn();
	}

	private void announceWinner(int winner) {
		String winnerName = "";
		if (winner == Field.CROSS) {
			winnerName = crossPlayerName;
		} else if (winner == Field.CIRCLE) {
			winnerName = crossPlayerName;
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

				JSONObject jsonFieldData = new JSONObject();
				jsonFieldData.put("state", field.getState());
				jsonFieldData.put("x", x);
				jsonFieldData.put("y", y);

				array.put(jsonFieldData);
			}
		}

		root.put("fields", array);
	}

	public void loadGame(String jsonText) throws JSONException {
		JSONObject root = new JSONObject(jsonText);

		loadGeneralInformations(root);
		loadFields(root);

		updateInformationLabel();
		repaint();
	}

	private void loadGeneralInformations(JSONObject root) throws JSONException {
		sizeOfGameBoard = root.getInt("sizeOfGameBoard");
		fieldsNeededForWin = root.getInt("fieldsNeededForWin");
		crossPlayerName = root.getString("crossPlayerName");
		circlePlayerName = root.getString("circlePlayerName");
		randomPlayerSwaps = root.getBoolean("randomPlayerSwaps");
		playerTurn = root.getInt("playerTurn");

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

	@Override
	public void componentResized(ComponentEvent e) {
		width = (int) getSize().getWidth();
		height = (int) getSize().getHeight();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (gameEnded) {
			return;
		}

		int xOfInteractedField = e.getX() / Field.lengthOfSide;
		int yOfInteracteddField = e.getY() / Field.lengthOfSide;

		// Check if it was clicked at game board
		if (xOfInteractedField > sizeOfGameBoard - 1 || yOfInteracteddField > sizeOfGameBoard - 1) {
			return;
		}

		startedDragAtField = gameBoard[xOfInteractedField][yOfInteracteddField];
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (startedDragAtField == null) {
			return;
		}

		int xOfInteractedField = e.getX() / Field.lengthOfSide;
		int yOfInteractedField = e.getY() / Field.lengthOfSide;

		// Check if it was clicked at game board
		if (xOfInteractedField > sizeOfGameBoard - 1 || yOfInteractedField > sizeOfGameBoard - 1) {
			startedDragAtField = null;
			return;
		}

		if (gameBoard[xOfInteractedField][yOfInteractedField] == startedDragAtField) {
			fieldClicked(startedDragAtField);
			return;
		}

		startedDragAtField = null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
