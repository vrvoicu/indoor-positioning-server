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
public class ArrayToStringConverter {
    
    public static String convertToString(int array[], int size, String rowSeparator){
        StringBuilder result = new StringBuilder();
        
        if(size > 0){
            int parts  = array.length/size;
            int rest = array.length%size;
            for(int i = 0; i*size < array.length- rest; i++){
                result.append(convertToString(array, i*size, (i+1)*size));
                result.append(rowSeparator);
            }
            result.append(convertToString(array, parts*size, rest));
        }
        else
            result.append(convertToString(array, 0, array.length));
        
        return result.toString();
    }
    
    private static String convertToString(int array[], int start, int end){
        StringBuilder result = new StringBuilder();
        
        for(int i= start; i<end; i++)
            result.append(array[i]);
        
        return result.toString();
    }
    
}
