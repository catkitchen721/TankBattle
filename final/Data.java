import java.io.*;
import java.util.*;


public class Data{
	public static enum DataType{ACCEPT,Update,GAMESTART,REJECT,GAMEOVER};
	private DataType datatype;
	private int x;
	private int y;
	private boolean shoot;
	private int direction;
	private int hp;
	
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
	public void setDataType(DataType d){
		this.datatype = d;
	}
}