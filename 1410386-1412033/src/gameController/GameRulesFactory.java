package gameController;

public class GameRulesFactory {
	private static ObservedGame game = null;

	//get jogo novo
	public static ObservedGame getGameInstance(boolean[] activePlayers)
	{
		if(game == null)
		{
			game = new GameRules(activePlayers);
		}
		return game;
	}
	
	//get jogo salvo
	public static void  getGameInstance()
	{
		
	}
}
