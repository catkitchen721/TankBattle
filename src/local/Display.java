package local;

import java.util.List;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import local.Status.Direction;


import javax.imageio.ImageIO;

public class Display extends Canvas {
	
	public static final int FRAME_WIDTH = 1200;
	public static final int FRAME_HEIGHT = 1000;
	public static final int BOX_WIDTH = 50;
	
	public int[][] map = null;

	private BufferedImage grass;
	private BufferedImage wall;
	private BufferedImage tankP1;
	private BufferedImage bullet;
	private BufferedImage tankP1_R;
	private BufferedImage tankP1_L;
	private BufferedImage tankP1_U;
	private BufferedImage tankP1_D;
	private BufferStrategy bs;
	
	public Player player1 = new Player(1);
	public Player player2 = new Player(2);

	public boolean player1AppearFlag = false;
	public boolean player2AppearFlag = false;

	public Display()
	{	

		loadImages();
		
		addPlayer1();
		addPlayer2();
		repaint();
	}
	
	
	public void paint(Graphics g)
	{
		try 
		{
			map = ReadData.readFromFile("resource/map.txt");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		
		super.paint(g2d);
		g2d.fillRect(10, 10, 950, 950);
		
        drawMap(g2d);
        
        if(player1AppearFlag) {
			drawPlayer(g2d, player1);
		}
        if(player2AppearFlag) {
			drawPlayer(g2d, player2);
		}
        
        if(!player1.getBullet().isEmpty()) {
        	drawBullets(g2d, player1);
        }
        
        if(!player2.getBullet().isEmpty()) {
        	drawBullets(g2d, player2);
        }
        
        bs.show();
        g2d.dispose();
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
		
	}
	public void drawPlayer(Graphics g, Player player) {
		Graphics2D g2d = (Graphics2D) g;
		if(player.data.getDirect() == Direction.UP) {
			g2d.drawImage(tankP1_U, player.data.getX(), player.data.getY(), BOX_WIDTH, BOX_WIDTH, null);
		}
		else if(player.data.getDirect() == Direction.DOWN) {
			g2d.drawImage(tankP1_D, player.data.getX(), player.data.getY(), BOX_WIDTH, BOX_WIDTH, null);
		}
		else if(player.data.getDirect() == Direction.LEFT) {
			g2d.drawImage(tankP1_L, player.data.getX(), player.data.getY(), BOX_WIDTH, BOX_WIDTH, null);
		}
		else if(player.data.getDirect() == Direction.RIGHT) {
			g2d.drawImage(tankP1_R, player.data.getX(), player.data.getY(), BOX_WIDTH, BOX_WIDTH, null);
		}
	}
	public void drawBullets(Graphics g, Player player) {
		Graphics2D g2d = (Graphics2D) g;
		for(Bullet element : player.getBullet()) {
			if(element.getVisible())
				g2d.drawImage(bullet, element.data.getX() + 25, element.data.getY(), 10, 10, null);
		}
	}
	
	
	public void loadImages() {
		try {
			tankP1_R = ImageIO.read(new File("resource/player1_R.png"));
			tankP1_D = ImageIO.read(new File("resource/player1_D.png"));
			tankP1_U = ImageIO.read(new File("resource/player1_U.png"));
			tankP1_L = ImageIO.read(new File("resource/player1_L.png"));
			grass = ImageIO.read(new File("resource/grass.jpg"));
			wall = ImageIO.read(new File("resource/wall.jpg"));
			tankP1 = ImageIO.read(new File("resource/player1.png"));
			bullet = ImageIO.read(new File("resource/bullet.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void addPlayer1()
	{
		player1AppearFlag = true;
		player1.data.setX(260);
		player1.data.setY(110);
		System.out.println("x: " + player1.getBound().x);
		System.out.println("y: " + player1.getBound().y);
	}
	
	public void addPlayer2()
	{
		player2AppearFlag = true;
		player2.data.setX(260);
		player2.data.setY(160);
	}
	
	public void update(Graphics g)
	{
		this.paint(g);
	}
}
