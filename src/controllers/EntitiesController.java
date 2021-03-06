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
import ipsocketmessage.IPRequestType;
import ipsocketmessage.IPSocketMessage;
import ipsocketmessage.ImageReading;
import ipsocketmessage.ImageReadings;
import ipsocketmessage.OrientationReading;
import ipsocketmessage.PhoneDetails;
import ipsocketmessage.Reading;
import ipsocketmessage.Readings;
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
import persistance.ReadingsEntity;
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
    private HashMap<IPSocketMessage, ReadingsEntity.ReadingType> ipsocketMessages;
    
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
//                        persistSocketMessage(iPSocketMessage, ipsocketMessages.get(iPSocketMessage));
                        persistSocketMessage(iPSocketMessage);
                    //for(IPSocketMessage iPSocketMessage: ipsocketMessages)
                    //    persistSocketMessage(iPSocketMessage);
                    ipsocketMessages.clear();
                }
            }
            
        }
    }
    
    public ArrayList<WifiReadingEntity> getWifiReadingEntitiesByBSSIDs(ArrayList<String> bssids){
        Query query = em.createNamedQuery("WifiReadingEntity.findAllWhereIn");
        query.setParameter("bssids", bssids);
        List<WifiReadingEntity> wifiReadingEntitys = query.getResultList();
        return new ArrayList<WifiReadingEntity>(wifiReadingEntitys);
    }
    
    private void persistSocketMessage(IPSocketMessage ipSocketMessage){
        
        PhoneDetails phoneDetails = (PhoneDetails) ipSocketMessage.getObjectFromMessage(PhoneDetails.class);
        Readings readings = (Readings)ipSocketMessage.getObjectFromMessage(Readings.class);
        
        if(readings == null)
            return;
        
        ReadingsEntity readingsEntity = new ReadingsEntity();
        
        PhoneDetailsEntity phoneDetailsEntity;
        if(phoneDetails != null){
//            phoneDetailsEntity= new PhoneDetailsEntity(phoneDetails);
//            readingsEntity.setPhoneDetailsEntity(phoneDetailsEntity);
//            persist(phoneDetailsEntity);
        }
       
        ReadingEntity readingEntity;
        for(Reading reading: readings.getReadings()){
            readingEntity = new ReadingEntity();
            
            if(reading.getOrientationReading() != null){
                OrientationReadingEntity orientationReadingEntity = new OrientationReadingEntity(reading.getOrientationReading());
                readingEntity.setOrientationReadingEntity(orientationReadingEntity);
                persist(orientationReadingEntity);
            }
            
            if(reading.getWifiReadings() != null){
                WifiReadingEntity wifiReadingEntity;
                for(WifiReading wifiReading: reading.getWifiReadings().getWifiReadings()){
                    wifiReadingEntity= new WifiReadingEntity(wifiReading);
                    readingEntity.addWifiReadingEntity(wifiReadingEntity);
                    persist(wifiReadingEntity);
                }
            }
            
            if(reading.getaRMarkerReadings() != null){
                ARMarkerReadingEntity arMarkerReadingEntity;
                for(ARMarkerReading arMarkerReading: reading.getaRMarkerReadings().getArMarkerReadings()){
                    arMarkerReadingEntity = new ARMarkerReadingEntity(arMarkerReading);
                    readingEntity.addArMarkerReading(arMarkerReadingEntity);
                    persist(arMarkerReadingEntity);
                }
            }
            
            persist(readingEntity);
            readingsEntity.addReadingsEntity(readingEntity);
        }
        
        readingsEntity.setReadingType(getReadingType((IPRequestType)ipSocketMessage.getSocketMessageRequest()));
        persist(readingsEntity);
        
    }
    
    private ReadingsEntity.ReadingType getReadingType(IPRequestType ipRequestType){
        for(ReadingsEntity.ReadingType readingType : ReadingsEntity.ReadingType.values())
            if(ipRequestType.toString().equals(readingType.toString()))
                return readingType;
        
        return null;
    }
    
//    private void persistSocketMessage(IPSocketMessage iPSocketMessage, ReadingEntity.ReadingType readingType){
//        //System.out.println("start");
//        
//        WifiReadings wifiReadings = (WifiReadings)iPSocketMessage.getObjectFromMessage(WifiReadings.class);
//        OrientationReading orientationReading = (OrientationReading)iPSocketMessage.getObjectFromMessage(OrientationReading.class);
//        ARMarkerReadings arMarkerReadings = (ARMarkerReadings)iPSocketMessage.getObjectFromMessage(ARMarkerReadings.class);
//        GSMReading gsmReading = (GSMReading)iPSocketMessage.getObjectFromMessage(GSMReading.class);
//        PhoneDetails phoneDetails = (PhoneDetails) iPSocketMessage.getObjectFromMessage(PhoneDetails.class);
//        ImageReadings imageReadings = (ImageReadings) iPSocketMessage.getObjectFromMessage(ImageReadings.class);
//        
//        if(phoneDetails == null)
//            return;
//        
//        PhoneDetailsEntity phoneDetailsEntity = getPhoneDetailsEntity(phoneDetails.getImei());
//        if(phoneDetailsEntity == null){
//            phoneDetailsEntity = new PhoneDetailsEntity(phoneDetails);
//            persist(phoneDetailsEntity);
//        }
//
//        GSMReadingEntity gsmReadingEntity = null;
//        if(gsmReading != null){
//            gsmReadingEntity = new GSMReadingEntity(gsmReading);
//            persist(gsmReadingEntity);
//        }
//        
//        OrientationReadingEntity orientationReadingEntity = null;
//        if(orientationReading != null){
//            orientationReadingEntity = new OrientationReadingEntity(orientationReading);   
//            persist(orientationReadingEntity);
//        }
//                    
//        ArrayList<WifiReadingEntity> wifiReadingEntities = new ArrayList<>();
//        ArrayList<ARMarkerReadingEntity> arMarkerReadingsEntities = new ArrayList<>();
//
//        ARMarkerReadingEntity arMarkerReadingEntity;
//        WifiReadingEntity wifiReadingEntity;
//
//        if(wifiReadings != null)
//            for(WifiReading wifiReading : wifiReadings.getWifiReadings()){
//                wifiReadingEntity = new WifiReadingEntity(wifiReading);
//                wifiReadingEntities.add(wifiReadingEntity);
//                persist(wifiReadingEntity);
//            }
//
//        
//        if(arMarkerReadings != null){
//            ImageReading imageReading = null;
//            ImageEntity imageEntity = null;
//            for(int i=0; i< arMarkerReadings.getArMarkerReadings().size(); i++){
//                 arMarkerReadingEntity = new ARMarkerReadingEntity(arMarkerReadings.getArMarkerReadings().get(i));
//                 if(imageReading != null){
//                    imageReading = imageReadings.getImages().get(i);
//                    imageEntity = new ImageEntity(imageReading.getImage(), imageReading.getSize()[0], imageReading.getSize()[1]);
//                 }
//                 persist(arMarkerReadingEntity);
//                 if(imageEntity != null){
//                    arMarkerReadingEntity.setImage(imageEntity);
//                    persist(imageEntity);
//                 }
//                 arMarkerReadingsEntities.add(arMarkerReadingEntity);
//            }
//        }
//
//        ReadingEntity readingEntity = new ReadingEntity();
//        readingEntity.setReadingType(readingType);
//        readingEntity.setPhoneDetailsEntity(phoneDetailsEntity);
//        readingEntity.setWifiReadings(wifiReadingEntities);
//        readingEntity.setGsmReadingEntity(gsmReadingEntity);
//        readingEntity.setOrientationReadingEntity(orientationReadingEntity);
//        readingEntity.setArMarkerReadingEntitys(arMarkerReadingsEntities);
//        
//        persist(readingEntity);
//        //System.out.println("end");
//    }
    
    public PhoneDetailsEntity getPhoneDetailsEntity(String imei){
        Query query = em.createNamedQuery("PhoneDetailsEntity.findByImei");
        query.setParameter("imei", imei);
        List<PhoneDetailsEntity> phoneDetailsEntity = query.getResultList();
        if(phoneDetailsEntity.isEmpty())
            return null;
        return phoneDetailsEntity.get(0);
    }
    
    public void addSocketMessage(IPSocketMessage iPSocketMessage, ReadingsEntity.ReadingType readingType){
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
