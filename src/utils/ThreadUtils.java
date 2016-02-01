package com.vvdev.indoorpositioning.utils;

/**
 * Created by victor-vm on 7/14/2015.
 */
public class ThreadUtils {

    public static void localSleep(long millis){
        try{
            Thread.sleep(millis);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void localWait(Runnable runnable){
        try {
            synchronized (runnable) {
                runnable.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void localNotifyAll(Runnable runnable){
        synchronized (runnable){
            runnable.notifyAll();
        }
    }

    public static void localNotify(Runnable runnable){
        synchronized (runnable){
            runnable.notifyAll();
        }
    }

}
