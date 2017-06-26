package gameView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameController.Card;

public class CardImages 
{
	//array de arrays, para conter as imagens de jogares/armas/quartos respectivamente
	//porque não podem ser final?
	private static  BufferedImage playerImages[];
	private static  BufferedImage weaponImages[];
	private static  BufferedImage roomImages[];
	private static  BufferedImage NaN;
	
	//private static final BufferedImage NaN;
	
	public static BufferedImage getImage(Card c)
	{
		try
		{
			switch(c.type)
			{
				case SUSPECT: 
					return playerImages[c.id];
				case WEAPON:
					return weaponImages[c.id];
				case ROOM:
					return roomImages[c.id];
			}
		}catch(IndexOutOfBoundsException e)
		{
			System.out.println("Não existe imagem para carta procurada:" + e.getMessage());
		}
		return NaN;
	}
	
	//bloco de inicialização static
	static{
		try
		{
			
			//imagem nula, usada em casos de exceção
			NaN = ImageIO.read(new File("assets/assets/NaN"));
			
			//primeiro array contem imagens de jogadores
			playerImages = new BufferedImage[6];
			//"Reverendo Green", "Coronel Mustard", "Senhora Peacock", "Professor Plum", "Senhorita Scarlet","Senhora White"
			playerImages[0] = ImageIO.read(new File("assets/assets/Suspeitos/green"));
			playerImages[1] = ImageIO.read(new File("assets/assets/Suspeitos/mustard"));
			playerImages[2] = ImageIO.read(new File("assets/assets/Suspeitos/peacock"));
			playerImages[3] = ImageIO.read(new File("assets/assets/Suspeitos/plum"));
			playerImages[4] = ImageIO.read(new File("assets/assets/Suspeitos/scarlet"));
			playerImages[5] = ImageIO.read(new File("assets/assets/Suspeitos/white"));
			
			//segundo contem armas
			weaponImages = new BufferedImage[6];
			//"Corda", "Cano de Chumbo", "Faca", "Chave Inglesa", "Castiçal", "Revólver"
			weaponImages[0] = ImageIO.read(new File("assets/assets/Armas/Corda"));
			weaponImages[1] = ImageIO.read(new File("assets/assets/Armas/Cano"));
			weaponImages[2] = ImageIO.read(new File("assets/assets/Armas/Faca"));
			weaponImages[3] = ImageIO.read(new File("assets/assets/Armas/ChaveInglesa"));
			weaponImages[4] = ImageIO.read(new File("assets/assets/Armas/Castical"));
			weaponImages[5] = ImageIO.read(new File("assets/assets/Armas/Revolver"));
			
			roomImages = new BufferedImage[9];
			//"Cozinha", "Sala de Jantar", "Sala de Estar", "Sala de Música", "Entrada", "Jardim de Inverno", "Salão de Jogos", "Biblioteca", "Escritório"
			roomImages[0] = ImageIO.read(new File("assets/assets/Comodos/Cozinha"));
			roomImages[1] = ImageIO.read(new File("assets/assets/Comodos/SalaDeJantar"));
			roomImages[2] = ImageIO.read(new File("assets/assets/Comodos/SalaDeEstar"));
			roomImages[3] = ImageIO.read(new File("assets/assets/Comodos/SalaDeMusica"));
			roomImages[4] = ImageIO.read(new File("assets/assets/Comodos/Entrada"));
			roomImages[5] = ImageIO.read(new File("assets/assets/Comodos/JardimInverno"));
			roomImages[6] = ImageIO.read(new File("assets/assets/Comodos/SalaoDeJogos"));
			roomImages[7] = ImageIO.read(new File("assets/assets/Comodos/Biblioteca"));
			roomImages[8] = ImageIO.read(new File("assets/assets/Comodos/Escritorio"));
			
			
			
			
			
		}catch (IOException e)
		{
			System.out.println("Incapaz de abrir imagem. Erro:" + e.getMessage());
			System.exit(1);
		}
		
	}
}
