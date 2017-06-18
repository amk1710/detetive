package gameView;


import gameController.GameRulesFactory;
import gameController.ObservedGame;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import preGame_Controller.NewGameHandler;

//classe view que se registra como observadora e interage com o jogo
public class GameView extends JFrame implements Observer
{

	ObservedGame gc;
	DieDisplay die;
	Tabuleiro grid;
	NotesButtonPanel notesP;
	
	//construtor para novo jogo
	public GameView(String s, boolean[] activePlayer)
	{
		super (s);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		gc = GameRulesFactory.getGameInstance(activePlayer);
		gc.addObserver(this);		
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING, 15, 30));
		grid = new Tabuleiro(activePlayer, gc);
		die = new DieDisplay(gc);
		notesP = new NotesButtonPanel(gc);
		
		
		
		getContentPane().add(grid);
		getContentPane().add(die);
		getContentPane().add(notesP);
		pack();
		// Inicializa janela no tamanho default no centro da tela.
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int	sl=screenSize.width;
		int	sa=screenSize.height;
		
		setBounds(0,0,(int)getContentPane().getPreferredSize().getWidth(),(int)getContentPane().getPreferredSize().getHeight());
				
	}
	
	public void update(Observable o, Object arg)
	{
		this.repaint();
		grid.repaint();
		die.repaint();
		notesP.repaint();
	}	

}

//painel que interage com e exibe resultado dos dados do jogo
class DieDisplay extends JPanel
{
	// usado para manipular o jogo
	private ObservedGame gc;
	private int result;
	
	private ImagePanel imgPanel;
	
	//imagens para resultados de 1 a 6
	private BufferedImage d1;
	private BufferedImage d2;
	private BufferedImage d3;
	private BufferedImage d4;
	private BufferedImage d5;
	private BufferedImage d6;
	
	public DieDisplay(ObservedGame game)
	{
		gc = game;
		
		try
		{
			d1 = ImageIO.read(new File("assets/dado1.jpg"));
			d2 = ImageIO.read(new File("assets/dado2.jpg"));
			d3 = ImageIO.read(new File("assets/dado3.jpg"));
			d4 = ImageIO.read(new File("assets/dado4.jpg"));
			d5 = ImageIO.read(new File("assets/dado5.jpg"));
			d6 = ImageIO.read(new File("assets/dado6.jpg"));

		} catch (IOException e)
		{
			System.out.println("Incapaz de abrir imagem. Erro:" + e.getMessage());
			System.exit(1);
		}
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.insets = new Insets(0, 0, 8, 0);	
        c.anchor = GridBagConstraints.PAGE_END;
		
		//bot�o usado para tentar rolar o dado
        JButton rollDie = new JButton("Rolar Dado");
		rollDie.addActionListener(new RollDieHandler(gc));
		
		imgPanel = new ImagePanel(d1);
		
		c.gridy=0;
	    c.weighty = 1.0;
	    add(rollDie, c);
	    
	    c.gridy=0;
        c.weighty = 1.0;
		add(imgPanel, c);
	    
	    
		
	}
	
	public Dimension getPreferredSize() 
	{
        return new Dimension(d1.getWidth(), d1.getHeight());
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		switch(gc.getDie())
		{
			case 1: 
				//imgPanel.setImage(d1);
				g.drawImage(d1, 0, 0, d1.getWidth(), d1.getHeight(), this);
				break;
			case 2:
				//imgPanel.setImage(d2);
				g.drawImage(d2, 0, 0, d2.getWidth(), d2.getHeight(), this);
				break;
			case 3: 
				//imgPanel.setImage(d3);
				g.drawImage(d3, 0, 0, d3.getWidth(), d3.getHeight(), this);
				break;
			case 4: 
				//imgPanel.setImage(d4);
				g.drawImage(d4, 0, 0, d4.getWidth(), d4.getHeight(), this);
				break;
			case 5: 
				//imgPanel.setImage(d5);
				g.drawImage(d5, 0, 0, d5.getWidth(), d5.getHeight(), this);
				break;
			case 6: 
				//imgPanel.setImage(d6);
				g.drawImage(d6, 0, 0, d6.getWidth(), d6.getHeight(), this);
				break;		
		}
		
		imgPanel.repaint();
		
		
	}
	
	
}

class NotesButtonPanel extends JPanel
{
	// usado para manipular o jogo
	private ObservedGame gc;
		
	public NotesButtonPanel(ObservedGame game)
	{
		gc = game;
		System.out.println("created");
		
		
		//bot�o usado para tentar rolar o dado
        JButton notesB = new JButton("Ver Notas");
		notesB.addActionListener(new NotesButtonHandler(gc));
		
		add(notesB);
		
				
	}
	
	public Dimension getPreferredSize() 
	{
        return new Dimension(40, 30);
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		System.out.println("painted");
	}
	
	
}

class ImagePanel extends JPanel
{
	BufferedImage img;
		
	public ImagePanel(BufferedImage i)
	{
		img = i;
	}
	
	public void setImage(BufferedImage i)
	{
		img = i;
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);
	}
		
}

class Tabuleiro extends JPanel
{
	private BufferedImage i;
	private final int treshold = 18;
	private int passo = 1000; //obtido do dado - via notificaçao observer
	private ObservedGame gc = null;
	
	//Players info
	private boolean[] playerActive = null;
	
	//Tamanho das peças - cada quadrado é 36x36 pixels
	private int square		= 36;
	private int diameter 	= 36;
	
	private int x_green 	= 582;
	private int y_green		= 59;
	private Color green_color = new Color(36,83,41);
	private int x_mustard	= 68;
	private int y_mustard	= 682;
	private Color mustard_color = new Color(204,176,32);
	private int x_peacock 	= 911;
	private int y_peacock	= 277;
	private Color peacock_color = new Color(45,62,118);
	private int x_plum		= 916;
	private int y_plum		= 746;
	private Color plum_color = new Color(101,68,85);
	private int x_scarlet	= 330;
	private int y_scarlet	= 937;
	private Color scarlet_color = new Color(203,82,74);
	private int x_white 	= 401;
	private int y_white		= 58;
	private Color white_color = new Color(183,186,193);
	
	public Tabuleiro(boolean[] playerActive, ObservedGame game)
	{
		try{
			i = ImageIO.read(new File("assets/Tabuleiro-Original.JPG"));

		} catch (IOException e){
			System.out.println("Incapaz de abrir imagem. Erro:" + e.getMessage());
			System.exit(1);
		}
		
		this.playerActive = playerActive;
		this.gc = game;
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if( posValida(e.getX(), e.getY())){
					//Muda posição do jogador 
					mudaPos(e.getX(), e.getY());
					//Manda repintar
					repaint();
					//Passa a vez
					gc.endTurn();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}	
		});
	}
	
	protected void mudaPos(int x, int y) {
		int playerX=0, playerY=0, deslX=0, deslY=0, player=-1;
		switch(gc.getTurn()){
			case 0: playerX = x_green; playerY = y_green; player=0; break;
			case 1:	playerX = x_mustard; playerY = y_mustard; player=1;break;
			case 2: playerX = x_peacock; playerY = y_peacock; player=2;break;
			case 3: playerX = x_plum; playerY = y_plum; player=3;break;
			case 4: playerX = x_scarlet; playerY = y_scarlet; player=4;break;
			case 5: playerX = x_white; playerY = y_white; player=5;break;
		};
		
		deslX = (int) Math.ceil((x-playerX)/36);System.out.println(deslX);
		deslY = (int) Math.ceil((y-playerY)/36);System.out.println(deslY);
		
		switch(player){
		case 0: x_green +=  36*deslX; y_green +=  36*deslY; break;
		case 1:	x_mustard +=  36*deslX; y_mustard +=  36*deslY; break;
		case 2: x_peacock +=  36*deslX; y_peacock+=  36*deslY; break;
		case 3: x_plum +=  36*deslX; y_plum +=  36*deslY; break;
		case 4: x_scarlet+=  36*deslX; y_scarlet +=  36*deslY; break;
		case 5: x_white +=  36*deslX; y_white +=  36*deslY;break;
	};
		
	}

	public Dimension getPreferredSize() {
        return new Dimension(i.getWidth(), i.getHeight());
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(i, 0, 0, i.getWidth(), i.getHeight(), this);
		Graphics2D g2d  = (Graphics2D) g;
		
		g2d.setColor(mustard_color);
		g2d.fillOval(x_mustard, y_mustard, diameter, diameter);
		g2d.setColor(peacock_color);
		g2d.fillOval(x_peacock, y_peacock, diameter, diameter);
		g2d.setColor(plum_color);
		g2d.fillOval(x_plum, y_plum, diameter, diameter);
		g2d.setColor(green_color);
		g2d.fillOval(x_green, y_green, diameter, diameter);
		g2d.setColor(scarlet_color);
		g2d.fillOval(x_scarlet, y_scarlet, diameter, diameter);
		g2d.setColor(white_color);
		g2d.fillOval(x_white, y_white, diameter, diameter);
	}

	protected boolean posValida(int x, int y) {
		//Provavel necessaria mudança para distancia valida a partir do mesmo quadrado usado para jogadorEm
		if(x >=106 && x <=908 && y >=92 && y <=938  ){
			//Dentro do bloco central
			if(x >=440 && x <=625 && y >=419 && y <=678){System.out.println("Dentro do bloco central"); return false;}
			//Dentro da sla de jantar	
			if( (x >=104 && x <=365 && y >=450 && y<=642) || (x >=104 && x <=254 && y >=384 && y <=450)){
				if(distanciaValida(311, 660, passo) && !jogadorEm(311, 660)){System.out.println("Dentro da sala de jantar");return true;}
			}
			//Dentro da cozinha
			if(x >=106 && x <=289 && y >=92 && y<=312){
				if(distanciaValida(236, 330, passo) && !jogadorEm(236, 330)){System.out .println("Dentro da cozinha");return true;}
			}
			//Dentro do salão de música
			if( (x >=441 && x <=577 && y >=106 && y<=133) || (x >=366 && x <=655 && y >=133 && y<=346)){
				if((distanciaValida(420,365, passo) || distanciaValida(675,258, passo)) && (!jogadorEm(420,365) || !jogadorEm(675,258))){System.out.println("Dentro do salao de musica");return true;}
			}
			//Dentro do jardim de inverno 
			if( (x >=733 && x <=908 && y >=97 && y<=239) || (x >=771 && x <=913 && y >=239 && y<=278)){
				if(distanciaValida(748, 258, passo) && !jogadorEm(748, 258)){System.out.println("Dentro do jardim de inverno");return true;}
			}
			//Dentro do salão de jogos
			if(x>=734 && x <=908 && y >=348 && y<=529){
				if(distanciaValida(713,401, passo) && !jogadorEm(713, 401)){System.out.println("Dentro do salao de jogos");return true;}
			}
			//Dentro da biblioteca
			if((x >=696 && x <=908 && y >=607 && y <=711) || ( x >=734 && x <=908 && ((y >=567 && y <=607) || (y >=711 && y <=748))) ){
				if(distanciaValida(678,657, passo) && !jogadorEm(678,657)){System.out.println("Dentro da biblioteca");return true;}
			}
			//Dentro do escritório
			if(x >=700 && x <=908 && y >=819 && y <=938){
				if(distanciaValida(716,802, passo) && !jogadorEm(716,802)){System.out.println("Dentro do escritório");return true;}
			}
			//Dentro da sala de estar
			if(x >=106 && x <=330 && y >=752 && y <=938){
				if(distanciaValida(312,733,passo) && !jogadorEm(312,733)){System.out.println("Dentro da sala de estar");return true;}
			}
			//Dentro da entrada
			if(x >=407 && x <=620 && y >=713 && y <=938){
				if(distanciaValida(532,695, passo) && !jogadorEm(532,695)){System.out.println("Dentro da entrada");return true;}
			}
			//Testa se respeita distancia de acordo com o dado
			if(distanciaValida(x, y, passo)) return true;
		}
		return false;
	}

	private boolean jogadorEm(int x, int y) {
		if(playerActive[0] && (x_green >= x-treshold   && x_green <= x+treshold)   && (y_green >= y-treshold   && y_green <= y+treshold))	return true;
		if(playerActive[1] && (x_mustard >= x-treshold && x_mustard <= x+treshold) && (y_mustard >= y-treshold && y_mustard <= y+treshold))	return true;
		if(playerActive[2] && (x_peacock >= x-treshold && x_peacock <= x+treshold) && (y_peacock >= y-treshold && y_peacock <= y+treshold))	return true;
		if(playerActive[3] && (x_plum >= x-treshold    && x_plum <= x+treshold)    && (y_plum >= y-treshold    && y_plum <= y+treshold))	return true;
		if(playerActive[4] && (x_scarlet >= x-treshold && x_scarlet <= x+treshold) && (y_scarlet >= y-treshold && y_scarlet <= y+treshold))	return true;
		if(playerActive[5] && (x_white >= x-treshold   && x_white <= x+treshold)   && (y_white >= y-treshold   && y_white <= y+treshold))	return true;
		return false;
	}

	private boolean distanciaValida(int x, int y, int passo) {
		int playerX=0, playerY=0;
		switch(gc.getTurn()){
			case 0: playerX = x_green; playerY = y_green; break;
			case 1:	playerX = x_mustard; playerY = y_mustard; break;
			case 2: playerX = x_peacock; playerY = y_peacock; break;
			case 3: playerX = x_plum; playerY = y_plum; break;
			case 4: playerX = x_scarlet; playerY = y_scarlet; break;
			case 5: playerX = x_white; playerY = y_white; break;
		};
		if(Math.abs(x-playerX)/36 + (Math.abs(y-playerY)/36) <=passo){System.out.println("Passo valido"); return true;}
		return false;
	}

}
