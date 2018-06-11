package Player;
import java.io.Serializable;
import java.io.*;
import java.util.*;


public class Data implements Serializable{
	private static final long serialVersionUID = 2218706848770106240L;
	public static enum DataType{FIRST,ACCEPT,UPDATE,WAIT,START,REJECT,GAMEOVER};
	private DataType datatype;
	public int x;
	private int y;
	private boolean shoot;
	private int direction;
	private int hp;
	private boolean isSingle;
	private int roomNum;
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
	public int getDirect(){
		return direction;
	}
	public int getHp(){
		return hp;
	}
	public void setX(int X){
		this.x = X;
	}
	public void setY(int Y){
		this.y = Y;
	}
	public void setDataType(DataType d){
		this.datatype = d;
	}
	public DataType getDataType(){
		return datatype;
	}
}