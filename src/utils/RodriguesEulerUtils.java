/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author victor
 */
public class RodriguesEulerUtils {
    
    public static double[] getEulerAngles(double[][] rodriguesMatrix){
        double eulerAngles[] = new double[3];
        eulerAngles[0] = Math.atan2(rodriguesMatrix[2][1], rodriguesMatrix[2][2]);
        eulerAngles[1] = Math.atan2(-rodriguesMatrix[2][0], Math.sqrt(Math.pow(rodriguesMatrix[2][1], 2) + Math.pow(rodriguesMatrix[2][2], 2)));
        eulerAngles[2] = Math.atan2(rodriguesMatrix[1][0], rodriguesMatrix[0][0]);
        return eulerAngles;
    }
    
}
