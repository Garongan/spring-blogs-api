package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ImageResponse {
    private String id;
    private String name;
    private String path;
    private String contentType;
}
