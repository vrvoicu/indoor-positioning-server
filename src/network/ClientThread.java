/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import com.sun.imageio.plugins.common.ImageUtil;
import controllers.EntitiesController;
import ipsocketmessage.ARMarkerReading;
import ipsocketmessage.ARMarkerReadings;
import ipsocketmessage.BitmapImage;
import ipsocketmessage.GSMReading;
import ipsocketmessage.IPRequestType;
import ipsocketmessage.IPSocketMessage;
import ipsocketmessage.ImageReading;
import ipsocketmessage.OrientationReading;
import ipsocketmessage.PhoneDetails;
import ipsocketmessage.WifiReading;
import ipsocketmessage.WifiReadings;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import persistance.ARMarkerReadingEntity;
import persistance.GSMReadingEntity;
import persistance.OrientationReadingEntity;
import persistance.PhoneDetailsEntity;
import persistance.ReadingEntity;
import persistance.WifiReadingEntity;
import utils.ImageUtils;
import utils.MatrixToStringConverter;

/**
 *
 * @author victor
 */
public class ClientThread extends Thread{
    private Socket socket;
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    private boolean run = true;
    
    public ClientThread(Socket socket){
        this.socket = socket;
        openStreams();
    }
    
    public void run(){
        IPSocketMessage ipSocketMessage;
        IPRequestType ipRequestType;
        try{
            while(run){
                ipSocketMessage = (IPSocketMessage) ois.readObject();
                ipRequestType = (IPRequestType)ipSocketMessage.getSocketMessageRequest();
                
                if(ipSocketMessage.getSocketMessageRequest() == IPRequestType.POST_READING_MARKER_WITHOUT_IMAGE){
                    
                    EntitiesController.getInstance().addSocketMessage(ipSocketMessage, ReadingEntity.ReadingType.MARKER_WITHOUT_IMAGE);
                }
                if(ipSocketMessage.getSocketMessageRequest() == IPRequestType.POST_READING_MARKER_WITH_IMAGE){
//                    ARMarkerReadings armr = (ARMarkerReadings)ipSocketMessage.getObjectFromMessage(ARMarkerReadings.class);
//                    ImageReading imageReading = (ImageReading)ipSocketMessage.getObjectFromMessage(ImageReading.class);
//                    for(ARMarkerReading arMarkerReading: armr.getArMarkerReadings()){
//                        System.out.println(arMarkerReading.getMarkerId());
//                        System.out.println(
//                                MatrixToStringConverter.convertToString(arMarkerReading.getVertex(), ",", ",")
//                        );
//                        System.out.println(
//                                MatrixToStringConverter.convertToString(arMarkerReading.getTransMat(), ",", ",")
//                        );
//                    }
                    //System.out.println(imageReading.getImage());
                    EntitiesController.getInstance().addSocketMessage(ipSocketMessage, ReadingEntity.ReadingType.MARKER_WITH_IMAGE);
                }
                if(ipSocketMessage.getSocketMessageRequest() == IPRequestType.POST_READING_ORIENTATION){
                    //OrientationReading or = (OrientationReading)ipSocketMessage.getObjectFromMessage(OrientationReading.class);
                    //WifiReadings wrs = (WifiReadings)ipSocketMessage.getObjectFromMessage(WifiReadings.class);
                    
                    EntitiesController.getInstance().addSocketMessage(ipSocketMessage, ReadingEntity.ReadingType.ORIENTATION);
                    
                }
                
                if(ipRequestType == IPRequestType.REQUEST_MAP){
                    BufferedImage bufferedImage = ImageIO.read(new File("DSC_0926.jpg"));
                    //bufferedImage.getRGB(MIN_PRIORITY, MIN_PRIORITY, MIN_PRIORITY, MIN_PRIORITY, rgbArray, MIN_PRIORITY, MIN_PRIORITY)
                    byte[] image = ImageUtils.imageToByteArray(bufferedImage);
                    int []bitmapImagePixels = ImageUtils.convertToBitmap(image, bufferedImage.getWidth(), bufferedImage.getHeight());
                    
                    bufferedImage.getRGB(
                            0,
                            0, 
                            bufferedImage.getWidth(), 
                            bufferedImage.getHeight(), 
                            bitmapImagePixels, 
                            0, bufferedImage.getWidth());
                    
                    BitmapImage bitmapImage = new BitmapImage(bitmapImagePixels, new int[]{bufferedImage.getWidth(), bufferedImage.getHeight()});
                    ipSocketMessage.setSocketMessageObjects(new ArrayList<>());
                    ipSocketMessage.getSocketMessageObjects().add(bitmapImage);
                    System.out.println("sent");
                    oos.writeObject(ipSocketMessage);
                    oos.flush();
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            closeStreams();
            closeSocket();
            ServerThread.getClientThreads().remove(ClientThread.this);
        }
    }
    
    private boolean openStreams(){
        boolean successful = false;
        try{
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return successful;
    }
    
    private boolean closeStreams(){
        boolean successful = false;
        try{
            ois.close();
            oos.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return successful;
    }
    
    private void closeSocket(){
        try{
            socket.close();
            socket = null;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
}
