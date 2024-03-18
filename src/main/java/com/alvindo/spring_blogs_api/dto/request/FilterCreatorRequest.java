package com.alvindo.spring_blogs_api.dto.request;

import lombok.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FilterCreatorRequest {
    private String name;
    private String sortBy;
    private String sortDirection;
    private Integer page;
    private Integer size;
}
