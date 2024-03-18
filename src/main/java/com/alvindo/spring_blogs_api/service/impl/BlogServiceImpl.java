package com.alvindo.spring_blogs_api.service.impl;

import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.FilterBlogRequest;
import com.alvindo.spring_blogs_api.dto.request.NewBlogRequest;
import com.alvindo.spring_blogs_api.dto.request.UpdateBlogRequest;
import com.alvindo.spring_blogs_api.dto.response.BlogResponse;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
import com.alvindo.spring_blogs_api.entity.Blog;
import com.alvindo.spring_blogs_api.entity.Creator;
import com.alvindo.spring_blogs_api.repository.BlogRepository;
import com.alvindo.spring_blogs_api.service.BlogService;
import com.alvindo.spring_blogs_api.service.CreatorService;
import com.alvindo.spring_blogs_api.specification.BlogSpecification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final CreatorService creatorService;
    private final BlogSpecification blogSpecification;

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

    @Transactional(readOnly = true)
    @Override
    public BlogResponse getById(String id) {
        Blog blog = blogRepository.getOneById(id);
        if (blog == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusMessage.NOT_FOUND);
        return getBlogResponse(blog);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BlogResponse> getAll(FilterBlogRequest request) {
        Specification<Blog> specification = blogSpecification.getBlogSpecification(request);
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy());
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);
        Page<Blog> pages = blogRepository.findAll(specification, pageable);
        return pages.map(this::getBlogResponse);
    }

    @Transactional
    @Override
    public BlogResponse update(UpdateBlogRequest request) {
        int update = blogRepository.update(new Date(), request.getTitle(), request.getBody(), request.getId());
        if (update == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, StatusMessage.BAD_REQUEST);

        return getById(request.getId());
    }

    @Transactional
    @Override
    public void delete(String id) {
        blogRepository.delete(id);
    }

    private BlogResponse getBlogResponse(@NonNull Blog blog){
        return BlogResponse.builder()
                .id(blog.getId())
                .createdAt(blog.getCreatedAt().toString())
                .title(blog.getTitle())
                .body(blog.getBody())
                .creatorName(blog.getCreator().getName())
                .build();
    }
}
