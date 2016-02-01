/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.vvdev.indoorpositioning.utils.ThreadUtils;
import ipsocketmessage.ARMarkerReading;
import ipsocketmessage.ARMarkerReadings;
import ipsocketmessage.GSMReading;
import ipsocketmessage.IPSocketMessage;
import ipsocketmessage.OrientationReading;
import ipsocketmessage.PhoneDetails;
import ipsocketmessage.WifiReading;
import ipsocketmessage.WifiReadings;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistance.ARMarkerReadingEntity;
import persistance.GSMReadingEntity;
import persistance.OrientationReadingEntity;
import persistance.PhoneDetailsEntity;
import persistance.ReadingEntity;
import persistance.WifiReadingEntity;

/**
 *
 * @author victor
 */
public class EntitiesController implements Runnable{
    private static EntitiesController entitiesController;
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private EntitiesController(){
        emf = Persistence.createEntityManagerFactory("CercetarePU");
        em = emf.createEntityManager();
        
        ipsocketMessages = new ArrayList<>();
        
        new Thread(EntitiesController.this).start();
    }
    
    public synchronized static EntitiesController getInstance(){
        if(entitiesController == null)
            entitiesController = new EntitiesController();
        return entitiesController;
    }
    
    private boolean run = true;
    private ArrayList<IPSocketMessage> ipsocketMessages;
    
    private Object lock = new Object();
    
    public void run(){
        while(run){
            
            synchronized(lock){
                
                while(ipsocketMessages.isEmpty() && run)
                    ThreadUtils.localWait(EntitiesController.this);
                
                if(!run)
                    break;
                
                synchronized(ipsocketMessages){
                    for(IPSocketMessage iPSocketMessage: ipsocketMessages)
                        persistSocketMessage(iPSocketMessage);
                    ipsocketMessages.clear();
                }
            }
            
        }
    }
    
    private void persistSocketMessage(IPSocketMessage iPSocketMessage){
        System.out.println("start");
        WifiReadings wifiReadings = (WifiReadings)iPSocketMessage.getObjectFromMessage(WifiReadings.class);
        OrientationReading orientationReading = (OrientationReading)iPSocketMessage.getObjectFromMessage(OrientationReading.class);
        ARMarkerReadings arMarkerReadings = (ARMarkerReadings)iPSocketMessage.getObjectFromMessage(ARMarkerReadings.class);
        GSMReading gsmReading = (GSMReading)iPSocketMessage.getObjectFromMessage(GSMReading.class);
        PhoneDetails phoneDetails = (PhoneDetails) iPSocketMessage.getObjectFromMessage(PhoneDetails.class);

        ReadingEntity readingEntity = new ReadingEntity();
        GSMReadingEntity gsmReadingEntity = new GSMReadingEntity(gsmReading);
        OrientationReadingEntity orientationReadingEntity = new OrientationReadingEntity(orientationReading);
        PhoneDetailsEntity phoneDetailsEntity = new PhoneDetailsEntity(phoneDetails);

        ArrayList<WifiReadingEntity> wifiReadingEntities = new ArrayList<>();
        ArrayList<ARMarkerReadingEntity> arMarkerReadingsEntities = new ArrayList<>();

        ARMarkerReadingEntity arMarkerReadingEntity;
        WifiReadingEntity wifiReadingEntity;

        for(WifiReading wifiReading : wifiReadings.getWifiReadings()){
            wifiReadingEntity = new WifiReadingEntity(wifiReading);
            wifiReadingEntities.add(wifiReadingEntity);
            persist(wifiReadingEntity);
        }

        for(ARMarkerReading arMarkerReading : arMarkerReadings.getArMarkerReadings()){
            arMarkerReadingEntity = new ARMarkerReadingEntity(arMarkerReading);
            arMarkerReadingsEntities.add(arMarkerReadingEntity);
            persist(arMarkerReadingEntity);
        }

        persist(orientationReadingEntity);
        persist(gsmReadingEntity);
        persist(phoneDetailsEntity);

        readingEntity.setWifiReadings(wifiReadingEntities);
        readingEntity.setGsmReadingEntity(gsmReadingEntity);
        readingEntity.setOrientationReadingEntity(orientationReadingEntity);
        readingEntity.setArMarkerReadingEntitys(arMarkerReadingsEntities);
        readingEntity.setPhoneDetailsEntity(phoneDetailsEntity);

        persist(readingEntity);
        System.out.println("end");
    }
    
    public void addSocketMessage(IPSocketMessage iPSocketMessage){
        synchronized(ipsocketMessages){
            ipsocketMessages.add(iPSocketMessage);
            ThreadUtils.localNotify(EntitiesController.this);
        }
    }

    public void persist(Object object) {
        
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            //em.close();
        }
    }
    
}
