package local;

import local.Status.Direction;
import java.util.List;
import java.util.ArrayList;


public class Player extends Tank{
	
    private List<Bullet> bullets;
    private Gamer p1;
	public Player(int num)
	{
		super();
		data.player_num = num;
		System.out.println("One player created.");
		
		bullets = new ArrayList<>();
	}
	
	@Override
	public void move(Direction d) {		//accept direction(decided by user keyboard input in display window)
		
		this.data.setDirect(d);
		
		if(d == Direction.UP)
		{
			this.data.setY(this.data.getY() - PER_MOVE);
			this.data.setDirect(Direction.UP);
		}
		else if(d == Direction.DOWN)
		{
			this.data.setY(this.data.getY() + PER_MOVE);
			this.data.setDirect(Direction.DOWN);
		}
		else if(d == Direction.LEFT)
		{
			this.data.setX(this.data.getX() - PER_MOVE);
			this.data.setDirect(Direction.LEFT);
		}
		else if(d == Direction.RIGHT)
		{
			this.data.setX(this.data.getX() + PER_MOVE);
			this.data.setDirect(Direction.RIGHT);
		}
		else;
		this.update(this.data);
	}
	
	
	public List<Bullet>getBullet() {
		return bullets;
	}
	
	@Override
	public void shoot() {
		System.out.println("shoooo");
		if(data.getDirect() == Direction.UP)
			bullets.add(new Bullet(data.getX(), data.getY() - PER_MOVE, this));			
		else if(data.getDirect() == Direction.DOWN)
			bullets.add(new Bullet(data.getX(), data.getY() + PER_MOVE, this));
		else if(data.getDirect() == Direction.LEFT)
			bullets.add(new Bullet(data.getX() - PER_MOVE, data.getY(), this));
		else if(data.getDirect() == Direction.RIGHT)
			bullets.add(new Bullet(data.getX() + PER_MOVE, data.getY(), this));			
	}
	
	public void getHit() {
		data.setHp(data.getHp() - 5);
		System.out.println("Hp: " + data.getHp());
	}

}
