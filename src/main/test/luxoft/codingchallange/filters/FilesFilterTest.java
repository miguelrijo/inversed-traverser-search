package luxoft.codingchallange.filters;

import luxoft.codingchallange.enums.ComparisonOperators;
import luxoft.codingchallange.enums.FileAttributes;
import luxoft.codingchallange.enums.LogicOperators;
import luxoft.codingchallange.exceptions.OperationNotSupportedException;
import luxoft.codingchallange.filesystem.File;
import luxoft.codingchallange.filesystem.Permissions;
import luxoft.codingchallange.traversers.ArrayTraverser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HERE I INCLUDED THE MOST IMPORTANT TEST CASE . THERE ARE MANY OTHERS COMBINATIONS THOUGH
 */


class FilesFilterTest {
    private static File[][] files = new File[5][5];

    @BeforeAll
    public static void populateFiles() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                long size = 1000 * (i + j);
                String name = "file" + i + j;
                final LocalDateTime creationTime = LocalDateTime.of(2022, 12, i + j + 1, 12, 12);
                Permissions permission = Permissions.values()[(10 * i + j) % 3];
                files[i][j] = new File(size, name, creationTime, permission);
            }
        }
    }


    @Test
    void executeWithString() throws OperationNotSupportedException {
        var searcher = new FilesFilter(files, new MockTraverser<File>());
        searcher.addCriteria(LogicOperators.OR, FileAttributes.NAME, ComparisonOperators.EQUALS, "file02");
        var files = searcher.execute();

        assertEquals(1, files.size());
        assertEquals("file02", files.get(0).getName());
    }

    @Test
    void executeWithDate() throws OperationNotSupportedException {
        var searcher = new FilesFilter(files, new MockTraverser<File>());
        var date = LocalDateTime.of(2022, 12, 03, 12, 12);
        searcher.addCriteria(LogicOperators.OR, FileAttributes.TIME_CREATED, ComparisonOperators.EQUALS, date);
        var files = searcher.execute();

        for (var file : files) {
            assertEquals(date, file.getTimeCreated());
        }
    }

    @Test
    void executeWithSize() throws OperationNotSupportedException {
        var searcher = new FilesFilter(files, new MockTraverser<File>());
        var size = 2000L;
        searcher.addCriteria(LogicOperators.OR, FileAttributes.SIZE, ComparisonOperators.GREATER_THAN, size);
        var files = searcher.execute();

        for (var file : files) {
            assertTrue(file.getSize() > size);
        }
    }

    @Test
    void executeWithPermissions() throws OperationNotSupportedException {
        var searcher = new FilesFilter(files, new MockTraverser<File>());
        var permission = Permissions.R;
        searcher.addCriteria(LogicOperators.OR, FileAttributes.PERMISSIONS, ComparisonOperators.WITH, permission);
        var files = searcher.execute();

        for (var file : files) {
            assertTrue(file.getPermission().equals(Permissions.R) || file.getPermission().equals(Permissions.RW));
        }
    }

    @Test
    void executeWithCombination1() throws OperationNotSupportedException {
        var searcher = new FilesFilter(files, new MockTraverser<File>());
        var permission = Permissions.R;
        var size = 2000L;
        searcher.addCriteria(LogicOperators.OR, FileAttributes.PERMISSIONS, ComparisonOperators.WITH, permission);
        searcher.addCriteria(LogicOperators.OR, FileAttributes.SIZE, ComparisonOperators.GREATER_THAN, size);
        var files = searcher.execute();

        for (var file : files) {
            assertTrue(file.getPermission().equals(Permissions.R) || file.getPermission().equals(Permissions.RW) || file.getSize() > size);
        }
    }

    @Test
    void executeWithCombination2() throws OperationNotSupportedException {
        var searcher = new FilesFilter(files, new MockTraverser<File>());
        var size = 2000L;
        var date = LocalDateTime.of(2022, 12, 03, 12, 12);
        searcher.addCriteria(LogicOperators.OR, FileAttributes.TIME_CREATED, ComparisonOperators.AFTER, date);
        searcher.addCriteria(LogicOperators.OR, FileAttributes.SIZE, ComparisonOperators.GREATER_THAN, size);
        var files = searcher.execute();

        for (var file : files) {
            assertTrue(file.getSize() > size && file.getTimeCreated().isAfter(date));
        }
    }

    @Test
    void executeWithOperationNotSupported() {
        var searcher = new FilesFilter(files, new MockTraverser<File>());
        var size = 2000L;
        var date = LocalDateTime.of(2022, 12, 03, 12, 12);


        assertThrows(OperationNotSupportedException.class, () -> {
            searcher.addCriteria(LogicOperators.OR, FileAttributes.SIZE, ComparisonOperators.AFTER, size);
            searcher.execute();
        });
    }
}

class MockTraverser<T> extends ArrayTraverser<T> {

    @Override
    public <T> void traverse(T[][] matrix, BiConsumer<T, Integer> operation) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                operation.accept(matrix[i][j], 0);
            }
        }
    }


}