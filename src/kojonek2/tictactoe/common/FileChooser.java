package kojonek2.tictactoe.common;

import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class FileChooser extends JFileChooser {

	@Override
	public void approveSelection() {
		if (getDialogType() == SAVE_DIALOG) {
			saveDialogApprove();
		} else if (getDialogType() == OPEN_DIALOG) {
			openDialogApprove();
		} else {
			super.approveSelection();
		}
	}

	private void saveDialogApprove() {
		File file = getSelectedFile();

		if (!isJsonExtension(file)) {
			return;
		}

		file = getSelectedFile();
		if (file.exists()) {
			int resultCode = JOptionPane.showConfirmDialog(this, "Would you like to override this file?");
			if (resultCode != JOptionPane.YES_OPTION) {
				return;
			}
		}

		super.approveSelection();
	}

	private void openDialogApprove() {
		File file = getSelectedFile();

		if (!isJsonExtension(file)) {
			return;
		}

		file = getSelectedFile();
		if (!file.exists()) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "You need to choose existing file to load!", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		super.approveSelection();
	}

	private boolean isJsonExtension(File file) {
		String extension = getExtension(file);
		
		if (extension == null) {
			String path = file.getAbsolutePath();
			setSelectedFile(new File(path + ".json"));
			return true;
		} else if (!extension.equals("json")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "You need to choose file with extension .json!", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	private String getExtension(File file) {
		String[] splitedName = file.getName().split("\\.");
		if (splitedName.length > 1) {
			return splitedName[splitedName.length - 1];
		}
		return null;
	}
}
