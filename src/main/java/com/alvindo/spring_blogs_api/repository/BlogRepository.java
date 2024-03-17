package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Blog;

import java.util.List;

public interface BlogRepository {
    void create (Blog blog);
    Blog getById(String id);
    List<Blog> getALl();
}
