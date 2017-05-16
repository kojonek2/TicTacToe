package kojonek2.tictactoe.views;

import javax.swing.JLabel;

import kojonek2.tictactoe.common.ConnectionToServer;
import kojonek2.tictactoe.common.MultiGameController;

@SuppressWarnings("serial")
public class MultiGameBoardPanel extends GameBoardPanel {

	public MultiGameBoardPanel(JLabel jLabel, int sizeOfBoard, int fieldsNeededForWin, ConnectionToServer connection) {
		super();
		setGameController(new MultiGameController(this, sizeOfBoard, fieldsNeededForWin, connection));
		//setInformationLabel(jLabel);
	}
	
	@Override
	public void updateInformationLabel() {

	}

}
