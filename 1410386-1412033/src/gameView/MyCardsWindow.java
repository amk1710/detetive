package gameView;

import gameController.Card;
import java.awt.Canvas;
import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;



public class MyCardsWindow extends JFrame 
{
	public final int WIDTH	= 640;
	public final int HEIGHT = 480;
	
	
	GameView gv;
	
	public MyCardsWindow(String s,GameView gameview)
	{
		super (s);
		gv = gameview;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		Card[] cards = gv.gc.getPlayerHand(gv.gc.getTurn());
		if(cards == null) System.out.println("cards null!!!");
		for(int i = 0; i < cards.length; i++)
		{
			if(cards[i] == null) System.out.println("null!!!");
			this.add(new CardsView(cards[i]));
		}
		
		
		pack();
		
		// Inicializa janela no tamanho default no centro da tela.
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int	sl=screenSize.width;
		int	sa=screenSize.height;
		
		int	x=sl/2- ((int)getContentPane().getPreferredSize().getWidth())/2;
		int	y=sa/2- ((int)getContentPane().getPreferredSize().getHeight())/2;
		
		setBounds(x,y,(int)getContentPane().getPreferredSize().getWidth(),(int)getContentPane().getPreferredSize().getHeight());
	
	}
	
	
}

class CardsView extends ImagePanel{
	
	GameView gv;
	
	CardsView(BufferedImage i)
	{
		super(i);		
	}
	
	CardsView(Card c)
	{
		super(CardImages.getImage(c));
	}
}