package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.constant.ApiUrl;
import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.NewCommentRequest;
import com.alvindo.spring_blogs_api.dto.request.UpdateCommentRequest;
import com.alvindo.spring_blogs_api.dto.response.CommentResponse;
import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import com.alvindo.spring_blogs_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.COMMENT_URL)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<CommentResponse>> create(@RequestBody NewCommentRequest request){
        CommentResponse commentResponse = commentService.create(request);

        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .httpStatus(HttpStatus.CREATED.value())
                .httpMessage(StatusMessage.SUCCESS_CREATE)
                .data(commentResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<CommentResponse>> getById(@PathVariable String id) {
        CommentResponse commentResponse = commentService.getById(id);

        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE)
                .data(commentResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping(path = "/{blog-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<CommentResponse>>> getAll(@PathVariable(name = "blog-id") String blogId){
        List<CommentResponse> commentResponses = commentService.getAllByBlogId(blogId);

        CommonResponse<List<CommentResponse>> commonResponse = CommonResponse.<List<CommentResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE_LIST)
                .data(commentResponses)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<CommentResponse>> update(@RequestBody UpdateCommentRequest request) {
        CommentResponse commentResponse = commentService.update(request);

        CommonResponse<CommentResponse> commonResponse = CommonResponse.<CommentResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_UPDATE)
                .data(commentResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String id){
        commentService.delete(id);

        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .httpStatus(HttpStatus.NO_CONTENT.value())
                .httpMessage(StatusMessage.SUCCESS_DELETE)
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }
}
