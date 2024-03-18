package com.alvindo.spring_blogs_api.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewImageRequest {
    private MultipartFile multipartFile;
    private String contentType;
    private String path;
    private String size;
    private String blogId;
}
