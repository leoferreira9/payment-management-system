package leonardo.payment_management_system.dto.paymentRecord;

import jakarta.validation.constraints.NotNull;
import leonardo.payment_management_system.enums.PaymentRecordStatus;

public class CreatePaymentRecordDTO {

    @NotNull
    private PaymentRecordStatus status;

    public CreatePaymentRecordDTO(){}

    public PaymentRecordStatus getStatus() {
        return status;
    }
}
