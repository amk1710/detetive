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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gameController.Card;
import gameController.CardType;
import gameController.ObservedGame;

public class GuessWindow extends JFrame 
{
	public final int WIDTH	= 640;
	public final int HEIGHT = 480;
	
	GameView gv;
	GuessControl guess;
	JButton gb;
	ImageIcon cardImage;
	JLabel label;
	
	GuessWindow(String s, GameView gameview)
	{
		super (s);
		gv = gameview;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(true);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
			
		guess = new GuessControl(gv);
		gb = new JButton("Palpitar");
		try 
		{
			cardImage = new ImageIcon(ImageIO.read(new File("assets/blank.jpg")));
			label = new JLabel(cardImage);
		}  catch (IOException e)
		{
			System.out.println("Incapaz de abrir imagem. Erro:" + e.getMessage());
			System.exit(1);
		}
		
		
		gb.addActionListener(new GuessHandler(this));
		c.gridx = 0;
		c.gridx= 0;
		getContentPane().add(guess, c);
		c.gridx = 1;
		getContentPane().add(gb, c);
		c.gridx = 2;
		getContentPane().add(label, c);
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
	
	void ShowCard(Card c)
	{
		label.setIcon(new ImageIcon(CardImages.getImage(c)));
	}
	
	
}


class GuessControl extends JPanel{
	
	GameView gv;

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
		
		gv = gameview;
		suspectID = -1;
		weaponID = -1;
				
		roomID = gv.gc.getTabuleiro().emComodo(gv.gc.getTurn()) - ObservedGame.COZINHA;		
		
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
			suspectButtons[i] = new JRadioButton(ObservedGame.NAMES[i + ObservedGame.SCARLET]);
			suspectButtons[i].addActionListener(Plistener);
			suspectGroup.add(suspectButtons[i]);
			
		}
		
		weaponGroup = new ButtonGroup();
		weaponButtons = new JRadioButton[6];
		for(int i = 0; i < weaponButtons.length; i++)
		{
			weaponButtons[i] = new JRadioButton(ObservedGame.NAMES[i + ObservedGame.CORDA]);
			weaponButtons[i].addActionListener(Wlistener);
			weaponGroup.add(weaponButtons[i]);
			
		}
		//TO-DO pegar quarto correto
		roomButton = new JRadioButton(ObservedGame.NAMES[gv.gc.getTabuleiro().emComodo(gv.gc.getTurn())], true);
		
				
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


class GuessHandler implements ActionListener
{
	
	GuessWindow gw;
	
	public GuessHandler(GuessWindow guesswindow)
	{
		gw = guesswindow;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		
		if(!gw.gv.gc.getHasGuessed() && gw.guess.suspectID != -1 && gw.guess.weaponID != -1)
		{
			Card c = gw.gv.gc.guess(gw.guess.suspectID, gw.guess.weaponID, gw.guess.roomID);
			gw.ShowCard(c);
		}
		
		
	}

}
