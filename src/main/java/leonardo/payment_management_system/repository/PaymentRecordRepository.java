package leonardo.payment_management_system.repository;

import leonardo.payment_management_system.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
    List<PaymentRecord> findAllByPaymentId(Long id);
}
