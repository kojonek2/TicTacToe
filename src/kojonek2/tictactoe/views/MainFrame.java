package kojonek2.tictactoe.views;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblInformationLabel;

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
					MainFrame frame = new MainFrame();
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
	public MainFrame() {

		//initializing components and adding events listeners
		componentsInitialization();
		eventsInitialization();
	}
	
	
	/*
	* Adding event listeners to components
	*/
	private void eventsInitialization() {
		
	}
	
	/**
	* Initializing components generated via Window Builder
	*/
	private void componentsInitialization() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblInformationLabel = new JLabel("Information Label");
		GameBoardPanel gameBoardPanel = new GameBoardPanel(lblInformationLabel, 3, 3);
		

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(gameBoardPanel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addComponent(lblInformationLabel))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblInformationLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gameBoardPanel, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
