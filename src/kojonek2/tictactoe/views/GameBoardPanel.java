package kojonek2.tictactoe.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import kojonek2.tictactoe.common.Field;

public class GameBoardPanel extends JPanel implements ComponentListener {

	private final int sizeOfGameBoard;
	private Field[][] gameBoard;
	private int width, height;
	private int fieldsNeededForWin;

	public GameBoardPanel(int sizeOfBoard, int fieldsNeededForWin) {
		super();
		addComponentListener(this);
		this.sizeOfGameBoard = sizeOfBoard;
		this.fieldsNeededForWin = fieldsNeededForWin;
		createGameBoard(sizeOfBoard);
	}

	public int getSizeOfGameBoard() {
		return sizeOfGameBoard;
	}

	public Field getFieldFromGameBoard(int x, int y) {
		return gameBoard[x][y];
	}

	private void createGameBoard(int sizeOfGameBoard) {
		gameBoard = new Field[sizeOfGameBoard][sizeOfGameBoard];
		for (int x = 0; x < sizeOfGameBoard; x++) {
			for (int y = 0; y < sizeOfGameBoard; y++) {
				gameBoard[x][y] = new Field(this, x, y);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (width > height) {
			Field.lengthOfSide = height / sizeOfGameBoard;
		} else {
			Field.lengthOfSide = width / sizeOfGameBoard;
		}

		for (int i = 0; i < sizeOfGameBoard; i++) {
			for (int j = 0; j < sizeOfGameBoard; j++) {
				drawImageOfField(g2d, i, j);
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

	private int checkForWinner() {
		for (int x = 0; x < gameBoard.length; x++) {
			for (int y = 0; y < gameBoard[x].length; y++) {

				Field field = gameBoard[x][y];
				int stateOfField = field.getState();
				if (stateOfField == Field.CIRCLE) {
					if (isFieldCreatingWinningRow(Field.CIRCLE, x, y)) {
						System.out.println("wykrywa kolo");
						return Field.CIRCLE;
					}
				} else if (stateOfField == Field.CROSS) {
					if (isFieldCreatingWinningRow(Field.CROSS, x, y)) {
						System.out.println("wykrywa krzyzyk");
						return Field.CROSS;
					}
				}

			}
		}
		System.out.println("nikt nie wygrał");
		return Field.BLANK;
	}

	private boolean isFieldCreatingWinningRow(int stateOfField, int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; i <= 1; i++) {

				boolean result = gameBoard[x][y].isWinningField(stateOfField, fieldsNeededForWin, i, j);
				if (result) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		width = (int) getSize().getWidth();
		height = (int) getSize().getHeight();
		repaint();
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

}
