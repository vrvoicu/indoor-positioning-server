/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import com.vvdev.indoorpositioning.utils.ThreadUtils;

/**
 *
 * @author victor
 */
public class SocketMessageHandlerImpl extends Thread implements SocketMessageHandler{
    
    private boolean run = true;
    private Object lock = new Object();
    private Object socketMessage;
    
    public void run(){
        while(socketMessage == null)
            ThreadUtils.localWait(SocketMessageHandlerImpl.this);
        socketMessage = null;
    }

    @Override
    public void handleMessage(Object socketMessage) {
        this.socketMessage = socketMessage;
    }
    
    
    
}
