package gameController;

public class GameRulesFactory {
	private static ObservedGame game = null;

	//get jogo novo
	public static ObservedGame getGameInstance(boolean[] activePlayers)
	{
		game = new GameRules(activePlayers);
		return game;
	}
	
	public static ObservedGame getGameInstance()
	{
		return game;
	}
	
	//get jogo salvo
	public static void  getGameInstance2()
	{
		
	}
}
