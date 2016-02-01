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
public class MatrixToStringConverter {
    
    public static String convertToString(double [][] matrix, String columnSeparator, String rowSeparator){
        String result = "";
        
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++)
                result += matrix[i][j]+columnSeparator;
            result += rowSeparator;
        }
        
        return result;
    }
    
}
