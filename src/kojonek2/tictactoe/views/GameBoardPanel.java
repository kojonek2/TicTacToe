package kojonek2.tictactoe.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import kojonek2.tictactoe.common.Field;
import kojonek2.tictactoe.common.FieldState;
import kojonek2.tictactoe.common.GameController;

@SuppressWarnings("serial")
public abstract class GameBoardPanel extends JPanel implements ComponentListener, MouseListener {

	private JLabel informationLabel;
	
	private Field startedDragAtField = null;
	
	private GameController gameController;

	/**
	 * Create game Board
	 */
	public GameBoardPanel() {
		super();
		
		addComponentListener(this);
		addMouseListener(this);
	}
	
	public abstract void updateInformationLabel();
	
	public void setGameController(GameController gameController) {
		if(gameController == null) {
			System.err.println("GameBoardPanel:setGameController - game controller can't be null");
			return;
		}
		this.gameController = gameController;
	}
	
	public void setInformationLabel(JLabel informationLabel) {
		if(informationLabel == null) {
			System.err.println("GameBoardPannel:setinformationLablel - label is null");
		}
		this.informationLabel = informationLabel;
	}
	
	public JLabel getInformationLabel() {
		return informationLabel;
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// this is there because window builder showed divided by 0 error
		/////////////////////////////////////////////////////////////////////////////////
		
		if (gameController.getField(0, 0) == null) {
			System.err.println("GameBoardPanel - paintComponent: this is only for window builder purpose");
			return;
		}
		////////////////////////////////////////////////////////////////////////////////////

		int sizeOfGameBoard = gameController.getSizeOfGameBoard();
		int width = getWidth();
		int height = getHeight();
		
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
				.getImage(LocalGameBoardPanel.class.getResource("/kojonek2/tictactoe/resources/cross_512.png"));
		Image imgCircle = Toolkit.getDefaultToolkit()
				.getImage(LocalGameBoardPanel.class.getResource("/kojonek2/tictactoe/resources/circle_512.png"));

		int offsetFromCorner = (int) (0.05 * Field.lengthOfSide);
		int dx1 = column * Field.lengthOfSide + offsetFromCorner;
		int dx2 = (column + 1) * Field.lengthOfSide - offsetFromCorner;
		int dy1 = row * Field.lengthOfSide + offsetFromCorner;
		int dy2 = (row + 1) * Field.lengthOfSide - offsetFromCorner;
		int sx1 = 0;
		int sx2 = 512;
		int sy1 = 0;
		int sy2 = 512;

		if (gameController.getField(column, row).getState() == FieldState.CIRCLE) {
			g2d.drawImage(imgCircle, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
		} else if (gameController.getField(column, row).getState() == FieldState.CROSS) {
			g2d.drawImage(imgCross, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
		}
	}

	private void drawLinesOfField(Graphics2D g2d, int column, int row) {
		int sizeOfGameBoard = gameController.getSizeOfGameBoard();
		
		if (column < sizeOfGameBoard - 1) {
			drawVerticalLine(g2d, column, row);
		}
		if (row < sizeOfGameBoard - 1) {
			drawHorizontalLine(g2d, column, row);
		}
	}

	private void drawVerticalLine(Graphics2D g2d, int column, int row) {
		Image imgLineVertical = Toolkit.getDefaultToolkit()
				.getImage(LocalGameBoardPanel.class.getResource("/kojonek2/tictactoe/resources/line_vertical_512.png"));

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
				.getImage(LocalGameBoardPanel.class.getResource("/kojonek2/tictactoe/resources/line_horizontal_512.png"));

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

	@Override
	public void componentResized(ComponentEvent e) {
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (gameController.getGameEnded()) {
			return;
		}

		int sizeOfGameBoard = gameController.getSizeOfGameBoard();
		int xOfInteractedField = e.getX() / Field.lengthOfSide;
		int yOfInteractedField = e.getY() / Field.lengthOfSide;

		// Check if it was clicked at game board (JPanel is bigger than game board)
		if (xOfInteractedField > sizeOfGameBoard - 1 || yOfInteractedField > sizeOfGameBoard - 1) {
			return;
		}

		startedDragAtField = gameController.getField(xOfInteractedField, yOfInteractedField);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (startedDragAtField == null) {
			return;
		}

		int sizeOfGameBoard = gameController.getSizeOfGameBoard();
		int xOfInteractedField = e.getX() / Field.lengthOfSide;
		int yOfInteractedField = e.getY() / Field.lengthOfSide;

		// Check if it was clicked at game board
		if (xOfInteractedField > sizeOfGameBoard - 1 || yOfInteractedField > sizeOfGameBoard - 1) {
			startedDragAtField = null;
			return;
		}

		Field field = gameController.getField(xOfInteractedField, yOfInteractedField);
		if (field == startedDragAtField) {
			gameController.fieldClicked(startedDragAtField);
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
