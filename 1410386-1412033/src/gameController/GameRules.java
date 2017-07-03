package gameController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

//classe que controlam regras do jogo, recebendo a��es da GUI atrav�s da interface ObservedGame e notificando mudan�as
class GameRules extends Observable implements ObservedGame, java.io.Serializable
{
	private GameRules gr;
	
	Tabuleiro tabuleiro;
	
	
	private boolean[] activePlayers;
	private int currentTurn;
	private int die;
	//diz se dado j� foi rolado esse turno
	private boolean dieWasRolled;
	private boolean hasGuessed;
	
	private PlayerNotes[] notes;
	
	// cartas de cada jogador
	private Vector<Card>[] playerCards;
	
	//cartas no pacote confidencial
	private Card[] answer;
	
	private Random roller;
	
	//construtor para novo jogo
	public GameRules(boolean[] activePlayer)
	{
		roller = new Random();
		dieWasRolled = false;
		die = 1;
		activePlayers = activePlayer;
		tabuleiro = new Tabuleiro();
		hasGuessed = false;
		
		
		for(int i = 0; i <= 5; i++)
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
		
		positionWeapons();
		dealCards();
	}
	
	//construtor para jogo salvo
	public GameRules()
	{
		
	}
	
	public void notifyObservers()
	{
		setChanged();
		super.notifyObservers();
	}
	
	public void addObserver(Observer o) 
	{
		super.addObserver(o);
		
	}
	
	public void rollDie()
	{
		//se dado j� foi rolado essa rodada, n�o faz nada
		if(dieWasRolled)
		{
			return;
		}
		
		dieWasRolled = true;
		die = (1+ roller.nextInt(6));
		notifyObservers();
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
		hasGuessed = false;
		notifyObservers();
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
		System.out.println("getnotedplayers");
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
		notes[this.getPreviousTurn()].setEliminatedPlayers(notedPlayers);
	}

	
	public void setNotes_Weapons(boolean[] notedWeapons) 
	{
		notes[this.getPreviousTurn()].setEliminatedWeapons(notedWeapons);	
	}

	
	private int getPreviousTurn() {
		int previousTurn;
		for(int i=1; i<6; i++)
		{
			previousTurn = currentTurn -i;
			if(previousTurn <0)
				previousTurn = 6 - (i - currentTurn);
			if(activePlayers[previousTurn] == true)
				return previousTurn; 
		}
		return -1;
	}

	public void setNotes_Rooms(boolean[] notedRooms) 
	{
		notes[this.getPreviousTurn()].setEliminatedRooms(notedRooms);
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	//fun��es auxiliares
	
	public Card[] getPlayerHand(int i) 
	{
		Card[] hand = new Card[1];
		hand = playerCards[i].toArray(hand);
		//System.out.println(hand[0].toString());
		return hand;
	}
	
	private void positionWeapons()
	{
		int indexComodo;
		for(int i=0; i<ObservedGame.numWeapons; i++)
		{
			indexComodo = roller.nextInt(ObservedGame.numRooms);
			if(tabuleiro.comodos[indexComodo][0] == Tabuleiro.VAZIO)
				tabuleiro.comodos[indexComodo][0] = ObservedGame.CORDA + i;
			else
				i--;
		}
	}
	
	//fun��o distribui as cartas de jogo pelos jogadores
	private void dealCards()
	{
		Vector<Card> allCards = new Vector<Card>();
		Vector<Card> suspects = new Vector<Card>();
		Vector<Card> weapons = new Vector<Card>();
		Vector<Card> rooms = new Vector<Card>();
		
		for(int j = 0;j < ObservedGame.numPlayers;j++)
		{
			suspects.add(new Card(CardType.SUSPECT, j));
		}
		
		for(int j = 0; j < ObservedGame.numWeapons;j++)
		{
			weapons.add(new Card(CardType.WEAPON, j));
		}
		
		for(int j = 0; j < ObservedGame.numRooms;j++)
		{
			rooms.add(new Card(CardType.ROOM, j));
		}
		
		Collections.shuffle(suspects);
		Collections.shuffle(weapons);
		Collections.shuffle(rooms);
		
		//tira tr�s cartas para o envelope confidencial
		answer = new Card[3];
		answer[0] = suspects.remove(suspects.size() - 1);
		answer[1] = weapons.remove(weapons.size() - 1);
		answer[2] = rooms.remove(rooms.size() - 1);
		
		allCards.addAll(suspects);
		allCards.addAll(weapons);
		allCards.addAll(rooms);
		
		
		//distribui demais cartas entre os jogadores
		Collections.shuffle(allCards);
			
		playerCards = new Vector[ObservedGame.numPlayers];
		for (int i = 0; i < ObservedGame.numPlayers; i++)
		{
			playerCards[i] = new Vector<Card>();
		}
		
		int i = 0;
		while(!allCards.isEmpty())
		{
			if(activePlayers[i])
			{
				Card temp = allCards.remove(allCards.size() - 1);
				playerCards[i].add(temp);
				//marca carta nas notas do jogador
				notes[i].eliminateCard(temp);
								
			}
			i = (i+1) % ObservedGame.numPlayers;
		}
		
		
		
	}

	public Card guess(int suspectID, int weaponID, int roomID) 
	{
		hasGuessed = true;
		//checa se h� alguma carta na pr�pria m�o
		for(Card c: playerCards[currentTurn])
		{
			if(	(c.type == CardType.SUSPECT && c.id == suspectID) 
				||	(c.type == CardType.WEAPON &&  c.id == weaponID)
				||	(c.type == CardType.ROOM &&  c.id == roomID))
				{
					notes[currentTurn].eliminateCard(c);
					return c;
				}
		}
		
		//checa se h� alguma carta em alguma outra m�o
		int i = (currentTurn+1)%6;
		while(i != (currentTurn+1)%6)
		{
			for(Card c: playerCards[i])
			{
				if(	(c.type == CardType.SUSPECT && c.id == suspectID) 
					||	(c.type == CardType.WEAPON &&  c.id == weaponID)
					||	(c.type == CardType.ROOM &&  c.id == roomID)) 
					{
						notes[currentTurn].eliminateCard(c);
						return c;
					}
			}
			i = (i+1)%6;
		}
		
		//se nenhuma est� em nenhuma m�o, as tr�s est�o no envelope confidencial
		return null;
	}

	public boolean getHasGuessed()
	{
		return hasGuessed;
	}

	public boolean[] getActivePlayers() {
		return activePlayers;
	}
	
	public boolean accuse(int suspectID, int weaponID, int roomID) 
	{
		if(answer[0].equals(new Card(CardType.SUSPECT, suspectID)) && answer[1].equals(new Card(CardType.WEAPON, weaponID)) && answer[2].equals(new Card(CardType.ROOM, roomID)))
		{
			//player ganhou o jogo
			return true;
		}
		else
		{
			//player deve sair do jogo
			activePlayers[currentTurn] = false;
			endTurn();
			return false;
		}
	}

	
	public boolean allLost() 
	{
		for(int i = 0; i < activePlayers.length; i++)
		{
			if(activePlayers[i]) return false;
		}
		return true;
	}
	
	public void saveGame(FileOutputStream fileOut)
	{
		try {
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved!");
	      }catch(IOException i) {
	    	  System.out.println("Erro de salvamento:" + i.getMessage());
	      }
	}

}


