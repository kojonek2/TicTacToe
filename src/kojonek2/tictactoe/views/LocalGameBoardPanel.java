package kojonek2.tictactoe.views;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import kojonek2.tictactoe.common.FieldState;

@SuppressWarnings("serial")
public class LocalGameBoardPanel extends GameBoardPanel {

	/**
	 * Create game Board
	 */
	public LocalGameBoardPanel(JLabel jLabel, int sizeOfBoard, int fieldsNeededForWin) {
		super();
		createGameController(sizeOfBoard, fieldsNeededForWin);
		setInformationLabel(jLabel);
	}
	
	public void updateInformationLabel() {
		FieldState playerTurn = getGameController().getPlayerTurn();
		if (playerTurn == FieldState.CROSS) {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Turn: " + getGameController().getCrossPlayerName()));
		} else if (playerTurn == FieldState.CIRCLE) {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Turn: " + getGameController().getCirclePlayerName()));
		} else {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Error"));
		}
	}
	

}
