package com.hilbert.shared.search.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams {
    private String searchQuery;
    private String sortBy;
    private Boolean isAscending;
    private Integer page;
    private Integer itemsPerPage;
}
