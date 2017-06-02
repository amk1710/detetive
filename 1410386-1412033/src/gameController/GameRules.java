package gameController;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

//classe que controlar� regras do jogo, recebendo a��es da GUI atrav�s da interface ObservedGame e notificando mudan�as
class GameRules extends Observable implements ObservedGame
{
	GameRules gr;
	boolean[] activePlayers;
	int currentTurn;
	int die;
	private Random roller;
	
	//construtor para novo jogo
	public GameRules(boolean[] activePlayer)
	{
		roller = new Random();
		die = 1;
		activePlayers = activePlayer;
		for(int i = 0; i <= 5; i++)
		{
			if(activePlayers[i] == true)
			{
				currentTurn = i;
				break;
			}
		}
	}
	
	//construtor para jogo salvo
	public GameRules()
	{
		
	}
	
	private void notifyView()
	{
		setChanged();
		notifyObservers();
	}
	
	public void addObserver(Observer o) 
	{
		super.addObserver(o);
		
	}
	
	public void rollDie()
	{
		die = (1+ roller.nextInt(6));
		notifyView();
	}

	public int getDie() {
		return die;
	}

	
	public void endTurn() 
	{
		for(int i = currentTurn + 1; i <= 5 + currentTurn; i++)
		{
			if(activePlayers[i%(5+1)] == true)
			{
				currentTurn = i%(5+1);
				break;
			}
		}
		notifyView();
	}

	
	public int getTurn() {
		return currentTurn;
	}

	
	

}
