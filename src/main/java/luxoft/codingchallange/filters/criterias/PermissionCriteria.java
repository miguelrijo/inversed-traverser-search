package luxoft.codingchallange.filters.criterias;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import luxoft.codingchallange.filesystem.Permissions;
import luxoft.codingchallange.enums.ComparisonOperators;
import luxoft.codingchallange.enums.LogicOperators;
import luxoft.codingchallange.exceptions.OperationNotSupportedException;

@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PermissionCriteria extends Criteria<Permissions> {

    public PermissionCriteria(LogicOperators operator, String attribute, ComparisonOperators comparator, Permissions value) throws OperationNotSupportedException {
        super(operator, attribute, comparator, value,  ComparisonOperators.WITH, ComparisonOperators.NOT, ComparisonOperators.EQUALS);
    }

    public boolean validate(Permissions value) {
        switch (comparator) {
            case EQUALS:
                return value.equals(conditionValue);
            case NOT:
                return conditionValue != value;
            case WITH:
                return value.equals(conditionValue) || value.equals(Permissions.RW);
            default:
                return false;
        }
    }
}
