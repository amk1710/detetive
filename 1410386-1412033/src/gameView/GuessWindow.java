package gameView;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gameController.ObservedGame;

public class GuessWindow extends JFrame 
{
	public final int WIDTH	= 640;
	public final int HEIGHT = 480;
	
	GameView gv;
	GuessControl guess;
	
	public GuessWindow(String s, GameView gameview)
	{
		super (s);
		gv = gameview;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
			
		guess = new GuessControl(gv);
		setContentPane(guess);
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


class GuessControl extends JPanel{
	
	GameView gv;
	/*
	private JCheckBox[] playerCheckBoxes;
	private JCheckBox[] weaponCheckBoxes;
	private JCheckBox[] roomCheckBoxes;
	
	
	private ItemListener checkboxListener = null;
	*/
	private JRadioButton   roomButton;
	private JRadioButton[] weaponButtons;
	private JRadioButton[] suspectButtons;
	
	private ButtonGroup suspectGroup;
	private ButtonGroup weaponGroup;
	
	private ActionListener Plistener;
	private ActionListener Wlistener;
	
	int suspectID;
	int weaponID;
	//roomId � fixo no quarto
	final int roomID;
	
	
	
	GuessControl(GameView gameview)
	{
		
		//Define layout do painel contendo os botÃµes
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		
		//TO-DO pegar room certa
		roomID = 0;
		
	
		
		gv = gameview;
		
		Plistener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int i;
				for(i = 0; i < suspectButtons.length; i++)
				{
					if(suspectButtons[i].equals(e.getSource())) break;
				}
				suspectID = i;
			}
		};
		
		Wlistener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int i;
				for(i = 0; i < weaponButtons.length; i++)
				{
					if(weaponButtons[i].equals(e.getSource())) break;
				}
				weaponID = i;
			}
		};
		
		
		
		
		
		suspectGroup = new ButtonGroup();
		suspectButtons = new JRadioButton[6];
		for(int i = 0; i < suspectButtons.length; i++)
		{
			suspectButtons[i] = new JRadioButton(gv.playerNames[i]);
			suspectButtons[i].addActionListener(Plistener);
			suspectGroup.add(suspectButtons[i]);
			
		}
		
		weaponGroup = new ButtonGroup();
		weaponButtons = new JRadioButton[6];
		for(int i = 0; i < weaponButtons.length; i++)
		{
			weaponButtons[i] = new JRadioButton(gv.weaponNames[i]);
			weaponButtons[i].addActionListener(Wlistener);
			weaponGroup.add(weaponButtons[i]);
			
		}
		
		//TO-DO pegar quarto correto
		roomButton = new JRadioButton(gv.roomNames[0]);
		
		
			
				
		c.weightx = 1;
		c.weighty = 1;
		
		
		c.gridy=0;
		add(suspectButtons[0], c);
		c.gridy=1;
		add(suspectButtons[1], c);
		c.gridy=2;
		add(suspectButtons[2], c);
		c.gridy=3;
		add(suspectButtons[3], c);
		c.gridy=4;
		add(suspectButtons[4], c);
		c.gridy=5;
		add(suspectButtons[5], c);
		c.gridy=6;
		
		c.gridx = 1;
		c.gridy=0;
		add(weaponButtons[0], c);
		c.gridy=1;
		add(weaponButtons[1], c);
		c.gridy=2;
		add(weaponButtons[2], c);
		c.gridy=3;
		add(weaponButtons[3], c);
		c.gridy=4;
		add(weaponButtons[4], c);
		c.gridy=5;
		add(weaponButtons[5], c);
		c.gridy=6;
		
		c.gridx = 2;
		c.gridy=0;
		add(roomButton, c);
		
		
		
		
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }
}
