package gameView;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import gameController.GameRulesFactory;
import gameController.ObservedGame;

public class PainelInformacoes extends JPanel implements Observer {
	private GameView gv;
	private ObservedGame gc;
	private PainelNotas notesP;
	private JButton myCardsB;
	private JButton accuseB;
	
	public PainelInformacoes(GameView game)
	{
		gv = game;
		gc = GameRulesFactory.getGameInstance();
		gc.addObserver(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações"));
		
		//botï¿½o usado para abrir a janela das notas
        notesP = new PainelNotas(gc,gv);
		
		//botï¿½o usado para ver cartas do player
		myCardsB = new JButton("Ver Minhas Cartas");
        myCardsB.addActionListener(new MyCardsButtonHandler(gv));
        myCardsB.setAlignmentX(CENTER_ALIGNMENT);
        //botï¿½o usado para acusaï¿½ï¿½o
        accuseB =  new JButton("Acusar");
        accuseB.addActionListener(new AccuseButtonHandler(gv));
        
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
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

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
