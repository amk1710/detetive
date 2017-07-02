package gameController;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Tabuleiro {
	public  BufferedImage imgTabuleiro;
	public  BufferedImage imgCorda;
	public  BufferedImage imgCano;
	public  BufferedImage imgFaca;
	public  BufferedImage imgChave;
	public  BufferedImage imgCastical;
	public  BufferedImage imgRevolver;
	private double img_escalaHorizontal 	= 1.0;
	private double img_escalaVertical 		= 1.0;

	
	public  int[][]  matriz;
	private int  matriz_numColunas;
	private int  matriz_numLinhas;
	private int  matriz_deslocamentoColunas;
	private int  matriz_deslocamentoLinhas;
	private int  matriz_larguraColuna;
	private int  matriz_alturaLinha;

	//Container dos itens que estão dentro de um comodo - tam = maxJogadores + maxArmas
	public int[][] comodos = new int[ObservedGame.numRooms][ObservedGame.numPlayers + ObservedGame.numWeapons];

	public static final Color corGreen 		= new Color(36,83,41, 255);
	public static final Color corMustard 	= new Color(204,176,32, 255);
	public static final Color corPeacock 	= new Color(45,62,118, 255);
	public static final Color corPlum 		= new Color(101,68,85, 255);
	public static final Color corScarlet 	= new Color(203,82,74, 255);
	public static final Color corWhite 		= new Color(183,186,193, 255);
	public static final Color corAlcance	= new Color(153, 255, 153, 128);

	/*	Estados da matriz
	 * 	Valores associados a cada posi��o da matriz.
	 */
	public static final int VAZIO		= -1;
	public static final int ALCANCE		= -2;
	public static final int INVALIDO	= -3;


	
	
	public Tabuleiro(){
		Scanner infomatriz=null;
		try{
			imgTabuleiro = ImageIO.read(new File("assets/Tabuleiro-Dobrado.JPG"));
			infomatriz = new Scanner(new FileReader("assets/Tabuleiro-Dobrado"));
			
			imgCorda = ImageIO.read(new File("assets/Armas/Corda.jpg"));
			imgCano = ImageIO.read(new File("assets/Armas/Cano.jpg"));
			imgFaca = ImageIO.read(new File("assets/Armas/Faca.jpg"));
			imgChave = ImageIO.read(new File("assets/Armas/ChaveInglesa.jpg"));
			imgCastical = ImageIO.read(new File("assets/Armas/Castical.jpg"));
			imgRevolver = ImageIO.read(new File("assets/Armas/Revolver.jpg"));
			
		} catch (IOException e){
			System.out.println("Incapaz de abrir arquivo. Erro:" + e.getMessage());
			System.exit(1);
		}		


		matriz_alturaLinha				= infomatriz.nextInt();
		matriz_larguraColuna			= infomatriz.nextInt();
		matriz_deslocamentoLinhas		= infomatriz.nextInt();
		matriz_deslocamentoColunas		= infomatriz.nextInt();
		matriz_numLinhas  = imgTabuleiro.getHeight()/matriz_alturaLinha;
		matriz_numColunas = imgTabuleiro.getWidth()/matriz_larguraColuna;		
		matriz = new int[matriz_numLinhas*matriz_numColunas][2];
		
		for(int j=0; j<ObservedGame.numRooms; j++)
			for(int i=0; i<(ObservedGame.numPlayers + ObservedGame.numWeapons); i++)
					comodos[j][i] = -1;
		
		for(int i=0; i<getTamanhoTabuleiro(); i++){
			if(infomatriz.hasNext())
			{
				matriz[i][0] = infomatriz.nextInt();
				matriz[i][1] = Tabuleiro.VAZIO;
			}else{
				System.out.println("Ärquivo da matriz menor que o esperado"); System.exit(1);
			}
		}
		infomatriz.close();
	}
	
	

	public int getXJogador(int x){
		return (int) img_escalaHorizontal*(matriz_larguraColuna*x + matriz_deslocamentoColunas);
	}
 	public int getXPosJogador(int pos){
 		System.out.println("pegando x jogador");
		return (int) img_escalaHorizontal*(matriz_larguraColuna*(pos%matriz_numColunas) + matriz_deslocamentoColunas);
	}
 	public int getYJogador(int y){
		return (int) img_escalaVertical*(matriz_alturaLinha*y + matriz_deslocamentoLinhas);
	}
	public int getYPosJogador(int pos){ 
		return (int)  img_escalaVertical*(matriz_alturaLinha*(pos/matriz_numColunas) + matriz_deslocamentoLinhas);
	}

	public int getXComodo(int posComodo, int indexComodo)
	{
		System.out.println("pegando x de uma arma");
		indexComodo += ObservedGame.COZINHA;
		switch(indexComodo)
		{
		case(ObservedGame.COZINHA):	
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(1+(posComodo%4)) + matriz_deslocamentoColunas);
		case(ObservedGame.JANTAR):
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(1+(posComodo%4)) + matriz_deslocamentoColunas);
		case(ObservedGame.ESTAR):		
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(1+(posComodo%4)) + matriz_deslocamentoColunas);
		case(ObservedGame.MUSICA):		
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(10+(posComodo%4)) + matriz_deslocamentoColunas);
		case(ObservedGame.ENTRADA):	
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(10+(posComodo%4)) + matriz_deslocamentoColunas);
		case(ObservedGame.INVERNO):
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(19+(posComodo%4)) + matriz_deslocamentoColunas);
		case(ObservedGame.JOGOS):
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(19+(posComodo%4)) + matriz_deslocamentoColunas);
		case(ObservedGame.BIBLIOTECA):
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(19+(posComodo%4)) + matriz_deslocamentoColunas);
		case(ObservedGame.ESCRITORIO):
			return (int) img_escalaHorizontal*(matriz_larguraColuna*(19+(posComodo%4)) + matriz_deslocamentoColunas);
				
		}
		return 0;
	}
	public int getYComodo(int posComodo, int indexComodo)
	{
		indexComodo += ObservedGame.COZINHA;
		switch(indexComodo)
		{
		case(ObservedGame.COZINHA):	
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(2) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(3) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(4) + matriz_deslocamentoLinhas);
		case(ObservedGame.JANTAR):
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(11) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(12) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(13) + matriz_deslocamentoLinhas);
		case(ObservedGame.ESTAR):		
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(21) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(22) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(23) + matriz_deslocamentoLinhas);
		case(ObservedGame.MUSICA):		
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(2) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(3) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(4) + matriz_deslocamentoLinhas);
		case(ObservedGame.ENTRADA):	
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(21) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(22) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(23) + matriz_deslocamentoLinhas);
		case(ObservedGame.INVERNO):
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(2) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(3) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(4) + matriz_deslocamentoLinhas);
		case(ObservedGame.JOGOS):
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(9) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(10) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(11) + matriz_deslocamentoLinhas);
		case(ObservedGame.BIBLIOTECA):
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(15) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(16) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(17) + matriz_deslocamentoLinhas);
		case(ObservedGame.ESCRITORIO):
			if(posComodo < 4)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(21) + matriz_deslocamentoLinhas);
			if(posComodo < 8)
				return (int)  img_escalaVertical*(matriz_alturaLinha*(22) + matriz_deslocamentoLinhas);
			return (int)  img_escalaVertical*(matriz_alturaLinha*(23) + matriz_deslocamentoLinhas);
				
		}
		return 0;
	}
	
public int getTamanhoTabuleiro(){
		return matriz_numLinhas*matriz_numColunas;
	}
	public int getDX(){
		return (int) (matriz_larguraColuna*img_escalaHorizontal);
	}
	public int getDY(){
		return (int) (matriz_alturaLinha*img_escalaVertical);
	}
	
	public boolean posValida(int X, int Y, int passo, int indexJogador, boolean pixelFlag) {
		int xMouse, yMouse;
		int[] posJogador = {-1, -1, -1, -1};
		if(pixelFlag == true){
			xMouse = X/matriz_larguraColuna;
			yMouse = Y/matriz_alturaLinha;
		}
		else{
			if(X<0 || X>=matriz_numColunas)
				return false;
			else
				xMouse = X;
			if(Y<0  || Y>=matriz_numLinhas)
				return false;
			else
				yMouse = Y;
		}
		int valorPos = matriz[xMouse + yMouse*matriz_numColunas][0];
		int yJogador =-1;
		int xJogador =-1;
		if(valorPos != Tabuleiro.INVALIDO)
		{
			for(int i=0; i < getTamanhoTabuleiro();i++)
				if(matriz[i][0] == indexJogador){
					posJogador[0] = i;
					break;
				}
			if(posJogador[0]==-1)
			{
				for(int j=0; j<9; j++)
					for(int i=0; i<12; i++)
						if(comodos[j][i] == indexJogador)
						{
							//switch para usar posjogador como porta do comodo
							switch(j + ObservedGame.COZINHA)
							{
							case (ObservedGame.COZINHA):	
								if(!jogadorEm(4,6, indexJogador))
									posJogador[0] = 4 + 6*matriz_numColunas;
								break;
							case (ObservedGame.JANTAR):		
								if(!jogadorEm(8,12, indexJogador))
									posJogador[0] = 8 + 12*matriz_numColunas;
								if(!jogadorEm(6,16, indexJogador))
									posJogador[1] = 6 + 16*matriz_numColunas;
								break;
							case (ObservedGame.ESTAR):
								if(!jogadorEm(6,18, indexJogador))
									posJogador[0] = 6 + 18*matriz_numColunas;
								break;
							case (ObservedGame.MUSICA):
								if(!jogadorEm(7,5, indexJogador))
									posJogador[0] = 7 + 5*matriz_numColunas;
								if(!jogadorEm(16,5, indexJogador))
									posJogador[1] = 16 + 5*matriz_numColunas;
								if(!jogadorEm(9,8, indexJogador))
									posJogador[2] = 9 + 8*matriz_numColunas;
								if(!jogadorEm(14,8, indexJogador))
									posJogador[3] = 14 + 8*matriz_numColunas;
								break;
							case (ObservedGame.ENTRADA):
								if(!jogadorEm(11,17, indexJogador))
									posJogador[0] = 11 + 17*matriz_numColunas;
								if(!jogadorEm(12,17, indexJogador))
									posJogador[1] = 12 + 17*matriz_numColunas;
								if(!jogadorEm(15,20, indexJogador))
									posJogador[2] = 15 + 20*matriz_numColunas;
								break;
							case (ObservedGame.INVERNO):	
								if(!jogadorEm(18,5, indexJogador))
									posJogador[0] = 18 + 5*matriz_numColunas;
								break;
							case (ObservedGame.JOGOS):			
								if(!jogadorEm(17, 9, indexJogador))
									posJogador[0] = 17 + 9*matriz_numColunas;
								if(!jogadorEm(22,13, indexJogador))
									posJogador[1] = 22 + 13*matriz_numColunas;
								break;
							case (ObservedGame.BIBLIOTECA):	
								if(!jogadorEm(16,16, indexJogador))
									posJogador[0] = 16 + 16*matriz_numColunas;
								if(!jogadorEm(20,13, indexJogador))
									posJogador[1] = 20 + 13*matriz_numColunas;
								break;
							case (ObservedGame.ESCRITORIO):
								if(!jogadorEm(17, 20, indexJogador))
									posJogador[0] = 17 + 20*matriz_numColunas;
								break;
							}
						}
				if(posJogador[0]==-1)
					return false;
			}
			for(int i=0; i<4; i++)
			{
				if(posJogador[i] == -1)
					break;
			
				xJogador = posJogador[i]%matriz_numColunas; 
				yJogador = posJogador[i]/matriz_numColunas;
			if(matriz[xMouse + yMouse*matriz_numColunas][0] == -1){
				System.out.println("nao em comodo");
				if(emComodo(indexJogador) == -1){
					if(distanciaValida(xMouse, yMouse, xJogador, yJogador, passo)  &&  !jogadorEm(xMouse, yMouse, indexJogador))		
						return true;
				}
				else if(movimentacaoValidaSaindoComodo(xMouse, yMouse, passo, indexJogador))
					return true;
			}
			else{
				System.out.println("em comodo");
				return movimentacaoValidaComodo(xMouse, yMouse, xJogador, yJogador, passo, indexJogador);
			}
		}
		}
		return false;

	}



	private boolean movimentacaoValidaComodo(int xMouse, int yMouse, int xJogador, int yJogador, int passo, int indexJogador) {
		//Dentro da cozinha
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.COZINHA)
			if(distanciaValida(4, 6, xJogador, yJogador, passo-1) && !jogadorEm(4,6, indexJogador))
				return true;
		//Dentro da sala de musica
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.MUSICA)
			if(	(distanciaValida(7, 5, xJogador, yJogador, passo-1)  && !jogadorEm(7,5, indexJogador)) 	|| 
				(distanciaValida(16, 5, xJogador, yJogador, passo-1) && !jogadorEm(16,5, indexJogador))  	||
				(distanciaValida(9, 8, xJogador, yJogador, passo-1)  && !jogadorEm(9,8, indexJogador)) 	||
				(distanciaValida(14, 8, xJogador, yJogador, passo-1) && !jogadorEm(14,8, indexJogador))	)	
				return true;
		//Dentro do jardim de inverno
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.INVERNO)
			if(distanciaValida(18, 5, xJogador, yJogador, passo-1) && !jogadorEm(18, 5, indexJogador))

				return true;
		//Dentro do salao de jogos
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.JOGOS)
			if(((distanciaValida(17, 9, xJogador, yJogador, passo-1) && !jogadorEm(17,9, indexJogador)) || (distanciaValida(22, 13, xJogador, yJogador, passo-1) && !jogadorEm(22,13, indexJogador))))
				return true;
		//Dentro da biblioteca
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.BIBLIOTECA)
			if(((distanciaValida(16, 16, xJogador, yJogador, passo-1) && !jogadorEm(16,16, indexJogador)) || (distanciaValida(20, 13, xJogador, yJogador, passo-1) && !jogadorEm(20,13, indexJogador))))
				return true;
		//Dentro do escritorio
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.ESCRITORIO)
			if(distanciaValida(17, 20, xJogador, yJogador, passo-1) && !jogadorEm(17,20, indexJogador))
				return true;
		//Dentro da entrada
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.ENTRADA)
			if((					(distanciaValida(11, 17, xJogador, yJogador, passo-1) && !jogadorEm(11,17, indexJogador)) ||
									(distanciaValida(12, 17, xJogador, yJogador, passo-1) && !jogadorEm(12,17, indexJogador)) ||
									(distanciaValida(15, 20, xJogador, yJogador, passo-1) && !jogadorEm(15,20, indexJogador))) )
				return true;
		//Dentro da sala de estar
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.ESTAR)
			if(distanciaValida(6, 18, xJogador, yJogador, passo-1) && !jogadorEm(6,18, indexJogador))
				return true;
		//Dentro da sala de jantar
		if(matriz[xMouse + yMouse*matriz_numColunas][0] == ObservedGame.JANTAR){
			if(((distanciaValida(8, 12, xJogador, yJogador, passo-1) && !jogadorEm(8,12, indexJogador)) || (distanciaValida(6, 16, xJogador, yJogador, passo-1) && !jogadorEm(6,16, indexJogador))))
				return true;}

		return false;
	}
	private boolean movimentacaoValidaSaindoComodo(int xMouse, int yMouse, int passo, int indexJogador){
		int comodoJogador = emComodo(indexJogador);
		//Dentro da cozinha
		switch(comodoJogador)
		{
		case ObservedGame.COZINHA:
			if(distanciaValida(4, 6, xMouse, yMouse, passo-1) && !jogadorEm(4,6, indexJogador))
				return true;
		//Dentro da sala de musica
		case ObservedGame.MUSICA:
			if(	(distanciaValida(7, 5, xMouse, yMouse, passo-1)  && !jogadorEm(7,5, indexJogador)) 	|| 
				(distanciaValida(16, 5, xMouse, yMouse, passo-1) && !jogadorEm(16,5, indexJogador))  	||
				(distanciaValida(9, 8, xMouse, yMouse, passo-1)  && !jogadorEm(9,8, indexJogador)) 	||
				(distanciaValida(14, 8, xMouse, yMouse, passo-1) && !jogadorEm(14,8, indexJogador))	)	
				return true;
		//Dentro do jardim de inverno
		case ObservedGame.INVERNO:
			if(distanciaValida(18, 5, xMouse, yMouse, passo-1) && !jogadorEm(18, 5, indexJogador))
				return true;
		//Dentro do salao de jogos
		case ObservedGame.JOGOS:
			if(((distanciaValida(17, 9, xMouse, yMouse, passo-1) && !jogadorEm(17,9, indexJogador)) || (distanciaValida(22, 13, xMouse, yMouse, passo-1) && !jogadorEm(22,13, indexJogador))))
				return true;
		//Dentro da biblioteca
		case ObservedGame.BIBLIOTECA:
			if(((distanciaValida(16, 16, xMouse, yMouse, passo-1) && !jogadorEm(16,16, indexJogador)) || (distanciaValida(20, 13, xMouse, yMouse, passo-1) && !jogadorEm(20,13, indexJogador))))
				return true;
		//Dentro do escritorio
		case ObservedGame.ESCRITORIO:
			if(distanciaValida(17, 20, xMouse, yMouse, passo-1) && !jogadorEm(17,20, indexJogador))
				return true;
		//Dentro da entrada
		case ObservedGame.ENTRADA:
			if(((distanciaValida(11, 17, xMouse, yMouse, passo-1) && !jogadorEm(11,17, indexJogador)) ||
				(distanciaValida(12, 17, xMouse, yMouse, passo-1) && !jogadorEm(12,17, indexJogador)) ||
				(distanciaValida(15, 20, xMouse, yMouse, passo-1) && !jogadorEm(15,20, indexJogador))) )
				return true;
		//Dentro da sala de estar
		case ObservedGame.ESTAR:
			if(distanciaValida(6, 18, xMouse, yMouse, passo-1) && !jogadorEm(6,18, indexJogador))
				return true;
		//Dentro da sala de jantar
		case ObservedGame.JANTAR:
			if(((distanciaValida(8, 12, xMouse, yMouse, passo-1) && !jogadorEm(8,12, indexJogador)) || (distanciaValida(6, 16, xMouse, yMouse, passo-1) && !jogadorEm(6,16, indexJogador))))
				return true;
		}
		return false;		
	}
	private int emComodo(int indexJogador) {
		for(int j=0; j<9; j++)
			for(int i=0; i<12; i++)
				if(comodos[j][i] == indexJogador)
					return j + ObservedGame.COZINHA;
		return -1;
		}
	
	private boolean jogadorEm(int x, int y, int indexJogador) {
		if(matriz[x+y*matriz_numColunas][0] != Tabuleiro.INVALIDO && matriz[x+y*matriz_numColunas][0] != Tabuleiro.VAZIO && matriz[x+y*matriz_numColunas][0] != Tabuleiro.ALCANCE  &&  matriz[x+y*matriz_numColunas][0] != indexJogador)
			return true;
		return false;
	}
	private boolean distanciaValida(int x, int y, int xJogador, int yJogador, int passo) {
		if(Math.abs(x-xJogador) + Math.abs(y-yJogador) <= passo)
			return true;
		System.out.println("Muito longe!");
		return false;
	}

	public void mudaPosJogador(int pixelX, int pixelY, int indexJogador) {

			int x = pixelX/matriz_larguraColuna;
			int y = pixelY/matriz_alturaLinha;
			int flag = 0;
			for(int i=0; i<getTamanhoTabuleiro(); i++){
				if(matriz[i][0] == indexJogador){
					matriz[i][0] = Tabuleiro.VAZIO;
					flag = 1;
					break;
				}
			}
			if(flag==0)
			{
				for(int i=0; i<ObservedGame.numRooms; i++){
					for(int j=0; j<ObservedGame.numPlayers+ObservedGame.numWeapons; j++)
					{
						if(comodos[i][j] == indexJogador){
							comodos[i][j] = Tabuleiro.VAZIO;
							break;
						}
					}
				}
			}
			switch(matriz[x + y*matriz_numColunas][0])
			{
				case(ObservedGame.COZINHA):	
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[0][i] == Tabuleiro.VAZIO){
							comodos[0][i] = indexJogador;
							break;
						}
					break;		
				case(ObservedGame.JANTAR):
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[1][i] == Tabuleiro.VAZIO){
							comodos[1][i] = indexJogador;
							break;
						}
					break;					
				case(ObservedGame.ESTAR):		
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[2][i] == Tabuleiro.VAZIO){
							comodos[2][i] = indexJogador;
							break;
						}
					break;					
				case(ObservedGame.MUSICA):		
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[3][i] == Tabuleiro.VAZIO){
							comodos[3][i] = indexJogador;
							break;
						}
					break;					
				case(ObservedGame.ENTRADA):	
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[4][i] == Tabuleiro.VAZIO){
							comodos[4][i] = indexJogador;
							break;
						}
					break;					
				case(ObservedGame.INVERNO):
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[5][i] == Tabuleiro.VAZIO){
							comodos[5][i] = indexJogador;
							break;
						}
					break;
				case(ObservedGame.JOGOS):
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[6][i] == Tabuleiro.VAZIO){
							comodos[6][i] = indexJogador;
							break;
						}
					break;
				case(ObservedGame.BIBLIOTECA):
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[7][i] == Tabuleiro.VAZIO){
							comodos[7][i] = indexJogador;
							break;
						}
					break;
				case(ObservedGame.ESCRITORIO):
					for(int i=0; i<ObservedGame.numPlayers+ObservedGame.numWeapons; i++)
						if(comodos[8][i] == Tabuleiro.VAZIO){
							comodos[8][i] = indexJogador;
							break;
						}
					break;
				default: matriz[x+y*matriz_numColunas][0] = indexJogador; break;
			}
			
	}
	public void mudaPosArma(int indexComodo, int indexArma) {
		for(int j=0; j<9; j++)
			for(int i=0; i<12; i++)
				if(comodos[j][i] == indexArma)
				{
					comodos[j][i] = Tabuleiro.VAZIO;
				}
		
		switch(indexComodo)
		{
		case (ObservedGame.COZINHA):		
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.COZINHA - 12][i] == -1){
					comodos[ObservedGame.COZINHA - 12][i] = indexArma;
					break;
				}
			break;
		case (ObservedGame.JANTAR):			
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.JANTAR - 12][i] == -1){
					comodos[ObservedGame.JANTAR - 12][i] = indexArma;
					break;
				}
			break;
		case (ObservedGame.ESTAR):			
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.ESTAR - 12][i] == -1){
					comodos[ObservedGame.ESTAR - 12][i] = indexArma;
					break;
				}
			break;
		case (ObservedGame.MUSICA):			
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.MUSICA - 12][i] == -1){
					comodos[ObservedGame.MUSICA - 12][i] = indexArma;
					break;
				}
			break;
		case (ObservedGame.ENTRADA):			
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.ENTRADA - 12][i] == -1){
					comodos[ObservedGame.ENTRADA - 12][i] = indexArma;
					break;
				}
			break;
		case (ObservedGame.INVERNO):		
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.INVERNO - 12][i] == -1){
					comodos[ObservedGame.INVERNO - 12][i] = indexArma;
					break;
				}
			break;
		case (ObservedGame.JOGOS):			
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.JOGOS - 12][i] == -1){
					comodos[ObservedGame.JOGOS - 12][i] = indexArma;
					break;
				}
			break;
		case (ObservedGame.BIBLIOTECA):		
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.BIBLIOTECA - 12][i] == -1){
					comodos[ObservedGame.BIBLIOTECA - 12][i] = indexArma;
					break;
				}
			break;
		case (ObservedGame.ESCRITORIO):		
			for(int i=0; i<12;i++)
				if(comodos[ObservedGame.ESCRITORIO - 12][i] == -1){
					comodos[ObservedGame.ESCRITORIO - 12][i] = indexArma;
					break;
				}
			break;
		}
}
	
	public double getEscalaHorizontal(){
		return img_escalaHorizontal;
	}
	public void setEscalaHorizontal(double width){
		img_escalaHorizontal = width/imgTabuleiro.getWidth();
	}
	public double getEscalaVertical(){
		return img_escalaVertical;
	}
	public void setEscalaVertical(double height){
		img_escalaVertical = height/imgTabuleiro.getHeight();
	}

	public void AdicionaAlcance(int die, int jogador) {
		int[] posJogador= {-1,-1,-1,-1};
		int xJogador, yJogador, comodoJogador=-1;
		int Ymin, Ymax;
		
		for(int i=0; i<getTamanhoTabuleiro(); i++)
			if(matriz[i][0] == jogador){
				posJogador[0] = i; 
				break;
			}
		if(posJogador[0]==-1)
		{
			for(int j=0; j<9; j++)
				for(int i=0; i<12; i++)
					if(comodos[j][i] == jogador)
					{
						//switch para usar posjogador como porta do comodo
						switch(j + ObservedGame.COZINHA)
						{
						case (ObservedGame.COZINHA):	
							comodoJogador = ObservedGame.COZINHA;
							if(!jogadorEm(4,6, jogador))
								posJogador[0] = 4 + 6*matriz_numColunas;
							break;
						case (ObservedGame.JANTAR):		
							comodoJogador = ObservedGame.JANTAR;
							if(!jogadorEm(8,12, jogador))
								posJogador[0] = 8 + 12*matriz_numColunas;
							if(!jogadorEm(6,16, jogador))
								posJogador[1] = 6 + 16*matriz_numColunas;
							break;
						case (ObservedGame.ESTAR):
							comodoJogador = ObservedGame.ESTAR;
							if(!jogadorEm(6,18, jogador))
								posJogador[0] = 6 + 18*matriz_numColunas;
							break;
						case (ObservedGame.MUSICA):
							comodoJogador = ObservedGame.MUSICA;
							if(!jogadorEm(7,5, jogador))
								posJogador[0] = 7 + 5*matriz_numColunas;
							if(!jogadorEm(16,5, jogador))
								posJogador[1] = 16 + 5*matriz_numColunas;
							if(!jogadorEm(9,8, jogador))
								posJogador[2] = 9 + 8*matriz_numColunas;
							if(!jogadorEm(14,8, jogador))
								posJogador[3] = 14 + 8*matriz_numColunas;
							break;
						case (ObservedGame.ENTRADA):
							comodoJogador = ObservedGame.ENTRADA;
							if(!jogadorEm(11,17, jogador))
								posJogador[0] = 11 + 17*matriz_numColunas;
							if(!jogadorEm(12,17, jogador))
								posJogador[1] = 12 + 17*matriz_numColunas;
							if(!jogadorEm(15,20, jogador))
								posJogador[2] = 15 + 20*matriz_numColunas;
							break;
						case (ObservedGame.INVERNO):	
							comodoJogador = ObservedGame.INVERNO;
							if(!jogadorEm(18,5, jogador))
								posJogador[0] = 18 + 5*matriz_numColunas;
							break;
						case (ObservedGame.JOGOS):			
							comodoJogador = ObservedGame.JOGOS;
							if(!jogadorEm(17, 9, jogador))
								posJogador[0] = 17 + 9*matriz_numColunas;
							if(!jogadorEm(22,13, jogador))
								posJogador[1] = 22 + 13*matriz_numColunas;
							break;
						case (ObservedGame.BIBLIOTECA):	
							comodoJogador = ObservedGame.BIBLIOTECA;
							if(!jogadorEm(16,16, jogador))
								posJogador[0] = 16 + 16*matriz_numColunas;
							if(!jogadorEm(20,13, jogador))
								posJogador[1] = 20 + 13*matriz_numColunas;
							break;
						case (ObservedGame.ESCRITORIO):
							comodoJogador = ObservedGame.ESCRITORIO;
							if(!jogadorEm(17, 20, jogador))
								posJogador[0] = 17 + 20*matriz_numColunas;
							break;
						}
					}
			if(posJogador[0]==-1)
				return;
		}
		
		for(int k=0; k<4; k++)
		{
			if(posJogador[k] == -1)
				break;
			xJogador = posJogador[k]%matriz_numColunas;
			yJogador = posJogador[k]/matriz_numColunas;
			
			if(yJogador-die <= 0)
				Ymin =0;
			else
				Ymin =yJogador-die;
			if(yJogador+die >= matriz_numLinhas)
				Ymax = matriz_numLinhas-1;
			else 
				Ymax = yJogador+die;
			for(int i=Ymin; i<=Ymax; i++){
				for(int passosRestantes = die - Math.abs(yJogador-i); passosRestantes>=0; passosRestantes--){
					if(yJogador==i && passosRestantes==0 && comodoJogador == -1)
						break;
					if(posValida(xJogador+passosRestantes, i, die, jogador, false) && (comodoJogador == -1 || comodoJogador != matriz[i*matriz_numColunas +(xJogador+passosRestantes)][0])){
						matriz[i*matriz_numColunas +(xJogador+passosRestantes)][1] = Tabuleiro.ALCANCE;
					}
					if(posValida(xJogador-passosRestantes, i, die, jogador, false) && (comodoJogador == -1 || comodoJogador != matriz[i*matriz_numColunas +(xJogador-passosRestantes)][0])){
						matriz[i*matriz_numColunas +(xJogador-passosRestantes)][1] = Tabuleiro.ALCANCE;
					}
				}
			}
		}
	}
	public void removeAlcance() {
		for(int i=0; i<getTamanhoTabuleiro(); i++)
			if(matriz[i][1] == Tabuleiro.ALCANCE)	
				matriz[i][1] = Tabuleiro.VAZIO;
	}
}
