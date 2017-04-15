package kojonek2.tictactoe.common;

public enum InviteState {
	NOT_SENT(""),
	SENT("Wysłano zaproszenie"),
	DECLINED("Zaproszenie zostało odrzucone"),
	CANCELED("Zaproszenie zostało anulowane");
	
	private String description;
	
	private InviteState(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
