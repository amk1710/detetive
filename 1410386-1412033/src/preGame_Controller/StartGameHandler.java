package preGame_Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRootPane;

import gameController.ObservedGame;
import gameView.GameView;
import preGame_View.GameScreen;

public class StartGameHandler{
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
						case("Reverendo Green"):  playerActive[0] = true; break;
						case("Coronel Mustard"):  playerActive[1] = true; break;
						case("Senhora Peacock"):  playerActive[2] = true; break;
						case("Professor Plum"):   playerActive[3] = true; break;
						case("Senhorita Scarlet"):playerActive[4] = true; break;
						case("Senhora White"):    playerActive[5] = true; break;
					}
				} else if(e.getStateChange() == ItemEvent.DESELECTED && numActive>0){
					numActive--;
					switch(source.getText()){
					case("Reverendo Green"):  playerActive[0] = false; break;
					case("Coronel Mustard"):  playerActive[1] = false; break;
					case("Senhora Peacock"):  playerActive[2] = false; break;
					case("Professor Plum"):   playerActive[3] = false; break;
					case("Senhorita Scarlet"):playerActive[4] = false; break;
					case("Senhora White"):    playerActive[5] = false; break;
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
					GameScreen game = new GameScreen("DETETIVE - CLUE ", playerActive);
					GameView view = new GameView(playerActive);
					System.out.println(view.toString());
					game.setVisible(true);
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
