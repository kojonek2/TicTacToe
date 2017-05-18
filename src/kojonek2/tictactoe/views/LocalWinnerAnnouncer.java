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

import kojonek2.tictactoe.common.LocalGameController;

@SuppressWarnings("serial")
public class LocalWinnerAnnouncer extends JDialog {

	private final JPanel contentPanel;
	private LocalGameController gameController;
	
	private JLabel lblInfo;
	
	private JButton btnNewGame;
	private JButton btnCancel;
	
	/**
	 * Create the dialog.
	 */
	public LocalWinnerAnnouncer(LocalGameController gameController, String winnerName) {
		setTitle("Tic Tac Toe - Game Ended");
		this.gameController = gameController;

		setModal(true);

		contentPanel = new JPanel();
		componentsInitialization();
		eventsInitialization();

		if (winnerName != null) {
			setInfoLabelText(winnerName + " has won!");
		} else {
			setInfoLabelText("Draw!");
		}
	}

	private void setInfoLabelText(String text) {
		lblInfo.setText(text);
	}

	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		btnCancel.addActionListener((e) -> dispose());

		btnNewGame.addActionListener((e) -> {
			gameController.startNewGame();
			dispose();
		});
	}

	/**
	 * Initializing components generated via Window Builder
	 */
	private void componentsInitialization() {
		setMinimumSize(new Dimension(200, 100));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(LocalWinnerAnnouncer.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
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
