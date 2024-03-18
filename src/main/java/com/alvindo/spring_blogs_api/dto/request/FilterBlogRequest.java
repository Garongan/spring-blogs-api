package com.alvindo.spring_blogs_api.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FilterBlogRequest {
    private String title;
    private Integer day;
    private Integer month;
    private Integer year;
    private String sortBy;
    private String sortDirection;
    private Integer page;
    private Integer size;
}
