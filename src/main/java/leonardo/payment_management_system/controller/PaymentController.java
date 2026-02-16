package leonardo.payment_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leonardo.payment_management_system.common.pagination.PageResponse;
import leonardo.payment_management_system.dto.payment.CreatePaymentDTO;
import leonardo.payment_management_system.dto.payment.PaymentDTO;
import leonardo.payment_management_system.enums.PaymentStatus;
import leonardo.payment_management_system.enums.PaymentType;
import leonardo.payment_management_system.service.PaymentService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Payments", description = "Operations related to payments")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @Operation(summary = "Add new Payment", description = "Creates a new payment")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "payment successfully created"),
            @ApiResponse(responseCode = "400", description = "failed to create payment")
    })
    @PostMapping
    public ResponseEntity<PaymentDTO> create(@RequestBody @Valid CreatePaymentDTO dto){
        PaymentDTO paymentDTO = paymentService.create(dto);
        URI location = URI.create("/payments/" + paymentDTO.getId());
        return ResponseEntity.created(location).body(paymentDTO);
    }

    @Operation(summary = "Find payment", description = "Return a payment by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "payment successfully found"),
            @ApiResponse(responseCode = "404", description = "payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findById(
            @Parameter(description = "Payment ID to be found", example = "1", required = true)
            @PathVariable Long id){
        return ResponseEntity.ok().body(paymentService.findById(id));
    }

    @Operation(summary = "Find payments", description = "Find all payments")
    @ApiResponse(responseCode = "200", description = "found all payments successfully")
    @GetMapping
    public ResponseEntity<PageResponse<PaymentDTO>> findAll(
            @RequestParam(required = false) PaymentStatus status,
            @RequestParam(required = false) PaymentType paymentType,
            Pageable pageable){
        PageResponse<PaymentDTO> paymentsPage = PageResponse.from(paymentService.findAll(status, paymentType, pageable));
        return ResponseEntity.ok().body(paymentsPage);
    }

    @Operation(summary = "Confirm payment", description = "Confirms a pending payment and marks it as PAID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "payment successfully confirmed"),
            @ApiResponse(responseCode = "409", description = "Invalid payment status transition")
    })
    @PostMapping("/{paymentId}/confirm")
    public ResponseEntity<PaymentDTO> confirm(
            @Parameter(description = "payment id", example = "1", required = true)
            @PathVariable Long paymentId
    ){
        return ResponseEntity.ok().body(paymentService.confirmPayment(paymentId));
    }

    @Operation(summary = "Cancel payment", description = "Cancels a pending payment and marks it as CANCELLED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "payment successfully cancelled"),
            @ApiResponse(responseCode = "409", description = "Invalid payment status transition")
    })
    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<PaymentDTO> cancel(
            @Parameter(description = "payment id", example = "1", required = true)
            @PathVariable Long paymentId
    ){
        return ResponseEntity.ok().body(paymentService.cancelPayment(paymentId));
    }

    @Operation(summary = "Refund payment", description = "Refunds a paid payment and marks it as REFUNDED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "payment successfully refunded"),
            @ApiResponse(responseCode = "409", description = "Invalid payment status transition")
    })
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentDTO> refund(
            @Parameter(description = "payment id", example = "1", required = true)
            @PathVariable Long paymentId
    ){
        return ResponseEntity.ok().body(paymentService.refundPayment(paymentId));
    }
}
