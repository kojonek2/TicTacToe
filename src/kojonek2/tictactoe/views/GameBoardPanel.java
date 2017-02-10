package kojonek2.tictactoe.views;

import javax.swing.JPanel;

import kojonek2.tictactoe.common.Field;

public class GameBoardPanel extends JPanel {

	private final int sizeOfGameBoard;
	private Field[][] gameBoard;
	
	public GameBoardPanel(int sizeOfBoard) {
		this.sizeOfGameBoard = sizeOfBoard;
		createGameBoard(sizeOfBoard);
	}
	
	private void createGameBoard(int sizeOfGameBoard) {
		gameBoard = new Field[sizeOfGameBoard][sizeOfGameBoard];
		for(int x = 0; x < sizeOfGameBoard; x++) {
			for(int y = 0; y < sizeOfGameBoard; y++) {
				gameBoard[x][y] = new Field();
			}
		}
	}
	
	
	
	
	
}
