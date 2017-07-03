package gameView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import gameController.GameRulesFactory;
import gameController.ObservedGame;

public class PainelAcoes extends JPanel implements Observer {

	private ObservedGame gc;
	private GameView gv;
	
	JButton notesB;
	JButton rollDie;
	JButton myCardsB;
	JButton accuseB;
	JButton endB;
	JButton saveB;
	private DieDisplay die;
	
	public PainelAcoes(GameView gameview)
	{
		gv = gameview;
		gc = GameRulesFactory.getGameInstance();
		gc.addObserver(this);
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 0, 0, 0);	
		
		c.weightx = 1;
		c.weighty = 1;
		
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
		
		//botï¿½o usado para abrir a janela das notas
        notesB = new JButton("Ver Notas");
		notesB.addActionListener(new NotesButtonHandler(gc, gv));
		
		die = new DieDisplay(GameRulesFactory.getGameInstance());
		
		//botï¿½o usado para tentar rolar o dado
        rollDie = new JButton("Rolar Dado");
		rollDie.addActionListener(new RollDieHandler(gc));
		
		//botï¿½o usado para ver cartas do player
		myCardsB = new JButton("Ver Minhas Cartas");
        myCardsB.addActionListener(new MyCardsButtonHandler(gv));
        
        //botï¿½o usado para acusaï¿½ï¿½o
        accuseB =  new JButton("Acusar");
        accuseB.addActionListener(new AccuseButtonHandler(gv));
        
      //bot�o usado para passar de turno
        endB =  new JButton("Passa turno");
        endB.addActionListener(new PassButtonHandler(gv));
        
      //bot�o usado para salvar o jogo
        saveB =  new JButton("Salva jogo");
        saveB.addActionListener(new SaveHandler(gv));
		
        c.gridy=0;
        add(die, c);
        c.gridy=1;
		add(rollDie, c);
		c.gridy=2;
		add(myCardsB, c);
		c.gridy=3;
		add(notesB, c);
		c.gridy = 4;
		add(accuseB, c);		
		c.gridy = 5;
		add(endB, c);
		c.gridy = 6;
		add(saveB, c);
		
				
	}
	public Dimension getPreferredSize() 
	{
		int width = (int)die.getPreferredSize().getWidth();
        return new Dimension(300,width);
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(gc.getDieWasRolled() == true)
			rollDie.setVisible(false);
		else
			rollDie.setVisible(true);
	}

}

class DieDisplay extends JPanel implements Observer
{
	// usado para manipular o jogo
	private ObservedGame gc;
	private int result;
	
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
		
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		result = gc.getDie();
		this.repaint();
		
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


