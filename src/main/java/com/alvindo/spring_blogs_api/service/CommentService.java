package com.alvindo.spring_blogs_api.service;

import com.alvindo.spring_blogs_api.dto.request.NewCommentRequest;
import com.alvindo.spring_blogs_api.dto.request.UpdateCommentRequest;
import com.alvindo.spring_blogs_api.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse create(NewCommentRequest request);

    CommentResponse getById(String id);

    List<CommentResponse> getAll();

    CommentResponse update(UpdateCommentRequest request);

    void delete(String id);
}
