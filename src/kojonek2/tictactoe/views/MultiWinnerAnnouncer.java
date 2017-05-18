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

import kojonek2.tictactoe.common.MultiGameController;

@SuppressWarnings("serial")
public class MultiWinnerAnnouncer extends JDialog {
	
	private final JPanel contentPanel;
	private MultiGameController gameController;
	
	private JLabel lblInfo;
	private JLabel lblGameRequest;
	
	private JButton btnQuit;
	private JButton btnGoToLobby;
	private JButton btnPlayAgain;
	

	
	/**
	 * Create the dialog.
	 */
	public MultiWinnerAnnouncer(MultiGameController gameController, String information) {
		setTitle("Tic Tac Toe - Game Ended");
		this.gameController = gameController;

		setModal(true);

		contentPanel = new JPanel();
		
		componentsInitialization();
		lblInfo.setText(information);
		eventsInitialization();
	}
	
	
	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
	}
	
	/**
	 * Initializing components generated via Window Builder
	 */
	private void componentsInitialization() {
		setMinimumSize(new Dimension(200, 100));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(LocalWinnerAnnouncer.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		setBounds(100, 100, 400, 150);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		lblInfo = new JLabel("Info Label");
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblInfo, BorderLayout.CENTER);
		{
			lblGameRequest = new JLabel("You can press \"Play again\" to request another round");
			lblGameRequest.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblGameRequest, BorderLayout.SOUTH);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnPlayAgain = new JButton("Play Again");
				buttonPane.add(btnPlayAgain);
			}
			{
				btnQuit = new JButton("Quit");
				buttonPane.add(btnQuit);
				getRootPane().setDefaultButton(btnQuit);
			}
			{
				btnGoToLobby = new JButton("Go back to lobby");
				buttonPane.add(btnGoToLobby);
			}
		}
	}
	
}
