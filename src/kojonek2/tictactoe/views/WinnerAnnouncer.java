package kojonek2.tictactoe.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import kojonek2.tictactoe.common.Field;

@SuppressWarnings("serial")
public class WinnerAnnouncer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblInfo;
	private JButton btnNewGame;
	private JButton btnCancel;
	private GameBoardPanel gameBoard;

	/**
	 * Create the dialog.
	 */
	public WinnerAnnouncer(GameBoardPanel gameBoard, int winner) {
		setTitle("Tic Tac Toe - Game Ended");
		this.gameBoard = gameBoard;
		
		//Block other windows
		setModal(true);
		
		componentsInitialization();
		eventsInitialization();
		
		//make changes after initializing components
		setInfoLabel(winner);
	}
	
	private void setInfoLabel(int winner) {
		if(winner == Field.CROSS) {
			lblInfo.setText("Cross has won!");
		} else if (winner == Field.CIRCLE) {
			lblInfo.setText("Circle has won!");
		} else {
			System.err.println("WinnerAnnouncer - no winner");
		}
	}
	
	/**
	* Adding event listeners to components
	*/
	private void eventsInitialization() {
		//close windows after clicking "cancel" button
		btnCancel.addActionListener((e) -> dispose());
		
		btnNewGame.addActionListener((e) -> {
			gameBoard.startNewGame();
			dispose();
		});
	}
	
	/**
	* Initializing components generated via Window Builder
	*/
	private void componentsInitialization() {
		setMinimumSize(new Dimension(200,100));
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinnerAnnouncer.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		setBounds(100, 100, 250, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		lblInfo = new JLabel("Info Label");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblInfo, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnNewGame = new JButton("New Game");
				buttonPane.add(btnNewGame);
				getRootPane().setDefaultButton(btnNewGame);
			}
			{
				btnCancel = new JButton("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}
	
}
