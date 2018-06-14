package local;
import java.net.*;
import java.io.*;
import java.util.*;

//client network 
public class Gamer extends Thread{
    private Socket client;
    private ObjectInputStream ois;
    //private ObjectOutputStream oos;
    private boolean ClientClosed;
    private String HOST = "192.168.43.125";
    private static int PORT = 3000;
    private Queue<Data> dataQ;
    private Queue<Data> recieveData;
    private boolean singleGame;
    private int roomNum;
    public boolean isWait;
    
    public Gamer(){
        ClientClosed = false;
        dataQ = new LinkedList<Data>();
        recieveData = new LinkedList<Data>();
        
    }
    @Override
    public void run(){
        if(ClientClosed == true)
            return;

        System.out.println("Client Start");
        try{
            
            client = new Socket(HOST,PORT);
            System.out.println("Connect");

            //ois = new ObjectInputStream(client.getInputStream());
            Data msg = new Data();
            try{

                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                
                Data first = new Data();
                first.setDataType(Data.DataType.FIRST);
                first.setisSingle(singleGame);
                first.setRoomNum(roomNum);
                System.out.println("test");
                oos.reset();
                oos.writeObject(first);
                

            }catch(Exception e){
                e.printStackTrace();
            }

            try{
                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                Data temp;
                temp = (Data)ois.readUnshared();
                if(temp.getDataType() == Data.DataType.WAIT){
                	this.isWait = true;
                    while(true){
                        temp = (Data)ois.readUnshared();
                        if( temp.getDataType() == Data.DataType.START)
                            break;
                    }
                    this.isWait = false;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            // continue to ouput the data if dataQ is not empty
            Thread outputThread = new Thread(new Runnable(){
                @Override
                public void run(){
                    
                    try{
                        while(true){
                     
                            Data temp ;
                            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                            
                            //oos.writeUnshared(first);
                            synchronized( dataQ ){
                                while(dataQ.isEmpty()){
                                    dataQ.wait();
                                }
                                System.out.println("dataQ is not empty");
                                temp = dataQ.poll();
                            }
                            System.out.println(temp.getX());
                            //sending data
                            oos.reset();
                            oos.writeUnshared(temp);
                            
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            outputThread.start();
            //get Server data
            try{

                while(true){

                    msg = (Data)ois.readUnshared();
                    synchronized(recieveData){
                        recieveData.add(msg);
                            if(recieveData.size() == 1){
                                recieveData.notify();
                                System.out.println("notify");
                            }
                    }
                    //log
                    int x = msg.getX();
                    System.out.println("get " + x);
                    //update the game frame
                    //
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            

        }catch(UnknownHostException e){
            showErrorMessage("Something is wrong", "Server does not response");
        }catch(SocketTimeoutException e){
            showErrorMessage("Something is wrong", "Time Out Error");
        }catch(IOException e){
            e.printStackTrace();
        }
    


    }

    private void showErrorMessage(String title, String msg){
        //JOptionPane.showMessageDialog(this, msg, title, JOptionPane.WARNING_MESSAGE);

        System.out.println("msg");
    }
    //update frame and tank data;
    //use for game update player data for sending to server
    public void updateData(Data data){
        
        System.out.println("updateData "+data.getX());
        synchronized(dataQ){
            dataQ.add(data);
            if(dataQ.size() == 1){
                dataQ.notify();
                System.out.println("Notify thread");
            }
        }
    }

    // update frame and tank data
    public Data updateFrame(){
        Data temp;
        if(recieveData.isEmpty())
            temp = null;
        else{
            temp = dataQ.poll();
        }
        return temp;
    }
    public void waitRecieveData() throws Exception{
        recieveData.wait();
    }
    public boolean p2DataisEmpty() {
    	return recieveData.isEmpty();
    }
    public void setSingleGame(boolean flag){
        this.singleGame = flag;
    }
    public void setRoomNum(int num){
        this.roomNum = num;
    }
}
