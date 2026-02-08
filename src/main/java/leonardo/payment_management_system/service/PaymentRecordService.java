package leonardo.payment_management_system.service;

import jakarta.transaction.Transactional;
import leonardo.payment_management_system.dto.paymentRecord.CreatePaymentRecordDTO;
import leonardo.payment_management_system.dto.paymentRecord.PaymentRecordDTO;
import leonardo.payment_management_system.entity.Payment;
import leonardo.payment_management_system.entity.PaymentRecord;
import leonardo.payment_management_system.enums.PaymentRecordStatus;
import leonardo.payment_management_system.enums.PaymentStatus;
import leonardo.payment_management_system.exception.EntityNotFound;
import leonardo.payment_management_system.mapper.PaymentRecordMapper;
import leonardo.payment_management_system.repository.PaymentRecordRepository;
import leonardo.payment_management_system.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentRecordService {

    private final PaymentRecordRepository paymentRecordRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentRecordMapper mapper;

    public PaymentRecordService(PaymentRecordRepository paymentRecordRepository, PaymentRecordMapper mapper, PaymentRepository paymentRepository){
        this.paymentRecordRepository = paymentRecordRepository;
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
    }

    public Payment findPaymentOrThrow(Long id){
        return paymentRepository.findById(id).orElseThrow(() -> new EntityNotFound("Payment not found with ID: " + id));
    }

    private static final Map<PaymentRecordStatus, PaymentStatus> map = new EnumMap<>(PaymentRecordStatus.class);

    static {
        map.put(PaymentRecordStatus.PAID, PaymentStatus.PAID);
        map.put(PaymentRecordStatus.CANCELLED,PaymentStatus.CANCELLED);
        map.put(PaymentRecordStatus.REFUNDED, PaymentStatus.CANCELLED);
    }

    @Transactional
    public PaymentRecordDTO create(Long paymentId, CreatePaymentRecordDTO dto){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new EntityNotFound("Payment not found with ID: " + paymentId));
        PaymentRecord paymentRecord = mapper.toEntity(dto);

        paymentRecord.setValue(payment.getValue());
        paymentRecord.setPayment(payment);
        paymentRecord.setPaymentType(payment.getPaymentType());
        paymentRecord.setPaymentDeadlineSnapshot(payment.getPaymentDeadline());
        paymentRecord.setEventDate(LocalDateTime.now());

        PaymentRecord savedPaymentRecord = paymentRecordRepository.save(paymentRecord);
        payment.setStatus(map.get(dto.getStatus()));
        paymentRepository.save(payment);

        return mapper.toDto(savedPaymentRecord);
    }

    public Page<PaymentRecordDTO> findAllByPaymentId(Long id, Pageable pageable){
        findPaymentOrThrow(id);
        return paymentRecordRepository.findAllByPaymentId(id, pageable).map(mapper::toDto);
    }
}
