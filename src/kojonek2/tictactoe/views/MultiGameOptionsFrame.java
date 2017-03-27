package kojonek2.tictactoe.views;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import kojonek2.tictactoe.common.ConnectionToServer;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;

@SuppressWarnings("serial")
public class MultiGameOptionsFrame extends JFrame {

	private JPanel contentPane;
	
	private final ButtonGroup buttonGroupInviteYou = new ButtonGroup();
	private final ButtonGroup buttonGroupInviteAnother = new ButtonGroup();
	
	private JRadioButton rbInviteCircleYou;
	private JRadioButton rbInviteCrossYou;
	private JRadioButton rbInviteRandomYou;
	
	private JRadioButton rbInviteCircleAnother;
	private JRadioButton rbInviteCrossAnother;
	private JRadioButton rbInviteRandomAnother;

	/**
	 * Create the frame.
	 */
	public MultiGameOptionsFrame(String playerName) {
		setTitle("Tic Tac Toe - Lobby");

		componentsInitialization();
		eventsInitialization();
		
		new Thread(new ConnectionToServer(playerName)).start();
	}


	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		addOnClickReactionsForRadioButtons();
	}
	
	private void addOnClickReactionsForRadioButtons() {
		rbInviteCircleYou.addActionListener((e) -> rbInviteCrossAnother.setSelected(true));
		rbInviteCrossYou.addActionListener((e) -> rbInviteCircleAnother.setSelected(true));
		rbInviteRandomYou.addActionListener((e) -> rbInviteRandomAnother.setSelected(true));
		
		rbInviteCircleAnother.addActionListener((e) -> rbInviteCrossYou.setSelected(true));
		rbInviteCrossAnother.addActionListener((e) -> rbInviteCircleYou.setSelected(true));
		rbInviteRandomAnother.addActionListener((e) -> rbInviteRandomYou.setSelected(true));
	}
	
	/**
	 * Initializing components generated via Window Builder
	 */
	private void componentsInitialization() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panelInvite = new JPanel();
		contentPane.add(panelInvite);
		
		JSplitPane splitPaneInvite = new JSplitPane();
		splitPaneInvite.setEnabled(false);
		GroupLayout gl_panelInvite = new GroupLayout(panelInvite);
		gl_panelInvite.setHorizontalGroup(
			gl_panelInvite.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPaneInvite, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
		);
		gl_panelInvite.setVerticalGroup(
			gl_panelInvite.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPaneInvite, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
		);
		
		JPanel panelInviteRight = new JPanel();
		splitPaneInvite.setRightComponent(panelInviteRight);
		
		JLabel lblInviteSizeOfGameBoard = new JLabel("Size of game board:");
		
		JSpinner spnInviteGameBoardSize = new JSpinner();
		spnInviteGameBoardSize.setVerifyInputWhenFocusTarget(false);
		spnInviteGameBoardSize.setModel(new SpinnerNumberModel(3, 3, 15, 1));
		
		JLabel lblInviteFIeldsNeededForWin = new JLabel("Fields in row needed for win:");
		
		JSpinner spnInviteFieldNeededForWin = new JSpinner();
		spnInviteFieldNeededForWin.setVerifyInputWhenFocusTarget(false);
		spnInviteFieldNeededForWin.setModel(new SpinnerNumberModel(3, 3, 15, 1));
		
		JLabel lblInviteYou = new JLabel("You:");
		
		JLabel lblInviteAnother = new JLabel("Invited:");
		
		rbInviteCircleYou = new JRadioButton("Circle");
		buttonGroupInviteYou.add(rbInviteCircleYou);
		
		rbInviteCircleAnother = new JRadioButton("Circle");
		buttonGroupInviteAnother.add(rbInviteCircleAnother);
		
		rbInviteCrossYou = new JRadioButton("Cross");
		buttonGroupInviteYou.add(rbInviteCrossYou);
		
		rbInviteCrossAnother = new JRadioButton("Cross");
		buttonGroupInviteAnother.add(rbInviteCrossAnother);
		
		rbInviteRandomYou = new JRadioButton("Random");
		buttonGroupInviteYou.add(rbInviteRandomYou);
		rbInviteRandomYou.setSelected(true);
		
		rbInviteRandomAnother = new JRadioButton("Random");
		buttonGroupInviteAnother.add(rbInviteRandomAnother);
		rbInviteRandomAnother.setSelected(true);
		
		JButton btnInvite = new JButton("Invite");
		
		JLabel lblInviteChoosePlayerFrom = new JLabel("Choose game options and invite player!");
		lblInviteChoosePlayerFrom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_panelInviteRight = new GroupLayout(panelInviteRight);
		gl_panelInviteRight.setHorizontalGroup(
			gl_panelInviteRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInviteRight.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInviteChoosePlayerFrom)
						.addGroup(gl_panelInviteRight.createSequentialGroup()
							.addComponent(lblInviteSizeOfGameBoard, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spnInviteGameBoardSize, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelInviteRight.createSequentialGroup()
							.addComponent(lblInviteFIeldsNeededForWin, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(spnInviteFieldNeededForWin, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnInvite, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelInviteRight.createSequentialGroup()
							.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInviteYou, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblInviteAnother, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelInviteRight.createSequentialGroup()
									.addComponent(rbInviteCircleYou, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
									.addComponent(rbInviteCrossYou, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
									.addComponent(rbInviteRandomYou, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelInviteRight.createSequentialGroup()
									.addComponent(rbInviteCircleAnother, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
									.addComponent(rbInviteCrossAnother, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
									.addComponent(rbInviteRandomAnother, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		gl_panelInviteRight.setVerticalGroup(
			gl_panelInviteRight.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelInviteRight.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblInviteChoosePlayerFrom)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(spnInviteGameBoardSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInviteSizeOfGameBoard))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInviteFIeldsNeededForWin)
						.addComponent(spnInviteFieldNeededForWin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelInviteRight.createSequentialGroup()
							.addComponent(lblInviteYou)
							.addGap(12)
							.addComponent(lblInviteAnother)
							.addGap(12))
						.addGroup(gl_panelInviteRight.createSequentialGroup()
							.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.LEADING)
								.addComponent(rbInviteCircleYou)
								.addComponent(rbInviteCrossYou)
								.addComponent(rbInviteRandomYou))
							.addGap(3)
							.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.LEADING)
								.addComponent(rbInviteCircleAnother)
								.addComponent(rbInviteCrossAnother)
								.addComponent(rbInviteRandomAnother))
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addComponent(btnInvite)
					.addGap(42))
		);
		panelInviteRight.setLayout(gl_panelInviteRight);
		
		JScrollPane scrollPaneInviteLeft = new JScrollPane();
		splitPaneInvite.setLeftComponent(scrollPaneInviteLeft);
		
		JList listInvite = new JList();
		scrollPaneInviteLeft.setViewportView(listInvite);
		
		JLabel lblInviteChoosePlayerTo = new JLabel("Choose player to invite");
		scrollPaneInviteLeft.setColumnHeaderView(lblInviteChoosePlayerTo);
		splitPaneInvite.setDividerLocation(300);
		panelInvite.setLayout(gl_panelInvite);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		
		JSeparator separator = new JSeparator();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(102, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
	}
}
