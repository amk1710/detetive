package gameView;


import gameController.GameRulesFactory;
import gameController.ObservedGame;

import java.util.Observable;
import java.util.Observer;

//classe view que se registra como observadora e interage com o jogo
public class GameView implements Observer
{

	ObservedGame gc;
	
	//construtor para novo jogo
	public GameView(boolean[] activePlayer)
	{
		gc = GameRulesFactory.getGameInstance(activePlayer);
		gc.addObserver(this);		
		
	
		for(int i =0; i < 10; i++)		
		{
			gc.endTurn();
		}
	}
	
	public void update(Observable o, Object arg)
	{
		System.out.println(gc.getTurn());
	}	

}