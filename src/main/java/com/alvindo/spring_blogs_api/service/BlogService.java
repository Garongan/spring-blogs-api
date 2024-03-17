package com.alvindo.spring_blogs_api.service;

import com.alvindo.spring_blogs_api.dto.request.NewBlogRequest;
import com.alvindo.spring_blogs_api.dto.response.BlogResponse;

import java.util.List;

public interface BlogService {
    BlogResponse create(NewBlogRequest request);

    BlogResponse getById(String id);

    List<BlogResponse> getAll();
}
