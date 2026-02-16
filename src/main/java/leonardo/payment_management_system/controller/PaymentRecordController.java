package leonardo.payment_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import leonardo.payment_management_system.common.pagination.PageResponse;
import leonardo.payment_management_system.dto.paymentRecord.PaymentRecordDTO;
import leonardo.payment_management_system.service.PaymentRecordService;
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

    @Operation(summary = "Find all payment records", description = "Find all payment records by payment ID")
    @ApiResponse(responseCode = "200", description = "found all payment records successfully")
    @GetMapping
    public ResponseEntity<PageResponse<PaymentRecordDTO>> findAllByPaymentId(
            Pageable pageable,

            @Parameter(description = "payment ID", example = "1", required = true)
            @PathVariable Long paymentId
    ){
        PageResponse<PaymentRecordDTO> records = PageResponse.from(paymentRecordService.findAllByPaymentId(paymentId, pageable));
        return ResponseEntity.ok().body(records);
    }
}
