package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, String> {
}
