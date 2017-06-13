package kojonek2.tictactoe.views;

import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kojonek2.tictactoe.common.ConnectionToServer;
import kojonek2.tictactoe.common.MultiGameController;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MultiGameMainFrame extends JFrame {
	
	private ConnectionToServer connection;

	private JPanel contentPane;
	private MultiGameBoardPanel multiGameBoardPanel;
	private JMenuBar menuBar;
	private JMenu mnGame;
	private JMenuItem miGoBack;

	/**
	 * Create the frame.
	 */
	public MultiGameMainFrame(ConnectionToServer connectionToServer) {
		this.connection = connectionToServer;
		setTitle("Tic Tac Toe");
		
		componentsInitialization();
		eventsInitialization();
	}
	
	public MultiGameController getGameController() {
		return (MultiGameController) multiGameBoardPanel.getGameController();
	}

	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		miGoBack.addActionListener((e) -> {
			MultiGameController gameController = getGameController();
			synchronized(gameController) {
				gameController.setJOptionPaneUp(true);
				int result = JOptionPane.showConfirmDialog(this, "Do you want to go back to lobby?");
				if(result == JOptionPane.YES_OPTION) {
					if(gameController.hasOpponentWon() && !gameController.hasOpponentleft()) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(this, "Game has ended your opponent has won when you have been leaving!");
						connection.goBackAndInformOpponent();
					} else if(gameController.hasOpponentWon() && gameController.hasOpponentleft()) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(this, "Game has ended your opponent has won when you have been leaving!");
						gameController.goBackToLobby(false);
					} else if(gameController.hasOpponentleft()) {
							gameController.goBackToLobby(false);
					} else  {
						connection.goBackAndInformOpponent();
					}
				}
				gameController.setJOptionPaneUp(false);
				if(gameController.hasOpponentleft()) {
					gameController.goBackToLobby(true);
				}
				gameController.notify();
			}
		});
	}
	
	/**
	 * Initializing components generated via Window Builder
	 */
	private void componentsInitialization() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		miGoBack = new JMenuItem("Go back to lobby");
		mnGame.add(miGoBack);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblInformation = new JLabel("New label");
		
		multiGameBoardPanel = new MultiGameBoardPanel(this, lblInformation, 3, 3, connection);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblInformation)
					.addContainerGap(388, Short.MAX_VALUE))
				.addComponent(multiGameBoardPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblInformation)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(multiGameBoardPanel, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
