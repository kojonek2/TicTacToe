package kojonek2.tictactoe.common;

public class Field {

	public final static int DRAW = -1;
	public final static int BLANK = 0;
	public final static int CROSS = 1;
	public final static int CIRCLE = 2;

	public static int lengthOfSide;

	private int state = 0;

	private int x;
	private int y;
	
	private LocalGameController gameController;

	public Field(LocalGameController gameController, int x, int y) {
		this.x = x;
		this.y = y;
		this.gameController = gameController;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

	// Recursive function which tells if this field and next in given direction
	// forms long enough sequence
	public boolean isWinningField(int checkedState, int fieldsNeededForWin, int directionX, int directionY) {
		int XOfNextCheckedField = x + directionX;
		int YOfNextCheckedField = y + directionY;

		if (checkedState != state) {
			return false;
		}
		if (fieldsNeededForWin <= 1) {
			return true;
		}
		if (areCoordinatesCorrect(XOfNextCheckedField, YOfNextCheckedField)) {
			return false;
		}
		Field nextFieldToCheck = gameController.getField(XOfNextCheckedField, YOfNextCheckedField);
		return nextFieldToCheck.isWinningField(checkedState, fieldsNeededForWin - 1, directionX, directionY);
	}

	private boolean areCoordinatesCorrect(int x, int y) {
		int sizeOfGameBoard = gameController.getSizeOfGameBoard();
		if (x < 0 || x >= sizeOfGameBoard) {
			return true;
		}
		if (y < 0 || y >= sizeOfGameBoard) {
			return true;
		}
		return false;
	}

}
