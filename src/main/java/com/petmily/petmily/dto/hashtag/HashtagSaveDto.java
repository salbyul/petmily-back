package com.petmily.petmily.dto.hashtag;

import com.petmily.petmily.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HashtagSaveDto {

    private String hashtagName;
    private Post post;
}
