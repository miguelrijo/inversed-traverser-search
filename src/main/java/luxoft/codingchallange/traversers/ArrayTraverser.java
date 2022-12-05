package luxoft.codingchallange.traversers;

import java.util.function.BiConsumer;

public abstract class ArrayTraverser <T> {
    public abstract  <T> void traverse(T[][] matrix, BiConsumer<T, Integer> operation );
}
