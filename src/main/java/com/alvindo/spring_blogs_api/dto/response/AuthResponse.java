package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthResponse {
    private String id;
    private String name;
    private String email;
    private List<?> roles;
    private String token;
}
