package leonardo.payment_management_system.dto.payment;

import leonardo.payment_management_system.enums.PaymentStatus;
import leonardo.payment_management_system.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {

    private Long id;
    private BigDecimal value;
    private PaymentType paymentType;
    private LocalDateTime paymentDeadline;
    private PaymentStatus status;

    public PaymentDTO(){}

    public PaymentDTO(Long id, BigDecimal value, PaymentType paymentType, LocalDateTime paymentDeadline, PaymentStatus status) {
        this.id = id;
        this.value = value;
        this.paymentType = paymentType;
        this.paymentDeadline = paymentDeadline;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getPaymentDeadline() {
        return paymentDeadline;
    }

    public void setPaymentDeadline(LocalDateTime paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
