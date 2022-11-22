package com.petmily.petmily.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostBookmarkDto {

    private PostShowDto post;
    private Long BookmarkId;

    public PostBookmarkDto(PostShowDto post, Long bookmarkId) {
        this.post = post;
        BookmarkId = bookmarkId;
    }
}
