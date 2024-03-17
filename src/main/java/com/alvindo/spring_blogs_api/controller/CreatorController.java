package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.constant.ApiUrl;
import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
import com.alvindo.spring_blogs_api.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.CREATOR_URL)
@RequiredArgsConstructor
public class CreatorController {
    private final CreatorService creatorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<CreatorResponse>>> getAll(){
        List<CreatorResponse> responses = creatorService.getAll();
        CommonResponse<List<CreatorResponse>> commonResponse = CommonResponse.<List<CreatorResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE_LIST)
                .data(responses)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<CreatorResponse>> getById(@PathVariable(name = "id") String id){
        CreatorResponse creatorResponse = creatorService.getById(id);
        CommonResponse<CreatorResponse> commonResponse = CommonResponse.<CreatorResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE)
                .data(creatorResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

}
