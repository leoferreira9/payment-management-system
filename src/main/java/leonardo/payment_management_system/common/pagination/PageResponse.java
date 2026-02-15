package leonardo.payment_management_system.common.pagination;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponse<T>{

    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final int numberOfElements;
    private final boolean first;
    private final boolean last;

    private PageResponse(List<T> content, int page, int size, long totalElements, int totalPages, int numberOfElements,boolean first, boolean last){
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.last = last;
    }

    public static <T> PageResponse<T> from(Page<T> page){
        int pageNumber = page.getNumber() + 1;
        return new PageResponse<>(
                page.getContent(),
                pageNumber,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.isFirst(),
                page.isLast()
        );
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }
}

