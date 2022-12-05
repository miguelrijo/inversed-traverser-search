import luxoft.codingchallange.filesystem.File;
import luxoft.codingchallange.filesystem.FileSystem;
import luxoft.codingchallange.enums.ComparisonOperators;
import luxoft.codingchallange.enums.FileAttributes;
import luxoft.codingchallange.enums.LogicOperators;
import luxoft.codingchallange.filters.FilesFilter;
import luxoft.codingchallange.traversers.ReversedDiagonalTraverser;

import java.time.LocalDateTime;

public class Main {

    /**
     *
     * FOR MORE EXHAUSTIVE TESTS PLS CHECK THE TEST SUIT
     */

    public static void main(String[] args) {
        // TODO search for files

        var searcher = new FilesFilter(FileSystem.files, new ReversedDiagonalTraverser<File>());

        try {
            searcher.addCriteria(LogicOperators.OR, FileAttributes.NAME, ComparisonOperators.EQUALS, "file02");
            searcher.addCriteria(LogicOperators.OR, FileAttributes.TIME_CREATED, ComparisonOperators.AFTER, LocalDateTime.of(2022, 12, 05, 12, 12));

            var filterFiles = searcher.execute();

            // TODO print list of files found for previous search
            System.out.println("Result" + filterFiles);


        } catch (Exception e) {
            System.out.println(e);
        }

        /**/
    }
}
