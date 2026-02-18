package leonardo.payment_management_system.exception.handler;

import java.time.Instant;

public record ErrorResponse(int status, String message, Instant timeStamp, String error, String path) {}
