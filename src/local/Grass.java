package local;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.Color;


public class Grass extends Element {
	
	public Grass(int x, int y) {
		super("resource/grass.png", x, y);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, x, y, null);
	}
}
