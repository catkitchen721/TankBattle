package local;




import local.Status.Direction;

import java.io.Serializable;


public class Data implements Serializable{
	private static final long serialVersionUID = 6899636466339053193L;
	public static enum DataType{FIRST,ACCEPT,UPDATE,WAIT,START,REJECT,GAMEOVER};
	public int player_num;
	private DataType datatype;
	private int x;
	private int y;
	private boolean shoot;
	private Direction direction;
	private int hp;
	private boolean isSingle;
	private int roomNum;
	
	public Data()
	{
		this.datatype = DataType.START;
		this.x = 0;
		this.y = 0;
		this.shoot = false;
		this.direction = Status.Direction.UP;
		this.hp = 100;
	}
	public void setisSingle(boolean flag){
		this.isSingle = flag;
	}
	public boolean getisSingle(){
		return isSingle;
	}
	public void setRoomNum(int num){
		roomNum = num;
	}
	public int getRoomNum(){
		return roomNum;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public boolean getShoot(){
		return shoot;
	}
	public Direction getDirect(){
		return direction;
	}
	public int getHp(){
		return hp;
	}
	
	public DataType getDataType(){
		return datatype;
	}
	
	public void setDataType(DataType d){
		this.datatype = d;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void setShoot(boolean shoot){
		this.shoot = shoot;
	}
	public void setDirect(Direction direction){
		this.direction = direction;
	}
	public void setHp(int hp){
		this.hp = hp;
	}
	
	public void setAll(DataType d, int x, int y, boolean shoot, Direction direction, int hp)
	{
		this.datatype = d;
		this.x = x;
		this.y = y;
		this.shoot = shoot;
		this.direction = direction;
		this.hp = hp;
	}
}