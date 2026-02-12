package leonardo.payment_management_system.repository;

import leonardo.payment_management_system.entity.Payment;
import leonardo.payment_management_system.enums.PaymentStatus;
import leonardo.payment_management_system.enums.PaymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);
    Page<Payment> findByPaymentType(PaymentType type, Pageable pageable);
    Page<Payment> findByStatusAndPaymentType(PaymentStatus status, PaymentType type, Pageable pageable);
}
