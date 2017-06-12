package gameView;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NotesWindow extends JFrame 
{
	public final int WIDTH	= 640;
	public final int HEIGHT = 480;
	
	NotesControl notesController;
	
	public NotesWindow(String s)
	{
		super (s);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		
		notesController = new NotesControl();
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

class NotesControl extends JPanel{
	public NotesControl()
	{
		
		//Define layout do painel contendo os botões
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);	
		
		JCheckBox green		= new JCheckBox("Reverendo Green"); 	
		green.addItemListener(preGame_Controller.StartGameHandler.getInstance().getCheckboxListener());
		JCheckBox mustard	= new JCheckBox("Coronel Mustard"); 	
		mustard.addItemListener(preGame_Controller.StartGameHandler.getInstance().getCheckboxListener());
		JCheckBox peacock	= new JCheckBox("Senhora Peacock"); 	
		peacock.addItemListener(preGame_Controller.StartGameHandler.getInstance().getCheckboxListener());
		JCheckBox plum		= new JCheckBox("Professor Plum");  	
		plum.addItemListener(preGame_Controller.StartGameHandler.getInstance().getCheckboxListener());
		JCheckBox scarlet	= new JCheckBox("Senhorita Scarlet");	
		scarlet.addItemListener(preGame_Controller.StartGameHandler.getInstance().getCheckboxListener());
		JCheckBox white		= new JCheckBox("Senhora White"); 		
		white.addItemListener(preGame_Controller.StartGameHandler.getInstance().getCheckboxListener());
		
		
		c.weightx = 1;
		c.weighty = 1;
		
		c.gridy=0;
		add(green, c);
		c.gridy=1;
		add(mustard, c);
		c.gridy=2;
		add(peacock, c);
		c.gridy=3;
		add(plum, c);
		c.gridy=4;
		add(scarlet, c);
		c.gridy=5;
		add(white, c);
		c.gridy=6;
		
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(400, 300);
    }
}
