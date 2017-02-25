package kojonek2.tictactoe.views;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblInformation;
	private JMenuItem miNewGame;
	private GameBoardPanel gameBoardPanel;
	private JMenu mnHelp;
	private JMenuItem miAbout;

	/**
	 * Create the frame.
	 */
	public MainFrame(int sizeOfGameBoard, int fieldsNeededForWin) {
		setTitle("Tic Tac Toe");

		//initializing components and adding events listeners
		componentsInitialization(sizeOfGameBoard, fieldsNeededForWin);
		eventsInitialization();
	}
	
	
	/**
	* Adding event listeners to components
	*/
	private void eventsInitialization() {
		miNewGame.addActionListener((e) -> gameBoardPanel.startNewGame());
		miAbout.addActionListener((e) -> {
			JDialog about = new AboutDialog();
			about.setVisible(true);
		});
	}
	
	/**
	* Initializing components generated via Window Builder
	*/
	private void componentsInitialization(int sizeOfGameBoard, int fieldsNeededForWin) {
		setMinimumSize(new Dimension(300,250));
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		miNewGame = new JMenuItem("New Game");
		mnGame.add(miNewGame);
		
		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		miAbout = new JMenuItem("About");
		mnHelp.add(miAbout);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblInformation = new JLabel("Information Label");
		gameBoardPanel = new GameBoardPanel(lblInformation, sizeOfGameBoard, fieldsNeededForWin);
		

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(gameBoardPanel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addComponent(lblInformation))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblInformation)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gameBoardPanel, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
