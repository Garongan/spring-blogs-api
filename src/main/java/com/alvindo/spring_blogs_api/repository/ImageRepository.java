package com.alvindo.spring_blogs_api.repository;

import com.alvindo.spring_blogs_api.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
