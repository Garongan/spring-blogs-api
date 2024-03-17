package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BlogRepository extends JpaRepository<Blog, String> {

    @Modifying
    @Query(value = "INSERT INTO blog (id, created_at, updated_at, body, title, creator_id) VALUES (:id, :created_at, :updated_at, :body, :title, :creator_id)",
            nativeQuery = true)
    void create (@Param("id") String id,
                 @Param("created_at") Date createdAt,
                 @Param("updated_at") Date updatedAt,
                 @Param("body") String body,
                 @Param("title") String title,
                 @Param("creator_id") String creatorId);

    @Query(value = "SELECT * FROM blog WHERE id = :id", nativeQuery = true)
    Blog getOneById(@Param("id") String id);

}
