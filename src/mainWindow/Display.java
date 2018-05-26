package mainWindow;

import java.awt.Canvas;
import javax.swing.JFrame;

public class Display extends Canvas{
	
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGHT = 600;

	public static void main(String[] args) {
		
		Display display = new Display();
		JFrame frame = new JFrame();
		
		frame.add(display);
		frame.pack();
		frame.setResizable(false);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

}
