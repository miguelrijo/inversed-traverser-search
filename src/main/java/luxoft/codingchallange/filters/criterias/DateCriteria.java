package luxoft.codingchallange.filters.criterias;

import luxoft.codingchallange.enums.ComparisonOperators;
import luxoft.codingchallange.enums.LogicOperators;
import luxoft.codingchallange.exceptions.OperationNotSupportedException;

import java.time.LocalDateTime;


public class DateCriteria extends Criteria<LocalDateTime> {

    public DateCriteria(LogicOperators operator, String attribute, ComparisonOperators comparator, LocalDateTime value) throws OperationNotSupportedException {
        super(operator, attribute, comparator, value,  ComparisonOperators.AFTER, ComparisonOperators.BEFORE, ComparisonOperators.NOT, ComparisonOperators.EQUALS);
    }

    public boolean validate( LocalDateTime value) {
        switch (comparator){
            case EQUALS:
                return value.toString().equals(conditionValue.toString());
            case NOT:
                return!value.toString().equals(conditionValue.toString());
            case BEFORE:
                return value.isBefore(conditionValue);
            case AFTER:
                return value.isAfter(conditionValue);
            default:
                return false;
        }
    }
}
