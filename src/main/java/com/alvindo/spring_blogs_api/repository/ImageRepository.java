package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ImageRepository extends JpaRepository<Image, String> {
    @Modifying
    @Query(value = "INSERT INTO image (id, created_at, updated_at, content_type, path, size, blog_id) VALUES (:id, :createdAt, :updatedAt, :contentType, :path, :size, :blogId)", nativeQuery = true)
    void create (
            @Param("id") String id,
            @Param("createdAt") Date createdAt,
            @Param("updatedAt") Date updatedAt,
            @Param("contentType") String contentType,
            @Param("path") String path,
            @Param("size") Long size,
            @Param("blogId") String blogId);
}
