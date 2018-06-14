package local;

import java.awt.Canvas;

public class Menu {

	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 500;
	
	public static void main(String[] args) {
		
		Thread frameThread = new Thread(new Runnable() {
			@Override
			public void run()
			{
				Board board = new Board();
			}
		});
		frameThread.start();
		Board board = new Board();
		board.setVisible(true);

	}

}
