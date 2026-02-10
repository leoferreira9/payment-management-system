package leonardo.payment_management_system.service;

import jakarta.transaction.Transactional;
import leonardo.payment_management_system.dto.paymentRecord.CreatePaymentRecordDTO;
import leonardo.payment_management_system.dto.paymentRecord.PaymentRecordDTO;
import leonardo.payment_management_system.entity.Payment;
import leonardo.payment_management_system.entity.PaymentRecord;
import leonardo.payment_management_system.enums.PaymentRecordStatus;
import leonardo.payment_management_system.enums.PaymentStatus;
import leonardo.payment_management_system.exception.EntityNotFound;
import leonardo.payment_management_system.exception.InvalidPaymentStatusTransitionException;
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

    private static final Map<PaymentRecordStatus, PaymentStatus> setPaymentStatus = new EnumMap<>(PaymentRecordStatus.class);
    static {
        setPaymentStatus.put(PaymentRecordStatus.PAID, PaymentStatus.PAID);
        setPaymentStatus.put(PaymentRecordStatus.CANCELLED,PaymentStatus.CANCELLED);
        setPaymentStatus.put(PaymentRecordStatus.REFUNDED, PaymentStatus.CANCELLED);
    }

    private static final Map<PaymentStatus, Set<PaymentRecordStatus>> allowedTransitions = new HashMap<>();
    static{
        allowedTransitions.put(PaymentStatus.PENDING, EnumSet.of(PaymentRecordStatus.PAID, PaymentRecordStatus.CANCELLED));
        allowedTransitions.put(PaymentStatus.PAID, EnumSet.of(PaymentRecordStatus.REFUNDED));
    }

    @Transactional
    public PaymentRecordDTO create(Long paymentId, CreatePaymentRecordDTO dto){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new EntityNotFound("Payment not found with ID: " + paymentId));
        PaymentRecord paymentRecord = mapper.toEntity(dto);

        if(payment.getStatus().equals(PaymentStatus.CANCELLED)) throw new InvalidPaymentStatusTransitionException("Cannot update payment status, payment cancelled.");

        Set<PaymentRecordStatus> allowedStatus = allowedTransitions.get(payment.getStatus());
        if(allowedStatus != null && allowedStatus.contains(dto.getStatus())){
            payment.setStatus(setPaymentStatus.get(dto.getStatus()));
        } else {
            throw new InvalidPaymentStatusTransitionException("Cannot update payment from " + payment.getStatus() + " to " + dto.getStatus());
        }

        paymentRecord.setValue(payment.getValue());
        paymentRecord.setPayment(payment);
        paymentRecord.setPaymentType(payment.getPaymentType());
        paymentRecord.setPaymentDeadlineSnapshot(payment.getPaymentDeadline());
        paymentRecord.setEventDate(LocalDateTime.now());

        PaymentRecord savedPaymentRecord = paymentRecordRepository.save(paymentRecord);
        paymentRepository.save(payment);

        return mapper.toDto(savedPaymentRecord);
    }

    public Page<PaymentRecordDTO> findAllByPaymentId(Long id, Pageable pageable){
        findPaymentOrThrow(id);
        return paymentRecordRepository.findAllByPaymentId(id, pageable).map(mapper::toDto);
    }
}
