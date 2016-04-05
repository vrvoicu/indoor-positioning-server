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
import ipsocketmessage.ImageReading;
import ipsocketmessage.ImageReadings;
import ipsocketmessage.OrientationReading;
import ipsocketmessage.PhoneDetails;
import ipsocketmessage.WifiReading;
import ipsocketmessage.WifiReadings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import persistance.ARMarkerReadingEntity;
import persistance.GSMReadingEntity;
import persistance.ImageEntity;
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
        
        //ipsocketMessages = new ArrayList<>();
        ipsocketMessages = new HashMap<>();
        
        new Thread(EntitiesController.this).start();
    }
    
    public synchronized static EntitiesController getInstance(){
        if(entitiesController == null)
            entitiesController = new EntitiesController();
        return entitiesController;
    }
    
    private boolean run = true;
    //private ArrayList<IPSocketMessage> ipsocketMessages;
    private HashMap<IPSocketMessage, ReadingEntity.ReadingType> ipsocketMessages;
    
    private Object lock = new Object();
    
    public void run(){
        while(run){
            
            synchronized(lock){
                
                while(ipsocketMessages.isEmpty() && run)
                    ThreadUtils.localWait(EntitiesController.this);
                
                if(!run)
                    break;
                
                synchronized(ipsocketMessages){
                    for(IPSocketMessage iPSocketMessage: ipsocketMessages.keySet())
                        persistSocketMessage(iPSocketMessage, ipsocketMessages.get(iPSocketMessage));
                    //for(IPSocketMessage iPSocketMessage: ipsocketMessages)
                    //    persistSocketMessage(iPSocketMessage);
                    ipsocketMessages.clear();
                }
            }
            
        }
    }
    
    private void persistSocketMessage(IPSocketMessage iPSocketMessage, ReadingEntity.ReadingType readingType){
        System.out.println("start");
        WifiReadings wifiReadings = (WifiReadings)iPSocketMessage.getObjectFromMessage(WifiReadings.class);
        OrientationReading orientationReading = (OrientationReading)iPSocketMessage.getObjectFromMessage(OrientationReading.class);
        ARMarkerReadings arMarkerReadings = (ARMarkerReadings)iPSocketMessage.getObjectFromMessage(ARMarkerReadings.class);
        GSMReading gsmReading = (GSMReading)iPSocketMessage.getObjectFromMessage(GSMReading.class);
        PhoneDetails phoneDetails = (PhoneDetails) iPSocketMessage.getObjectFromMessage(PhoneDetails.class);
        
        ImageReadings imageReadings = (ImageReadings) iPSocketMessage.getObjectFromMessage(ImageReadings.class);


        ReadingEntity readingEntity = new ReadingEntity();
        GSMReadingEntity gsmReadingEntity = null;
        OrientationReadingEntity orientationReadingEntity = null;
        if(gsmReading != null)
            gsmReadingEntity = new GSMReadingEntity(gsmReading);
        if(orientationReading == null)
            orientationReadingEntity = new OrientationReadingEntity(orientationReading);
        PhoneDetailsEntity phoneDetailsEntity = getPhoneDetailsEntity(phoneDetails.getImei());
        
        if(phoneDetailsEntity == null)
            phoneDetailsEntity = new PhoneDetailsEntity(phoneDetails);

        ArrayList<WifiReadingEntity> wifiReadingEntities = new ArrayList<>();
        ArrayList<ARMarkerReadingEntity> arMarkerReadingsEntities = new ArrayList<>();

        ARMarkerReadingEntity arMarkerReadingEntity;
        WifiReadingEntity wifiReadingEntity;

        if(wifiReadings != null)
            for(WifiReading wifiReading : wifiReadings.getWifiReadings()){
                wifiReadingEntity = new WifiReadingEntity(wifiReading);
                wifiReadingEntities.add(wifiReadingEntity);
                persist(wifiReadingEntity);
            }

        
        if(arMarkerReadings != null){
            ImageReading imageReading;
            ImageEntity imageEntity;
            for(int i=0; i< arMarkerReadings.getArMarkerReadings().size(); i++){
                 arMarkerReadingEntity = new ARMarkerReadingEntity(arMarkerReadings.getArMarkerReadings().get(i));
                 imageReading = imageReadings.getImages().get(i);
                 imageEntity = new ImageEntity(imageReading.getImage(), imageReading.getSize()[0], imageReading.getSize()[1]);
                 persist(arMarkerReadingEntity);
                 arMarkerReadingEntity.setImage(imageEntity);
                 persist(imageEntity);
                 arMarkerReadingsEntities.add(arMarkerReadingEntity);
            }
        }

        if(orientationReadingEntity != null)
            persist(orientationReadingEntity);
        if(gsmReadingEntity != null)
            persist(gsmReadingEntity);
        persist(phoneDetailsEntity);

        readingEntity.setWifiReadings(wifiReadingEntities);
        if(gsmReadingEntity != null)
            readingEntity.setGsmReadingEntity(gsmReadingEntity);
        readingEntity.setOrientationReadingEntity(orientationReadingEntity);
        readingEntity.setArMarkerReadingEntitys(arMarkerReadingsEntities);
        readingEntity.setPhoneDetailsEntity(phoneDetailsEntity);

        persist(readingEntity);
        System.out.println("end");
    }
    
    public PhoneDetailsEntity getPhoneDetailsEntity(String imei){
        Query query = em.createNamedQuery("PhoneDetailsEntity.findByImei");
        query.setParameter("imei", imei);
        List<PhoneDetailsEntity> phoneDetailsEntity = query.getResultList();
        if(phoneDetailsEntity.isEmpty())
            return null;
        return phoneDetailsEntity.get(0);
    }
    
    public void addSocketMessage(IPSocketMessage iPSocketMessage, ReadingEntity.ReadingType readingType){
        synchronized(ipsocketMessages){
            ipsocketMessages.put(iPSocketMessage, readingType);
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
