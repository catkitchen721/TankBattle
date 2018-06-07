import java.net.*;
import java.io.*;
import java.util.*;

//client network 
public class Gamer extends Thread{
    private Socket client;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean ClientClosed;
    private String HOST = "127.0.0.1";
    private static int PORT = 3001;
    public Queue<Data> dataQ;

    public static void main(String[] args){
        Gamer g = new Gamer();
    }
    public Gamer(){
        ClientClosed = false;
        dataQ = new LinkedList<>();
        
    }
    @Override
    public void run(){
        if(ClientClosed == true)
            return;

        System.out.println("Client Start");
        try{
            
            client = new Socket(HOST,PORT);
            
            ois = new ObjectInputStream(client.getInputStream());
            Data msg = new Data();
            Thread OutputThread = new Thread(new Runnable(){
                @Override
                public void run(){
                    Data temp;
                    try{
                        //oos = new ObjectOutputStream(client.getOutputStream());
                        synchronized( dataQ ){
                            while(dataQ.isEmpty()){
                                dataQ.wait();
                            }
                            temp = dataQ.poll();
                        }
                    }catch(Exception e){
                        System.out.println("Test");
                    }
                    //send data
                }
            });
            //get Server data
            try{
                msg = (Data)ois.readUnshared();
            }catch(Exception e){
                e.printStackTrace();
            }
            //log
            int x = msg.getX();
            System.out.println(x);
            //
            updateFrame(msg);

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
    public void updateData(Data data){

    }
    // update frame and tank data
    private void updateFrame(Data data){

    }
}
