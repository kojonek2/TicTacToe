package kojonek2.tictactoe.common;

public class Player {
	
	private String nick;
	
	public Player(String nick) {
		this.nick = nick;
	}
	
	@Override
	public String toString() {
		return nick;
	}
}
