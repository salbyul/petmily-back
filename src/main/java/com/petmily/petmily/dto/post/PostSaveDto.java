package com.petmily.petmily.dto.post;

import com.petmily.petmily.domain.Content;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PostSaveDto {

    @NotNull
    private Content content;

    private String hashtag;

}
