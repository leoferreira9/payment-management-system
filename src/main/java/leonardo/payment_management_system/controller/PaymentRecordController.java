package leonardo.payment_management_system.controller;

import jakarta.validation.Valid;
import leonardo.payment_management_system.dto.paymentRecord.CreatePaymentRecordDTO;
import leonardo.payment_management_system.dto.paymentRecord.PaymentRecordDTO;
import leonardo.payment_management_system.service.PaymentRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments-records")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    public PaymentRecordController (PaymentRecordService paymentRecordService){
        this.paymentRecordService = paymentRecordService;
    }

    @PostMapping
    public PaymentRecordDTO create(@RequestBody @Valid CreatePaymentRecordDTO dto){
        return paymentRecordService.create(dto);
    }

    @GetMapping("/{paymentId}")
    public List<PaymentRecordDTO> findAllByPaymentId(@PathVariable Long paymentId){
        return paymentRecordService.findAllByPaymentId(paymentId);
    }
}
