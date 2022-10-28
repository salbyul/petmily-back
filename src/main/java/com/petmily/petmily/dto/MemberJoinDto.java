package com.petmily.petmily.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@Setter
public class MemberJoinDto {

    @NotNull
    @Email
    private String email;
    @NotNull
    @Length(min = 8, max = 20)
    private String password;
    @NotNull
    @Length(min = 5, max = 15)
    private String nickname;
}
