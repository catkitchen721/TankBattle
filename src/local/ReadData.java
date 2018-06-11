package local;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

public class ReadData {

	public static int[][] readFromFile(String path) throws Exception
	{
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		
		int[][] map = new int[19][19];
		
		for(int i=0; i<19; i++)
		{
			for(int j=0; j<19; j++)
			{
				int temp = br.read();
				
				if(temp == '\r'){
					j--;
					continue;
				}
				else if(temp == '\n'){
					j--;
					continue;
				}
				else if(temp >= '0' && temp <='2'){
					map[i][j] = temp;
				}
			}
		}
		
		return map;
	}
	
}
