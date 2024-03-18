package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.constant.ApiUrl;
import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.FilterBlogRequest;
import com.alvindo.spring_blogs_api.dto.request.NewBlogRequest;
import com.alvindo.spring_blogs_api.dto.request.UpdateBlogRequest;
import com.alvindo.spring_blogs_api.dto.response.BlogResponse;
import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import com.alvindo.spring_blogs_api.dto.response.PaginationResponse;
import com.alvindo.spring_blogs_api.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<CommonResponse<List<BlogResponse>>> getAll(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "day", required = false) Integer day,
            @RequestParam(name = "month", required = false) Integer month,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ){
        FilterBlogRequest request = FilterBlogRequest.builder()
                .title(title)
                .day(day)
                .month(month)
                .year(year)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .page(page)
                .size(size)
                .build();
        Page<BlogResponse> responsePage = blogService.getAll(request);

        PaginationResponse paginationResponse = PaginationResponse.builder()
                .totalPages(responsePage.getTotalPages())
                .page(responsePage.getNumber() + 1)
                .totalElement(responsePage.getTotalElements())
                .hasNext(responsePage.hasNext())
                .hasPrevious(responsePage.hasPrevious())
                .build();

        CommonResponse<List<BlogResponse>> commonResponse = CommonResponse.<List<BlogResponse>>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_RETRIEVE_LIST)
                .data(responsePage.getContent())
                .paginationResponse(paginationResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<BlogResponse>> update(@RequestBody UpdateBlogRequest request){
        BlogResponse updated = blogService.update(request);
        CommonResponse<BlogResponse> response = CommonResponse.<BlogResponse>builder()
                .httpStatus(HttpStatus.OK.value())
                .httpMessage(StatusMessage.SUCCESS_UPDATE)
                .data(updated)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> delete(@PathVariable String id){
        blogService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .httpStatus(HttpStatus.NO_CONTENT.value())
                .httpMessage(StatusMessage.SUCCESS_DELETE)
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
