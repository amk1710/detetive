package gameController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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
	public static ObservedGame  getGameInstance(File input)
	{
		
		GameRules temp;
		try {
	         FileInputStream fileIn = new FileInputStream(input);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         temp = (GameRules) in.readObject();
	         temp.tabuleiro.readImages();
	         game = (ObservedGame) temp;
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	    	  System.out.println("Read Error" + i.getMessage());
	    	  i.printStackTrace();
	      }catch(ClassNotFoundException c) {
	         System.out.println("ObservedGame class not found" + c.getMessage());
	         c.printStackTrace();
	         
	      }
				
		return game;
	      
	}
}
