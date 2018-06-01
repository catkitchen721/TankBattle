package local;

import local.Data.DataType;
import local.Status.Direction;

public abstract class Tank {
	
	protected Data data = null;
	private Gamer gamer = null;
	
	public Tank()
	{
		data = new Data();
		gamer = new Gamer();
	}
	
	public void update(Data newData)
	{
		this.data.setAll(
				DataType.Update, 
				newData.getX(), 
				newData.getY(), 
				newData.getShoot(), 
				newData.getDirect(), 
				newData.getHp()
				);
	}
	
	public abstract void move(Direction d);
	public abstract void shoot();
	
}
