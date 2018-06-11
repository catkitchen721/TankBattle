package local;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Display extends Canvas{
	
	public static final int FRAME_WIDTH = 1200;
	public static final int FRAME_HEIGHT = 1000;
	public static final int BOX_WIDTH = 50;
	
	public int[][] map = null;

	public Display()
	{	
		JFrame frame = new JFrame();
		
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(10, 10, 950, 950);
		
		try 
		{
			map = ReadData.readFromFile("resource/map.txt");
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int j=0; j<19; j++)
		{
			for(int i=0; i<19; i++)
			{
				if(map[j][i] == '0')
				{
					g.setColor(new Color(0x22, 0x77, 0x00));
				}
				else if(map[j][i] == '1')
				{
					g.setColor(new Color(0xff, 0x8c, 0x00));
				}
				else if(map[j][i] == '2')
				{
					g.setColor(new Color(0x44, 0x44, 0x44));
				}
				else;
				
				g.fillRect(10 + i * BOX_WIDTH, 10 + j * BOX_WIDTH, BOX_WIDTH, BOX_WIDTH);
			}
		}
	}
	
	public void addTank(Tank tank)
	{
		
	}

}
