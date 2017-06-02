package preGame_View;

import javax.imageio.ImageIO;
import javax.swing.*;

import preGame_Controller.NewGameHandler;
import preGame_Controller.SavedGameHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class startScreen extends JFrame {
	private startScreen(String s)
	{
		super (s);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);	
		
		setContentPane(new backgroundPanel());
		pack();
		
		// Inicializa janela no tamanho default no centro da tela.
				Toolkit tk=Toolkit.getDefaultToolkit();
				Dimension screenSize=tk.getScreenSize();
				int	sl=screenSize.width;
				int	sa=screenSize.height	;
				
				int	x=sl/2- ((int)getContentPane().getPreferredSize().getWidth())/2;
				int	y=sa/2- ((int)getContentPane().getPreferredSize().getHeight())/2;
				
				setBounds(x,y,(int)getContentPane().getPreferredSize().getWidth(),(int)getContentPane().getPreferredSize().getHeight());
	}
	
	public static void main(String[] args) {
		startScreen s = new startScreen("Detetive");
		s.setVisible(true);

	}
	

}

class backgroundPanel extends JPanel{
	private BufferedImage image;
	private final int scaleFactor = 2;
	
	public backgroundPanel()
	{
		try{
			image = ImageIO.read(new File("assets/detetive.jpg"));
		}catch(IOException e){
			System.out.println("Incapaz de abrir imagem. Erro:" + e.getMessage());
			System.exit(1);
		}
		
		//Define layout do painel contendo os botões
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 8, 0);	
        c.anchor = GridBagConstraints.PAGE_END;
        
		JButton newGame   = new JButton("Novo jogo"),
				savedGame = new JButton("Carregar jogo salvo");
		newGame.addActionListener(new NewGameHandler(this));
		savedGame.addActionListener(new SavedGameHandler(this));
				
		        
        c.gridy=0;
        c.weighty = 1.0;
		add(newGame, c);
		
		c.gridy=1;
        c.weighty = 0.0;
		add(savedGame, c);
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(scaleFactor*image.getWidth(), scaleFactor*image.getHeight());
    }
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.drawImage(image, 0, 0, scaleFactor*image.getWidth(), scaleFactor*image.getHeight(), this);
		
	}
}
