package com.alvindo.spring_blogs_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
