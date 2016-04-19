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
    
    public static double[][] convertToMatrix(String matrixString,  String columnSeparator, String rowSeparator){
        double matrix[][] = null;
        String rows[] = matrixString.split(rowSeparator);
        String columns[];
        for(String rowString: rows){
            columns = rowString.split(columnSeparator);
            if(matrix == null)
                matrix = new double[rows.length][columns.length];
            for(int column = 0; column < columns.length; column++){
                int row = Integer.parseInt(rowString);
                matrix[row][column] = Double.parseDouble(columns[column]);
            }
        }
        return matrix;
    }
    
//    public double[][] arUtilMatInv( double s[3][4]){
//    ARMat       *mat;
//    int         i, j;
//
//    mat = arMatrixAlloc( 4, 4 );
//    for( j = 0; j < 3; j++ ) {
//        for( i = 0; i < 4; i++ ) {
//            mat->m[j*4+i] = s[j][i];
//        }
//    }
//    mat->m[3*4+0] = 0; mat->m[3*4+1] = 0;
//    mat->m[3*4+2] = 0; mat->m[3*4+3] = 1;
//    arMatrixSelfInv( mat );
//    for( j = 0; j < 3; j++ ) {
//        for( i = 0; i < 4; i++ ) {
//            d[j][i] = mat->m[j*4+i];
//        }
//    }
//    arMatrixFree( mat );
//
//    return 0;
//    }
    
}
