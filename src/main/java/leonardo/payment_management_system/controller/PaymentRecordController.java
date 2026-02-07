package leonardo.payment_management_system.controller;

import jakarta.validation.Valid;
import leonardo.payment_management_system.dto.paymentRecord.CreatePaymentRecordDTO;
import leonardo.payment_management_system.dto.paymentRecord.PaymentRecordDTO;
import leonardo.payment_management_system.service.PaymentRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments/{paymentId}/records")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    public PaymentRecordController (PaymentRecordService paymentRecordService){
        this.paymentRecordService = paymentRecordService;
    }

    @PostMapping
    public ResponseEntity<PaymentRecordDTO> create(@PathVariable Long paymentId, @RequestBody @Valid CreatePaymentRecordDTO dto){
        return ResponseEntity.status(201).body(paymentRecordService.create(paymentId, dto));
    }

    @GetMapping
    public ResponseEntity<List<PaymentRecordDTO>> findAllByPaymentId(@PathVariable Long paymentId){
        return ResponseEntity.status(200).body(paymentRecordService.findAllByPaymentId(paymentId));
    }
}
