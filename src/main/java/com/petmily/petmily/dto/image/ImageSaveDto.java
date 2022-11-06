package com.petmily.petmily.dto.image;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class ImageSaveDto {

    private Member member;
    private Post post;
    private String uploadedName;
    private String storedName;
}
