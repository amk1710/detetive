package gameController;

import java.util.Observer;

//interface que define como a GUI pode interagir com as regras do jogo
public interface ObservedGame 
{		
	public static final int minPlayers = 2;
	
	public void addObserver(Observer o);
	
	public void rollDie();
	
	public int getDie();
	
	public void endTurn();
	
	public int getTurn();
	
}
