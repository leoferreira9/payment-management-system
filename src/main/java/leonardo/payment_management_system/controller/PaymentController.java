package leonardo.payment_management_system.controller;

import jakarta.validation.Valid;
import leonardo.payment_management_system.dto.payment.CreatePaymentDTO;
import leonardo.payment_management_system.dto.payment.PaymentDTO;
import leonardo.payment_management_system.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> create(@RequestBody @Valid CreatePaymentDTO dto){
        return ResponseEntity.status(201).body(paymentService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(paymentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> findAll(){
        return ResponseEntity.status(200).body(paymentService.findAll());
    }
}
