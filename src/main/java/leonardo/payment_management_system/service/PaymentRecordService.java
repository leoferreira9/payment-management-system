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
    private final PaginationService paginationService;


    public PaymentRecordService(PaymentRecordRepository paymentRecordRepository, PaymentRecordMapper mapper, PaymentRepository paymentRepository, PaginationService paginationService){
        this.paymentRecordRepository = paymentRecordRepository;
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.paginationService = paginationService;
    }

    public Payment findPaymentOrThrow(Long id){
        return paymentRepository.findById(id).orElseThrow(() -> new EntityNotFound("Payment not found with ID: " + id));
    }

    public PaymentRecord buildPaymentRecord(Payment payment, PaymentRecordStatus status){
        return new PaymentRecord(payment,
                payment.getValue(),
                LocalDateTime.now(),
                status,
                payment.getPaymentType(),
                payment.getPaymentDeadline()
        );
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
        Payment payment = findPaymentOrThrow(paymentId);

        if(payment.getStatus().equals(PaymentStatus.CANCELLED)) throw new InvalidPaymentStatusTransitionException("Cannot update payment status, payment cancelled.");

        PaymentRecord paymentRecord = mapper.toEntity(dto);

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

    @Transactional
    public Payment create (Long paymentId, PaymentRecordStatus status){
        Payment payment = findPaymentOrThrow(paymentId);

        if(payment.getStatus().equals(PaymentStatus.CANCELLED)) throw new InvalidPaymentStatusTransitionException("Cannot update payment status, payment cancelled.");

        Set<PaymentRecordStatus> allowedStatus = allowedTransitions.get(payment.getStatus());
        if(allowedStatus != null && allowedStatus.contains(status)){
            payment.setStatus(setPaymentStatus.get(status));
        } else {
            throw new InvalidPaymentStatusTransitionException("Cannot update payment from " + payment.getStatus() + " to " + status);
        }

        PaymentRecord paymentRecord = buildPaymentRecord(payment, status);

        paymentRecordRepository.save(paymentRecord);
        paymentRepository.save(payment);

        return payment;
    }

    public Page<PaymentRecordDTO> findAllByPaymentId(Long id, Pageable pageable){
        findPaymentOrThrow(id);
        Pageable fixedPageable = paginationService.createPageable(pageable);
        return paymentRecordRepository.findAllByPaymentId(id, fixedPageable).map(mapper::toDto);
    }

    public PaymentRecordDTO createInitialRecord (Payment payment){
        PaymentRecord paymentRecord = buildPaymentRecord(payment, PaymentRecordStatus.PENDING);
        PaymentRecord savedPaymentRecord = paymentRecordRepository.save(paymentRecord);
        return mapper.toDto(savedPaymentRecord);
    }
}
