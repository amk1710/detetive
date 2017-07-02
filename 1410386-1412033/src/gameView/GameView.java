package gameView;


import gameController.Card;
import gameController.GameRulesFactory;
import gameController.ObservedGame;
import gameController.Tabuleiro;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt .event.MouseEvent;
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

//classe view que se registra como observadora e interage com o jogo

public class GameView extends JFrame //implements Observer

{

	ObservedGame gc;
	private DieDisplay die;
	private PainelTabuleiro grid;
	//private NotesButtonPanel notesP;
	//private CardsButtonPanel cardsP;
	private ButtonsPanel buttons;
	
	
	//construtor para novo jogo
	public GameView(String s, boolean[] activePlayer)
	{
		super (s);
		//setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
 
    gc = GameRulesFactory.getGameInstance();
		//gc.addObserver(this);
    getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING, 15, 30));
		die = new DieDisplay(gc);
		grid = new PainelTabuleiro(gc, this);
    //notesP = new NotesButtonPanel(gc, this);
		//cardsP = new CardsButtonPanel(this);
    buttons = new ButtonsPanel(gc, this);
		
		
		getContentPane().add(die);
		getContentPane().add(grid);
		getContentPane().add(buttons);
		//getContentPane().add(notesP);
		//getContentPane().add(cardsP);
		
		pack();
		// Inicializa janela no tamanho default no centro da tela.
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int	sl=screenSize.width;
		int	sa=screenSize.height;
		
		setBounds(0,0,(int)getContentPane().getPreferredSize().getWidth(),(int)getContentPane().getPreferredSize().getHeight());
		
				
	}
	/*
	public void update(Observable o, Object arg)
	{
		this.repaint();
		
	}	
	 */
}

class ButtonsPanel extends JPanel
{
	// usado para manipular o jogo
	private ObservedGame gc;
	private GameView gv;
	
	JButton notesB;
	JButton rollDie;
	JButton myCardsB;
	JButton accuseB;
		
	public ButtonsPanel(ObservedGame game, GameView gameview)
	{
		gc = game;
		gv = gameview;
		
		
		//bot�o usado para abrir a janela das notas
        notesB = new JButton("Ver Notas");
		notesB.addActionListener(new NotesButtonHandler(gc, gv));
		
		//bot�o usado para tentar rolar o dado
        rollDie = new JButton("Rolar Dado");
		rollDie.addActionListener(new RollDieHandler(gc));
		
		//bot�o usado para ver cartas do player
		myCardsB = new JButton("Ver Minhas Cartas");
        myCardsB.addActionListener(new MyCardsButtonHandler(gv));
        
        //bot�o usado para acusa��o
        accuseB =  new JButton("Acusar");
        accuseB.addActionListener(new AccuseButtonHandler(gv));
		
		add(rollDie);
		add(myCardsB);
		add(notesB);
		
				
	}
	
	public Dimension getPreferredSize() 
	{
        int width = Math.max(rollDie.getPreferredSize().width, Math.max(notesB.getPreferredSize().width, myCardsB.getPreferredSize().width));
		int height = rollDie.getPreferredSize().height + notesB.getPreferredSize().height + myCardsB.getPreferredSize().height + 30;
        return new Dimension(width, height);
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	
}

//painel que interage com e exibe resultado dos dados do jogo
class DieDisplay extends JPanel implements Observer
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
		result = gc.getDie();
		game.addObserver(this);
		
		
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
		
		imgPanel = new ImagePanel(d1);
	       
		
	}
	
	public Dimension getPreferredSize() 
	{
        return new Dimension(d1.getWidth(), d1.getHeight());
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		switch(result)
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

	@Override
	public void update(Observable arg0, Object arg1) {
		result = gc.getDie();
		this.repaint();
		
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

class NotesButtonPanel extends JPanel
{
	// usado para manipular o jogo
	private ObservedGame gc;
	private GameView gv;
		
	public NotesButtonPanel(ObservedGame game, GameView gameview)
	{
		gc = game;
		gv = gameview;
		
		
		//bot�o usado para abrir a janela das notas
        JButton notesB = new JButton("Ver Notas");
		notesB.addActionListener(new NotesButtonHandler(gc, gv));
		
		add(notesB);
		
				
	}
	
	public Dimension getPreferredSize() 
	{
        return new Dimension(40, 30);
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	
}

class CardsButtonPanel extends JPanel
{
	// usado para ver as cartas
	
	private GameView gv;
		
	public CardsButtonPanel(GameView gameview)
	{
		gv = gameview;		
		
		//bot�o usado para abrir a janela com as cartas do jogador
        JButton myCardsB = new JButton("Ver Minhas Cartas");
        myCardsB.addActionListener(new MyCardsButtonHandler(gv));
		
		add(myCardsB);
		
	}
	
	public Dimension getPreferredSize() 
	{
        return new Dimension(40, 30);
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	
}



class PainelTabuleiro extends JPanel implements Observer
{
	private ObservedGame gc = null;
	private GameView gv;
	private Tabuleiro tabuleiro = null;
	
	public PainelTabuleiro(ObservedGame game, GameView gameview)
	{
	
		this.gc = game;
		this.gc.addObserver(this);
		this.tabuleiro = gc.getTabuleiro();
		this.gv = gameview;

		this.addComponentListener(new ComponentListener(){
			public void componentHidden(ComponentEvent e) {
			}
			public void componentMoved(ComponentEvent e) {			
			}
			public void componentShown(ComponentEvent e) {	
			}
			public void componentResized(ComponentEvent e) {
				tabuleiro.setEscalaHorizontal(e.getComponent().getWidth());
				tabuleiro.setEscalaVertical(e.getComponent().getHeight());
			}

		});
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if( gc.getDieWasRolled() && tabuleiro.posValida(e.getX(), e.getY(), gc.getDie(), gc.getTurn(), true)){
					//Muda posição do jogador 
					tabuleiro.mudaPosJogador(e.getX(), e.getY(), gc.getTurn());
					tabuleiro.removeAlcance();
					//Manda repintar
					repaint();
					//abre janela de palpite
					//TO-DO se entrar em comodo chamar uma funçao para notificar painel de acoes e oferecer opcao de dar palpite
					//if()
					//{
						GuessWindow gw = new GuessWindow("Palpite", gv);
						gw.setVisible(true);
					//}
					
					//Passa a vez. Agora? Melhor por um bot�o n�?
					gc.endTurn();
				}
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}	
		});
	}

	public Dimension getPreferredSize() {
        return new Dimension((int) (tabuleiro.imgTabuleiro.getWidth()*tabuleiro.getEscalaHorizontal()), (int) (tabuleiro.imgTabuleiro.getHeight()*tabuleiro.getEscalaVertical()));
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(tabuleiro.imgTabuleiro, 0, 0, (int) (tabuleiro.imgTabuleiro.getWidth()*tabuleiro.getEscalaHorizontal()), (int) (tabuleiro.imgTabuleiro.getHeight()*tabuleiro.getEscalaVertical()), this);
		Graphics2D g2d  = (Graphics2D) g;
		
		
		for(int i=0; i<tabuleiro.getTamanhoTabuleiro(); i++){
				switch(tabuleiro.matriz[i]){
					case Tabuleiro.ALCANCE:
						g2d.setColor(Tabuleiro.corAlcance);
						g2d.fillRect(tabuleiro.getXPosJogador(i), tabuleiro.getYPosJogador(i), tabuleiro.getDX(), tabuleiro.getDY());
						break;
					case ObservedGame.GREEN:
						g2d.setColor(Tabuleiro.corGreen);
						g2d.fillOval(tabuleiro.getXPosJogador(i), tabuleiro.getYPosJogador(i), tabuleiro.getDX(), tabuleiro.getDY());
						break;
					case ObservedGame.MUSTARD:
						g2d.setColor(Tabuleiro.corMustard);
						g2d.fillOval(tabuleiro.getXPosJogador(i), tabuleiro.getYPosJogador(i), tabuleiro.getDX(), tabuleiro.getDY());
						break;
					case ObservedGame.PEACOCK:
						g2d.setColor(Tabuleiro.corPeacock);
						g2d.fillOval(tabuleiro.getXPosJogador(i), tabuleiro.getYPosJogador(i), tabuleiro.getDX(), tabuleiro.getDY());
						break;
					case ObservedGame.PLUM:
						g2d.setColor(Tabuleiro.corPlum);
						g2d.fillOval(tabuleiro.getXPosJogador(i), tabuleiro.getYPosJogador(i), tabuleiro.getDX(), tabuleiro.getDY());
						break;
					case ObservedGame.SCARLET:
						g2d.setColor(Tabuleiro.corScarlet);
						g2d.fillOval(tabuleiro.getXPosJogador(i), tabuleiro.getYPosJogador(i), tabuleiro.getDX(), tabuleiro.getDY());
						break;
					case ObservedGame.WHITE:
						g2d.setColor(Tabuleiro.corWhite);
						g2d.fillOval(tabuleiro.getXPosJogador(i), tabuleiro.getYPosJogador(i), tabuleiro.getDX(), tabuleiro.getDY());
						break;
				}
		}
				for(int k=0; k< ObservedGame.numRooms; k++)
				{
					for(int j=0; j<ObservedGame.numPlayers+ObservedGame.numWeapons; j++)
					{
						switch(tabuleiro.comodos[k][j])
						{
						case ObservedGame.GREEN:
							g2d.setColor(Tabuleiro.corGreen);
							g2d.fillOval(tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY());
							break;
						case ObservedGame.MUSTARD:
							g2d.setColor(Tabuleiro.corMustard);
							g2d.fillOval(tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY());
							break;
						case ObservedGame.PEACOCK:
							g2d.setColor(Tabuleiro.corPeacock);
							g2d.fillOval(tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY());
							break;
						case ObservedGame.PLUM:
							g2d.setColor(Tabuleiro.corPlum);
							g2d.fillOval(tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY());
							break;
						case ObservedGame.SCARLET:
							g2d.setColor(Tabuleiro.corScarlet);
							g2d.fillOval(tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY());
							break;
						case ObservedGame.WHITE:
							g2d.setColor(Tabuleiro.corWhite);
							g2d.fillOval(tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY());
							break;
						case ObservedGame.CORDA:
							g.drawImage(tabuleiro.imgCorda, tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY(), this);
							break;
						case ObservedGame.CANO:
							g.drawImage(tabuleiro.imgCano, tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY(), this);
							break;
						case ObservedGame.FACA:
							g.drawImage(tabuleiro.imgFaca, tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY(), this);
							break;
						case ObservedGame.CHAVE:
							g.drawImage(tabuleiro.imgChave, tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY(), this);
							break;
						case ObservedGame.CASTICAL:
							g.drawImage(tabuleiro.imgCastical, tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY(), this);
							break;
						case ObservedGame.REVOLVER:
							g.drawImage(tabuleiro.imgRevolver, tabuleiro.getXComodo(j, k), tabuleiro.getYComodo(j, k), tabuleiro.getDX(), tabuleiro.getDY(), this);
							break;		
						}
					}
				}
	}

	public void update(Observable o, Object obj) {
		if(gc.getDieWasRolled()){
			tabuleiro.AdicionaAlcance(gc.getDie(), gc.getTurn());
			repaint();
		}
		
	}
}
