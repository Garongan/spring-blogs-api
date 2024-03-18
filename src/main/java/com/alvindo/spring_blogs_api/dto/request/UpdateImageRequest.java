package com.alvindo.spring_blogs_api.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateImageRequest {
    private String id;
    private String path;
    private String contentType;
    private String size;
}
