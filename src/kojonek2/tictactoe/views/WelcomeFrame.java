package kojonek2.tictactoe.views;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class WelcomeFrame extends JFrame {

	private JPanel contentPane;
	private JButton btnStartGame;
	private JSpinner spnGameBoardSize;
	private JSpinner spnFieldNeededForWin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeFrame frame = new WelcomeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomeFrame() {
		setTitle("Tic Tac Toe");
		
		componentsInitialization();
		eventsInitialization();	
	}
	
	private void startGame() {
		MainFrame mainFrame = new MainFrame((int) spnGameBoardSize.getValue(), (int) spnFieldNeededForWin.getValue());
		mainFrame.setVisible(true);
		dispose();
	}

	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		btnStartGame.addActionListener((e) -> startGame());
	}

	/**
	 * Initializing components generated via Window Builder
	 */
	private void componentsInitialization() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTicTacToe = new JLabel("The Tic Tac Toe Game");
		lblTicTacToe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTicTacToe.setFont(new Font("Tahoma", Font.PLAIN, 37));
		
		JLabel lblChoseOptionsOf = new JLabel("Chose options of new game and have fun!");
		lblChoseOptionsOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoseOptionsOf.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblSizeOfGame = new JLabel("Size of game board:");
		
		btnStartGame = new JButton("Start Game");
		
		JLabel lblFieldsInRow = new JLabel("Fields in row needed for win:");
		
		spnGameBoardSize = new JSpinner();
		spnGameBoardSize.setVerifyInputWhenFocusTarget(false);
		spnGameBoardSize.setModel(new SpinnerNumberModel(3, 3, 15, 1));
		
		spnFieldNeededForWin = new JSpinner();
		spnFieldNeededForWin.setModel(new SpinnerNumberModel(3, 3, 15, 1));
		spnFieldNeededForWin.setVerifyInputWhenFocusTarget(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblTicTacToe, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChoseOptionsOf, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnStartGame)
					.addContainerGap(377, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblSizeOfGame)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spnGameBoardSize, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblFieldsInRow)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spnFieldNeededForWin, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(299, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblTicTacToe)
					.addGap(18)
					.addComponent(lblChoseOptionsOf)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSizeOfGame)
						.addComponent(spnGameBoardSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblFieldsInRow)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStartGame))
						.addComponent(spnFieldNeededForWin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(93, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
