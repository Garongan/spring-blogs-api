package com.alvindo.spring_blogs_api.dto.request;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBlogRequest {
    private String id;
    private String title;
    private String body;
}
