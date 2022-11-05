package com.petmily.petmily.dto.post;

import com.petmily.petmily.domain.Hashtag;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PostSaveDto {

    @NotNull
    private String content;

    private List<Hashtag> hashtag;

}
