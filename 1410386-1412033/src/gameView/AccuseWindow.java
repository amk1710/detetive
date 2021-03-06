package gameView;

import gameController.Card;
import gameController.ObservedGame;

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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AccuseWindow extends JFrame 
{
	public final int WIDTH	= 640;
	public final int HEIGHT = 480;
	
	AccuseControl accuse;
	
	ImageIcon image;
	JLabel label;
	
	ObservedGame gc;
	GameView gv;
	JButton ab;
	
	public AccuseWindow(String s, GameView gameview)
	{
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		
		gv = gameview;
			
		accuse = new AccuseControl(gv);
		ab = new JButton("Acusar");
		try 
		{
			image = new ImageIcon(ImageIO.read(new File("assets/blank.jpg")));
			label = new JLabel(image);
		}  catch (IOException e)
		{
			System.out.println("Incapaz de abrir imagem. Erro:" + e.getMessage());
			System.exit(1);
		}
		
		
		ab.addActionListener(new AccuseHandler(this));
		c.gridx = 0;
		c.gridx= 0;
		getContentPane().add(accuse, c);
		c.gridx = 1;
		getContentPane().add(ab, c);
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
	
	
	
	
}


class AccuseControl extends JPanel{
	
	GameView gv;
	
	private JRadioButton[] roomButtons;
	private JRadioButton[] weaponButtons;
	private JRadioButton[] suspectButtons;
	
	private ButtonGroup suspectGroup;
	private ButtonGroup weaponGroup;
	private ButtonGroup roomGroup;
	
	private ActionListener Plistener;
	private ActionListener Wlistener;
	private ActionListener Glistener;
	
	int suspectID = -1;
	int weaponID = -1;
	int roomID = -1;
	
	
	AccuseControl(GameView gameview)
	{
		
		
		
		//Define layout do painel contendo os botÃµes
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);	
		
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
		
		Glistener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int i;
				for(i = 0; i < roomButtons.length; i++)
				{
					if(roomButtons[i].equals(e.getSource())) break;
				}
				roomID = i;
			}
		};		
		
		
		
		suspectGroup = new ButtonGroup();
		suspectButtons = new JRadioButton[6];
		for(int i = 0; i < suspectButtons.length; i++)
		{
			suspectButtons[i] = new JRadioButton(ObservedGame.NAMES[i]);
			suspectButtons[i].addActionListener(Plistener);
			suspectGroup.add(suspectButtons[i]);
			
		}
		
		weaponGroup = new ButtonGroup();
		weaponButtons = new JRadioButton[6];
		for(int i = 0; i < weaponButtons.length; i++)
		{
			weaponButtons[i] = new JRadioButton(ObservedGame.NAMES[i+ ObservedGame.CORDA]);
			weaponButtons[i].addActionListener(Wlistener);
			weaponGroup.add(weaponButtons[i]);
			
		}
		
		roomGroup = new ButtonGroup();
		roomButtons = new JRadioButton[9];
		for(int i = 0; i < roomButtons.length; i++)
		{
			roomButtons[i] = new JRadioButton(ObservedGame.NAMES[i + ObservedGame.COZINHA]);
			roomButtons[i].addActionListener(Glistener);
			roomGroup.add(roomButtons[i]);
			
		}			
		
		
				
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
		add(roomButtons[0], c);
		c.gridy=1;
		add(roomButtons[1], c);
		c.gridy=2;
		add(roomButtons[2], c);
		c.gridy=3;
		add(roomButtons[3], c);
		c.gridy=4;
		add(roomButtons[4], c);
		c.gridy=5;
		add(roomButtons[5], c);
		c.gridy=6;
		add(roomButtons[6], c);
		c.gridy=7;
		add(roomButtons[7], c);
		c.gridy=8;
		add(roomButtons[8], c);

	}
	
	public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }
}

class AccuseHandler implements ActionListener
{
	
	AccuseWindow ac;
	
	public AccuseHandler(AccuseWindow accusewindow)
	{
		ac = accusewindow;
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(ac.accuse.roomID != -1 && ac.accuse.suspectID != -1 && ac.accuse.weaponID != -1)
		{
			boolean won = ac.gv.gc.accuse(ac.accuse.suspectID, ac.accuse.weaponID, ac.accuse.roomID);
			try
			{
				if(won)
				{
					ac.label.setIcon(new ImageIcon(ImageIO.read(new File("assets/won.jpg"))));
					ac.gv.endGame();
				}
				else if(ac.gv.gc.allLost())
				{
					ac.label.setIcon(new ImageIcon(ImageIO.read(new File("assets/lost2.jpg"))));
					ac.gv.endGame();
				}
				else
				{
					ac.label.setIcon(new ImageIcon(ImageIO.read(new File("assets/lost.jpg"))));
				}
			}catch (IOException e)
			{
				System.out.println("Incapaz de abrir imagem. Erro:" + e.getMessage());
				System.exit(1);
			}
			
		}
		
		
	}

}

