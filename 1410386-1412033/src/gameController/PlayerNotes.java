package gameController;

class PlayerNotes implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -886202489106592269L;
	
	//player é representado por um inteiro
	private int player;
	
	//arrays que indicam se player já eliminou aquele jogador/arma/local como suspeitos
	
	private boolean[] eliminatedPlayers;
	private boolean[] eliminatedWeapons;
	private boolean[] eliminatedRooms;
	
	public PlayerNotes(int player)
	{
		this.player = player;
		
		eliminatedPlayers = new boolean[ObservedGame.numPlayers];
		eliminatedWeapons = new boolean[ObservedGame.numWeapons];
		eliminatedRooms   = new boolean[9];
		
		for(int i = 0; i < ObservedGame.numPlayers; i++)
		{
			eliminatedPlayers[i] = false;
		}
		
		for(int i = 0; i < ObservedGame.numWeapons; i++)
		{
			eliminatedWeapons[i] = false;
		}
		
		for(int i = 0; i < 9; i++)
		{
			eliminatedRooms[i] = false;
		}
			
	}
	
	public boolean[] getEliminatedPlayers()
	{
		return eliminatedPlayers;
	}
	
	public boolean[] getEliminatedWeapons()
	{
		return eliminatedWeapons;
	}
	
	public boolean[] getEliminatedRooms()
	{
		return eliminatedRooms;
	}
	
	public void setEliminatedPlayers(boolean[] vec)
	{
		if(vec.length == eliminatedPlayers.length)
		{
			eliminatedPlayers = vec;
		}
		else return;		
	}
	
	public void setEliminatedWeapons(boolean[] vec)
	{
		if(vec.length == eliminatedWeapons.length)
		{
			eliminatedWeapons = vec;
		}
		else return;		
	}
	
	public void setEliminatedRooms(boolean[] vec)
	{
		if(vec.length == eliminatedRooms.length)
		{
			eliminatedRooms = vec;
		}
		else return;		
	}
	
	
	public int getPlayer()
	{
		return player;
	}
	
	public void eliminatePlayer(int i)
	{
		eliminatedPlayers[i] = true;
	}
	
	public void eliminateWeapon(int i)
	{
		eliminatedWeapons[i] = true;
	}
	
	public void eliminateRoom(int i)
	{
		eliminatedRooms[i] = true;
	}
	
	//recebe carta e elimina suspeito/arma/comodo equivalente
	public void eliminateCard(Card c)
	{
		if(c.type == CardType.SUSPECT)
		{
			eliminatedPlayers[c.id] = true;
		}
		
		if(c.type == CardType.WEAPON)
		{
			eliminatedWeapons[c.id] = true;
		}
		
		if(c.type == CardType.ROOM)
		{
			eliminatedRooms[c.id] = true;
		}
	}
	
} 