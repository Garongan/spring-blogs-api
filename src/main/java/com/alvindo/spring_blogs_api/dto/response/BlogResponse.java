package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponse {
    private String id;
    private String createdAt;
    private String title;
    private String body;
    private String creatorName;
    private List<ImageResponse> imageResponse;
    private List<CommentResponse> commentResponse;
}
