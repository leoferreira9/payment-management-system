package leonardo.payment_management_system.service;

import jakarta.transaction.Transactional;
import leonardo.payment_management_system.dto.payment.CreatePaymentDTO;
import leonardo.payment_management_system.dto.payment.PaymentDTO;
import leonardo.payment_management_system.dto.payment.UpdatePaymentDTO;
import leonardo.payment_management_system.entity.Payment;
import leonardo.payment_management_system.enums.PaymentRecordStatus;
import leonardo.payment_management_system.enums.PaymentStatus;
import leonardo.payment_management_system.enums.PaymentType;
import leonardo.payment_management_system.exception.EntityNotFound;
import leonardo.payment_management_system.exception.FailedToUpdateEntity;
import leonardo.payment_management_system.mapper.PaymentMapper;
import leonardo.payment_management_system.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final PaymentRecordService paymentRecordService;
    private final PaginationService paginationService;


    public PaymentService(PaymentRepository paymentRepository, PaymentMapper mapper, PaymentRecordService paymentRecordService, PaginationService paginationService){
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.paymentRecordService = paymentRecordService;
        this.paginationService = paginationService;
    }

    public Payment findPaymentOrThrow(Long id){
        return paymentRepository.findById(id).orElseThrow(() -> new EntityNotFound("Payment not found with ID: " + id));
    }

    @Transactional
    public PaymentDTO create(CreatePaymentDTO dto){
        Payment payment = mapper.toEntity(dto);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDeadline(dto.getPaymentType().calculateDeadline(LocalDateTime.now()));
        Payment savedPayment = paymentRepository.save(payment);
        paymentRecordService.createRecord(savedPayment);
        return mapper.toDto(savedPayment);

    }
    public PaymentDTO findById(Long id){
        Payment payment = findPaymentOrThrow(id);
        return mapper.toDto(payment);
    }

    public Page<PaymentDTO> findAll(PaymentStatus status, PaymentType type, Pageable pageable){

        Pageable fixedPageable = paginationService.createPageable(pageable);

        Page<Payment> payment;
        if(status != null && type != null){
            payment = paymentRepository.findByStatusAndPaymentType(status, type, fixedPageable);
        } else if (status != null){
            payment = paymentRepository.findByStatus(status, fixedPageable);
        } else if (type != null){
            payment = paymentRepository.findByPaymentType(type, fixedPageable);
        } else {
            payment = paymentRepository.findAll(fixedPageable);
        }

        return payment.map(mapper::toDto);
    }

    public PaymentDTO confirmPayment(Long id){
        Payment payment = paymentRecordService.create(id, PaymentRecordStatus.PAID);
        return mapper.toDto(payment);
    }

    public PaymentDTO cancelPayment(Long id){
        Payment payment = paymentRecordService.create(id, PaymentRecordStatus.CANCELLED);
        return mapper.toDto(payment);
    }

    public PaymentDTO refundPayment(Long id){
        Payment payment = paymentRecordService.create(id, PaymentRecordStatus.REFUNDED);
        return mapper.toDto(payment);
    }

    public PaymentDTO updatePayment(Long id, UpdatePaymentDTO dto){
        Payment payment = findPaymentOrThrow(id);

        if(!payment.getStatus().equals(PaymentStatus.PENDING)) throw new FailedToUpdateEntity("Cannot update, payment already paid or cancelled.");

        String description = dto.getDescription() != null ? dto.getDescription() : payment.getDescription();
        BigDecimal value = dto.getValue() != null ? dto.getValue() : payment.getValue();

        payment.setDescription(description);
        payment.setValue(value);
        Payment savedPayment = paymentRepository.save(payment);
        paymentRecordService.createRecord(payment);
        return mapper.toDto(savedPayment);
    }
}
