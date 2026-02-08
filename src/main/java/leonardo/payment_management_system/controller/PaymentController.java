package leonardo.payment_management_system.controller;

import jakarta.validation.Valid;
import leonardo.payment_management_system.dto.payment.CreatePaymentDTO;
import leonardo.payment_management_system.dto.payment.PaymentDTO;
import leonardo.payment_management_system.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> create(@RequestBody @Valid CreatePaymentDTO dto){
        return ResponseEntity.status(201).body(paymentService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(paymentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDTO>> findAll(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize){
        Page<PaymentDTO> paymentsPage = paymentService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok().body(paymentsPage);
    }
}
