package leonardo.payment_management_system.dto.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UpdatePaymentDTO {

    @DecimalMin(value = "0.0")
    private BigDecimal value;

    @Size(max = 100)
    private String description;

    public UpdatePaymentDTO(){}

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
