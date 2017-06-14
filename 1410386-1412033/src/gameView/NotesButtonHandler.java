package gameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import preGame_View.NewGameScreen;
import gameController.ObservedGame;

public class NotesButtonHandler implements ActionListener 
{

	ObservedGame gc;
	
	public NotesButtonHandler(ObservedGame game)
	{
		gc = game;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		NotesWindow notes = new NotesWindow("Minhas notas", gc);
		notes.setVisible(true);
	}

}