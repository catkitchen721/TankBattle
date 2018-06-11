package MapElement;
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
	public BufferedImage image = null;
	
	public Element(String imagePath) {
		try {
		    image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
		}
	}
	
	
}
