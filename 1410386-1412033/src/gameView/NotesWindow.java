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
	
	public NotesWindow(String s, ObservedGame game)
	{
		super (s);
		gc = game;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		//adiciona windows listener para rodar função quando fechar a janela
		this.addWindowListener(new notesWindowListener(this));
		
		notesController = new NotesControl(gc);
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
	}
}

class NotesControl extends JPanel{
	
	ObservedGame gc;
	
	private JCheckBox[] playerCheckBoxes;
	private JCheckBox[] weaponCheckBoxes;
	
	boolean[] playerMarked;
	boolean[] weaponsMarked;
	
	private ItemListener checkboxListener = null;
	
	
	NotesControl(ObservedGame game)
	{
		
		//Define layout do painel contendo os botÃµes
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);	
		
		gc = game;
		
		playerMarked = gc.getNotedPlayers();
		weaponsMarked = gc.getNotedWeapons();
		
		checkboxListener = new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) 
			{
				System.out.println("itemStateChanged");
				JCheckBox source = (JCheckBox) e.getSource();
				
				for(int i = 0; i < 6; i++)
				{
					if(source.equals(playerCheckBoxes[i]))
					{
						playerMarked[i] = source.isSelected();
						return;
					}
				}
				
				for(int i = 0; i < 6; i++)
				{
					if(source.equals(weaponCheckBoxes[i]))
					{
						weaponsMarked[i] = source.isSelected();
						return;
					}
				}
			}
		};
		
		playerCheckBoxes = new JCheckBox[6];
		
		playerCheckBoxes[0]		= new JCheckBox("Reverendo Green", playerMarked[0]); 	
		playerCheckBoxes[0].addItemListener(checkboxListener);
		playerCheckBoxes[1]	= new JCheckBox("Coronel Mustard", playerMarked[1]); 	
		playerCheckBoxes[1].addItemListener(checkboxListener);
		playerCheckBoxes[2]	= new JCheckBox("Senhora Peacock", playerMarked[2]); 	
		playerCheckBoxes[2].addItemListener(checkboxListener);
		playerCheckBoxes[3]		= new JCheckBox("Professor Plum", playerMarked[3]);  	
		playerCheckBoxes[3].addItemListener(checkboxListener);
		playerCheckBoxes[4]	= new JCheckBox("Senhorita Scarlet", playerMarked[4]);	
		playerCheckBoxes[4].addItemListener(checkboxListener);
		playerCheckBoxes[5]		= new JCheckBox("Senhora White", playerMarked[5]); 		
		playerCheckBoxes[5].addItemListener(checkboxListener);
		
		weaponCheckBoxes = new JCheckBox[6];
		
		weaponCheckBoxes[0]		= new JCheckBox("Corda", weaponsMarked[0]); 	
		weaponCheckBoxes[0].addItemListener(checkboxListener);
		weaponCheckBoxes[1]	= new JCheckBox("Cano de Chumbo", weaponsMarked[1]); 	
		weaponCheckBoxes[1].addItemListener(checkboxListener);
		weaponCheckBoxes[2]	= new JCheckBox("Faca", weaponsMarked[2]); 	
		weaponCheckBoxes[2].addItemListener(checkboxListener);
		weaponCheckBoxes[3]		= new JCheckBox("Chave Inglesa", weaponsMarked[3]);  	
		weaponCheckBoxes[3].addItemListener(checkboxListener);
		weaponCheckBoxes[4]	= new JCheckBox("Castiçal", weaponsMarked[4]);	
		weaponCheckBoxes[4].addItemListener(checkboxListener);
		weaponCheckBoxes[5]		= new JCheckBox("Revólver", weaponsMarked[5]); 		
		weaponCheckBoxes[5].addItemListener(checkboxListener);
		
		
		
		
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
		
		
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }
}
