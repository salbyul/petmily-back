package com.petmily.petmily.dto.post;

import com.petmily.petmily.domain.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostShowDto implements Comparable<PostShowDto>{

    private String author;
    private List<String> hashtagList = new ArrayList<>();
    private String content;
    private List<byte[]> resourceList = new ArrayList<>();
    private LocalDateTime createdDate;

    @Override
    public int compareTo(PostShowDto o) {
        return this.createdDate.compareTo(o.createdDate);
    }
}
