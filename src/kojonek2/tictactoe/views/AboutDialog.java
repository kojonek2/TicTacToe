package kojonek2.tictactoe.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnOk;
	private JTextArea txtAbout;

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {

		setTitle("Tic Tac Toe - About");
		setModal(true);

		componentsInitialization();
		eventsInitialization();

		setTextForAbout();
	}

	private void setTextForAbout() {
		InputStream stream = this.getClass().getClassLoader()
				.getResourceAsStream("kojonek2/tictactoe/resources/About.txt");
		if (stream == null) {
			System.err.println("AboutDialog -- stream for text file not found");
			return;
		}
		Scanner scanner = new Scanner(stream);

		// setting delimiter to a "beginning of the input boundary" so
		// scanner.next() reads all file
		scanner.useDelimiter("\\A");

		if (scanner.hasNext()) {
			txtAbout.setText(scanner.next());
		}

		scanner.close();
	}

	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		btnOk.addActionListener((e) -> dispose());
	}

	/**
	 * Initializing components generated via Window Builder
	 */
	private void componentsInitialization() {
		setMinimumSize(new Dimension(200, 100));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(WinnerAnnouncer.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel.createSequentialGroup()
						.addGap(2).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(scrollPane,
				GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE));

		txtAbout = new JTextArea();
		txtAbout.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtAbout.setCaretColor(UIManager.getColor("Button.background"));
		txtAbout.setBackground(UIManager.getColor("Button.background"));
		txtAbout.setEditable(false);
		txtAbout.setText("About Text Field");
		scrollPane.setViewportView(txtAbout);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnOk = new JButton("OK");
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
		}
	}
}
