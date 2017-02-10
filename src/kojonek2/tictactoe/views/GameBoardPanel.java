package kojonek2.tictactoe.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import kojonek2.tictactoe.common.Field;

public class GameBoardPanel extends JPanel implements ComponentListener {

	private final int sizeOfGameBoard;
	private Field[][] gameBoard;
	private int width, height;
	
	public GameBoardPanel(int sizeOfBoard) {
		super();
		addComponentListener(this);
		this.sizeOfGameBoard = sizeOfBoard;
		createGameBoard(sizeOfBoard);
	}

	private void createGameBoard(int sizeOfGameBoard) {
		gameBoard = new Field[sizeOfGameBoard][sizeOfGameBoard];
		for (int x = 0; x < sizeOfGameBoard; x++) {
			for (int y = 0; y < sizeOfGameBoard; y++) {
				gameBoard[x][y] = new Field();
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

	@Override
	public void componentResized(ComponentEvent e) {
		width = (int) getSize().getWidth();
		height = (int) getSize().getHeight();
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
