package kojonek2.tictactoe.views;

import java.awt.Component;
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
import org.json.JSONObject;

import kojonek2.tictactoe.common.FieldState;
import kojonek2.tictactoe.common.FileChooser;
import kojonek2.tictactoe.common.LocalGameController;

@SuppressWarnings("serial")
public class LocalGameMainFrame extends JFrame {

	private static JFileChooser fileChooser;

	private JPanel contentPane;
	private LocalGameController gameController;
	private LocalGameBoardPanel gameBoardPanel;
	
	private JLabel lblInformation;

	private JMenu mnHelp;

	private JMenuItem miNewGame;
	private JMenuItem miToOptionScreen;
	private JMenuItem miSaveGame;
	private JMenuItem miLoadGame;
	private JMenuItem miAbout;

	/**
	 * Create the frame.
	 */
	public LocalGameMainFrame(int sizeOfGameBoard, int fieldsNeededForWin, String circlePlayerName, String crossPlayerName) {
		setTitle("Tic Tac Toe");

		componentsInitialization(sizeOfGameBoard, fieldsNeededForWin);
		eventsInitialization();
		
		gameController = gameBoardPanel.getGameController();

		if (circlePlayerName != null && crossPlayerName != null) {
			gameController.setPlayerName(FieldState.CIRCLE, circlePlayerName);
			gameController.setPlayerName(FieldState.CROSS, crossPlayerName);
		} else if (circlePlayerName == null && crossPlayerName == null) {
			gameController.setPlayerName(FieldState.CIRCLE, FieldState.CIRCLE.getName());
			gameController.setPlayerName(FieldState.CROSS, FieldState.CROSS.getName());
		} else {
			System.err.println("MainFrame - Constructor: player1Name and player2Name must be both null or both set");
		}

	}

	public void startGame() {
		gameController.startNewGame();
	}

	public void randomlySwapPlayers(boolean b) {
		gameController.setRandomPlayerSwaps(b);
	}

	/**
	 * Adding event listeners to components
	 */
	private void eventsInitialization() {
		miNewGame.addActionListener((e) -> gameController.startNewGame());
		
		miSaveGame.addActionListener((e) -> saveGame());
		miLoadGame.addActionListener((e) -> {
			String save = loadSave(this);
			loadGame(save);
		});

		miToOptionScreen.addActionListener((e) -> {
			JFrame welcome = new LocalGameOptionsFrame();
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
	private static void createFileChooser() {
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
			String save = gameController.saveGame();

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

	static void loadGameStatic(Component parent) {
		try {
			String save = loadSave(parent);
			
			JSONObject root = new JSONObject(save);
			int sizeOfGameBoard = root.getInt("sizeOfGameBoard");
			int fieldsNeededForWin = root.getInt("fieldsNeededForWin");
			String circlePlayerName = root.getString("circlePlayerName");
			String crossPlayerName = root.getString("crossPlayerName");
			
			
			LocalGameMainFrame mainFrame = new LocalGameMainFrame(sizeOfGameBoard, fieldsNeededForWin, circlePlayerName, crossPlayerName);
			mainFrame.loadGame(save);
			mainFrame.setVisible(true);
			
		} catch (JSONException e) {
			e.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(parent, "Error occured during loading save!!", "Loading error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
		
	private static String loadSave(Component parent) {
		try {
			String save = "";

			File file = askForFileToLoad(parent);
			if (file == null) {
				return null;
			}

			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\\A");

			if (scanner.hasNext()) {
				save = scanner.next();
				scanner.close();
			} else {
				scanner.close();
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(parent, "Corruped or blank save.", "Loading error",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}

			return save;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(parent, "Save file not found!!", "Saving error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	private void loadGame(String save) {
		try {
			gameController.loadGame(save);

		} catch (JSONException e) {
			e.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "Error occured during loading save!!", "Loading error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static File askForFileToLoad(Component parent) {
		createFileChooser();
		int resultCode = fileChooser.showOpenDialog(parent);
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
				.getImage(LocalGameMainFrame.class.getResource("/kojonek2/tictactoe/resources/tic-tac-toe_16.png")));
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

		miToOptionScreen = new JMenuItem("Return to Option Screen");
		mnGame.add(miToOptionScreen);

		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		miAbout = new JMenuItem("About");
		mnHelp.add(miAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblInformation = new JLabel("Information Label");
		gameBoardPanel = new LocalGameBoardPanel(lblInformation, sizeOfGameBoard, fieldsNeededForWin);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(
						gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_contentPane.createSequentialGroup().addContainerGap()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(gameBoardPanel, GroupLayout.DEFAULT_SIZE, 404,
																Short.MAX_VALUE)
														.addComponent(lblInformation))
												.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addComponent(lblInformation)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(gameBoardPanel, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE).addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}
}
