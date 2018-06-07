
import java.net.*;
import java.io.*;
import java.util.*;

public class GameServer extends Thread{
    
    ServerSocket serverSock;
    static int MAX_PLAYER = 10;
    static int PORT = 3000;
    Socket[] cli_fd = new Socket[10];
    ArrayList<clientInfo> clientList;
    BufferedInputStream netIn;
    BufferedOutputStream netOut;
    int id=0;
    public static void main(String[] args){
        GameServer t = new GameServer();
    }
    public GameServer(){
        this.start();
    }
    //server listen thread
    @Override
    public void run(){
        int count = 0;
        int index = 0;
        try{
            serverSock = new ServerSocket();
            System.out.println("Server start");
            serverSock.setReuseAddress(true);
            serverSock.bind(new InetSocketAddress(PORT));
            clientList = new ArrayList<clientInfo>();
            while(true){
                //accept the client socket
                Socket client = serverSock.accept();
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                Data responseData = new Data();
                count++;
                // exceed the max player
                if(clientList.size()+1 >= MAX_PLAYER){
                    responseData.setDataType(Data.DataType.REJECT);
                    oos.reset();
                    oos.writeUnshared(responseData);
                    continue;
                }

                synchronized(clientList){
                    clientList.add(new clientInfo(client, oos));
                }
                // add two player into a game room
                if( count == 2){
                    count = 0;
                    GameRoom new_room = new GameRoom(clientList.get(index),clientList.get(index+1));
                    index += 2;
                }

            }           
        }catch(IOException e1){
            e1.printStackTrace();
            System.out.println("Server is closed");

        }
        ServerisOn = false;
    }
    public void closeAllSocke(){
        synchronized (clientList) {
            int size = clientList.size();
            for(int i=0; i<size; i++){
                try {
                    clientList.get(0).client.close();
                } catch (IOException e) {
                // already closed
                }
                clientList.remove(0);
            }
        }
        try {
            serverSock.close();
        } catch (IOException e) {
            // already closed
        } // invoke server socket exception in listenThread
    }
}

class clientInfo{
    Socket client;
    int clientID = -1;
    boolean running = true;
    ObjectOutputStream oos;
    public clientInfo (Socket client, ObjectOutputStream os){
        this.client = client;
        this.oos = os;
    }
    public Socket getSock(){
        return client;
    }
    public void setSock(Socket sc){
        this.client = sc;
    }
    public ObjectOutputStream getOos(){
        return oos;
    }
    public void setOos(ObjectOutputStream os){
        this.oos = os;
    }
    
}
//add two player to a Game Room
class GameRoom extends Thread{
    clientInfo player1,player2;
    Thread p1_readThread,p2_readThread;
    Thread p1_procThread,p2_procThread;
    //Tand AI update

    public GameRoom(clientInfo p1 , clientInfo p2){
        this.player1 = p1;
        this.player2 = p2;
    }    

        //Recieve Client data
    public void run(){
 
    }
    public void update(Date date, Data.DataType type){

    }
}