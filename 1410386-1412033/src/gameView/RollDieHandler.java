package gameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameController.ObservedGame;

public class RollDieHandler implements ActionListener 
{

	ObservedGame gc;
	
	public RollDieHandler(ObservedGame game)
	{
		gc = game;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		gc.rollDie();
	}

}
