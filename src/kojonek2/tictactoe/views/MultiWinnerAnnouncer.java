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
	
	private JButton btnQuit;
	private JButton btnGoToLobby;
	

	
	/**
	 * Create the dialog.
	 */
	public MultiWinnerAnnouncer(MultiGameController gameController, String information) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
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
		btnGoToLobby.addActionListener((e) -> {
			if(gameController.hasOpponentleft()) {
				gameController.goBackToLobby(false);
			} else  {
			gameController.connection.goBackAndInformOpponent();
			}
			dispose();
		});
		btnQuit.addActionListener((e) -> {
			if(!gameController.hasOpponentleft()) {
				gameController.connection.toSendQueue.put("Game:Quit");
			} 
			System.exit(0);
		});
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
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
