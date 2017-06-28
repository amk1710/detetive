package gameController;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Tabuleiro {
	public  BufferedImage img;
	public  int[]  matriz;

	private double img_escalaHorizontal 	= 1.0;
	private double img_escalaVertical 		= 1.0;
	
	private int  matriz_numColunas;
	private int  matriz_numLinhas;
	private int  matriz_deslocamentoColunas;
	private int  matriz_deslocamentoLinhas;
	private int  matriz_larguraColuna;
	private int  matriz_alturaLinha;

	

	public static final Color corGreen 		= new Color(36,83,41, 255);
	public static final Color corMustard 	= new Color(204,176,32, 255);
	public static final Color corPeacock 	= new Color(45,62,118, 255);
	public static final Color corPlum 		= new Color(101,68,85, 255);
	public static final Color corScarlet 	= new Color(203,82,74, 255);
	public static final Color corWhite 		= new Color(183,186,193, 255);
	public static final Color corAlcance	= new Color(153, 255, 153, 128);

	/*	Estados da matriz
	 * 	Valores associados a cada posição da matriz.
	 */
	public static final int VAZIO		= -1;
	public static final int ALCANCE		= -2;
	public static final int INVALIDO	= -3;

	//Indice dos jogadores
	public static final int GREEN		= 0;
	public static final int MUSTARD		= 1;
	public static final int PEACOCK		= 2;
	public static final int PLUM		= 3;
	public static final int SCARLET		= 4;
	public static final int WHITE		= 5;
	
	
	public Tabuleiro(){
		Scanner infomatriz=null;
		try{
			img = ImageIO.read(new File("assets/Tabuleiro-Dobrado.JPG"));
			infomatriz = new Scanner(new FileReader("assets/Tabuleiro-Dobrado"));

		} catch (IOException e){
			System.out.println("Incapaz de abrir arquivo. Erro:" + e.getMessage());
			System.exit(1);
		}		


		matriz_alturaLinha				= infomatriz.nextInt();
		matriz_larguraColuna			= infomatriz.nextInt();
		matriz_deslocamentoLinhas		= infomatriz.nextInt();
		matriz_deslocamentoColunas		= infomatriz.nextInt();
		matriz_numLinhas  = img.getHeight()/matriz_alturaLinha;
		matriz_numColunas = img.getWidth()/matriz_larguraColuna;		
		matriz = new int[matriz_numLinhas*matriz_numColunas];
		for(int i=0; i<getTamanhoTabuleiro(); i++){
			if(infomatriz.hasNext())
				matriz[i] = infomatriz.nextInt();
			else{
				System.out.println("Ã„rquivo da matriz menor que o esperado"); System.exit(1);
			}
		}
		infomatriz.close();
	}
	
	

	public int getXJogador(int x){
		return (int) img_escalaHorizontal*(matriz_larguraColuna*x + matriz_deslocamentoColunas);
	}
 	public int getXPosJogador(int pos){
		return (int) img_escalaHorizontal*(matriz_larguraColuna*(pos%matriz_numColunas) + matriz_deslocamentoColunas);
	}
	public int getYJogador(int y){
		return (int) img_escalaVertical*(matriz_alturaLinha*y + matriz_deslocamentoLinhas);
	}
	public int getYPosJogador(int pos){ 
		return (int)  img_escalaVertical*(matriz_alturaLinha*(pos/matriz_numColunas) + matriz_deslocamentoLinhas);
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
		int valorPos = matriz[xMouse + yMouse*matriz_numColunas];
		int yJogador =0;
		int xJogador =0;
		if(valorPos != Tabuleiro.INVALIDO)
		{
			for(int i=0; i < getTamanhoTabuleiro();i++)
				if(matriz[i] == indexJogador){
					yJogador = i/matriz_numColunas;
					xJogador = i%matriz_numColunas;
					break;
				}
			if(!emComodo(xMouse, yMouse)){
				System.out.println("nao em comodo");
				if(!emComodo(xJogador, yJogador)){
					if(distanciaValida(xMouse, yMouse, xJogador, yJogador, passo)  &&  !jogadorEm(xMouse, yMouse, indexJogador))		
						return true;
				}
				else if(movimentacaoValidaSaindoComodo(xMouse, yMouse, xJogador, yJogador, passo, indexJogador))
					return true;
			}
			else{
				System.out.println("em comodo");
				return movimentacaoValidaComodo(xMouse, yMouse, xJogador, yJogador, passo, indexJogador);
			}
		}
		return false;
	}



	private boolean movimentacaoValidaComodo(int xMouse, int yMouse, int xJogador, int yJogador, int passo, int indexJogador) {
		//Dentro da cozinha
		if(xMouse<=5 && yMouse <= 6)
			if(distanciaValida(4, 6, xJogador, yJogador, passo-1) && !jogadorEm(4,6, indexJogador))
				return true;
		//Dentro da sala de musica
		if((xMouse>=10 && xMouse<=13 && (yMouse==1 || yMouse==0)) || (xMouse>=8 && xMouse<=15 && yMouse>=2 && yMouse<=7))
			if(	(distanciaValida(7, 5, xJogador, yJogador, passo-1)  && !jogadorEm(7,5, indexJogador)) 	|| 
				(distanciaValida(16, 5, xJogador, yJogador, passo-1) && !jogadorEm(16,5, indexJogador))  	||
				(distanciaValida(9, 8, xJogador, yJogador, passo-1)  && !jogadorEm(9,8, indexJogador)) 	||
				(distanciaValida(14, 8, xJogador, yJogador, passo-1) && !jogadorEm(14,8, indexJogador))	)	
				return true;
		//Dentro do jardim de inverno
		if((xMouse>=18 && yMouse<=4) || (xMouse>=19 && yMouse==5))
			if(distanciaValida(18, 5, xJogador, yJogador, passo-1) && !jogadorEm(18, 5, indexJogador))

				return true;
		//Dentro do salao de jogos
		if(xMouse>=18 && yMouse>=8 && yMouse<=12)
			if(((distanciaValida(17, 9, xJogador, yJogador, passo-1) && !jogadorEm(17,9, indexJogador)) || (distanciaValida(22, 13, xJogador, yJogador, passo-1) && !jogadorEm(22,13, indexJogador))))
				return true;
		//Dentro da biblioteca
		if((xMouse>=18 && (yMouse==14 || yMouse==18)) || (xMouse>=17 && yMouse>=15 && yMouse<=17))
			if(((distanciaValida(16, 16, xJogador, yJogador, passo-1) && !jogadorEm(16,16, indexJogador)) || (distanciaValida(20, 13, xJogador, yJogador, passo-1) && !jogadorEm(20,13, indexJogador))))
				return true;
		//Dentro do escritorio
		if(xMouse>=17 && yMouse>=21)
			if(distanciaValida(17, 20, xJogador, yJogador, passo-1) && !jogadorEm(17,20, indexJogador))
				return true;
		//Dentro da entrada
		if(xMouse>=9 && xMouse<=14 && yMouse>=18)
			if((					(distanciaValida(11, 17, xJogador, yJogador, passo-1) && !jogadorEm(11,17, indexJogador)) ||
									(distanciaValida(12, 17, xJogador, yJogador, passo-1) && !jogadorEm(12,17, indexJogador)) ||
									(distanciaValida(15, 20, xJogador, yJogador, passo-1) && !jogadorEm(15,20, indexJogador))) )
				return true;
		//Dentro da sala de estar
		if(xMouse<=6 && yMouse>=19)
			if(distanciaValida(6, 19, xJogador, yJogador, passo-1) && !jogadorEm(6,19, indexJogador))
				return true;
		//Dentro da sala de jantar
		if((xMouse<=4 && yMouse==9) || (xMouse<=7 && yMouse>=10 & yMouse<=15)){
			if(((distanciaValida(8, 12, xJogador, yJogador, passo-1) && !jogadorEm(8,12, indexJogador)) || (distanciaValida(6, 16, xJogador, yJogador, passo-1) && !jogadorEm(6,16, indexJogador))))
				return true;}

		return false;
	}
	private boolean movimentacaoValidaSaindoComodo(int xMouse, int yMouse, int xJogador, int yJogador, int passo, int indexJogador){
		//Dentro da cozinha
		if(xJogador<=5 && yJogador <= 6)
			if(distanciaValida(4, 6, xMouse, yMouse, passo-1) && !jogadorEm(4,6, indexJogador))
				return true;
		//Dentro da sala de musica
		if((xJogador>=10 && xJogador<=13 && (yJogador==1 || yJogador==0)) || (xJogador>=8 && xJogador<=15 && yJogador>=2 && yJogador<=7))
			if(	(distanciaValida(7, 5, xMouse, yMouse, passo-1)  && !jogadorEm(7,5, indexJogador)) 	|| 
				(distanciaValida(16, 5, xMouse, yMouse, passo-1) && !jogadorEm(16,5, indexJogador))  	||
				(distanciaValida(9, 8, xMouse, yMouse, passo-1)  && !jogadorEm(9,8, indexJogador)) 	||
				(distanciaValida(14, 8, xMouse, yMouse, passo-1) && !jogadorEm(14,8, indexJogador))	)	
				return true;
		//Dentro do jardim de inverno
		if((xJogador>=18 && yJogador<=4) || (xJogador>=19 && yJogador==5))
			if(distanciaValida(18, 5, xMouse, yMouse, passo-1) && !jogadorEm(18, 5, indexJogador))

				return true;
		//Dentro do salao de jogos
		if(xJogador>=18 && yJogador>=8 && yJogador<=12)
			if(((distanciaValida(17, 9, xMouse, yMouse, passo-1) && !jogadorEm(17,9, indexJogador)) || (distanciaValida(22, 13, xMouse, yMouse, passo-1) && !jogadorEm(22,13, indexJogador))))
				return true;
		//Dentro da biblioteca
		if((xJogador>=18 && (yJogador==14 || yJogador==18)) || (xJogador>=17 && yJogador>=15 && yJogador<=17))
			if(((distanciaValida(16, 16, xMouse, yMouse, passo-1) && !jogadorEm(16,16, indexJogador)) || (distanciaValida(20, 13, xMouse, yMouse, passo-1) && !jogadorEm(20,13, indexJogador))))
				return true;
		//Dentro do escritorio
		if(xJogador>=17 && yJogador>=21)
			if(distanciaValida(17, 20, xMouse, yMouse, passo-1) && !jogadorEm(17,20, indexJogador))
				return true;
		//Dentro da entrada
		if(xJogador>=9 && xJogador<=14 && yJogador>=18)
			if((					(distanciaValida(11, 17, xMouse, yMouse, passo-1) && !jogadorEm(11,17, indexJogador)) ||
									(distanciaValida(12, 17, xMouse, yMouse, passo-1) && !jogadorEm(12,17, indexJogador)) ||
									(distanciaValida(15, 20, xMouse, yMouse, passo-1) && !jogadorEm(15,20, indexJogador))) )
				return true;
		//Dentro da sala de estar
		if(xJogador<=6 && yJogador>=19)
			if(distanciaValida(6, 19, xMouse, yMouse, passo-1) && !jogadorEm(6,19, indexJogador))
				return true;
		//Dentro da sala de jantar
		if((xJogador<=4 && yJogador==9) || (xJogador<=7 && yJogador>=10 & yJogador<=15))
			if(((distanciaValida(8, 12, xMouse, yMouse, passo-1) && !jogadorEm(8,12, indexJogador)) || (distanciaValida(6, 16, xMouse, yMouse, passo-1) && !jogadorEm(6,16, indexJogador))))
				return true;

		return false;		
	}
	private boolean emComodo(int x, int y) {
		//Dentro da cozinha
		if(x<=5 && y <= 6)																	return true;
		//Dentro da sala de musica
		if((x>=10 && x<=13 && (y==1 || y==0) ) || (x>=8 && x<=15 && y>=2 && y<=7))			return true;
		//Dentro do jardim de inverno
		if((x>=18 && y<=4) || (x>=19 && y==5))												return true;
		//Dentro do salao de jogos
		if(x>=18 && y>=8 && y<=12)															return true;
		//Dentro da biblioteca
		if((x>=18 && (y==14 || y==18)) || (x>=17 && y>=15 && y<=17))						return true;
		//Dentro do escritorio
		if(x>=17 && y>=21)																	return true;
		//Dentro da entrada
		if(x>=9 && x<=14 && y>=18)															return true;
		//Dentro da sala de estar
		if(x<=6 && y>=19)																	return true;
		//Dentro da sala de jantar
		if((x<=4 && y==9) || (x<=7 && y>=10 & y<=15))										return true;
		return false;
	}
	
	private boolean jogadorEm(int x, int y, int indexJogador) {
		if(matriz[x+y*matriz_numColunas] != Tabuleiro.INVALIDO && matriz[x+y*matriz_numColunas] != Tabuleiro.VAZIO && matriz[x+y*matriz_numColunas] != Tabuleiro.ALCANCE  &&  matriz[x+y*matriz_numColunas] != indexJogador)
			return true;
		return false;
	}
	private boolean distanciaValida(int x, int y, int xJogador, int yJogador, int passo) {
		if(Math.abs(x-xJogador) + Math.abs(y-yJogador) <= passo)
			return true;
		System.out.println("Muito longe!");
		return false;
	}

	public void mudaPos(int pixelX, int pixelY, int indexJogador) {

			int x = pixelX/matriz_larguraColuna;
			int y = pixelY/matriz_alturaLinha;
			
			for(int i=0; i<getTamanhoTabuleiro(); i++){
				if(matriz[i] == indexJogador){
					matriz[i] = Tabuleiro.VAZIO;
					break;
				}
			}
			matriz[x+y*matriz_numColunas] = indexJogador;
			
	}
	
	public double getEscalaHorizontal(){
		return img_escalaHorizontal;
	}
	public void setEscalaHorizontal(double width){
		img_escalaHorizontal = width/img.getWidth();
	}
	public double getEscalaVertical(){
		return img_escalaVertical;
	}
	public void setEscalaVertical(double height){
		img_escalaVertical = height/img.getHeight();
	}

	public void AdicionaAlcance(int die, int jogador) {
		int posJogador=-1, xJogador, yJogador;
		int Ymin, Ymax;
		for(int i=0; i<getTamanhoTabuleiro(); i++)
			if(matriz[i] == jogador){
				posJogador = i; break;
			}
		xJogador = posJogador%matriz_numColunas;
		yJogador = posJogador/matriz_numColunas;
		
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
				if(yJogador==i && passosRestantes==0)
					break;
				if(posValida(xJogador+passosRestantes, i, die, matriz[yJogador*matriz_numColunas + xJogador], false)){
					matriz[i*matriz_numColunas +(xJogador+passosRestantes)] = Tabuleiro.ALCANCE;
				}
				if(posValida(xJogador-passosRestantes, i, die, matriz[yJogador*matriz_numColunas + xJogador], false)){
					matriz[i*matriz_numColunas +(xJogador-passosRestantes)] = Tabuleiro.ALCANCE;
				}
			}
		}
	}
	public void removeAlcance() {
		for(int i=0; i<getTamanhoTabuleiro(); i++)
			if(matriz[i] == Tabuleiro.ALCANCE)
				matriz[i] = Tabuleiro.VAZIO;
	}
}
