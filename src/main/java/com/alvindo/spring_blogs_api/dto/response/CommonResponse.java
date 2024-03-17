package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommonResponse<T> {
    private Integer httpStatus;
    private String httpMessage;
    private T data;
}
