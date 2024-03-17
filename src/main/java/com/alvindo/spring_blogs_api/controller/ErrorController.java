package com.alvindo.spring_blogs_api.controller;

import com.alvindo.spring_blogs_api.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({Exception.class, ResponseStatusException.class})
    public ResponseEntity<CommonResponse<?>> responseStatusExceptionHandler(HttpStatus httpStatus, ResponseStatusException message){
        CommonResponse<?> response = CommonResponse.builder()
                .httpStatus(httpStatus.value())
                .httpMessage(message.getReason())
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }
}
