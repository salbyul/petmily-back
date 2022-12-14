package com.petmily.petmily.dto.member;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class MemberLoginDto {

    @NotNull
    private String email;
    @NotNull
    private String password;



}
