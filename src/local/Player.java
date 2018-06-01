package local;

import local.Status.Direction;

public class Player extends Tank{

	public Player()
	{
		super();
		System.out.println("One player created.");
	}
	
	@Override
	public void move(Direction d) {		//accept direction(decided by user keyboard input in display window)
		
		this.data.setDirect(d);
		
		if(d == Direction.UP)
		{
			this.data.setY(this.data.getY() + 1);
		}
		else if(d == Direction.DOWN)
		{
			this.data.setY(this.data.getY() - 1);
		}
		else if(d == Direction.LEFT)
		{
			this.data.setX(this.data.getX() - 1);
		}
		else if(d == Direction.RIGHT)
		{
			this.data.setX(this.data.getX() + 1);
		}
		else;
		
		this.update(this.data);
	}

	@Override
	public void shoot() {
		
	}

}
