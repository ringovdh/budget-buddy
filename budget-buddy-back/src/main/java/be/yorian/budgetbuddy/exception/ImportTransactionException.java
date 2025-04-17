package be.yorian.budgetbuddy.exception;

public class ImportTransactionException extends RuntimeException {

    public ImportTransactionException(String message) {
        super(message);
    }

    public ImportTransactionException(String message, Exception e) {
        super(message, e);
    }

}
