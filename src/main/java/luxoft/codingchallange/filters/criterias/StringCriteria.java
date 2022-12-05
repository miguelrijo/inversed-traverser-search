package luxoft.codingchallange.filters.criterias;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import luxoft.codingchallange.enums.ComparisonOperators;
import luxoft.codingchallange.enums.LogicOperators;
import luxoft.codingchallange.exceptions.OperationNotSupportedException;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StringCriteria extends Criteria<String> {

    public StringCriteria(LogicOperators operator, String attribute, ComparisonOperators comparator, String value) throws OperationNotSupportedException {
        super(operator, attribute, comparator, value,  ComparisonOperators.NOT, ComparisonOperators.EQUALS);
    }

    public boolean validate(String value) {
        switch (comparator) {
            case EQUALS:
                return value.equals(conditionValue);
            case NOT:
                return conditionValue != value;
            default:
                return false;
        }
    }
}
