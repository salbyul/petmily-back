package com.petmily.petmily.dto.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostSaveDto {

    @NotNull
    private String content;

}
