package kojonek2.tictactoe.common;

public class Player {
	
	private String nick;
	private int idOfConnection;
	
	public Player(int idOfConnection, String nick) {
		this.idOfConnection = idOfConnection;
		this.nick = nick;
	}
	
	int getIdOfConnection() {
		return idOfConnection;
	}
	
	@Override
	public String toString() {
		return idOfConnection + ": " + nick;
	}
}
