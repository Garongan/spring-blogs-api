package com.alvindo.spring_blogs_api.service.impl;

import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.NewBlogRequest;
import com.alvindo.spring_blogs_api.dto.response.BlogResponse;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
import com.alvindo.spring_blogs_api.entity.Blog;
import com.alvindo.spring_blogs_api.entity.Creator;
import com.alvindo.spring_blogs_api.repository.BlogRepository;
import com.alvindo.spring_blogs_api.service.BlogService;
import com.alvindo.spring_blogs_api.service.CreatorService;
import lombok.NonNull;
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
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final CreatorService creatorService;

    @Transactional
    @Override
    public BlogResponse create(NewBlogRequest request) {
        String id = UUID.randomUUID().toString();
        Date createdAt = new Date();

        CreatorResponse creatorResponse = creatorService.getById(request.getCreatorId());

        if (creatorResponse == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusMessage.NOT_FOUND);
        Creator creator = Creator.builder()
                .id(creatorResponse.getId())
                .name(creatorResponse.getName())
                .avatarUrl(creatorResponse.getAvatarUrl())
                .email(creatorResponse.getEmail())
                .updatedAt(creatorResponse.getUpdatedAt())
                .build();

        blogRepository.create(
                id,
                createdAt,
                request.getUpdatedAt(),
                request.getBody(),
                request.getTitle(),
                request.getCreatorId());
        Blog blog = Blog.builder()
                .id(id)
                .createdAt(createdAt)
                .title(request.getTitle())
                .body(request.getBody())
                .updatedAt(request.getUpdatedAt())
                .creator(creator)
                .build();
        return getBlogResponse(blog);
    }

    @Override
    public BlogResponse getById(String id) {
        Blog blog = blogRepository.getOneById(id);
        if (blog == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusMessage.NOT_FOUND);
        return getBlogResponse(blog);
    }

    @Override
    public List<BlogResponse> getAll() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream().map(this::getBlogResponse).toList();
    }

    private BlogResponse getBlogResponse(@NonNull Blog blog){
        return BlogResponse.builder()
                .id(blog.getId())
                .updatedAt(blog.getUpdatedAt())
                .title(blog.getTitle())
                .body(blog.getBody())
                .creatorName(blog.getCreator().getName())
                .build();
    }
}
