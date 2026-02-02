package leonardo.payment_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import leonardo.payment_management_system.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin("0.0")
    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    private LocalDateTime paymentDeadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public Payment(){}

    public Payment(BigDecimal value, LocalDateTime paymentDeadline, PaymentStatus status) {
        this.value = value;
        this.paymentDeadline = paymentDeadline;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
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
