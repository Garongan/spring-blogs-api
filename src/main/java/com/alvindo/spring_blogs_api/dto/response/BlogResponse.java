package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse {
    private String id;
    private Date updatedAt;
    private String title;
    private String body;
    private String creatorName;
}
