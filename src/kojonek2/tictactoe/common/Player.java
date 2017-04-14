package kojonek2.tictactoe.common;

public class Player {
	
	private String nick;
	private int idOfConnection;
	private boolean sentInvite;
	
	public Player(int idOfConnection, String nick) {
		this.idOfConnection = idOfConnection;
		this.nick = nick;
		sentInvite = false;
	}
	
	int getIdOfConnection() {
		return idOfConnection;
	}
	
	String getNick() {
		return nick;
	}
	
	void setSentInvite(boolean b) {
		sentInvite = b;
	}
	
	@Override
	public String toString() {
		if(sentInvite) {
			return idOfConnection + ": " + nick + " | Wysłano";
		}
		return idOfConnection + ": " + nick;
	}
}
