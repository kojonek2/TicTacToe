package kojonek2.tictactoe.views;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import kojonek2.tictactoe.common.ConnectionToServer;
import kojonek2.tictactoe.common.FieldState;
import kojonek2.tictactoe.common.MultiGameController;

@SuppressWarnings("serial")
public class MultiGameBoardPanel extends GameBoardPanel {

	public MultiGameBoardPanel(JLabel jLabel, int sizeOfBoard, int fieldsNeededForWin, ConnectionToServer connection) {
		super();
		setGameController(new MultiGameController(this, sizeOfBoard, fieldsNeededForWin, connection));
		setInformationLabel(jLabel);
	}
	
	@Override
	public void updateInformationLabel() {
		MultiGameController gameController = (MultiGameController) getGameController();
		FieldState playerTurn = gameController.getPlayerTurn();
		if (playerTurn == gameController.getClientState()) {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Turn: " + gameController.getClientPlayerName()));
		} else if (playerTurn == gameController.getOpponentState()) {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Turn: " + gameController.getOpponentPlayerName()));
		} else {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Error"));
		}
	}

}
