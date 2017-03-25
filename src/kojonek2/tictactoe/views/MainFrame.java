package kojonek2.tictactoe.views;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONException;

import kojonek2.tictactoe.common.Field;
import kojonek2.tictactoe.common.FileChooser;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JFileChooser fileChooser;

	private JPanel contentPane;
	private GameBoardPanel gameBoard;
	
	private JLabel lblInformation;

	private JMenu mnHelp;

	private JMenuItem miNewGame;
	private JMenuItem miToWelcomeFrame;
	private JMenuItem miSaveGame;
	private JMenuItem miLoadGame;
	private JMenuItem miAbout;

	/**
	 * Create the frame.
	 */
	public MainFrame(int sizeOfGameBoard, int fieldsNeededForWin, String circlePlayerName, String crossPlayerName) {
		setTitle("Tic Tac Toe");

		componentsInitialization(sizeOfGameBoard, fieldsNeededForWin);
		eventsInitialization();

		if (circlePlayerName != null && crossPlayerName != null) {
			gameBoard.setPlayerName(Field.CIRCLE, circlePlayerName);
			gameBoard.setPlayerName(Field.CROSS, crossPlayerName);
		} else if (circlePlayerName == null && crossPlayerName == null) {
			gameBoard.setPlayerName(Field.CIRCLE, "Circle");
			gameBoard.setPlayerName(Field.CROSS, "Cross");
		} else {
			System.err.println("MainFrame - Constructor: player1Name and player2Name must be both null or both set");
		}

	}

	public void startGame() {
		gameBoard.startNewGame();
	}

	public void randomlySwapPlayers(boolean b) {
		gameBoard.setRandomPlayerSwaps(b);
	}

	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		miNewGame.addActionListener((e) -> gameBoard.startNewGame());
		
		miSaveGame.addActionListener((e) -> saveGame());
		miLoadGame.addActionListener((e) -> loadGame());

		miToWelcomeFrame.addActionListener((e) -> {
			JFrame welcome = new WelcomeFrame();
			welcome.setVisible(true);
			dispose();
		});

		miAbout.addActionListener((e) -> {
			JDialog about = new AboutDialog();
			about.setVisible(true);
		});
	}

	/**
	 * Creates fileChooser if there isn't one
	 */
	private void createFileChooser() {
		if (fileChooser == null) {
			fileChooser = new FileChooser();
			FileFilter filter = new FileNameExtensionFilter("Load only .json files", "json");
			fileChooser.setFileFilter(filter);

			File workingDirectory = new File(System.getProperty("user.dir"));
			fileChooser.setCurrentDirectory(workingDirectory);
			fileChooser.setSelectedFile(new File("save.json"));
		}
	}

	private void saveGame() {
		try {
			String save = gameBoard.saveGame();

			File file = askForFileToWrite();
			if (file == null) {
				return;
			}

			FileWriter writer = new FileWriter(file);
			writer.write(save);
			writer.close();

		} catch (JSONException e) {
			e.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Error occured during saving game!!", "Saving error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Error occured during saving game!!", "Saving error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private File askForFileToWrite() {
		createFileChooser();
		int resultCode = fileChooser.showSaveDialog(this);
		if (resultCode == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	private void loadGame() {
		try {
			String save = "";

			File file = askForFileToLoad();
			if (file == null) {
				return;
			}

			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\\A");

			if (scanner.hasNext()) {
				save = scanner.next();
				scanner.close();
			} else {
				scanner.close();
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this, "Corruped or blank save.", "Loading error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			gameBoard.loadGame(save);

		} catch (JSONException e) {
			e.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Error occured during loading save!!", "Loading error",
					JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Save file not found!!", "Saving error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private File askForFileToLoad() {
		createFileChooser();
		int resultCode = fileChooser.showOpenDialog(this);
		if (resultCode == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		}
		return null;
	}

	/**
	 * Initializing components generated via Window Builder
	 */
	private void componentsInitialization(int sizeOfGameBoard, int fieldsNeededForWin) {
		setMinimumSize(new Dimension(300, 250));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(MainFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

		miNewGame = new JMenuItem("New Game");
		mnGame.add(miNewGame);

		miSaveGame = new JMenuItem("Save Game");
		mnGame.add(miSaveGame);

		miLoadGame = new JMenuItem("Load Game");
		mnGame.add(miLoadGame);

		miToWelcomeFrame = new JMenuItem("Return to Welcome Screen");
		mnGame.add(miToWelcomeFrame);

		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		miAbout = new JMenuItem("About");
		mnHelp.add(miAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblInformation = new JLabel("Information Label");
		gameBoard = new GameBoardPanel(lblInformation, sizeOfGameBoard, fieldsNeededForWin);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_contentPane.createSequentialGroup().addContainerGap()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(gameBoard, GroupLayout.DEFAULT_SIZE, 404,
																Short.MAX_VALUE)
														.addComponent(lblInformation))
												.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblInformation)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(gameBoard, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE).addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}
}
