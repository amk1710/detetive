package preGame_View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRootPane;

import gameController.GameRulesFactory;
import gameController.ObservedGame;
import gameView.GameView;

class StartGameHandler{
	private int numActive = 0;
	private boolean[] playerActive = {false,false,false,false,false,false};
	
	private static StartGameHandler instance;
	private ItemListener checkboxListener = null;
	private ActionListener buttonListener = null;
	
	private StartGameHandler() {
		checkboxListener = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox source = (JCheckBox) e.getSource();
				if(e.getStateChange() == ItemEvent.SELECTED){
					numActive++;
					switch(source.getText()){
						case("Senhorita Scarlet"):  playerActive[ObservedGame.SCARLET] = true; break;
						case("Coronel Mustard"):  	playerActive[ObservedGame.MUSTARD] = true; break;
						case("Senhora White"):  	playerActive[ObservedGame.WHITE] = true; break;
						case("Reverendo Green"):   	playerActive[ObservedGame.GREEN] = true; break;
						case("Senhora Peacock"):	playerActive[ObservedGame.PEACOCK] = true; break;
						case("Professor Plum"):    	playerActive[ObservedGame.PLUM] = true; break;
					}
				} else if(e.getStateChange() == ItemEvent.DESELECTED && numActive>0){
					numActive--;
					switch(source.getText()){
					case("Senhorita Scarlet"):  playerActive[ObservedGame.SCARLET] = false; break;
					case("Coronel Mustard"):  	playerActive[ObservedGame.MUSTARD] = false; break;
					case("Senhora White"):  	playerActive[ObservedGame.WHITE] = false; break;
					case("Reverendo Green"):   	playerActive[ObservedGame.GREEN] = false; break;
					case("Senhora Peacock"):	playerActive[ObservedGame.PEACOCK] = false; break;
					case("Professor Plum"):    	playerActive[ObservedGame.PLUM] = false; break;
					}
				}
			}
		};
		
		buttonListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(numActive >= ObservedGame.minPlayers){
					System.out.println(playerActive[0] + "\n" + playerActive[1] + "\n" + playerActive[2] + "\n" + 
									   playerActive[3] + "\n" + playerActive[4] + "\n" + playerActive[5] + "\n");
					
					//Inicializa regras do jogo e cria janela de jogo
					GameRulesFactory.getGameInstance(playerActive);
					GameView view = new GameView("DETETIVE - CLUE ", playerActive);
					System.out.println(view.toString());
					view.setVisible(true);
					JRootPane contentPane = ((JButton)e.getSource()).getRootPane();
					JFrame j = (JFrame) contentPane.getParent();
					j.dispose();
				}
			}
			
		};
	}
	
	public static StartGameHandler getInstance(){
		if(instance == null){
			instance = new StartGameHandler();
		}
		return instance;
	}
	
	public ItemListener getCheckboxListener(){
		return checkboxListener;
	}
	
	public ActionListener getButtonListener(){
		return buttonListener;
	}


}
