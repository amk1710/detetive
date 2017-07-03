package gameView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameController.GameRulesFactory;
import gameController.ObservedGame;

public class PainelInformacoes extends JPanel{
	private GameView gv;
	private ObservedGame gc;
	private PainelNotas notesP;
	private JButton myCardsB;
	private JButton accuseB;
	private PainelJogadores playersP;
	
	public PainelInformacoes(GameView game)
	{
		gv = game;
		gc = GameRulesFactory.getGameInstance();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações"));
		
		//botï¿½o usado para abrir a janela das notas
        notesP = new PainelNotas(gc,gv);
		playersP = new PainelJogadores(gc);
		//botï¿½o usado para ver cartas do player
		myCardsB = new JButton("Ver Minhas Cartas");
        myCardsB.addActionListener(new MyCardsButtonHandler(gv));
        myCardsB.setAlignmentX(CENTER_ALIGNMENT);
        //botï¿½o usado para acusaï¿½ï¿½o
        accuseB =  new JButton("Acusar");
        accuseB.addActionListener(new AccuseButtonHandler(gv));
        
        
        add(playersP);
		Dimension minSize = new Dimension(0, 30);
		Dimension prefSize = new Dimension(0, Short.MAX_VALUE);
		Dimension maxSize = new Dimension(0, Short.MAX_VALUE);
		add(new Box.Filler(minSize, prefSize, maxSize));
		add(myCardsB);
		add(notesP);
		//jogadores ativos e nao ativos com x na frente
				
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension((int)(notesP.getPreferredSize().getWidth()),(int) gv.getHeight());
	}

}

class PainelNotas extends JPanel implements Observer
{
	ObservedGame gc;
	GameView gv;
	
	private JCheckBox[] playerCheckBoxes;
	private JCheckBox[] weaponCheckBoxes;
	private JCheckBox[] roomCheckBoxes;
	
	boolean[] playerMarked;
	boolean[] weaponsMarked;
	boolean[] roomsMarked;
	
	private ItemListener checkboxListener = null;
	
	
	public PainelNotas(ObservedGame game, GameView gameview)
	{
		
		//Define layout do painel contendo os botÃµes
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);	
		
		gc = game;
		gc.addObserver(this);
		gv = gameview;

		playerMarked = gc.getNotedPlayers();
		weaponsMarked = gc.getNotedWeapons();
		roomsMarked = gc.getNotedRooms();
		
		setBorder(new javax.swing.border.TitledBorder("Notas do jogador"));
		checkboxListener = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				System.out.println("itemStateChanged");
				JCheckBox source = (JCheckBox) e.getSource();
				
				for(int i = 0; i < playerCheckBoxes.length; i++)
				{
					if(source.equals(playerCheckBoxes[i]))
					{
						playerMarked[i] = source.isSelected();
						return;
					}
				}
				
				for(int i = 0; i < weaponCheckBoxes.length; i++)
				{
					if(source.equals(weaponCheckBoxes[i]))
					{
						weaponsMarked[i] = source.isSelected();
						return;
					}
				}
				
				for(int i = 0; i < roomCheckBoxes.length; i++)
				{
					if(source.equals(roomCheckBoxes[i]))
					{
						roomsMarked[i] = source.isSelected();
						return;
					}
				}
			}
		};
		
		playerCheckBoxes = new JCheckBox[6];
		
		for(int i = 0; i < gc.numPlayers; i++)
		{
			playerCheckBoxes[i]		= new JCheckBox(ObservedGame.NAMES[i + ObservedGame.SCARLET], false);
			playerCheckBoxes[i].addItemListener(checkboxListener);
		}
		
		weaponCheckBoxes = new JCheckBox[6];
		
		for(int i = 0; i < gc.numWeapons; i++)
		{
			weaponCheckBoxes[i]		= new JCheckBox(ObservedGame.NAMES[i + ObservedGame.CORDA], false);
			weaponCheckBoxes[i].addItemListener(checkboxListener);
		}
		
		
		roomCheckBoxes = new JCheckBox[9];
		
		for(int i = 0; i < gc.numRooms; i++)
		{
			roomCheckBoxes[i]		= new JCheckBox(ObservedGame.NAMES[i + ObservedGame.COZINHA], false);
			roomCheckBoxes[i].addItemListener(checkboxListener);
		}
				
		c.weightx = 1;
		c.weighty = 1;
		
		
		c.gridy=0;
		add(playerCheckBoxes[0], c);
		c.gridy=1;
		add(playerCheckBoxes[1], c);
		c.gridy=2;
		add(playerCheckBoxes[2], c);
		c.gridy=3;
		add(playerCheckBoxes[3], c);
		c.gridy=4;
		add(playerCheckBoxes[4], c);
		c.gridy=5;
		add(playerCheckBoxes[5], c);
		c.gridy=6;
		
		c.gridx = 1;
		c.gridy=0;
		add(weaponCheckBoxes[0], c);
		c.gridy=1;
		add(weaponCheckBoxes[1], c);
		c.gridy=2;
		add(weaponCheckBoxes[2], c);
		c.gridy=3;
		add(weaponCheckBoxes[3], c);
		c.gridy=4;
		add(weaponCheckBoxes[4], c);
		c.gridy=5;
		add(weaponCheckBoxes[5], c);
		c.gridy=6;
		
		c.gridx = 2;
		c.gridy=0;
		add(roomCheckBoxes[0], c);
		c.gridy=1;
		add(roomCheckBoxes[1], c);
		c.gridy=2;
		add(roomCheckBoxes[2], c);
		c.gridy=3;
		add(roomCheckBoxes[3], c);
		c.gridy=4;
		add(roomCheckBoxes[4], c);
		c.gridy=5;
		add(roomCheckBoxes[5], c);
		c.gridy=6;
		add(roomCheckBoxes[6], c);
		c.gridy=7;
		add(roomCheckBoxes[7], c);
		c.gridy=8;
		add(roomCheckBoxes[8], c);
		c.gridy=9;
		
		
		
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		playerMarked = gc.getNotedPlayers();
		weaponsMarked = gc.getNotedWeapons();
		roomsMarked = gc.getNotedRooms();
		
		for(int i = 0; i < gc.numPlayers; i++)
		{
			if(playerMarked[i] == true)
				playerCheckBoxes[i].setSelected(true);
			else
				playerCheckBoxes[i].setSelected(false);
		}
		for(int i = 0; i < gc.numWeapons; i++)
		{
			if(weaponsMarked[i] == true)
				weaponCheckBoxes[i].setSelected(true);
			else
				weaponCheckBoxes[i].setSelected(false);
		}
		for(int i = 0; i < gc.numRooms; i++)
		{
			if(roomsMarked[i] == true)
				roomCheckBoxes[i].setSelected(true);
			else
				roomCheckBoxes[i].setSelected(false);
		}
	}
	public Dimension getPreferredSize() {
        return new Dimension(300, 50);
    }

	@Override
	public void update(Observable arg0, Object arg1) {
			//set note players
		String action = (String) arg1;
		if(action == "endTurn")
		{
			gc.setNotes_Players(playerMarked);
			gc.setNotes_Weapons(weaponsMarked);
			gc.setNotes_Rooms(roomsMarked);
			//draw new notes
		}
		repaint();
	}
}

class PainelJogadores extends JPanel implements Observer
{
	private BufferedImage green;
	JLabel greenLabel; 
	private BufferedImage mustard;
	JLabel mustardLabel; 
	private BufferedImage peacock;
	JLabel peacockLabel; 
	private BufferedImage plum;
	JLabel plumLabel; 
	private BufferedImage scarlet;
	JLabel scarletLabel; 
	private BufferedImage white;
	JLabel whiteLabel; 
	private ObservedGame gc;
	
	public PainelJogadores(ObservedGame game)
	{
		gc = game;
		gc.addObserver(this);
		try
		{
			green = ImageIO.read(new File("assets/Suspeitos/greenIcon.jpg"));
			mustard = ImageIO.read(new File("assets/Suspeitos/mustardIcon.jpg"));
			peacock = ImageIO.read(new File("assets/Suspeitos/peacockIcon.jpg"));
			plum = ImageIO.read(new File("assets/Suspeitos/plumIcon.jpg"));
			scarlet = ImageIO.read(new File("assets/Suspeitos/scarletIcon.jpg"));
			white = ImageIO.read(new File("assets/Suspeitos/whiteIcon.jpg"));
			
		}catch(IOException e)
		{
			System.out.println("Erro ao tentar abrir imagem.");
			System.exit(1);
		}
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
				
		c.weightx = 0;
		c.weighty = 1;
		
		greenLabel = new JLabel(new ImageIcon(green));
		mustardLabel = new JLabel(new ImageIcon(mustard));
		peacockLabel = new JLabel(new ImageIcon(peacock));
		plumLabel = new JLabel(new ImageIcon(plum));
		scarletLabel = new JLabel(new ImageIcon(scarlet));
		whiteLabel = new JLabel(new ImageIcon(white));
		
		c.gridx=0;
		c.gridy=0;
		add(scarletLabel,c);
		c.gridx=0;
		c.gridy=1;
		add(mustardLabel,c);
		c.gridx=0;
		c.gridy=2;
		add(whiteLabel,c);
		c.gridx=1;
		c.gridy=0;
		add(greenLabel,c);
		c.gridx=1;
		c.gridy=1;
		add(peacockLabel,c);
		c.gridx=1;
		c.gridy=2;
		add(plumLabel,c);
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		scarletLabel.setBorder(null);
		mustardLabel.setBorder(null);
		whiteLabel.setBorder(null);
		greenLabel.setBorder(null);
		peacockLabel.setBorder(null);
		plumLabel.setBorder(null);
		
		boolean []activePlayers = gc.getActivePlayers();
		for(int i=0; i<ObservedGame.numPlayers; i++)
		{
			if(activePlayers[i] == false)
			{
				switch(i)
				{
				case ObservedGame.SCARLET: scarletLabel.setBorder(new javax.swing.border.LineBorder(Color.RED, 10)); break;
				case ObservedGame.MUSTARD: mustardLabel.setBorder(new javax.swing.border.LineBorder(Color.RED, 10)); break;
				case ObservedGame.WHITE:   whiteLabel.setBorder(new javax.swing.border.LineBorder(Color.RED, 10));   break;
				case ObservedGame.GREEN:   greenLabel.setBorder(new javax.swing.border.LineBorder(Color.RED, 10));   break;
				case ObservedGame.PEACOCK: peacockLabel.setBorder(new javax.swing.border.LineBorder(Color.RED, 10)); break;
				case ObservedGame.PLUM:    plumLabel.setBorder(new javax.swing.border.LineBorder(Color.RED, 10));    break;
				
				}
			}
				
		}
		
		switch(gc.getTurn())
		{
		case ObservedGame.SCARLET: scarletLabel.setBorder(new javax.swing.border.LineBorder(Color.GREEN, 10)); break;
		case ObservedGame.MUSTARD: mustardLabel.setBorder(new javax.swing.border.LineBorder(Color.GREEN, 10)); break;
		case ObservedGame.WHITE:   whiteLabel.setBorder(new javax.swing.border.LineBorder(Color.GREEN, 10));   break;
		case ObservedGame.GREEN:   greenLabel.setBorder(new javax.swing.border.LineBorder(Color.GREEN, 10));   break;
		case ObservedGame.PEACOCK: peacockLabel.setBorder(new javax.swing.border.LineBorder(Color.GREEN, 10)); break;
		case ObservedGame.PLUM:    plumLabel.setBorder(new javax.swing.border.LineBorder(Color.GREEN, 10));    break;
		}
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(300, 50);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
		
	}
}
