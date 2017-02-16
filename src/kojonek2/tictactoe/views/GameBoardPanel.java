package kojonek2.tictactoe.views;

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

import kojonek2.tictactoe.common.Field;

@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel implements ComponentListener, MouseListener {

	private JLabel informationLabel;
	
	private final int sizeOfGameBoard;
	private Field[][] gameBoard;
	private int width, height;
	private int fieldsNeededForWin;
	
	private int playerTurn = Field.BLANK;

	public GameBoardPanel(JLabel jLabel, int sizeOfBoard, int fieldsNeededForWin) {
		super();
		addComponentListener(this);
		addMouseListener(this);
		this.sizeOfGameBoard = sizeOfBoard;
		this.fieldsNeededForWin = fieldsNeededForWin;
		this.informationLabel = jLabel;
		createGameBoard(sizeOfBoard);
		nextTurn();
	}

	public int getSizeOfGameBoard() {
		return sizeOfGameBoard;
	}

	public Field getFieldFromGameBoard(int x, int y) {
		return gameBoard[x][y];
	}
	
	private void nextTurn() {
		if(playerTurn == Field.CROSS) {
			playerTurn = Field.CIRCLE;
		} else if (playerTurn == Field.CIRCLE) {
			playerTurn = Field.CROSS;
		} else {
			playerTurn = ThreadLocalRandom.current().nextInt(1, 3);
		}
		
		updateInfoLabel();
	}

	private void createGameBoard(int sizeOfGameBoard) {
		gameBoard = new Field[sizeOfGameBoard][sizeOfGameBoard];
		for (int x = 0; x < sizeOfGameBoard; x++) {
			for (int y = 0; y < sizeOfGameBoard; y++) {
				gameBoard[x][y] = new Field(this, x, y);
			}
		}
	}
	
	private void updateInfoLabel() {
		if(playerTurn == Field.CROSS) {
			SwingUtilities.invokeLater(() -> informationLabel.setText("Cross"));
		} else if(playerTurn == Field.CIRCLE) {
			SwingUtilities.invokeLater(() -> informationLabel.setText("Circle"));
		} else {
			SwingUtilities.invokeLater(() -> informationLabel.setText("Error"));
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
				
				//don't check this same spot (generates always true when i == 0 and j == 0)
				if(!(i == 0 && j == 0)) {
					boolean result = gameBoard[x][y].isWinningField(stateOfField, fieldsNeededForWin, i, j);
					if (result) {
						return true;
					}
				}
				
			}
		}
		return false;
	}

	private void fieldClicked(int x, int y) {
		Field clickedField = gameBoard[x][y];
		
		//work only if clicked on blank field
		if(!(clickedField.getState() == Field.BLANK)) {
			return;
		}
		clickedField.setState(playerTurn);
		
		//repaint after changing one of fields
		repaint();
		
		if(!(checkForWinner() == Field.BLANK)) {
			JDialog dialog = new WinnerAnnouncer();
			dialog.setVisible(true);
			return;
		}
		
		//start next turn
		nextTurn();
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		width = (int) getSize().getWidth();
		height = (int) getSize().getHeight();
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int XOfClickedField = e.getX() / Field.lengthOfSide;
		int YOfClickedField = e.getY() / Field.lengthOfSide;
		if (XOfClickedField > sizeOfGameBoard - 1 || YOfClickedField > sizeOfGameBoard - 1) { 
			return;
		}
		
		fieldClicked(XOfClickedField, YOfClickedField);
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
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
