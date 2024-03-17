package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.dto.response.AuthResponse;
import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<AuthResponse>> registerUser(@AuthenticationPrincipal OidcUser principal) {

        String[] split = principal.getName().split("\\|");

        AuthResponse authResponse = AuthResponse.builder()
                .id(split[1])
                .name(principal.getNickName())
                .email(principal.getEmail())
                .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .token(principal.getIdToken().getTokenValue())
                .build();

        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage("Logged")
                .data(authResponse)
                .build();
        return ResponseEntity.ok(response);
    }

}
