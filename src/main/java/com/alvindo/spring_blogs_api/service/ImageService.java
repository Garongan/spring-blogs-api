package com.alvindo.spring_blogs_api.service;

import com.alvindo.spring_blogs_api.dto.request.NewImageRequest;
import com.alvindo.spring_blogs_api.dto.response.ImageResponse;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ImageService {

    ImageResponse create(NewImageRequest request);

    Resource getById(String id);

    void delete(String id);
}
