package leonardo.payment_management_system.dto.payment;

import jakarta.validation.constraints.NotNull;
import leonardo.payment_management_system.enums.PaymentType;

import java.math.BigDecimal;

public class CreatePaymentDTO {

    @NotNull
    private BigDecimal value;

    @NotNull
    private PaymentType paymentType;

    public CreatePaymentDTO(){}

    public BigDecimal getValue() {
        return value;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

}
