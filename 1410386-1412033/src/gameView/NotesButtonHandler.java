package gameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import gameController.ObservedGame;

public class NotesButtonHandler implements ActionListener 
{

	ObservedGame gc;
	GameView gv;
	
	public NotesButtonHandler(ObservedGame game, GameView gameview)
	{
		gc = game;
		gv = gameview;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		NotesWindow notes = new NotesWindow("Minhas notas", gc, gv);
		notes.setVisible(true);
	}

}