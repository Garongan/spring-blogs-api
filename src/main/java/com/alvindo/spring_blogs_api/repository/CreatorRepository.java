package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Creator;

import java.util.List;

public interface CreatorRepository {
    void create(Creator creator);

    Creator getById(String id);

    List<Creator> getAll();

}
