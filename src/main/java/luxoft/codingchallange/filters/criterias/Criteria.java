package luxoft.codingchallange.filters.criterias;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import luxoft.codingchallange.enums.ComparisonOperators;
import luxoft.codingchallange.enums.LogicOperators;
import luxoft.codingchallange.exceptions.OperationNotSupportedException;

import java.util.Arrays;


@Getter
@RequiredArgsConstructor
public abstract class Criteria<E> {
    protected LogicOperators operator;
    protected String attribute;
    protected ComparisonOperators comparator;
    protected E conditionValue;
    protected ComparisonOperators[] supportedOperators;


    public Criteria(LogicOperators operator, String attribute, ComparisonOperators comparator, E conditionValue,ComparisonOperators... supportedOperators ) throws OperationNotSupportedException {
        this.operator = operator;
        this.attribute = attribute;
        this.comparator = comparator;
        this.conditionValue = conditionValue;
        this.supportedOperators = supportedOperators;

        if (!validateOperation()) {
            throw new OperationNotSupportedException(comparator.name(), attribute);
        }
    }

    private boolean validateOperation() {
        return Arrays.stream(supportedOperators).anyMatch(element ->element.equals(comparator));
    }

    public abstract boolean validate(E value);

    protected boolean shouldEvaluate(boolean previousValidValue) {
        if (previousValidValue == true && operator.equals(LogicOperators.OR)) {
            return false;
        }

        if (previousValidValue == false && operator.equals(LogicOperators.AND)) {
            return false;
        }

        return true;
    }


    public boolean validate(boolean previousTruthValue, E value) {
        if (shouldEvaluate(previousTruthValue)) {
            return validate(value);
        }
        return previousTruthValue;
    }

}
