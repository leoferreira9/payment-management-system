package leonardo.payment_management_system.exception;

public class FailedToUpdateEntity extends RuntimeException {
    public FailedToUpdateEntity(String message) {
        super(message);
    }
}
