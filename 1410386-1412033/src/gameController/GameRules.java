package gameController;

import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;

//classe que controlam regras do jogo, recebendo ações da GUI através da interface ObservedGame e notificando mudanças
class GameRules extends Observable implements ObservedGame
{
	private GameRules gr;
	
	private Tabuleiro tabuleiro;
	
	
	private boolean[] activePlayers;
	private int currentTurn;
	private int die;
	//diz se dado já foi rolado esse turno
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
		
		dealCards();
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
		//se dado jï¿½ foi rolado essa rodada, nï¿½o faz nada
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
		hasGuessed = false;
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

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	//funções auxiliares
	
	public Card[] getPlayerHand(int i) 
	{
		Card[] hand = new Card[1];
		hand = playerCards[i].toArray(hand);
		//System.out.println(hand[0].toString());
		return hand;
	}
	
	//função distribui as cartas de jogo pelos jogadores
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
		
		//tira três cartas para o envelope confidencial
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
		//checa se há alguma carta na própria mão
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
		
		//checa se há alguma carta em alguma outra mão
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
		
		//se nenhuma está em nenhuma mão, as três estão no envelope confidencial
		return null;
	}

	public boolean getHasGuessed()
	{
		return hasGuessed;
	}
	

}


