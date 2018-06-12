package local;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class Display extends Canvas{
	
	public static final int FRAME_WIDTH = 1200;
	public static final int FRAME_HEIGHT = 1000;
	public static final int BOX_WIDTH = 50;
	
	public int[][] map = null;

	private BufferedImage grass;
	private BufferedImage wall;
	private BufferedImage circle;
	
	public Player player1;
	public boolean player1AppearFlag = false;

	
	public Display()
	{	

		loadImages();
		
		addPlayer1();
		repaint();
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(10, 10, 950, 950);
		
		try 
		{
			map = ReadData.readFromFile("resource/map.txt");
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        drawMap(g);
        
//		if(tankAppearFlag)
//		{
//			g.drawImage(circle, 250, 100, BOX_WIDTH, BOX_WIDTH, null);
//		}
	}
	
	public void drawMap(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for(int j=0; j<19; j++)
		{
			for(int i=0; i<19; i++)
			{
				if(map[j][i] == '0')
				{
					g2d.drawImage(grass,  10 + i * BOX_WIDTH, 10 + j * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH, null);

				}
				else if(map[j][i] == '1')
				{
					g2d.drawImage(grass, 10 + i * BOX_WIDTH, 10 + j * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH, null);
				}
				else if(map[j][i] == '2')
				{
					g2d.drawImage(wall, 10 + i * BOX_WIDTH, 10 + j * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH, null);
				}
				else;
				
			}
		}
		
		if(player1AppearFlag) {
			drawPlayer(g, player1);
		}
	}
	public void drawPlayer(Graphics g, Player player) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(circle, player.data.getX(), player.data.getY(), BOX_WIDTH, BOX_WIDTH, null);

	}
	public void loadImages() {
		try {
			grass = ImageIO.read(new File("resource/grass.jpg"));
			wall = ImageIO.read(new File("resource/wall.jpg"));
			circle = ImageIO.read(new File("resource/circle.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void addPlayer1()
	{
		player1AppearFlag = true;
		player1 = new Player();
		player1.data.setX(250);
		player1.data.setY(100);
	}
}
