package com.alvindo.spring_blogs_api.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AuthResponse {
    private String email;
    private String token;
}
