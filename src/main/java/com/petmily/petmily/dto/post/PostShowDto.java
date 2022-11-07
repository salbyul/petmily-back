package com.petmily.petmily.dto.post;

import com.petmily.petmily.domain.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostShowDto {

    private List<String> hashtagList = new ArrayList<>();
    private String content;
    private List<byte[]> resourceList = new ArrayList<>();
}
