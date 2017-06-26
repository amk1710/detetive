package gameView;

import gameController.ObservedGame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NotesWindow extends JFrame 
{
	public final int WIDTH	= 640;
	public final int HEIGHT = 480;
	
	NotesControl notesController;
	
	ObservedGame gc;
	GameView gv;
	
	public NotesWindow(String s, ObservedGame game, GameView gameview)
	{
		super (s);
		gc = game;
		gv = gameview;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		//adiciona windows listener para rodar função quando fechar a janela
		this.addWindowListener(new notesWindowListener(this));
		
		notesController = new NotesControl(gc, gv);
		setContentPane(notesController);
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

class notesWindowListener extends WindowAdapter
{
	private NotesWindow window;
	notesWindowListener(NotesWindow w)
	{
		window = w;
	}
	
	@Override
	public void windowClosed(WindowEvent e)
	{
		
		window.gc.setNotes_Players(window.notesController.playerMarked);
		window.gc.setNotes_Weapons(window.notesController.weaponsMarked);
		window.gc.setNotes_Rooms(window.notesController.roomsMarked);
	}
}

class NotesControl extends JPanel{
	
	ObservedGame gc;
	GameView gv;
	
	private JCheckBox[] playerCheckBoxes;
	private JCheckBox[] weaponCheckBoxes;
	private JCheckBox[] roomCheckBoxes;
	
	boolean[] playerMarked;
	boolean[] weaponsMarked;
	boolean[] roomsMarked;
	
	private ItemListener checkboxListener = null;
	
	
	NotesControl(ObservedGame game, GameView gameview)
	{
		
		//Define layout do painel contendo os botÃµes
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);	
		
		gc = game;
		gv = gameview;
		
		playerMarked = gc.getNotedPlayers();
		weaponsMarked = gc.getNotedWeapons();
		roomsMarked = gc.getNotedRooms();
		
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
			playerCheckBoxes[i]		= new JCheckBox(gv.playerNames[i], playerMarked[i]);
			playerCheckBoxes[i].addItemListener(checkboxListener);
		}
		
		weaponCheckBoxes = new JCheckBox[6];
		
		for(int i = 0; i < gc.numWeapons; i++)
		{
			weaponCheckBoxes[i]		= new JCheckBox(gv.weaponNames[i], weaponsMarked[i]);
			weaponCheckBoxes[i].addItemListener(checkboxListener);
		}
		
		
		roomCheckBoxes = new JCheckBox[9];
		
		for(int i = 0; i < gc.numRooms; i++)
		{
			roomCheckBoxes[i]		= new JCheckBox(gv.roomNames[i], roomsMarked[i]);
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
	
	public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }
}