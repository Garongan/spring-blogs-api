package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BlogRepository extends JpaRepository<Blog, String>, JpaSpecificationExecutor<Blog> {

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

    @Modifying
    @Query(value = "UPDATE blog SET created_at = :updatedAt, title = :title, body = :body WHERE id = :id", nativeQuery = true)
    int update(@Param("updatedAt") Date updatedAt,
                @Param("title") String title,
                @Param("body") String body,
                @Param("id") String id);

    @Modifying
    @Query(value = "DELETE FROM blog WHERE id = :id", nativeQuery = true)
    void delete(@Param("id") String id);

}
