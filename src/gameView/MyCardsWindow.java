package gameView;

import gameController.Card;
import gameController.ObservedGame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class MyCardsWindow extends JFrame 
{
	public final int WIDTH	= 640;
	public final int HEIGHT = 480;
	
	private CardsPanel cp;
	
	
	GameView gv;
	
	public MyCardsWindow(String s,GameView gameview)
	{
		super (s);
		gv = gameview;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		cp = new CardsPanel(gv);
		
		cp.setVisible(true);
		getContentPane().add(cp);
		
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

class CardsPanel extends JPanel
{
	// usado para manipular o jogo
	private GameView gv;
	private int cont;
	private Card[] cards;
	
	ImageIcon[] images;
	JLabel[] labels;
	
	public CardsPanel(GameView gameview)
	{
		gv = gameview;
		
		setLayout(new FlowLayout());

		
		//setLayout(new GridBagLayout());
		//GridBagConstraints c = new GridBagConstraints();
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 2;
		//c.insets = new Insets(0, 0, 8, 0);	
        //c.anchor = GridBagConstraints.PAGE_END;
		
		cards  = gv.gc.getPlayerHand(gv.gc.getTurn());
		if(cards == null) System.out.println("null!!");
		
		
		images = new ImageIcon[cards.length];
		labels = new JLabel[cards.length];
		
		cont = 0;
		for(int i = 0; i < cards.length; i++)
		{
			//c.gridy=0;
			//c.gridx = i * CardImages.getImage(cards[i]).getWidth();
			//c.weighty = 1.0;
			images[i] = new ImageIcon(CardImages.getImage(cards[i]));
			labels[i] = new JLabel(images[i]);
			labels[i].setBounds(10, 10, 10, 10);
			labels[i].setVisible(true);
			add(labels[i]);
			cont++;
		}
		 
	    
		
	}
	
	public Dimension getPreferredSize()
	{
        return new Dimension(labels[0].getWidth()*cont/2, labels[0].getHeight()*2);
		//return new Dimension(400, 400);
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int i = 0; i < labels.length; i++)
		{
			labels[i].repaint();
		}
		
	}
	
	
}