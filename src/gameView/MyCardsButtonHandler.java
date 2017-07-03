package gameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameController.ObservedGame;

public class MyCardsButtonHandler implements ActionListener 
{

	GameView gv;
	
	public MyCardsButtonHandler(GameView gameview)
	{
		gv = gameview;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		MyCardsWindow cards = new MyCardsWindow("Minhas cartas", gv);
		cards.setVisible(true);
	}

}