package kojonek2.tictactoe.common;

public class Field {

	public final static int BLANK = 0;
	public final static int CROSS = 1;
	public final static int CIRCLE = 2;
	
	public static int height;
	public static int width;
	
	private int state = 2;
	
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
