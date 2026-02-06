package leonardo.payment_management_system.enums;

import java.time.LocalDateTime;

public enum PaymentType {
    CREDIT_CARD(LocalDateTime.now().plusMinutes(30)),
    DEBIT_CARD(LocalDateTime.now().plusMinutes(30)),
    PIX(LocalDateTime.now().plusMinutes(30)),
    BOLETO(LocalDateTime.now().plusDays(2));

    private final LocalDateTime deadLine;

    PaymentType(LocalDateTime deadLine){
        this.deadLine = deadLine;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }
}
