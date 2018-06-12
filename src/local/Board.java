package local;

import javax.swing.JFrame;

import local.Status.Direction;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JFrame implements KeyListener {
	public static final int FRAME_WIDTH = 1200;
	public static final int FRAME_HEIGHT = 1000;
	public static final int BOX_WIDTH = 50;
	public int[][] map = null;

	public Display display;
	public Player player1;
	public Player player2;

	public Board() {
		display = new Display();
		add(display);
		
		loadMap();
		
		pack();
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		addKeyListener(this);
		display.addKeyListener(this);
		
	}
	
	public void addTank() {
		
	}
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void loadMap() {
		try {
			map = ReadData.readFromFile("resource/map.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isMovable(Player player, Direction d) {
		int x = display.player1.data.getX();
		int y = display.player1.data.getY();

		if(d == Direction.UP)
		{
			y -= BOX_WIDTH;
		}
		else if(d == Direction.DOWN)
		{
			y += BOX_WIDTH;
		}
		else if(d == Direction.LEFT)
		{
			x -= BOX_WIDTH;
		}
		else if(d == Direction.RIGHT)
		{
			x += BOX_WIDTH;
		}
		int mapi = x / BOX_WIDTH, mapj = y/ BOX_WIDTH;
		int mapValue = Character.getNumericValue(map[mapj][mapi]);
		
		if(mapValue == 2) {
			return false;
		}
		else return true;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			if(isMovable(player1, Direction.UP)) {
				display.player1.move(Direction.UP);
				display.repaint();
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_S) {
			if(isMovable(player1, Direction.DOWN)) {
				display.player1.move(Direction.DOWN);
				display.repaint();
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_D) {
			if(isMovable(player1, Direction.RIGHT)) {
				display.player1.move(Direction.RIGHT);
				display.repaint();
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_A) {
			if(isMovable(player1, Direction.LEFT)) {
				display.player1.move(Direction.LEFT);
				display.repaint();
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_Q) {
			System.exit(0);
		}

	}

	public void keyReleased(KeyEvent e) {
	}
	
//	private class playerAction implements KeyListener {
//		@Override
//		public void keyTyped(KeyEvent e) {
//	
//			if (e.getKeyCode() == KeyEvent.VK_W) {
//				
//				System.out.println("Right key typed");
//			}
//	
//		}
//	
//		@Override
//		public void keyPressed(KeyEvent e) {
//			if (e.getKeyCode() == KeyEvent.VK_W) {
//				System.out.println("Right key pressed");
//			}
//	
//		}
//	
//		@Override
//		public void keyReleased(KeyEvent e) {
//			if (e.getKeyCode() == KeyEvent.VK_W) {
//				System.out.println("Right key Released");
//			}
//		}
//	}
}
