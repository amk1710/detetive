package gameController;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

//classe que controlarï¿½ regras do jogo, recebendo aï¿½ï¿½es da GUI atravï¿½s da interface ObservedGame e notificando mudanï¿½as
class GameRules extends Observable implements ObservedGame
{
	private boolean[] activePlayers;
	private int currentTurn;
	private int die;
	//diz se dado já foi rolado esse turno
	private boolean dieWasRolled;
	
	private PlayerNotes[] notes;
	
	private Random roller;
	
	//construtor para novo jogo
	public GameRules(boolean[] activePlayer)
	{
		roller = new Random();
		dieWasRolled = false;
		die = 1;
		activePlayers = activePlayer;
		
		for(int i = 0; i < ObservedGame.numPlayers; i++)
		{
			if(activePlayers[i] == true)
			{
				currentTurn = i;
				break;
			}
		}
				
		notes = new PlayerNotes[ObservedGame.numPlayers];
		for(int i = 0; i < ObservedGame.numPlayers; i++)
		{
			if(activePlayers[i] == true)
			{
				notes[i] = new PlayerNotes(i);
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
		//se dado já foi rolado essa rodada, não faz nada
		if(dieWasRolled)
		{
			return;
		}
		
		dieWasRolled = true;
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
		dieWasRolled = false;
		notifyView();
	}

	
	public int getTurn() {
		return currentTurn;
	}
	
	public boolean getDieWasRolled()
	{
		return dieWasRolled;
	}

	public boolean[] getNotedPlayers() 
	{
		if(activePlayers[currentTurn] == true)
		{
			return notes[currentTurn].getEliminatedPlayers();
		}
		else return null;
	}

	public boolean[] getNotedWeapons() {
		if(activePlayers[currentTurn] == true)
		{
			return notes[currentTurn].getEliminatedWeapons();
		}
		else return null;
		
	}

	public boolean[] getNotedRooms() {
		if(activePlayers[currentTurn] == true)
		{
			return notes[currentTurn].getEliminatedRooms();
		}
		else return null;
	}

	
	public void setNotes_Players(boolean[] notedPlayers)
	{
		notes[currentTurn].setEliminatedPlayers(notedPlayers);
		
	}

	
	public void setNotes_Weapons(boolean[] notedWeapons) 
	{
		notes[currentTurn].setEliminatedWeapons(notedWeapons);
		
	}

	
	public void setNotes_Rooms(boolean[] notedRooms) 
	{
		notes[currentTurn].setEliminatedRooms(notedRooms);
		
	}

	
	
}
