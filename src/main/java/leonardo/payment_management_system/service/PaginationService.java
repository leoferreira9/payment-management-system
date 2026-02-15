package leonardo.payment_management_system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaginationService {

    @Value("${app.pagination.max-page-size}")
    private int maxPageSize;

    public Pageable createPageable(Pageable pageable){
        int size = Math.min(pageable.getPageSize(), maxPageSize);
        return PageRequest.of(
                pageable.getPageNumber(),
                size,
                pageable.getSort()
        );
    }
}
