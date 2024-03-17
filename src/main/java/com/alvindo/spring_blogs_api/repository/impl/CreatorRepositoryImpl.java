package com.alvindo.spring_blogs_api.repository.impl;

import com.alvindo.spring_blogs_api.entity.Creator;
import com.alvindo.spring_blogs_api.repository.CreatorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CreatorRepositoryImpl implements CreatorRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void create(Creator creator) {
        String sql = """
                INSERT INTO creator (id, updated_at, name, email, avatar_url)
                VALUES (?, ?, ?, ?, ?);
                """;
        entityManager.createNativeQuery(sql, Creator.class)
                .setParameter(1, creator.getId())
                .setParameter(2, creator.getUpdatedAt())
                .setParameter(3, creator.getName())
                .setParameter(4, creator.getEmail())
                .setParameter(5, creator.getAvatarUrl())
                .executeUpdate();
        entityManager.flush();
    }

    @Override
    public Creator getById(String id) {
        String sql = """
                SELECT * FROM creator
                WHERE id = ?;
                """;
        try {
            return (Creator) entityManager.createNativeQuery(sql, Creator.class)
                    .setParameter(1, id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
