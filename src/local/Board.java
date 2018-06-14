package local;

import javax.swing.JFrame;
import javax.swing.Timer;

import local.Status.Direction;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;


public class Board extends JFrame implements KeyListener, ActionListener {
	public static final int FRAME_WIDTH = 1200;
	public static final int FRAME_HEIGHT = 1000;
	public static final int BOX_WIDTH = 50;
	public int[][] map = null;

	public Display display;
	public Player player1;
	public Player player2;
	private Timer timer;
	
	private long keyInitialTime;
	private boolean allowKeypress;
	
	public boolean playerForwarding;
	public boolean playerBacking;
	public boolean playerRighting;
	public boolean playerLefting;

	public Board() {
		display = new Display();
		add(display);
		player1 = display.player1;
		player2 = display.player2;
		
		loadMap();
		
		pack();
		setResizable(false);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		addKeyListener(this);
		display.addKeyListener(this);
		
		timer = new Timer(30, this);
		timer.start();
		
		allowKeypress = false;
		keyInitialTime = System.currentTimeMillis();
		
		playerForwarding = false;
		playerBacking = false;
		playerRighting = false;
		playerLefting = false;
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
	
	public boolean isMovable(Player player, Player player2, Direction d) {
		int x = player1.data.getX();
		int y = player1.data.getY();

		if(d == Direction.UP)
		{
			y -= player.PER_MOVE;
		}
		else if(d == Direction.DOWN)
		{
			y += BOX_WIDTH;
		}
		else if(d == Direction.LEFT)
		{
			x -= player.PER_MOVE;
		}
		else if(d == Direction.RIGHT)
		{
			x += BOX_WIDTH;
		}
		System.out.println("X: " + x + "Y: " + y);
		int mapi = (x-10) / BOX_WIDTH;
		int mapj = (y-10) / BOX_WIDTH;
		int mapValue = Character.getNumericValue(map[mapj][mapi]);
		
		if(mapValue == 2) {
			return false;
		}
//		else if(player.getBound().intersects(player2.getBound())){
//			return false;
//		}
		else return true;
	}
	
	public boolean isMovable(Bullet bullet) { 
		int x = bullet.data.getX();
		int y = bullet.data.getY();
		Direction d = bullet.data.getDirect();

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
		int mapi = (x-10) / BOX_WIDTH;
		int mapj = (y-10) / BOX_WIDTH;
		int mapValue = Character.getNumericValue(map[mapj][mapi]);
		if(mapValue == 2) {
			return false;
		}
		else return true;
	}
	
	
	public void keyPressed(KeyEvent e) {
		
		allowKeypress = (System.currentTimeMillis() - keyInitialTime > 50)? true: false;
		keyInitialTime = System.currentTimeMillis();
		
		if(allowKeypress)
		{
			if (e.getKeyCode() == KeyEvent.VK_W) {
				if(isMovable(player1, player2, Direction.UP)) {
					playerForwarding = true;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_S) {
				if(isMovable(player1, player2, Direction.DOWN)) {
					playerBacking = true;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				if(isMovable(player1, player2, Direction.RIGHT)) {
					playerRighting = true;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_A) {
				if(isMovable(player1, player2, Direction.LEFT)) {
					playerLefting = true;
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_Q) {
				System.exit(0);
			}
			else if (e.getKeyCode() == KeyEvent.VK_J) {
				player1.shoot();
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			playerForwarding = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_S) {
			playerBacking = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_D) {
			playerRighting = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_A) {
			playerLefting = false;
		}
	}
	
    public void actionPerformed(ActionEvent e) {
    	bulletMove(player1, player2);
    	display.update(display.getGraphics());
    	if(playerForwarding && isMovable(player1, player2, Direction.UP)) playerMoving(Direction.UP);
    	if(playerBacking && isMovable(player1, player2, Direction.DOWN)) playerMoving(Direction.DOWN);
    	if(playerLefting && isMovable(player1, player2, Direction.LEFT)) playerMoving(Direction.LEFT);
    	if(playerRighting && isMovable(player1,player2,  Direction.RIGHT)) playerMoving(Direction.RIGHT);
    }
  
    public void bulletMove(Player player1, Player player2) {
		for(Bullet element : player1.getBullet()) {
			if(element.getBound().intersects(player2.getBound())) {
				System.out.println("got hit");
				player2.getHit();
				element.setVisible(false);
			}
			if(isMovable(element)) 
				element.move();
			else
				element.setVisible(false);
				
		}
	}
    
    public void playerMoving(Direction d)
    {
    	player1.move(d);
		display.update(display.getGraphics());
    }
	
}