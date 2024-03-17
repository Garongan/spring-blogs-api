package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreatorResponse {
    private String id;
    private Date updatedAt;
    private String name;
    private String email;
    private String avatarUrl;
}
