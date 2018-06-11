package Player;
import java.net.*;
import java.io.*;
import java.util.*;

public class GameServer extends Thread{
    
    ServerSocket serverSock;
    static int MAX_PLAYER = 10;
    static int PORT = 3000;
    Socket[] cli_fd = new Socket[10];
    ArrayList<clientInfo> clientList;
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
                Data recieveData;
                Socket client = serverSock.accept();
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                //ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                /*
                try{
                    recieveData = (Data)ois.readUnshared();
                    //System.out.println(recieveData.getisSingle());
                    if(recieveData.getisSingle() == true){
                        GameRoom new_room = new GameRoom(clientList.get(index));
                        new_room.start();
                    }
                    else if(recieveData.getisSingle() == false){

                    }
                }catch(Exception e){
                    System.out.println("Recieve first data error");
                    ois.close();
                }*/
                count++;
                // exceed the max player
                if(clientList.size()+1 >= MAX_PLAYER){
                    oos = new ObjectOutputStream(client.getOutputStream());
                    Data responseData = new Data();
                    responseData.setDataType(Data.DataType.REJECT);
                    oos.reset();
                    oos.writeUnshared(responseData);
                    oos.flush();
                    continue;
                }

                synchronized(clientList){
                    clientList.add(new clientInfo(client,oos));
                }
                GameRoom new_room = new GameRoom(clientList.get(index));
                new_room.start();
                index++;
                // add two player into a game room
                /*
                if( count == 2){
                    count = 0;
                    GameRoom new_room = new GameRoom(clientList.get(index),clientList.get(index+1));
                    //index += 2;
                }
                else if (count == 1){
                    GameRoom new_room = new GameRoom(clientList.get(index));
                    //index++;
                }
                */
            }           
        }catch(IOException e1){
            e1.printStackTrace();
            System.out.println("Server is closed");

        }
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
    private ObjectOutputStream oos;
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
    public void setRun(boolean flag){
        this.running = flag;
    }
    public boolean isRun(){
        return running;
    }
}
//add two player to a Game Room
class GameRoom extends Thread{
    private boolean isSingle;
    clientInfo player1,player2;
    Thread p1_readThread,p2_readThread;
    Thread p1_procThread,p2_procThread;
    //Tand AI update
    Queue<Data> p1Data;
    Queue<Data> p2Data;
    public GameRoom(clientInfo p1 , clientInfo p2){
        this.isSingle = false;
        this.player1 = p1;
        this.player2 = p2;
    }    
    //Overload constructor
    //for single player
    public GameRoom(clientInfo p1){
        this.isSingle = true;
        this.player1 = p1;
        p1Data = new LinkedList<>();
        
    }
    //Recieve Client data
    public void run(){

        try{
            if(isSingle){
                this.player1.setRun(true);
                p1_readThread = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try{
                            System.out.println("GameRoom Thread");
                            
                            
                            //Thread.sleep(100);
                            while(true){
                                Data recieveData = new Data();
                                ObjectInputStream ois = new ObjectInputStream(player1.getSock().getInputStream());
                                recieveData = (Data)ois.readUnshared();
                                System.out.println("Get Data " + recieveData.x);
                                System.out.println("Get Data " + recieveData.getDataType());
                                synchronized(p1Data){
                                    p1Data.add(recieveData);
                                    if(p1Data.size() == 1){
                                        p1Data.notify();
                                        System.out.println("notify");
                                    }
                                }

                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                p1_readThread.start();
                p1_procThread = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try{
                            while(true){
                                Data data;
                                synchronized(p1Data){
                                    while(p1Data.isEmpty()){
                                        p1Data.wait();
                                    }
                                    data = p1Data.poll();
                                }
                                player1.getOos().reset();
                                player1.getOos().writeUnshared(data);
                                
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                });
                p1_procThread.start();

            }
            else{

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void update(Date date, Data.DataType type){

    }
}