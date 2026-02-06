package leonardo.payment_management_system.dto.paymentRecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import leonardo.payment_management_system.enums.PaymentRecordStatus;
import leonardo.payment_management_system.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentRecordDTO {

    private Long id;
    private Long paymentId;
    private BigDecimal value;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime eventDate;
    private PaymentRecordStatus status;
    private PaymentType paymentType;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime paymentDeadlineSnapshot;

    public PaymentRecordDTO() {}

    public PaymentRecordDTO(Long id, Long paymentId, BigDecimal value, LocalDateTime eventDate, PaymentRecordStatus status, PaymentType paymentType, LocalDateTime paymentDeadlineSnapshot) {
        this.id = id;
        this.paymentId = paymentId;
        this.value = value;
        this.eventDate = eventDate;
        this.status = status;
        this.paymentType = paymentType;
        this.paymentDeadlineSnapshot = paymentDeadlineSnapshot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
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
