package com.alvindo.spring_blogs_api.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateCommentRequest {
    private String id;
    private String comment;
}
