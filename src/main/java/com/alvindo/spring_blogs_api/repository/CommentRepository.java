package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.dto.response.CommentResponse;
import com.alvindo.spring_blogs_api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {

    @Modifying
    @Query(value = "INSERT INTO comment (id, created_at, updated_at, comment, blog_id, creator_id) VALUES (:id, :createdAt, :updatedAt, :comment, :blogId, :creatorId)", nativeQuery = true)
    void create (
            @Param("id") String id,
            @Param("createdAt") Date createdAt,
            @Param("updatedAt") Date updatedAt,
            @Param("comment") String comment,
            @Param("blogId") String blogId,
            @Param("creatorId") String creatorId);

    @Query(value = "SELECT * FROM comment WHERE id = :id", nativeQuery = true)
    Comment getOneById(@Param("id") String id);

    @Query(value = "SELECT * FROM comment WHERE blog_id = :blogId", nativeQuery = true)
    List<CommentResponse> getAllByBlogId(@Param("blogId") String blogId);

    @Modifying
    @Query(value = "UPDATE comment SET (comment, updated_at) VALUES (:comment, :updatedAt) WHERE id = :id", nativeQuery = true)
    int update(@Param("comment") String comment,
               @Param("updatedAt") Date updatedAt,
               @Param("id") String id);

    @Modifying
    @Query(value = "DELETE FROM comment WHERE id = :id", nativeQuery = true)
    void delete(@Param("id") String id);
}
