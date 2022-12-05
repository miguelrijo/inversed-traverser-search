package luxoft.codingchallange.filters.criterias;

import luxoft.codingchallange.enums.ComparisonOperators;
import luxoft.codingchallange.enums.LogicOperators;
import luxoft.codingchallange.exceptions.OperationNotSupportedException;


public class LongCriteria extends Criteria<Long> {

    public LongCriteria(LogicOperators operator, String attribute, ComparisonOperators comparator, Long value) throws OperationNotSupportedException {
        super(operator, attribute, comparator, value, ComparisonOperators.GREATER_THAN, ComparisonOperators.LOWER_THAN, ComparisonOperators.NOT, ComparisonOperators.EQUALS);
    }

    public  boolean validate( Long value){
        switch (comparator){
            case EQUALS:
                return value == conditionValue;
            case NOT:
               return conditionValue != value;
            case GREATER_THAN:
                return  value > conditionValue;
            case LOWER_THAN:
               return value < conditionValue;
            default:
                return false;
        }
    }
}
