package kojonek2.tictactoe.views;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class WelcomeFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtPlayerName;
	private JButton btnPlayWithFriend;
	private JButton btnConnect;
	private JTextField txtIp;
	private JTextField txtPort;

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
	
	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		btnPlayWithFriend.addActionListener((e) -> {
			new LocalGameOptionsFrame().setVisible(true);
			dispose();
		});
		
		btnConnect.addActionListener((e) -> {
			String playerName = txtPlayerName.getText().trim();
			String ip = txtIp.getText().trim();
			
			int port;
			try {
				port = Integer.parseInt(txtPort.getText().trim());
			} catch (NumberFormatException exception) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this, "You need to enter correct server port!", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
					
			if (playerName == null || playerName.equals("")) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this, "You need to enter player name!", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!isVaildIpv4Adress(ip)) {
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this, "You need to enter correct ipv4 of server!", "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			new MultiGameOptionsFrame(playerName, ip, port).setVisible(true);
			dispose();
		});
	}
	
	private boolean isVaildIpv4Adress(String adress) {
		try {
			if (adress.equals("localhost")) {
				return true;
			}
			
			if (adress == null || adress.equals("")) {
				return false;
			}
			
			String[] parts = adress.split("\\.");
			if (parts.length != 4) {
				return false;
			}
			
			for (String part : parts) {
				int i = Integer.parseInt(part);
				if(i < 0 || i > 255) {
					return false;
				}
			}
			if (adress.endsWith(".")) {
				return false;
			}
			
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
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
		
		setContentPane(contentPane);
		
		JLabel lblTicTacToe = new JLabel("The Tic Tac Toe Game");
		lblTicTacToe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTicTacToe.setFont(new Font("Tahoma", Font.PLAIN, 37));
		
		JLabel lblDescription = new JLabel("Choose game mode and play!");
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblTicTacToe, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDescription, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblTicTacToe, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblDescription, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		btnPlayWithFriend = new JButton("Play with friend");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(48)
					.addComponent(btnPlayWithFriend)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(73)
					.addComponent(btnPlayWithFriend)
					.addContainerGap(73, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		txtPlayerName = new JTextField();
		txtPlayerName.setColumns(10);
		
		JLabel lblNick = new JLabel("Nick:");
		
		btnConnect = new JButton("Connect to server");
		
		txtIp = new JTextField();
		txtIp.setColumns(10);
		
		JLabel lbIIp = new JLabel("Ip:");
		
		JLabel lblPort = new JLabel("Port:");
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPort)
								.addComponent(lblNick)
								.addComponent(lbIIp))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(txtPort, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
								.addComponent(txtIp, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
								.addComponent(txtPlayerName, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))))
					.addGap(27))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPlayerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNick))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbIIp)
						.addComponent(txtIp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPort)
						.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnConnect)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		contentPane.setLayout(gl_contentPane);
	}
}
