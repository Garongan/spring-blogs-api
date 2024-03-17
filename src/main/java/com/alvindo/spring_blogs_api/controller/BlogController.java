package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.constant.ApiUrl;
import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.NewBlogRequest;
import com.alvindo.spring_blogs_api.dto.response.BlogResponse;
import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import com.alvindo.spring_blogs_api.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(ApiUrl.BLOG_URL)
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BlogResponse>> create(@RequestBody NewBlogRequest request){
        BlogResponse blogResponse = blogService.create(request);
        request.setUpdatedAt(new Date());
        CommonResponse<BlogResponse> response = CommonResponse.<BlogResponse>builder()
                .httpStatus(HttpStatus.CREATED.value())
                .httpMessage(StatusMessage.SUCCESS_CREATE)
                .data(blogResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<BlogResponse>> getById(@PathVariable String id){
        BlogResponse blogResponse = blogService.getById(id);
        CommonResponse<BlogResponse> commonResponse = CommonResponse.<BlogResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE)
                .data(blogResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<BlogResponse>>> getAll(){
        List<BlogResponse> blogResponses = blogService.getAll();
        CommonResponse<List<BlogResponse>> commonResponse = CommonResponse.<List<BlogResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE)
                .data(blogResponses)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
