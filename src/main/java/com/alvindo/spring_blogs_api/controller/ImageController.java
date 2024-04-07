package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.constant.ApiUrl;
import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.NewImageRequest;
import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import com.alvindo.spring_blogs_api.dto.response.ImageResponse;
import com.alvindo.spring_blogs_api.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.IMAGE_URL)
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ObjectMapper objectMapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<ImageResponse>> create(
            @RequestPart(name = "imageDetails") String imageDetails,
            @RequestPart(name = "image") MultipartFile multipartFile
    ) {
        try {
            NewImageRequest request = objectMapper.readValue(imageDetails, new TypeReference<>() {
            });
            request.setMultipartFile(multipartFile);

            ImageResponse imageResponse = imageService.create(request);

            CommonResponse<ImageResponse> commonResponse = CommonResponse.<ImageResponse>builder()
                    .httpStatus(HttpStatus.CREATED.value())
                    .httpMessage(StatusMessage.SUCCESS_CREATE)
                    .data(imageResponse)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
        } catch (JsonProcessingException e) {
            CommonResponse<ImageResponse> commonResponse = CommonResponse.<ImageResponse>builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .httpMessage(StatusMessage.INTERNAL_SERVER_ERROR)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(commonResponse);
        }
    }

    @GetMapping(path = "/{blog-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<ImageResponse>>> getAllByBlogId (@PathVariable(name = "blog-id") String blogId) {
        List<ImageResponse> imageResponses = imageService.getAllByBlogId(blogId);

        CommonResponse<List<ImageResponse>> commonResponse = CommonResponse.<List<ImageResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE_LIST)
                .data(imageResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
