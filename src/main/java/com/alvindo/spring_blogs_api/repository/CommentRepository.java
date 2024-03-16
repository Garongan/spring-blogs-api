package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
}
