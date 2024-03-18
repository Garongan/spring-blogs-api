package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String id;
    private String updatedAt;
    private String comment;
}
