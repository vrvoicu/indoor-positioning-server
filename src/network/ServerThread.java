/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import logger.Logger;

/**
 *
 * @author victor
 */
public class ServerThread extends Thread{
    
    private static ArrayList<ClientThread> clientThreads;
    private ClientThread clientThread;
    
    private ServerSocket serverSocket;
    private Socket socket;
    
    private int port = 4321;
    
    private boolean run = true;
    
    public ServerThread(){
        clientThreads = new ArrayList<>();
        openServerSocket();
    }
    
    public void run(){
        try{
            while(run){
                socket = serverSocket.accept();
                Logger.log("connectedToSocket");
                clientThread = new ClientThread(socket);
                clientThreads.add(clientThread);
                clientThread.start();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private boolean openServerSocket(){
        boolean successful = false;
        try{
            serverSocket = new ServerSocket(port);
            successful = true;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return successful;
    }

    public static ArrayList<ClientThread> getClientThreads() {
        return clientThreads;
    }
    
}
