package com.petmily.petmily.dto.member;

import com.petmily.petmily.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class MemberProfileDto {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String nickname;
    private String statusMessage;
}
