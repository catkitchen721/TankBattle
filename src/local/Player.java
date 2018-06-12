package local;

import local.Status.Direction;

public class Player extends Tank{
	public static final int PER_MOVE = 50;
	
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
			this.data.setY(this.data.getY() - PER_MOVE);
			System.out.println(this.data.getY());
		}
		else if(d == Direction.DOWN)
		{
			this.data.setY(this.data.getY() + PER_MOVE);
		}
		else if(d == Direction.LEFT)
		{
			this.data.setX(this.data.getX() - PER_MOVE);
		}
		else if(d == Direction.RIGHT)
		{
			this.data.setX(this.data.getX() + PER_MOVE);
		}
		else;
		
		this.update(this.data);
	}

	@Override
	public void shoot() {
		
	}

}
