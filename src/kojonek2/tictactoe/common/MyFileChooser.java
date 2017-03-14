package kojonek2.tictactoe.common;

import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MyFileChooser extends JFileChooser {

	@Override
	public void approveSelection() {
		if(getDialogType() == SAVE_DIALOG) {
			saveDialogApprove();
		} else if(getDialogType() == OPEN_DIALOG) {
			openDialogApprove();
		} else {
			super.approveSelection();
		}
	}
	
	private void saveDialogApprove() {
		File file = getSelectedFile();
		
		String extension = getExtension(file);
		if(extension == null) {
			String path = file.getAbsolutePath();
			setSelectedFile(new File(path + ".json"));
		} else if(!extension.equals("json")) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, "You need to choose file with extension .json", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		file = getSelectedFile();
		if(file.exists()) {
			int resultCode = JOptionPane.showConfirmDialog(this, "Would you like to override this file?");
			if(resultCode != JOptionPane.YES_OPTION) {
				return;
			}
		}
		
		super.approveSelection();
	}
	
	private void openDialogApprove() {
		
	}
	
	private String getExtension(File file) {
		String[] splitedName = file.getName().split("\\.");
		if(splitedName.length > 1) {
			return splitedName[splitedName.length - 1];
		}
		return null;
	}
}