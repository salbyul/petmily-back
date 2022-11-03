package com.petmily.petmily.web.controller.member.advice;

import com.petmily.petmily.exception.handler.ErrorResult;
import com.petmily.petmily.exception.member.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.petmily.petmily.web.controller.member")
public class MemberControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberException.class)
    public ErrorResult duplicateMember(MemberException e) {
        log.error("duplicate ERROR!!", e);
        return new ErrorResult(MemberException.DUPLICATE, "닉네임 중복");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult invalidMember(MethodArgumentNotValidException e) {
        log.error("invalid ERROR", e);
        return new ErrorResult(MemberException.INVALID, "제약 위반");
    }
}
