package com.alvindo.spring_blogs_api.service;

import com.alvindo.spring_blogs_api.dto.request.FilterBlogRequest;
import com.alvindo.spring_blogs_api.dto.request.NewBlogRequest;
import com.alvindo.spring_blogs_api.dto.response.BlogResponse;
import org.springframework.data.domain.Page;


public interface BlogService {
    BlogResponse create(NewBlogRequest request);

    BlogResponse getById(String id);

    Page<BlogResponse> getAll(FilterBlogRequest request);
}
