package kojonek2.tictactoe.common;

public class Invite {

	private Player sender;
	
	private int sizeOfGameBoard;
	private int fieldsNeededForWin;
	private FieldState yourState;
	private FieldState senderState;
	
	public Invite(Player sender, int sizeOfGameBoard, int fieldsNeededForWin, FieldState yourState, FieldState senderState) {
		this.sender = sender;
		this.sizeOfGameBoard = sizeOfGameBoard;
		this.fieldsNeededForWin = fieldsNeededForWin;
		this.yourState = yourState;
		this.senderState = senderState;
	}
	
	public int getSizeOfGameBoard() {
		return sizeOfGameBoard;
	}
	
	public int getFieldsNeededForWin() {
		return fieldsNeededForWin;
	}
	
	public FieldState getYourState() {
		return yourState;
	}
	
	public FieldState getSenderState() {
		return senderState;
	}
	
	public Player getSender() {
		return sender;
	}
	
	@Override
	public String toString() {
		return sender.getIdOfConnection() + ": " + sender.getNick();
	}
}
