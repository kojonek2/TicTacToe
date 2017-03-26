package kojonek2.tictactoe.views;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kojonek2.tictactoe.common.ConnectionToServer;

@SuppressWarnings("serial")
public class MultiGameOptionsFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MultiGameOptionsFrame(String playerName) {
		setTitle("Tic Tac Toe");

		componentsInitialization();
		eventsInitialization();
		
		new Thread(new ConnectionToServer(playerName)).start();
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
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	
}
