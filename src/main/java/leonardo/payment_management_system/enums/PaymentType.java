package leonardo.payment_management_system.enums;

import java.time.LocalDateTime;

public enum PaymentType {
    CREDIT_CARD{
        public LocalDateTime calculateDeadline(LocalDateTime date) {
            return date.plusMinutes(30);
        }
    },
    DEBIT_CARD{
        public LocalDateTime calculateDeadline(LocalDateTime date) {
            return date.plusMinutes(30);
        }
    },
    PIX{
        public LocalDateTime calculateDeadline(LocalDateTime date) {
            return date.plusMinutes(30);
        }
    },
    BOLETO{
        public LocalDateTime calculateDeadline(LocalDateTime date) {
            return date.plusDays(2);
        }
    };

    public abstract LocalDateTime calculateDeadline(LocalDateTime date);
}
