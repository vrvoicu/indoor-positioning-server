/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import static java.lang.System.in;

/**
 *
 * @author victor
 */
public class ImageUtils {

    public static byte[] imageToByteArray(BufferedImage bufferedImage) {
        return ((DataBufferByte) bufferedImage.getData().getDataBuffer()).getData();
    }

    public static int[] convertToBitmap(byte image[], int width, int height) {
        byte[] imgBytes = new byte[width * height * 3]; // 4 byte ABGR

        System.out.println(imgBytes.length);
        System.out.println(image.length);

        int[] pixels = new int[width * height];
        for (int i = 0; i < pixels.length -3; i++) {
            int byteIndex = i * 3;
//            pixels[i]
//                    = ((imgBytes[byteIndex] & 0xFF) << 24)
//                    | ((imgBytes[byteIndex + 3] & 0xFF) << 16)
//                    | ((imgBytes[byteIndex + 2] & 0xFF) << 8)
//                    | (imgBytes[byteIndex + 1] & 0xFF);
            
            pixels[i] = 
                    ((imgBytes[byteIndex] & 0xFF) << 24) | 
                    ((imgBytes[byteIndex + 3] & 0xFF) << 16) | 
                    ((imgBytes[byteIndex + 2] & 0xFF) << 8) | 
                    (imgBytes[byteIndex + 1] & 0xFF);
        }

        return pixels;
    }

//// Convert 4 byte interleaved ABGR to int packed ARGB
//int[] pixels = new int[w * h];
//for (int i = 0; i < pixels.length; i++) {
//    int byteIndex = i * 4;
//    pixels[i] = 
//            ((imgBytes[byteIndex    ] & 0xFF) << 24) 
//          | ((imgBytes[byteIndex + 3] & 0xFF) << 16) 
//          | ((imgBytes[byteIndex + 2] & 0xFF) <<  8) 
//          |  (imgBytes[byteIndex + 1] & 0xFF);
//} 
//
//// Finally, create bitmap from packed int ARGB, using ARGB_8888
//Bitmap bitmap = Bitmap.createBitmap(pixels, w, h, Bitmap.Config.ARGB_8888);
//    }
}
