package kojonek2.tictactoe.common;

public class Field {

	public final static int BLANK = 0;
	public final static int CROSS = 1;
	public final static int CIRCLE = 2;
	
	public static int lengthOfSide;
	
	private int state = 1;
	
	public Field() {
		
	}
	
	public void setStateToCircle() {
		state = Field.CIRCLE;
	}
	
	public void setStateToCross() {
		state = Field.CROSS;
	}
	
	public int getState() {
		return state;
	}
	
	
}
