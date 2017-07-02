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
	private final static  BufferedImage playerImages[] = new BufferedImage[6];
	private final static  BufferedImage weaponImages[] = new BufferedImage[6];
	private final static  BufferedImage roomImages[] =  new BufferedImage[9];
	private static  BufferedImage NaN;
	
	//private static final BufferedImage NaN;
	
	public static BufferedImage getImage(Card c)
	{
		if(c == null)
		{
			return NaN;
		}
		
		try
		{
			
			switch(c.type)
			{
				case SUSPECT: 
					System.out.println(playerImages[c.id].toString());
					return playerImages[c.id];
				case WEAPON:
					System.out.println(weaponImages[c.id].toString());
					return weaponImages[c.id];
				case ROOM:
					System.out.println(roomImages[c.id].toString());
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
			NaN = ImageIO.read(new File("assets/NaN.jpg"));
			
			//primeiro array contem imagens de jogadores
			//"Reverendo Green", "Coronel Mustard", "Senhora Peacock", "Professor Plum", "Senhorita Scarlet","Senhora White"
						
			playerImages[0] = ImageIO.read(new File("assets/Suspeitos/scarlet.jpg"));
			playerImages[1] = ImageIO.read(new File("assets/Suspeitos/mustard.jpg"));
			playerImages[2] = ImageIO.read(new File("assets/Suspeitos/white.jpg"));
			playerImages[3] = ImageIO.read(new File("assets/Suspeitos/green.jpg"));
			playerImages[4] = ImageIO.read(new File("assets/Suspeitos/peacock.jpg"));
			playerImages[5] = ImageIO.read(new File("assets/Suspeitos/plum.jpg"));
			
			
			//segundo contem armas
			//"Corda", "Cano de Chumbo", "Faca", "Chave Inglesa", "Castiçal", "Revólver"
			weaponImages[0] = ImageIO.read(new File("assets/Armas/Corda.jpg"));
			weaponImages[1] = ImageIO.read(new File("assets/Armas/Cano.jpg"));
			weaponImages[2] = ImageIO.read(new File("assets/Armas/Faca.jpg"));
			weaponImages[3] = ImageIO.read(new File("assets/Armas/ChaveInglesa.jpg"));
			weaponImages[4] = ImageIO.read(new File("assets/Armas/Castical.jpg"));
			weaponImages[5] = ImageIO.read(new File("assets/Armas/Revolver.jpg"));
			
			
			//"Cozinha", "Sala de Jantar", "Sala de Estar", "Sala de Música", "Entrada", "Jardim de Inverno", "Salão de Jogos", "Biblioteca", "Escritório"
			roomImages[0] = ImageIO.read(new File("assets/Comodos/Cozinha.jpg"));
			roomImages[1] = ImageIO.read(new File("assets/Comodos/SalaDeJantar.jpg"));
			roomImages[2] = ImageIO.read(new File("assets/Comodos/SalaDeEstar.jpg"));
			roomImages[3] = ImageIO.read(new File("assets/Comodos/SalaDeMusica.jpg"));
			roomImages[4] = ImageIO.read(new File("assets/Comodos/Entrada.jpg"));
			roomImages[5] = ImageIO.read(new File("assets/Comodos/JardimInverno.jpg"));
			roomImages[6] = ImageIO.read(new File("assets/Comodos/SalaoDeJogos.jpg"));
			roomImages[7] = ImageIO.read(new File("assets/Comodos/Biblioteca.jpg"));
			roomImages[8] = ImageIO.read(new File("assets/Comodos/Escritorio.jpg"));
			
			
			
			
		}catch (IOException e)
		{
			System.out.println("Incapaz de abrir imagem. Erro:" + e.getMessage());
			System.exit(1);
		}
	}
}
