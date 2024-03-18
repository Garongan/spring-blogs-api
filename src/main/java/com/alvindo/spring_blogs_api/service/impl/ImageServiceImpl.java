package com.alvindo.spring_blogs_api.service.impl;

import com.alvindo.spring_blogs_api.constant.StatusMessage;
import com.alvindo.spring_blogs_api.dto.request.NewImageRequest;
import com.alvindo.spring_blogs_api.dto.response.ImageResponse;
import com.alvindo.spring_blogs_api.entity.Image;
import com.alvindo.spring_blogs_api.repository.ImageRepository;
import com.alvindo.spring_blogs_api.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final Path path;

    @Autowired
    public ImageServiceImpl(
            ImageRepository imageRepository,
            @Value("${spring_blogs_api.image.path}") String path) {
        this.imageRepository = imageRepository;
        this.path = Paths.get(path);
    }

    @PostConstruct
    public void initPath() {
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, StatusMessage.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ImageResponse create(NewImageRequest request) {
        try {
            MultipartFile multipartFile = request.getMultipartFile();
            if (!List.of("image/jpg", "image/jpeg", "image/png", "image/svg+xml").contains(multipartFile.getContentType()))
                throw new ConstraintViolationException("invalid image type", null);
            String fileName = System.currentTimeMillis() + multipartFile.getName();
            Path filePath = path.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), filePath);

            Image image = Image.builder()
                    .name(fileName)
                    .path(filePath.toString())
                    .size(multipartFile.getSize())
                    .contentType(multipartFile.getContentType())
                    .build();

            String id = UUID.randomUUID().toString();

            imageRepository.create(id, new Date(), new Date(), multipartFile.getContentType(), image.getPath(), multipartFile.getSize(), request.getBlogId());
            return ImageResponse.builder()
                    .id(id)
                    .name(image.getName())
                    .contentType(image.getContentType())
                    .path(image.getPath())
                    .build();

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, StatusMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Resource getById(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, StatusMessage.IMAGE_NOT_FOUND));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusMessage.IMAGE_NOT_FOUND);
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, StatusMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(String id) {
        try {
            Image image = imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, StatusMessage.IMAGE_NOT_FOUND));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusMessage.IMAGE_NOT_FOUND);
            Files.delete(filePath);
            imageRepository.deleteById(id);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, StatusMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
