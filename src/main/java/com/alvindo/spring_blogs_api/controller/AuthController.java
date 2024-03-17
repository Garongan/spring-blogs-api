package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.dto.request.NewCreatorRequest;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
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
    public ResponseEntity<CommonResponse<CreatorResponse>> loginCreator(@AuthenticationPrincipal OidcUser principal) {

        String[] split = principal.getName().split("\\|");

        NewCreatorRequest request = NewCreatorRequest.builder()
                .id(split[1])
                .updatedAt(new Date(principal.getUpdatedAt().toEpochMilli()))
                .name(principal.getNickName())
                .email(principal.getEmail())
                .avatarUrl(principal.getPicture())
                .build();

        CreatorResponse creatorResponse = creatorService.create(request);
        creatorResponse.setToken(principal.getIdToken().getTokenValue());

        CommonResponse<CreatorResponse> response = CommonResponse.<CreatorResponse>builder()
                .httpStatus(HttpStatus.CREATED.value())
                .httpMessage("Logged")
                .data(creatorResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/test")
    public String testToken(){
        return "test token success";
    }

}
