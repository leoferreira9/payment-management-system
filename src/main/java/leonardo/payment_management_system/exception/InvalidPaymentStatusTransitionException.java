package leonardo.payment_management_system.exception;

public class InvalidPaymentStatusTransitionException extends RuntimeException {
    public InvalidPaymentStatusTransitionException(String message) {
        super(message);
    }
}
