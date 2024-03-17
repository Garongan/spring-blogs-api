package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.NewCreatorRequest;
import com.alvindo.spring_blogs_api.dto.response.AuthResponse;
import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import com.alvindo.spring_blogs_api.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final CreatorService creatorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<AuthResponse>> loginCreator(@AuthenticationPrincipal OidcUser principal) {

        NewCreatorRequest request = NewCreatorRequest.builder()
                .id(principal.getName())
                .updatedAt(new Date(principal.getUpdatedAt().toEpochMilli()))
                .name(principal.getNickName())
                .email(principal.getEmail())
                .avatarUrl(principal.getPicture())
                .build();

        creatorService.create(request);

        AuthResponse authResponse = AuthResponse.builder()
                .email(principal.getEmail())
                .token(principal.getIdToken().getTokenValue())
                .build();

        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .httpStatus(HttpStatus.CREATED.value())
                .httpMessage(StatusMessage.SUCCESS_CREATE)
                .data(authResponse)
                .build();
        return ResponseEntity.ok(response);
    }

}
