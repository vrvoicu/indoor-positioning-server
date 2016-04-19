/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import controllers.EntitiesController;
import ipsocketmessage.IPSocketMessage;
import ipsocketmessage.WifiReading;
import ipsocketmessage.WifiReadings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import persistance.ARMarkerReadingEntity;
import persistance.ReadingEntity;
import persistance.WifiReadingEntity;
import utils.RodriguesEulerUtils;

/**
 *
 * @author victor
 */
public class LocationDetectionHandler {
    
    private static LocationDetectionHandler locationDetectionHandler;
    private static Object lock = new Object();
    
    private LocationDetectionHandler(){};
    
    public static LocationDetectionHandler getInstance(){
        synchronized(lock){
            if(locationDetectionHandler == null)
                locationDetectionHandler = new LocationDetectionHandler();
            return locationDetectionHandler;
        }
    }
    
    
    public int[] getLocation(IPSocketMessage iPSocketMessage){
        WifiReadings wifiReadings = (WifiReadings)iPSocketMessage.getObjectFromMessage(WifiReadings.class);
        Map<WifiReading, WifiReadingEntity> closestWifiReadings = new HashMap<>();
        ArrayList<String> bssids = new ArrayList<>();
        for(WifiReading wifiReading: wifiReadings.getWifiReadings()){
            closestWifiReadings.put(wifiReading, null);
            bssids.add(wifiReading.getBSSID());
        }
        
        WifiReadingEntity tempWifiReadingEntity;
        ArrayList<WifiReadingEntity> wifiReadingEntitys = EntitiesController.getInstance().getWifiReadingEntitiesByBSSIDs(bssids);
        for(WifiReading wifiReading: wifiReadings.getWifiReadings())
            for(WifiReadingEntity wifiReadingEntity: wifiReadingEntitys)
                if(wifiReading.getBSSID().equals(wifiReadingEntity.getBssid())){
                    tempWifiReadingEntity = closestWifiReadings.get(wifiReading);
                    if(tempWifiReadingEntity == null)
                        closestWifiReadings.put(wifiReading, wifiReadingEntity);
                    else{
                        int levelDifference = Math.abs(wifiReading.getLevel() - wifiReadingEntity.getLevel());
                        int altLevelDifference = Math.abs(wifiReading.getLevel() - tempWifiReadingEntity.getLevel());
                        if(altLevelDifference < levelDifference)
                            closestWifiReadings.put(wifiReading, wifiReadingEntity);
                    }
                }
        
        WifiReadingEntity wifiReadingEntity;
        ReadingEntity.ReadingType readingType;
        ArrayList<ARMarkerReadingEntity> arMarkerReadingEntitys;
        ARMarkerReadingEntity arMarkerReadingEntity;
        double eulerAngles[];
        double [][] transMat = null;
        for(WifiReading wifiReading: closestWifiReadings.keySet()){
            wifiReadingEntity = closestWifiReadings.get(wifiReading);
//            readingType = wifiReadingEntity.getReadingEntity().getReadingType();
//            if(readingType == )
            arMarkerReadingEntitys = new ArrayList<>(wifiReadingEntity.getReadingEntity().getArMarkerReadingEntitys());
            if(arMarkerReadingEntitys.size() == 1){
                arMarkerReadingEntity = arMarkerReadingEntitys.get(0);
                transMat = arMarkerReadingEntity.getTransMatAsVector();
                eulerAngles = RodriguesEulerUtils.getEulerAngles(transMat);
            }
        }
                
        
        return null;
    }
    
}
