package leonardo.payment_management_system.controller;

import jakarta.validation.Valid;
import leonardo.payment_management_system.dto.paymentRecord.CreatePaymentRecordDTO;
import leonardo.payment_management_system.dto.paymentRecord.PaymentRecordDTO;
import leonardo.payment_management_system.service.PaymentRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<PaymentRecordDTO>> findAllByPaymentId(
            @PathVariable Long paymentId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ){

        if(pageSize > 50) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok().body(paymentRecordService.findAllByPaymentId(paymentId, pageable));
    }
}
