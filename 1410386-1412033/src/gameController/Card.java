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
	
	Card(CardType type, int id)
	{
		this.type = type;
		this.id = id;
	}
}