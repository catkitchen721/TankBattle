package local;
import java.net.*;
import java.io.*;
import java.util.*;

public class GameServer extends Thread{
    
    private ServerSocket serverSock;
    private static int MAX_PLAYER = 10;
    private static int PORT = 3000;
    private Socket[] cli_fd = new Socket[10];
    private ArrayList<clientInfo> clientList;
    int id=0;
    public int roomNum ;
    
    public static void main(String[] args){
        GameServer t = new GameServer();
    }
    public GameServer(){
        roomNum = 1;

        this.start();
    }
    //server listen thread
    @Override
    public void run(){
        int count = 0;
        int index = 0;
        boolean isSingle = false;
        try{
            serverSock = new ServerSocket();
            System.out.println("Server start");
            serverSock.setReuseAddress(true);
            serverSock.bind(new InetSocketAddress(PORT));
            clientList = new ArrayList<clientInfo>();
            while(true){
                //accept the client socket
                Data recieveData = new Data();
                Socket client = serverSock.accept();
                Data.DataType dt = Data.DataType.FIRST;
                try{
                    ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                    recieveData = (Data)ois.readObject();
                    System.out.println("Recieve first data");
                    isSingle = recieveData.getisSingle();
                    dt = recieveData.getDataType();
                    roomNum = recieveData.getRoomNum();
                }catch(Exception e){
                    e.printStackTrace();
                }


                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                //ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                index++;
                try{
                    Data temp = new Data();
                    temp.setDataType(Data.DataType.WAIT);
                    oos.writeObject(temp);
                }catch(Exception e){
                    System.out.println("Recieve first data error");
                    oos.close();
                }
                System.out.println("break point1");
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
                System.out.println("break point2");

                synchronized(clientList){
                    clientList.add(new clientInfo(client,oos,roomNum));
                }
                count++;
                if(dt == Data.DataType.FIRST){
                    System.out.println("break point3");
                    if( isSingle ){

                        GameRoom new_room = new GameRoom(clientList.get(index));
                        new_room.start();
                        index++;
                        count--;
                    }
                    else if(count == 2){
                        count = 0;
                        // find the same room number player;
                        /*
                        clientInfo p1, p2;
                        int temp_room;
                        boolean find = false;
                        for( int i=0 ; i<clientList.size(); i++){
                            if(!clientList.get(i).isRun && !find){
                                temp_room = clientList.roomNum;
                                p1 = clientList.get(i);
                                find = true;
                            }
                            else if (find == true){
                                if( !clientList.get(i).isRun() ){
                                    p2 = clientList.get(i);
                                }
                            }
                                
                        }*/
                        System.out.println("break point5");
                        System.out.println("count " + count);
                        Data temp = new Data();
                        temp.setDataType(Data.DataType.START);

                        clientList.get(index-2).getOos().writeObject(temp);
                        clientList.get(index-1).getOos().writeObject(temp);
                        GameRoom new_room = new GameRoom(clientList.get(index-2),clientList.get(index-1));
                        System.out.println("GameRoom start");
                    }
                }
                System.out.println("break point4 count " + count);
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
    public boolean running = false;
    private ObjectOutputStream oos;
    public int roomNum;
    public clientInfo (Socket client, ObjectOutputStream os,int num){
        this.client = client;
        this.oos = os;
        this.roomNum = num;    
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
                                //
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
                this.player1.setRun(true);
                p1_readThread = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try{
                            System.out.println("GameRoom Thread");
                            
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
                                synchronized(p2Data){
                                    while(p2Data.isEmpty()){
                                        p2Data.wait();
                                    }
                                    data = p2Data.poll();
                                }
                                player1.getOos().reset();
                                //
                                player1.getOos().writeUnshared(data);
                                
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                });
                p1_procThread.start();
                this.player2.setRun(true);
                p2_readThread = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        try{
                            //System.out.println("GameRoom Thread");
                            
                            while(true){
                                Data recieveData = new Data();
                                ObjectInputStream ois = new ObjectInputStream(player2.getSock().getInputStream());
                                recieveData = (Data)ois.readUnshared();
                                System.out.println("Get Data " + recieveData.x);
                                System.out.println("Get Data " + recieveData.getDataType());
                                synchronized(p1Data){
                                    p2Data.add(recieveData);
                                    if(p2Data.size() == 1){
                                        p2Data.notify();
                                        System.out.println("notify");
                                    }
                                }

                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                p2_readThread.start();
                p2_procThread = new Thread(new Runnable(){
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
                                player2.getOos().reset();
                                //
                                player2.getOos().writeUnshared(data);
                                
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                });
                p2_procThread.start();


            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void update(Date date, Data.DataType type){

    }
}