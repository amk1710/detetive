package gameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import preGame_View.NewGameScreen;
import gameController.ObservedGame;

class AccuseButtonHandler implements ActionListener 
{

	
	GameView gv;
	
	public AccuseButtonHandler(GameView gameview)
	{
		gv = gameview;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		AccuseWindow accuse = new AccuseWindow("Minhas notas", gv);
		accuse.setVisible(true);
	}

}
