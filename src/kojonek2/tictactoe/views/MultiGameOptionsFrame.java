package kojonek2.tictactoe.views;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import kojonek2.tictactoe.common.ConnectionToServer;
import kojonek2.tictactoe.common.Field;
import kojonek2.tictactoe.common.Player;

@SuppressWarnings("serial")
public class MultiGameOptionsFrame extends JFrame {

	private JPanel contentPane;
	
	private ConnectionToServer connection;
	
	private final ButtonGroup buttonGroupInviteYou = new ButtonGroup();
	private final ButtonGroup buttonGroupInviteAnother = new ButtonGroup();
	
	private JRadioButton rbInviteCircleYou;
	private JRadioButton rbInviteCrossYou;
	private JRadioButton rbInviteRandomYou;
	
	private JRadioButton rbInviteCircleAnother;
	private JRadioButton rbInviteCrossAnother;
	private JRadioButton rbInviteRandomAnother;
	
	private JSpinner spnInviteSizeOfBoard;
	private JSpinner spnInviteFieldNeeded;
	
	private JButton btnInvite;

	private JButton btnPendingAccept;
	private JButton btnPendingDecline;
	
	private JList<Player> listInvite;
	private JList<Player> listPending;
	
	public JLabel lblPendingSizeOfBoardDetails;
	public JLabel lblPendingFIeldsNeededDetails;
	public JLabel lblPendingYouDetails;
	public JLabel lblPendingAnotherDetails;

	/**
	 * Create the frame.
	 */
	public MultiGameOptionsFrame(String playerName) {
		setTitle("Tic Tac Toe - Lobby");

		componentsInitialization();
		eventsInitialization();
		
		listInvite.setModel(new DefaultListModel<Player>());
		listPending.setModel(new DefaultListModel<Player>());
		
		connection = new ConnectionToServer(playerName, this);
		new Thread(connection).start();
	}
	
	public int getSizeOfGameBoardInput() {
		return (int) spnInviteSizeOfBoard.getValue();
	}
	
	public int getFieldsNeededForWinInput() {
		return (int) spnInviteFieldNeeded.getValue();
	}

	public int getYourStateInput() {
		if(rbInviteCircleYou.isSelected()) {
			return Field.CIRCLE;
		} else if (rbInviteCrossYou.isSelected()) {
			return Field.CROSS;
		} 
		return Field.RANDOM;
	}

	public int getInvitedStateInput() {
		if(rbInviteCircleAnother.isSelected()) {
			return Field.CIRCLE;
		} else if (rbInviteCrossAnother.isSelected()) {
			return Field.CROSS;
		} 
		return Field.RANDOM;
	}
	
	public Player getSelectedPlayerForInvite() {
		if(listInvite.isSelectionEmpty()) {
			return null;
		}
		return listInvite.getSelectedValue();
	}
	
	public DefaultListModel<Player> getListModelInvite() {
		return (DefaultListModel<Player>) listInvite.getModel();
	}
	
	public DefaultListModel<Player> getListModelPending() {
		return (DefaultListModel<Player>) listPending.getModel();
	}
	
	//Use Field.CiRCLE or Field.CROSS or Field.RANDOM for yourState and anotherState
	public void setPedningDetails(int sizeOfGameBoard, int neededFieldsForWin, int yourState, int anotherState) {
		
	}
	
	public void clearPendingDetails() {
		lblPendingSizeOfBoardDetails.setText("");
		lblPendingFIeldsNeededDetails.setText("");
		lblPendingYouDetails.setText("");
		lblPendingAnotherDetails.setText("");
	}
	
	
	
	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		addOnClickReactionsForRadioButtons();
		addActionListenerInviteButton();
	}
	
	private void addActionListenerInviteButton() {
		btnInvite.addActionListener((e) -> {
			if(listInvite.isSelectionEmpty()) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this, "You need to choose player to invite", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			connection.sendInvite();
		});
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
		
		JLabel lblInviteSizeOfBoard = new JLabel("Size of game board:");
		
		spnInviteSizeOfBoard = new JSpinner();
		spnInviteSizeOfBoard.setVerifyInputWhenFocusTarget(false);
		spnInviteSizeOfBoard.setModel(new SpinnerNumberModel(3, 3, 15, 1));
		
		JLabel lblInviteFIeldsNeeded = new JLabel("Fields in row needed for win:");
		
		spnInviteFieldNeeded = new JSpinner();
		spnInviteFieldNeeded.setVerifyInputWhenFocusTarget(false);
		spnInviteFieldNeeded.setModel(new SpinnerNumberModel(3, 3, 15, 1));
		
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
		
		btnInvite = new JButton("Invite");
		
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
							.addComponent(lblInviteSizeOfBoard, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spnInviteSizeOfBoard, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelInviteRight.createSequentialGroup()
							.addComponent(lblInviteFIeldsNeeded, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spnInviteFieldNeeded, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(spnInviteSizeOfBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInviteSizeOfBoard))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelInviteRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(spnInviteFieldNeeded, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInviteFIeldsNeeded))
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
		
		listInvite = new JList<Player>();
		listInvite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneInviteLeft.setViewportView(listInvite);
		
		JLabel lblInviteListHeader = new JLabel("Choose player to invite");
		scrollPaneInviteLeft.setColumnHeaderView(lblInviteListHeader);
		splitPaneInvite.setDividerLocation(300);
		panelInvite.setLayout(gl_panelInvite);
		
		JPanel panelPending = new JPanel();
		contentPane.add(panelPending);
		
		JSeparator separator = new JSeparator();
		
		JSplitPane splitPanePending = new JSplitPane();
		splitPanePending.setEnabled(false);
		
		JScrollPane scrollPanePendingLeft = new JScrollPane();
		splitPanePending.setLeftComponent(scrollPanePendingLeft);
		
		listPending = new JList<Player>();
		listPending.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPanePendingLeft.setViewportView(listPending);
		
		JLabel lblPendingListHeader = new JLabel("Pending invites from players");
		scrollPanePendingLeft.setColumnHeaderView(lblPendingListHeader);
		
		JPanel panelPendingRight = new JPanel();
		splitPanePending.setRightComponent(panelPendingRight);
		
		JLabel lblPendingInviteDetails = new JLabel("Invite details");
		lblPendingInviteDetails.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblPendingSizeOfBoard = new JLabel("Size of game board:");
		
		JLabel lblPendingFIeldsNeeded = new JLabel("Fields in row needed for win:");
		
		btnPendingAccept = new JButton("Accept");
		
		JLabel lblPendingYou = new JLabel("You:");
		
		JLabel lblPendingAnother = new JLabel("Abother:");
		
		btnPendingDecline = new JButton("Decline");
		
		lblPendingSizeOfBoardDetails = new JLabel("0");
		
		lblPendingFIeldsNeededDetails = new JLabel("0");
		
		lblPendingAnotherDetails = new JLabel("Circle");
		
		lblPendingYouDetails = new JLabel("Cross");
		GroupLayout gl_panelPendingRight = new GroupLayout(panelPendingRight);
		gl_panelPendingRight.setHorizontalGroup(
			gl_panelPendingRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPendingRight.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelPendingRight.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPendingInviteDetails)
						.addGroup(gl_panelPendingRight.createSequentialGroup()
							.addComponent(lblPendingSizeOfBoard, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPendingSizeOfBoardDetails))
						.addGroup(gl_panelPendingRight.createSequentialGroup()
							.addComponent(lblPendingFIeldsNeeded, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPendingFIeldsNeededDetails))
						.addGroup(gl_panelPendingRight.createSequentialGroup()
							.addComponent(btnPendingAccept, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPendingDecline, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelPendingRight.createSequentialGroup()
							.addComponent(lblPendingYou, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPendingYouDetails))
						.addGroup(gl_panelPendingRight.createSequentialGroup()
							.addComponent(lblPendingAnother, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPendingAnotherDetails)))
					.addContainerGap(128, Short.MAX_VALUE))
		);
		gl_panelPendingRight.setVerticalGroup(
			gl_panelPendingRight.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelPendingRight.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPendingInviteDetails)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addGroup(gl_panelPendingRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPendingSizeOfBoard)
						.addComponent(lblPendingSizeOfBoardDetails))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelPendingRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPendingFIeldsNeeded)
						.addComponent(lblPendingFIeldsNeededDetails))
					.addGap(15)
					.addGroup(gl_panelPendingRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPendingYou)
						.addComponent(lblPendingYouDetails))
					.addGap(12)
					.addGroup(gl_panelPendingRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPendingAnother)
						.addComponent(lblPendingAnotherDetails))
					.addGap(12)
					.addGroup(gl_panelPendingRight.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPendingAccept)
						.addComponent(btnPendingDecline))
					.addGap(42))
		);
		panelPendingRight.setLayout(gl_panelPendingRight);
		splitPanePending.setDividerLocation(300);
		GroupLayout gl_panelPending = new GroupLayout(panelPending);
		gl_panelPending.setHorizontalGroup(
			gl_panelPending.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelPending.createSequentialGroup()
					.addComponent(splitPanePending, GroupLayout.PREFERRED_SIZE, 634, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE))
		);
		gl_panelPending.setVerticalGroup(
			gl_panelPending.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelPending.createSequentialGroup()
					.addGroup(gl_panelPending.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(splitPanePending, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelPending.setLayout(gl_panelPending);
	}
}
