package gameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import preGame_View.NewGameScreen;
import gameController.ObservedGame;

class SaveHandler implements ActionListener 
{

	
	GameView gv;
	
	public SaveHandler(GameView gameview)
	{
		gv = gameview;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		FileOutputStream fileOut;
		int id = (int) Math.floor(Math.random()*100);
		try {
			fileOut = new FileOutputStream("assets/saves/save" + id + ".ser");
			gv.gc.saveGame(fileOut);
		} catch (FileNotFoundException e) {
			System.out.println("Erro de salvamento:" + e.getMessage());
		}
		
	}

}
