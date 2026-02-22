package leonardo.payment_management_system.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import leonardo.payment_management_system.exception.EntityNotFound;
import leonardo.payment_management_system.exception.FailedToUpdateEntity;
import leonardo.payment_management_system.exception.InvalidPaymentStatusTransitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                message,
                Instant.now(),
                status.getReasonPhrase(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status.value()).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ErrorResponse> entityNotFoundHandler(EntityNotFound ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(FailedToUpdateEntity.class)
    public ResponseEntity<ErrorResponse> failedToUpdateHandler(FailedToUpdateEntity ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidPaymentStatusTransitionException.class)
    public ResponseEntity<ErrorResponse> invalidPaymentStatusTransitionHandler(InvalidPaymentStatusTransitionException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableHandler(HttpMessageNotReadableException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> noResourceFoundException(NoResourceFoundException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidHandler(MethodArgumentNotValidException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
}
