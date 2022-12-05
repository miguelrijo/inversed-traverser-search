package luxoft.codingchallange.exceptions;

public class OperationNotSupportedException extends Exception{
    private static final String MESSAGE = " no supported for field: ";

    public OperationNotSupportedException(String operation, String fieldName) {
        super(operation + OperationNotSupportedException.MESSAGE + fieldName);
    }
}
