package local;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class MouseClick extends MouseAdapter{
	
	public void mousePressed(MouseEvent e) {
		System.out.println(e.getButton());
		if(e.getButton() == MouseEvent.BUTTON1) {			
			System.out.println(e.getID());
		}
	}
}
