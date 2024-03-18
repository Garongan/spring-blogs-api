package com.alvindo.spring_blogs_api.service.impl;

import com.alvindo.spring_blogs_api.dto.request.FilterCreatorRequest;
import com.alvindo.spring_blogs_api.dto.request.NewCreatorRequest;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
import com.alvindo.spring_blogs_api.entity.Creator;
import com.alvindo.spring_blogs_api.repository.CreatorRepository;
import com.alvindo.spring_blogs_api.service.CreatorService;
import com.alvindo.spring_blogs_api.specification.CreatorSpecification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final CreatorRepository creatorRepository;
    private final CreatorSpecification creatorSpecification;

    @Transactional
    @Override
    public CreatorResponse create(@NonNull NewCreatorRequest request) {
        CreatorResponse foundedCreator = getById(request.getId());
        if (foundedCreator != null) return foundedCreator;

        creatorRepository.create(
                request.getId(),
                request.getUpdatedAt(),
                request.getName(),
                request.getEmail(),
                request.getAvatarUrl());
        Creator creator = Creator.builder()
                .id(request.getId())
                .updatedAt(request.getUpdatedAt())
                .name(request.getName())
                .email(request.getEmail())
                .avatarUrl(request.getAvatarUrl())
                .build();
        return getCreatorResponse(creator);
    }

    @Transactional(readOnly = true)
    @Override
    public CreatorResponse getById(String id) {
        Creator creator = creatorRepository.getOneById(id);
        if (creator != null ) return getCreatorResponse(creator);
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CreatorResponse> getAll(FilterCreatorRequest request) {
        Specification<Creator> specification = creatorSpecification.getCreatorSpecification(request);
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy());
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);
        Page<Creator> creators = creatorRepository.findAll(specification, pageable);
        return creators.map(this::getCreatorResponse);
    }

    private CreatorResponse getCreatorResponse(@NonNull Creator creator) {
        return CreatorResponse.builder()
                .id(creator.getId())
                .updatedAt(creator.getUpdatedAt())
                .name(creator.getName())
                .email(creator.getEmail())
                .avatarUrl(creator.getAvatarUrl())
                .build();
    }
}
