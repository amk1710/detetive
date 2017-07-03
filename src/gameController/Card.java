package gameController;


//uma carta tem um identificar de tipo(suspeito, arma ou comodo) e um identificador(qual suspeito/arma/comodo). Ambos são imutáveis
public class Card implements java.io.Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -453809330571829129L;
	public final CardType type;
	public final int id;
	
	private static final String[] playerNames = {  "Senhorita Scarlet", "Coronel Mustard", "Senhora White", "Reverendo Green", "Senhora Peacock", "Professor Plum"};
	private static final String[] weaponNames = {"Corda", "Cano de Chumbo", "Faca", "Chave Inglesa", "CastiÃ§al", "Revólver"};
	private static final String[] roomNames =   { "Cozinha", "Sala de Jantar", "Sala de Estar", "Sala de Mï¿½sica", "Entrada", "Jardim de Inverno", "Salï¿½o de Jogos", "Biblioteca", "Escritï¿½rio"};
	
	
	Card(CardType type, int id)
	{
		this.type = type;
		switch(this.type)
		{
			case SUSPECT:
				if(id < ObservedGame.numPlayers) this.id = id;
				else this.id = -1;
				break;
			case WEAPON:
				if(id < ObservedGame.numWeapons) this.id = id;
				else this.id = -1;
				break;
			case ROOM:
				if(id < ObservedGame.numRooms) this.id = id;
				else this.id = -1;
				break;
			default:
				this.id = -1;
				break;
		}
	}
	
	public String getName()
	{
		switch(this.type)
		{
			case SUSPECT:
				return playerNames[this.id];
			case WEAPON:
				return weaponNames[this.id];
			case ROOM:
				return roomNames[this.id];
			default:
				return "Not a Card";
		}
		
	}
	
	public static String getName(CardType type, int id)
	{
		switch(type)
		{
			case SUSPECT:
				return playerNames[id];
			case WEAPON:
				return weaponNames[id];
			case ROOM:
				return roomNames[id];
			default:
				return "Not a Card";
		}
		
	}
	
}