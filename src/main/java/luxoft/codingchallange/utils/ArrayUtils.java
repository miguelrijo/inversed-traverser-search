package luxoft.codingchallange.utils;

import java.util.function.BiConsumer;

public class ArrayUtils {

    public static <T> void traverseMatrixInReversedDiagonal(T[][] matrix, BiConsumer<T,Integer> accumulator){

        int i = 0;
        int j = 0;
        int order = matrix.length;
        int lastColumn = order - 1;
        int elementTh = 0;  //position of element
        int diagonalTh = 0;  //position of element in diagonal
        int diagonalElementTh = 0; // number of diagonal
        int currentDiagonalMaxElements = 1;
        Double n = Math.pow(order, 2);

        while (elementTh < n) {
            accumulator.accept(matrix[i][j], elementTh);
            //Moving to the next element
            //Have we reached the last element of the diagonal?
            if (diagonalElementTh == currentDiagonalMaxElements - 1) {
                //Jumping to first element on next diagonal
                boolean hasPassedMainDiagonal = ++diagonalTh > lastColumn;
                if(hasPassedMainDiagonal) {
                    j =  lastColumn;
                    i =  diagonalTh - j;
                    //Max count of elements for next diagonal
                    currentDiagonalMaxElements =  currentDiagonalMaxElements - 1;
                } else {
                    j =  diagonalTh;
                    i =  0;
                    //Max count of elements for next diagonal
                    currentDiagonalMaxElements =  diagonalTh + 1;
                }
                //Resetting diagonalElementh to 0 since we are on a new diagonal
                diagonalElementTh = 0;
            } else {
                diagonalElementTh++;
                //Moving to nex element in same diagonal
                j--;
                i++;
            }
            elementTh++;
        }
    }



}
