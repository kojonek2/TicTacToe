package kojonek2.tictactoe.common;

public class Player {
	
	private String nick;
	private int idOfConnection;
	private InviteState inviteState;
	
	public Player(int idOfConnection, String nick) {
		this.idOfConnection = idOfConnection;
		this.nick = nick;
		inviteState = InviteState.NOT_SENT;
	}
	
	public int getIdOfConnection() {
		return idOfConnection;
	}
	
	public String getNick() {
		return nick;
	}
	
	synchronized public void setInviteState(InviteState state) {
		inviteState = state;
	}
	
	synchronized public InviteState getInviteState() {
		return inviteState;
	}
	
	@Override
	public String toString() {
		return idOfConnection + ": " + nick + " " + inviteState.getDescription();
	}
}
