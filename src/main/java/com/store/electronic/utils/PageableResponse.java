package com.store.electronic.utils;

import lombok.*;

import java.util.List;

// customizing API paging and sorting

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse<T>{

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean lastPage;

}
