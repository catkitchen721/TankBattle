package local;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {

	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 500;
	private static HomeScreen screen;
	
	public Menu() {
		setTitle("gameview");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		screen = new HomeScreen();
		setLayout(null);
		add(screen);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		Menu menu = new Menu();
		
		int current = screen.getPosition();
		
		while(current < 100) {
			current += 10;
			screen.setPosition(current);
			screen.repaint();
			
			try {
				Thread.sleep(50);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		menu.setVisible(true);
		menu.addMouseListener(new MouseClick());
		
		
	}

}
