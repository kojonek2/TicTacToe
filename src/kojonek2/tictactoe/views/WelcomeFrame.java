package kojonek2.tictactoe.views;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
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
	private JTextField txtPlayer1Name;
	private JTextField txtPlayer2Name;

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
		//TODO add possibility to play without nicknames
		if(!areInputsValid()) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Inputs can't be blank.");
			return;
		}
		
		MainFrame mainFrame = new MainFrame((int) spnGameBoardSize.getValue(), (int) spnFieldNeededForWin.getValue(),
				txtPlayer1Name.getText(), txtPlayer2Name.getText());
		mainFrame.setVisible(true);
		dispose();
	}
	
	private boolean areInputsValid() {
		String player1Name = txtPlayer1Name.getText().trim();
		String player2Name = txtPlayer2Name.getText().trim();
		return player1Name != null && player2Name != null && !player1Name.equals("") && !player2Name.equals("");
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
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(WelcomeFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
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

		JLabel lblPlayer1Name = new JLabel("Player 1:");

		JLabel lblPlayer2Name = new JLabel("Player 2:");

		txtPlayer1Name = new JTextField();
		txtPlayer1Name.setColumns(10);

		txtPlayer2Name = new JTextField();
		txtPlayer2Name.setColumns(10);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblTicTacToe, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(lblChoseOptionsOf, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE).addGap(10))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblSizeOfGame)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(spnGameBoardSize,
												GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
										.createParallelGroup(Alignment.LEADING).addComponent(lblFieldsInRow)
										.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblPlayer1Name)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(txtPlayer1Name, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(spnFieldNeededForWin,
												GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(217, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(lblPlayer2Name)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtPlayer2Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(291, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(btnStartGame)
						.addContainerGap(337, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblTicTacToe).addGap(18)
						.addComponent(lblChoseOptionsOf).addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblSizeOfGame)
								.addComponent(spnGameBoardSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(lblFieldsInRow)
								.addComponent(spnFieldNeededForWin, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblPlayer1Name)
								.addComponent(txtPlayer1Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtPlayer2Name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPlayer2Name))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnStartGame)
						.addContainerGap(45, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
