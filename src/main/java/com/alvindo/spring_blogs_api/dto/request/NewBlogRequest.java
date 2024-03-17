package com.alvindo.spring_blogs_api.dto.request;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewBlogRequest {
    private String body;
    private String title;
    private Date updatedAt;
    private String creatorId;
}
