package com.alvindo.spring_blogs_api.service;

import com.alvindo.spring_blogs_api.dto.request.FilterCreatorRequest;
import com.alvindo.spring_blogs_api.dto.request.NewCreatorRequest;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
import org.springframework.data.domain.Page;


public interface CreatorService {
    CreatorResponse create(NewCreatorRequest request);
    CreatorResponse getById(String id);
    Page<CreatorResponse> getAll(FilterCreatorRequest request);

}
