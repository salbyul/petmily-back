package com.petmily.petmily.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberJoinDto {

    private String email;
    private String password;
    private String nickname;

    public MemberJoinDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
