package com.alvindo.spring_blogs_api.service.impl;

import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.NewCommentRequest;
import com.alvindo.spring_blogs_api.dto.request.UpdateCommentRequest;
import com.alvindo.spring_blogs_api.dto.response.BlogResponse;
import com.alvindo.spring_blogs_api.dto.response.CommentResponse;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
import com.alvindo.spring_blogs_api.entity.Blog;
import com.alvindo.spring_blogs_api.entity.Comment;
import com.alvindo.spring_blogs_api.entity.Creator;
import com.alvindo.spring_blogs_api.repository.CommentRepository;
import com.alvindo.spring_blogs_api.service.BlogService;
import com.alvindo.spring_blogs_api.service.CommentService;
import com.alvindo.spring_blogs_api.service.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CreatorService creatorService;
    private final BlogService blogService;

    @Transactional
    @Override
    public CommentResponse create(NewCommentRequest request) {
        String id = UUID.randomUUID().toString();
        Date createdAt = new Date();
        Date updatedAt = new Date();
        commentRepository.create(id, createdAt, updatedAt, request.getComment(), request.getBlogId(), request.getCreatorId());

        CreatorResponse creatorResponse = creatorService.getById(request.getCreatorId());
        BlogResponse blogResponse = blogService.getById(request.getBlogId());

        Comment comment = Comment.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .comment(request.getComment())
                .creator(Creator.builder()
                        .id(creatorResponse.getId())
                        .build())
                .blog(Blog.builder()
                        .id(blogResponse.getId())
                        .build())
                .build();
        return getCommentResponse(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentResponse getById(String id) {
        Comment comment = commentRepository.getOneById(id);
        if (comment == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusMessage.NOT_FOUND);
        return getCommentResponse(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentResponse> getAll() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::getCommentResponse).toList();
    }

    @Transactional
    @Override
    public CommentResponse update(UpdateCommentRequest request) {
        Date updatedAt = new Date();
        int update = commentRepository.update(request.getComment(), updatedAt, request.getId());
        if (update == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, StatusMessage.BAD_REQUEST);
        Comment comment = Comment.builder()
                .id(request.getId())
                .updatedAt(updatedAt)
                .comment(request.getComment())
                .build();
        return getCommentResponse(comment);
    }

    @Override
    public void delete(String id) {
        commentRepository.delete(id);
    }

    private CommentResponse getCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .updatedAt(comment.getUpdatedAt().toString())
                .build();
    }
}
