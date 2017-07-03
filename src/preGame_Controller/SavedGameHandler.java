package preGame_Controller; 	

import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import gameView.GameView;

public class SavedGameHandler implements ActionListener {
	private JPanel p;
	
	public SavedGameHandler(JPanel x) {p=x;}
	
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fc = new JFileChooser();
		//FileNameExtensionFilter filter = new FileNameExtensionFilter(".ser");
		//fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog((JComponent)e.getSource());
		if(returnVal == JFileChooser.APPROVE_OPTION){
			System.out.println(fc.getSelectedFile().getName());
		}
		
		//abre janela de jogo
		GameView gv = new GameView("Detetive", fc.getSelectedFile());
		gv.setVisible(true);
		//JFrame j = (JFrame) p.getParent();//contentPane.getParent();
		//j.dispose();
	}
}
