package gameController;

import java.util.Observer;

//interface que define como a GUI pode interagir com as regras do jogo
public interface ObservedGame 
{		
	public static final int minPlayers = 2;
	public static final int numPlayers = 6;
	public static final int numWeapons = 6;
	
	//getters
	
	public int getDie();
	
	public int getTurn();
	
	public boolean getDieWasRolled();
	
	//funções retornam um array de booleans que indica quais jogadores/armas/quartos o jogador p já eliminou como suspeitos
	public boolean[] getNotedPlayers();
	public boolean[] getNotedWeapons();
	public boolean[] getNotedRooms();
	
	
	//controllers
	
	public void addObserver(Observer o);
	
	public void rollDie();
	
	public void setNotes_Players(boolean[] notedPlayers);
	public void setNotes_Weapons(boolean[] notedWeapons);
	public void setNotes_Rooms(boolean[] notedRooms);
	
		
	public void endTurn();
	
	
}
