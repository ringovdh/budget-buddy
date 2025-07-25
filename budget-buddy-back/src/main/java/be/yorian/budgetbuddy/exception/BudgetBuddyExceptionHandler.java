package be.yorian.budgetbuddy.exception;

import be.yorian.budgetbuddy.response.BudgetBuddyErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class BudgetBuddyExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BudgetBuddyExceptionHandler.class);


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BudgetBuddyErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new BudgetBuddyErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BudgetBuddyErrorResponse> handleException(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred for request {}:", request.getDescription(false), ex);
        return new ResponseEntity<>(new BudgetBuddyErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected internal server error occurred."
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
