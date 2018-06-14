package local;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class HomeScreen extends Canvas{
	private boolean HomeFlag = true;
	private int titleHeight = 0;
	private BufferedImage img;
	private BufferedImage title;
	private BufferStrategy bs;
	
	public HomeScreen() {
		loadImage();
		repaint();
	}
	
	public int getPosition() {
		return titleHeight;
	}
	
	public void setPosition(int titleHeight) {
		this.titleHeight = titleHeight;
	}
	
	public void loadImage() {
			try {
				img = ImageIO.read(new File("./resource/bg.jpg"));
				title = ImageIO.read(new File("resource/title.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	public void paint(Graphics g) {
		bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		// For HomeScreen	
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		g2d.drawImage(img, 0, 0, 500, 500, this);
		g2d.drawImage(title, 150, titleHeight, 250, 250, this);
		
		bs.show();
		g2d.dispose();
	}
}
