package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {
    private Integer page;
    private Integer totalPages;
    private Long totalElement;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
