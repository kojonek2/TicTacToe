package kojonek2.tictactoe.views;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import kojonek2.tictactoe.common.FieldState;
import kojonek2.tictactoe.common.LocalGameController;

@SuppressWarnings("serial")
public class LocalGameBoardPanel extends GameBoardPanel {

	/**
	 * Create game Board
	 */
	public LocalGameBoardPanel(JLabel jLabel, int sizeOfBoard, int fieldsNeededForWin) {
		super();
		setGameController(new LocalGameController(this, sizeOfBoard, fieldsNeededForWin));
		setInformationLabel(jLabel);
	}
	
	@Override
	public void updateInformationLabel() {
		FieldState playerTurn = getGameController().getPlayerTurn();
		if (playerTurn == FieldState.CROSS) {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Turn: " + ((LocalGameController) getGameController()).getCrossPlayerName()));
		} else if (playerTurn == FieldState.CIRCLE) {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Turn: " + ((LocalGameController) getGameController()).getCirclePlayerName()));
		} else {
			SwingUtilities.invokeLater(() -> getInformationLabel().setText("Error"));
		}
	}
	

}
