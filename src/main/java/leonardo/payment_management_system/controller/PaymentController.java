package leonardo.payment_management_system.controller;

import jakarta.validation.Valid;
import leonardo.payment_management_system.dto.payment.CreatePaymentDTO;
import leonardo.payment_management_system.dto.payment.PaymentDTO;
import leonardo.payment_management_system.service.PaymentService;
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
    public PaymentDTO create(@RequestBody @Valid CreatePaymentDTO dto){
        return paymentService.create(dto);
    }

    @GetMapping("/{id}")
    public PaymentDTO findById(@PathVariable Long id){
        return paymentService.findById(id);
    }

    @GetMapping
    public List<PaymentDTO> findAll(){
        return paymentService.findAll();
    }
}
