package com.petmily.petmily.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class MemberModifyDto {

    @NotNull
    @Length(min = 5, max = 15)
    @NotBlank
    private String nickname;
    private String statusMessage;
}
