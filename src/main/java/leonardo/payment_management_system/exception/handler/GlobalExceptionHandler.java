package leonardo.payment_management_system.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import leonardo.payment_management_system.exception.EntityNotFound;
import leonardo.payment_management_system.exception.FailedToUpdateEntity;
import leonardo.payment_management_system.exception.InvalidPaymentStatusTransitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ErrorResponse> entityNotFoundHandler(EntityNotFound ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                Instant.now(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(FailedToUpdateEntity.class)
    public ResponseEntity<ErrorResponse> failedToUpdateHandler(FailedToUpdateEntity ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                Instant.now(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidPaymentStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> invalidPaymentStatusTransitionHandler(InvalidPaymentStatusTransitionException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                Instant.now(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableHandler(HttpMessageNotReadableException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                Instant.now(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> NoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                Instant.now(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
