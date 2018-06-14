package local;

import java.io.*;
import java.util.*;

import local.Status.Direction;

public class Data{
	public static enum DataType{ACCEPT,Update,GAMESTART,REJECT,GAMEOVER};
	public int player_num;
	private DataType datatype;
	private int x;
	private int y;
	private boolean isShoot;
	private Direction direction;
	private int hp;
	
	public Data()
	{
		this.datatype = DataType.GAMESTART;
		this.x = 0;
		this.y = 0;
		this.isShoot = false;
		this.direction = Status.Direction.UP;
		this.hp = 100;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public boolean getShoot(){
		return isShoot;
	}
	public Direction getDirect(){
		return direction;
	}
	public int getHp(){
		return hp;
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
		this.isShoot = shoot;
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
		this.isShoot = shoot;
		this.direction = direction;
		this.hp = hp;
	}
}