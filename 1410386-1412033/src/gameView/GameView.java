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
	private PainelTabuleiro grid;
	private PainelAcoes actions;
	private PainelInformacoes infos;
	
	//construtor para novo jogo
	public GameView(String s, boolean[] activePlayer)
	{
		super (s);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		gc = GameRulesFactory.getGameInstance();
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.LEADING, 15, 0));
		
		grid = new PainelTabuleiro(this);
		actions = new  PainelAcoes(this);
		infos   = new  PainelInformacoes(this);
		
		getContentPane().add(grid);
		getContentPane().add(actions);
		getContentPane().add(infos);
		
		setBounds(0,0, (int)this.getPreferredSize().getWidth()+80, (int)this.getPreferredSize().getHeight());
		
				
	}
	
	public int getHeight()
	{
		return (int)grid.getPreferredSize().getHeight();
	}
	public Dimension getPreferredSize()
	{
		return new Dimension((int) (grid.getPreferredSize().getWidth() + actions.getPreferredSize().getWidth() + infos.getPreferredSize().getWidth()), (int) (grid.getPreferredSize().getHeight()));
				
	}
}


class PainelTabuleiro extends JPanel implements Observer
{
	private ObservedGame gc = null;
	private GameView gv;
	private Tabuleiro tabuleiro = null;
	
	public PainelTabuleiro( GameView gameview)
	{
		this.gc = GameRulesFactory.getGameInstance();
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

	public Dimension getPreferredSize(){
		return new Dimension((int) (tabuleiro.imgTabuleiro.getWidth()), (int) (tabuleiro.imgTabuleiro.getHeight()+50));
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(tabuleiro.imgTabuleiro, 0, 0, (int) (tabuleiro.imgTabuleiro.getWidth()), (int) (tabuleiro.imgTabuleiro.getHeight()), this);
		Graphics2D g2d  = (Graphics2D) g;
		
		
		for(int i=0; i<tabuleiro.getTamanhoTabuleiro(); i++){
			if(tabuleiro.matriz[i][1] == Tabuleiro.ALCANCE)
			{
				g2d.setColor(Tabuleiro.corAlcance);
				g2d.fillRect(tabuleiro.getXPosJogador(i), tabuleiro.getYPosJogador(i), tabuleiro.getDX(), tabuleiro.getDY());
			}
			switch(tabuleiro.matriz[i][0]){
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
		}
		repaint();
	}	
}
