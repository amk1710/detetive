package gameController;

import java.util.Observer;

//interface que define como a GUI pode interagir com as regras do jogo
public interface ObservedGame 
{		
	public static final int minPlayers = 2;
	public static final int numPlayers = 6;
	public static final int numWeapons = 6;
	public static final int numRooms   = 9;
	
	public void addObserver(Observer o);
	
	
	public int getTurn();
	
	public void rollDie();
	
	public int getDie();
	public boolean getDieWasRolled();
	
	//funções retornam um array de booleans que indica quais jogadores/armas/quartos o jogador p já eliminou como suspeitos
	public boolean[] getNotedPlayers();
	public boolean[] getNotedWeapons();
	public boolean[] getNotedRooms();
		
		
	public void setNotes_Players(boolean[] notedPlayers);
	public void setNotes_Weapons(boolean[] notedWeapons);
	public void setNotes_Rooms(boolean[] notedRooms);
	
	public void endTurn();
	
	public Card[] getPlayerHand(int i);
	
	//tempor�ria! Quebra arquitetura
	public Tabuleiro getTabuleiro();
	
}
