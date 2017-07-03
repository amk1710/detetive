package gameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gameController.ObservedGame;

public class passagemSecretaHandler implements ActionListener {
	ObservedGame gc;
	public passagemSecretaHandler(ObservedGame game)
	{
		gc = game;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		int comodoJogador = gc.getTabuleiro().emComodo(gc.getTurn());
		gc.getTabuleiro().mudaPosJogadorPassagemSecreta(gc.getTurn(), comodoJogador);
		gc.endTurn();
	}

}
