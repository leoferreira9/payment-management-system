package leonardo.payment_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leonardo.payment_management_system.dto.paymentRecord.CreatePaymentRecordDTO;
import leonardo.payment_management_system.dto.paymentRecord.PaymentRecordDTO;
import leonardo.payment_management_system.service.PaymentRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment Records", description = "Operations related to payment records")
@RestController
@RequestMapping("/payments/{paymentId}/records")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    public PaymentRecordController (PaymentRecordService paymentRecordService){
        this.paymentRecordService = paymentRecordService;
    }

    @Operation(summary = "Add new Payment record", description = "Creates a new record for the payment")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "payment record successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "409", description = "Invalid payment status transition")
    })
    @PostMapping
    public ResponseEntity<PaymentRecordDTO> create(
            @Parameter(description = "payment id", example = "1", required = true)
            @PathVariable Long paymentId,
            @RequestBody @Valid CreatePaymentRecordDTO dto){
        return ResponseEntity.status(201).body(paymentRecordService.create(paymentId, dto));
    }


    @Operation(summary = "Find all payment records", description = "Find all payment records by payment ID")
    @ApiResponse(responseCode = "200", description = "found all payment records successfully")
    @GetMapping
    public ResponseEntity<Page<PaymentRecordDTO>> findAllByPaymentId(
            @Parameter(description = "payment ID", example = "1", required = true)
            @PathVariable Long paymentId,

            @Parameter(description = "page number", example = "0")
            @RequestParam(defaultValue = "0") int pageNumber,

            @Parameter(description = "page size", example = "10")
            @RequestParam(defaultValue = "10") int pageSize
    ){

        if(pageSize > 50) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok().body(paymentRecordService.findAllByPaymentId(paymentId, pageable));
    }
}
