package luxoft.codingchallange.traversers;

import luxoft.codingchallange.utils.ArrayUtils;

import java.util.function.BiConsumer;

public class ReversedDiagonalTraverser<T> extends ArrayTraverser<T> {

    @Override
    public <T> void traverse(T[][] matrix, BiConsumer<T, Integer> operation) {
        ArrayUtils.traverseMatrixInReversedDiagonal(matrix,operation );
    }
}


