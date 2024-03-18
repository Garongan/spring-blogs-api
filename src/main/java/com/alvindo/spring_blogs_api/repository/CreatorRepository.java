package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface CreatorRepository extends JpaRepository<Creator, String>, JpaSpecificationExecutor<Creator> {

    @Modifying
    @Query(value = "INSERT INTO creator (id, updated_at, name, email, avatar_url) VALUES (:id, :updated_at, :name, :email, :avatar_url)",
            nativeQuery = true)
    void create(@Param("id") String id,
                @Param("updated_at") Date updatedAt,
                @Param("name") String name,
                @Param("email") String email,
                @Param("avatar_url") String avatarUrl);

    @Query(value = "SELECT * FROM creator WHERE id = :id", nativeQuery = true)
    Creator getOneById(@Param("id") String id);

}
