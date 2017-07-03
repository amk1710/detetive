package preGame_Controller; 	

import java.awt.event.*;
import javax.swing.*;

public class SavedGameHandler implements ActionListener {
	private JPanel p;
	
	public SavedGameHandler(JPanel x) {p=x;}
	
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fc = new JFileChooser();
		//FileNameExtensionFilter filter = new FileNameExtensionFilter("detetive");
		//fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog((JComponent)e.getSource());
		if(returnVal == JFileChooser.APPROVE_OPTION){
			System.out.println(fc.getSelectedFile().getName());
		}
		
		//abre janela de jogo
		//j.dispose();
	}
}
