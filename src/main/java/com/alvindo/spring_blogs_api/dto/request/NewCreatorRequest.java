package com.alvindo.spring_blogs_api.dto.request;

import jakarta.validation.constraints.NegativeOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@AllArgsConstructor
@NegativeOrZero
@Setter
@Getter
public class NewCreatorRequest {
    private String id;
    private Date updatedAt;
    private String name;
    private String email;
    private String avatarUrl;
}
