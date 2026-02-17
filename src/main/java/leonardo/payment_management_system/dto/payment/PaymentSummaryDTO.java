package leonardo.payment_management_system.dto.payment;

import java.math.BigDecimal;

public record PaymentSummaryDTO(BigDecimal totalPending, BigDecimal totalPaid, BigDecimal totalCancelled) {}
