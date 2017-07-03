package gameView;

import java.awt.BorderLayout;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameController.GameRulesFactory;
import gameController.ObservedGame;

public class PainelAcoes extends JPanel implements Observer {

	private ObservedGame gc;
	private GameView gv;
	
	JButton passagemSecreta;
	JButton rollDie;
	JButton myCardsB;
	JButton accuseB;
	private DieDisplay die;
	
	public PainelAcoes(GameView gameview)
	{
		gv = gameview;
		gc = GameRulesFactory.getGameInstance();
		gc.addObserver(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
		
		die = new DieDisplay(gc);
		//botï¿½o usado para tentar rolar o dado
        rollDie = new JButton("Rolar Dado");
		rollDie.addActionListener(new RollDieHandler(gc));
		rollDie.setAlignmentX(CENTER_ALIGNMENT);
		
		passagemSecreta= new JButton("Usar passagem secreta");
		passagemSecreta.addActionListener(new passagemSecretaHandler(gc));
		//palpite
		//acusar
		//usar passagem secreta
		add(passagemSecreta);
		passagemSecreta.setVisible(false);
        add(Box.createVerticalGlue());
		add(rollDie);
		add(Box.createRigidArea(new Dimension(0,10)));
        add(die);

        
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension((int)die.getPreferredSize().getWidth(),(int)gv.getHeight() );
		
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		die.repaint();
	}
	
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(gc.getDieWasRolled() == true)
			rollDie.setVisible(false);
		else{
			rollDie.setVisible(true);
			rollDie.setAlignmentX(CENTER_ALIGNMENT);
		}
		if(gc.getTabuleiro().emComodo(gc.getTurn()) == ObservedGame.COZINHA || gc.getTabuleiro().emComodo(gc.getTurn()) == ObservedGame.ESCRITORIO
			|| gc.getTabuleiro().emComodo(gc.getTurn()) == ObservedGame.ESTAR || gc.getTabuleiro().emComodo(gc.getTurn()) == ObservedGame.INVERNO)
		{
			passagemSecreta.setVisible(true);
			passagemSecreta.setAlignmentX(CENTER_ALIGNMENT);
		}
		else
			passagemSecreta.setVisible(false);
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
        return new Dimension(d1.getWidth()*2, d1.getHeight()*2);
    }
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		switch(result)
		{
			case 1: 
				//imgPanel.setImage(d1);
				g.drawImage(d1, 0, 0, d1.getWidth()*2, d1.getHeight()*2, this);
				break;
			case 2:
				//imgPanel.setImage(d2);
				g.drawImage(d2, 0, 0, d2.getWidth()*2, d2.getHeight()*2, this);
				break;
			case 3: 
				//imgPanel.setImage(d3);
				g.drawImage(d3, 0, 0, d3.getWidth()*2, d3.getHeight()*2, this);
				break;
			case 4: 
				//imgPanel.setImage(d4);
				g.drawImage(d4, 0, 0, d4.getWidth()*2, d4.getHeight()*2, this);
				break;
			case 5: 
				//imgPanel.setImage(d5);
				g.drawImage(d5, 0, 0, d5.getWidth()*2, d5.getHeight()*2, this);
				break;
			case 6: 
				//imgPanel.setImage(d6);
				g.drawImage(d6, 0, 0, d6.getWidth()*2, d6.getHeight()*2, this);
				break;		
		}
		
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		result = gc.getDie();
		this.repaint();
		
	}
	
	
}

