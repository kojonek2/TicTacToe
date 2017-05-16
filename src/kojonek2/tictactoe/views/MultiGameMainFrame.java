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

@SuppressWarnings("serial")
public class MultiGameMainFrame extends JFrame {

	private JPanel contentPane;
	private MultiGameBoardPanel multiGameBoardPanel;

	/**
	 * Create the frame.
	 */
	public MultiGameMainFrame(ConnectionToServer connectionToServer) {
		setTitle("Tic Tac Toe");
		
		componentsInitialization(connectionToServer);
		eventsInitialization();
	}
	
	public MultiGameController getGameController() {
		return (MultiGameController) multiGameBoardPanel.getGameController();
	}

	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {

	}
	
	/**
	 * Initializing components generated via Window Builder
	 */
	private void componentsInitialization(ConnectionToServer connection) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		multiGameBoardPanel = new MultiGameBoardPanel((JLabel) null, 3, 3, connection);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(multiGameBoardPanel, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(multiGameBoardPanel, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}
}
