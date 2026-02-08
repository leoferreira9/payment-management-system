package leonardo.payment_management_system.service;

import leonardo.payment_management_system.dto.payment.CreatePaymentDTO;
import leonardo.payment_management_system.dto.payment.PaymentDTO;
import leonardo.payment_management_system.entity.Payment;
import leonardo.payment_management_system.enums.PaymentStatus;
import leonardo.payment_management_system.exception.EntityNotFound;
import leonardo.payment_management_system.mapper.PaymentMapper;
import leonardo.payment_management_system.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper mapper){
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
    }

    public Payment findPaymentOrThrow(Long id){
        return paymentRepository.findById(id).orElseThrow(() -> new EntityNotFound("Payment not found with ID: " + id));
    }

    public PaymentDTO create(CreatePaymentDTO dto){
        Payment payment = mapper.toEntity(dto);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDeadline(dto.getPaymentType().calculateDeadline(LocalDateTime.now()));
        Payment savedPayment = paymentRepository.save(payment);
        return mapper.toDto(savedPayment);
    }

    public PaymentDTO findById(Long id){
        Payment payment = findPaymentOrThrow(id);
        return mapper.toDto(payment);
    }

    public Page<PaymentDTO> findAll(Pageable pageable){
        return paymentRepository.findAll(pageable).map(mapper::toDto);
    }

}
