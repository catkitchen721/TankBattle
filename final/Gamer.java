import java.net.*;
import java.io.*;
import java.util.*;

//client network 
public class Gamer{
    Socket client;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    String HOST = "127.0.0.1";
    static int PORT = 3000;
    public static void main(String[] args){
        Gamer g = new Gamer();
    }


    public Gamer(){
        int t = 1;
        try{
            client = new Socket(host,port);

            out = new BufferedOutputStream(client.getOutputStream());
            while(true){
                if( t == 1){
                    t = 0;
                    String n = "test";
                    out.write(n.getBytes());
                }



            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
