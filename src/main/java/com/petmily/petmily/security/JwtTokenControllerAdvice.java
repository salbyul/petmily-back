package com.petmily.petmily.security;

import com.petmily.petmily.exception.handler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class JwtTokenControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult invalidationToken(JwtTokenException e) {
        log.error("invalidationToken ERROR!!", e);
        return new ErrorResult(JwtTokenException.INVALIDATION, "유효하지 않은 토큰");
    }
}
