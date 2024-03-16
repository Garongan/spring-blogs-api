package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatorRepository extends JpaRepository<Creator, String> {
}
