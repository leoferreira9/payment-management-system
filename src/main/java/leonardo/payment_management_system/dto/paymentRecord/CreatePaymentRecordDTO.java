package leonardo.payment_management_system.dto.paymentRecord;

import jakarta.validation.constraints.NotNull;
import leonardo.payment_management_system.enums.PaymentRecordStatus;

public class CreatePaymentRecordDTO {

    @NotNull
    private Long paymentId;

    @NotNull
    private PaymentRecordStatus status;

    public CreatePaymentRecordDTO(){}

    public Long getPaymentId() {
        return paymentId;
    }

    public PaymentRecordStatus getStatus() {
        return status;
    }
}
