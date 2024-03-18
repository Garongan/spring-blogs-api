package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.constant.ApiUrl;
import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.FilterCreatorRequest;
import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
import com.alvindo.spring_blogs_api.dto.response.PaginationResponse;
import com.alvindo.spring_blogs_api.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<CommonResponse<List<CreatorResponse>>> getAll(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ){
        FilterCreatorRequest request = FilterCreatorRequest.builder()
                .name(name)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .page(page)
                .size(size)
                .build();

        Page<CreatorResponse> responses = creatorService.getAll(request);

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .totalPages(responses.getTotalPages())
                .totalElement(responses.getTotalElements())
                .hasNext(responses.hasNext())
                .hasPrevious(responses.hasPrevious())
                .build();

        CommonResponse<List<CreatorResponse>> commonResponse = CommonResponse.<List<CreatorResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE_LIST)
                .data(responses.getContent())
                .paginationResponse(paginationResponse)
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
