package leonardo.payment_management_system.mapper;

import leonardo.payment_management_system.dto.paymentRecord.PaymentRecordDTO;
import leonardo.payment_management_system.entity.PaymentRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentRecordMapper {

    @Mapping(source = "payment.id", target = "paymentId")
    PaymentRecordDTO toDto(PaymentRecord paymentRecord);
}
