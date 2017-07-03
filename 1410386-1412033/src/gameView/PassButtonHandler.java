package gameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PassButtonHandler implements ActionListener {

	GameView gv;
	
	PassButtonHandler(GameView gameview)
	{
		gv = gameview;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		gv.gc.endTurn();

	}

}
