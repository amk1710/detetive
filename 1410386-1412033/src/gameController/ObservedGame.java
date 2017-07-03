package gameController;

import java.io.FileOutputStream;
import java.util.Observer;

//interface que define como a GUI pode interagir com as regras do jogo
public interface ObservedGame 
{		
	public static final int minPlayers = 2;
	public static final int numPlayers = 6;
	public static final int numWeapons = 6;
	public static final int numRooms   = 9;
	
	//Indice dos jogadores
		public static final int SCARLET		= 0;
		public static final int MUSTARD		= 1;
		public static final int WHITE		= 2;
		public static final int GREEN		= 3;
		public static final int PEACOCK		= 4;
		public static final int PLUM		= 5;

	//Indice das armas
		public static final int CORDA		= 6;
		public static final int CANO		= 7;
		public static final int FACA		= 8;
		public static final int CHAVE		= 9;
		public static final int CASTICAL	= 10;
		public static final int REVOLVER	= 11;
	
	//Indices dos comodos
		public static final int COZINHA		= 12;
		public static final int JANTAR		= 13;
		public static final int ESTAR		= 14;
		public static final int MUSICA		= 15;
		public static final int ENTRADA		= 16;
		public static final int INVERNO		= 17;
		public static final int JOGOS		= 18;
		public static final int BIBLIOTECA	= 19;
		public static final int ESCRITORIO	= 20;
	
	
	//Nomes utilizados para as cartas
	public static final String[] NAMES = {  "Senhorita Scarlet", "Coronel Mustard", "Senhora White", "Reverendo Green", "Senhora Peacock", "Professor Plum", 
											"Corda", "Cano de Chumbo", "Faca", "Chave Inglesa", "Castiçal", "Revólver",      
											"Cozinha", "Sala de Jantar", "Sala de Estar", "Sala de M�sica", "Entrada", "Jardim de Inverno", "Sal�o de Jogos", "Biblioteca", "Escrit�rio"};
	
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
	
	public Card guess(int suspectID, int weaponID, int roomID);
	
	public boolean getHasGuessed();
	
	public boolean accuse(int suspectID, int weaponID, int roomID);
	
	public boolean allLost();
	
	public void saveGame(FileOutputStream fileOut);
	
	public Tabuleiro getTabuleiro();
	
}
