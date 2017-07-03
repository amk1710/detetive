package preGame_View; 	

import java.awt.event.*;
import javax.swing.*;

import gameView.GameView;

class SavedGameHandler implements ActionListener {
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
		GameView gv = new GameView("Detetive", fc.getSelectedFile());
		gv.setVisible(true);
		//j.dispose();
	}
}
