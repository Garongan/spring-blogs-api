package com.alvindo.spring_blogs_api.service.impl;

import com.alvindo.spring_blogs_api.dto.request.NewCreatorRequest;
import com.alvindo.spring_blogs_api.dto.response.CreatorResponse;
import com.alvindo.spring_blogs_api.entity.Creator;
import com.alvindo.spring_blogs_api.repository.CreatorRepository;
import com.alvindo.spring_blogs_api.service.CreatorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final CreatorRepository creatorRepository;

    @Transactional
    @Override
    public CreatorResponse create(@NonNull NewCreatorRequest request) {
        CreatorResponse foundedCreator = getById(request.getId());
        if (foundedCreator != null) return foundedCreator;

        Creator creator = Creator.builder()
                .id(request.getId())
                .updatedAt(request.getUpdatedAt())
                .name(request.getName())
                .email(request.getEmail())
                .avatarUrl(request.getAvatarUrl())
                .build();
        creatorRepository.create(creator);
        return getCreatorResponse(creator);
    }

    @Transactional
    @Override
    public CreatorResponse getById(String id) {
        Creator creator = creatorRepository.getById(id);
        if (creator != null ) return getCreatorResponse(creator);
        return null;
    }

    @Override
    public List<CreatorResponse> getAll() {
        List<Creator> creators = creatorRepository.getAll();
        return creators.stream().map(this::getCreatorResponse).toList();
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
