package com.alvindo.spring_blogs_api.dto.request;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentRequest {
    private String comment;
    private String blogId;
    private String creatorId;
}
