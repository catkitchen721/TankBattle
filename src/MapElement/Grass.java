package MapElement;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.Color;


public class Grass extends Element {
	public Grass() {
		super("src/MapElement/grass.png");
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 50, 50,null);
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		Grass g1 = new Grass();
		frame.add(g1);
		frame.setVisible(true);
	}

}
