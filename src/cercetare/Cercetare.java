/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cercetare;

import controllers.EntitiesController;
import network.ServerThread;

/**
 *
 * @author victor
 */
public class Cercetare {

    static ServerThread serverThread;
    
    public static void main(String[] args) {
        serverThread = new ServerThread();
        serverThread.start();
    }
    
}
