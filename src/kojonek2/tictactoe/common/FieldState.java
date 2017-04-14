package kojonek2.tictactoe.common;

public enum FieldState {
	DRAW(-1, "Draw"),
	BLANK(0, "Blank"),
	CROSS(1, "Cross"),
	CIRCLE(2, "Circle"),
	RANDOM(3, "Random");
	
	private int value;
	private String name;
	
	private FieldState(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public static FieldState fromInt(int i) {
		for(FieldState state : FieldState.values()) {
			if(state.getValue() == i) {
				return state;
			}
		}
		return null;
	}
}
