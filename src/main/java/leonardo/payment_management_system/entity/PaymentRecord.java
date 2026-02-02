package leonardo.payment_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import leonardo.payment_management_system.enums.PaymentRecordStatus;
import leonardo.payment_management_system.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @DecimalMin("0.0")
    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentRecordStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private LocalDateTime paymentDeadlineSnapshot;

    public PaymentRecord(){}

    public PaymentRecord(Payment payment, BigDecimal value, LocalDateTime eventDate, PaymentRecordStatus status, PaymentType paymentType, LocalDateTime paymentDeadlineSnapshot) {
        this.payment = payment;
        this.value = value;
        this.eventDate = eventDate;
        this.status = status;
        this.paymentType = paymentType;
        this.paymentDeadlineSnapshot = paymentDeadlineSnapshot;
    }

    public Long getId() {
        return id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public PaymentRecordStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentRecordStatus status) {
        this.status = status;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateTime getPaymentDeadlineSnapshot() {
        return paymentDeadlineSnapshot;
    }

    public void setPaymentDeadlineSnapshot(LocalDateTime paymentDeadlineSnapshot) {
        this.paymentDeadlineSnapshot = paymentDeadlineSnapshot;
    }
}
