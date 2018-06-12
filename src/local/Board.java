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
	public Display display;
	public Player player1;
	public Player player2;

	public Board() {
		display = new Display();
		add(display);
		
		pack();
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		addKeyListener(this);
		
	}
	
	public void addTank() {
		
	}
	public void keyTyped(KeyEvent e) {

	}
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			System.out.println("Right key pressed");
			display.player1.move(Direction.UP);
			display.repaint();
		}
		else if (e.getKeyCode() == KeyEvent.VK_S) {
			display.player1.move(Direction.DOWN);
			display.repaint();
		}
		else if (e.getKeyCode() == KeyEvent.VK_D) {
			display.player1.move(Direction.RIGHT);
			display.repaint();
		}
		else if (e.getKeyCode() == KeyEvent.VK_A) {
			display.player1.move(Direction.LEFT);
			display.repaint();
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
		}
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
