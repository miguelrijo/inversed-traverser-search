package luxoft.codingchallange.filters;

import luxoft.codingchallange.exceptions.OperationNotSupportedException;
import luxoft.codingchallange.filters.criterias.*;
import luxoft.codingchallange.filesystem.File;
import luxoft.codingchallange.filesystem.Permissions;
import luxoft.codingchallange.enums.ComparisonOperators;
import luxoft.codingchallange.enums.FileAttributes;
import luxoft.codingchallange.enums.LogicOperators;
import luxoft.codingchallange.traversers.ArrayTraverser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class FilesFilter {
    private LinkedList<Criteria> conditionList = new LinkedList<>();
    private File[][] files;
    private final ArrayTraverser<File> traverser;

    public FilesFilter(File[][] files, ArrayTraverser<File> traverser){
        this.traverser = traverser;
        this.files = files;
    }

    public FilesFilter addCriteria(LogicOperators operator, FileAttributes attribute, ComparisonOperators comparator, LocalDateTime value) throws OperationNotSupportedException{
        this.conditionList.add(new DateCriteria(operator, attribute.name(),comparator,value ));
        return this;
    }

    public FilesFilter addCriteria(LogicOperators operator, FileAttributes attribute, ComparisonOperators comparator, Long value) throws OperationNotSupportedException{
        this.conditionList.add(new LongCriteria(operator, attribute.name(),comparator,value));
        return this;
    }

    public FilesFilter addCriteria(LogicOperators operator, FileAttributes attribute, ComparisonOperators comparator, String value) throws OperationNotSupportedException {
        this.conditionList.add(new StringCriteria(operator, attribute.name(),comparator,value ));
        return this;
    }


    public FilesFilter addCriteria(LogicOperators operator, FileAttributes attribute, ComparisonOperators comparator, Permissions value)throws OperationNotSupportedException{
        this.conditionList.add(new PermissionCriteria(operator, attribute.name(),comparator,value ));
        return this;
    }

    public List<File> execute(){
        var list = new ArrayList<File>();
        traverser.traverse(files, ( file, position)->{
            if(this.validateFile(file)){
                list.add(file);
            }
       } );
        return list;
    }

    private boolean validateFile(File file){
        boolean valid = true;
        boolean startCriteria = true;

        for(Criteria criteria : conditionList){
            valid = startCriteria && criteria.getOperator().equals(LogicOperators.OR) ? false : valid;
            FileAttributes attribute = FileAttributes.valueOf(criteria.getAttribute());
            switch (attribute)  {
                case NAME:
                    valid = criteria.validate(valid,file.getName());
                    break;
                case SIZE:
                    valid = criteria.validate(valid,file.getSize());
                    break;
                case TIME_CREATED:
                    valid = criteria.validate(valid,file.getTimeCreated());
                    break;
                case PERMISSIONS:
                    valid = criteria.validate(valid,file.getPermission());
                    break;
            }
            startCriteria = false;
        }
         return valid;
    }




}
