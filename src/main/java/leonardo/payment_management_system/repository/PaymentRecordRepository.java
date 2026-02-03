package leonardo.payment_management_system.repository;

import leonardo.payment_management_system.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {}
