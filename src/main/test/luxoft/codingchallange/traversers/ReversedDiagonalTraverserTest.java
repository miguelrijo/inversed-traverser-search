package luxoft.codingchallange.traversers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReversedDiagonalTraverserTest {

    @Test
    void traverse3() {
        int order = 3;
        var matrix = this.getSquaredMatrix(order);
        var expectedList = new String[]{"1", "2", "4", "3", "5", "7", "6", "8","9"};

        new ReversedDiagonalTraverser<String>().traverse(matrix, (element, position)->{
            assertEquals(element, expectedList[position]);
        });
    }


    @Test
    void traverse4() {
        int order = 4;
        var matrix = this.getSquaredMatrix(order);
        var expectedList = new String[]{"1", "2", "5", "3", "6", "9", "4", "7","10","13","8","11","14","12", "15", "16"};

        new ReversedDiagonalTraverser<String>().traverse(matrix, (element, position)->{
            assertEquals(element, expectedList[position]);
        });
    }

    private String[][] getSquaredMatrix(int order){
       String[][] m1 = new String[order][order];
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                m1[i][j] = String.valueOf( (i * order) + j + 1);
            }
        }
        return m1;
    }

}