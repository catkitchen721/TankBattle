package local;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public abstract class Element extends JComponent{
	public static int ELEMENT_WIDTH = 50;
	public static int ELEMENT_HEIGHT = 50;
	
	public int x = 0;
	public int y = 0;
	
	public BufferedImage image = null;
	
	public Element(String imagePath, int x, int y) {
		this.x = x;
		this.y = y;
		try {
		    image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
		}
	}
}
