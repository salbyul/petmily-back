package com.petmily.petmily.domain;

import com.petmily.petmily.dto.hashtag.HashtagSaveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {

    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    private String hashtagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected Hashtag(Long id, String hashtagName, Post post) {
        this.id = id;
        this.hashtagName = hashtagName;
        this.post = post;
    }

    public Hashtag(String hashtagName, Post post) {
        this.hashtagName = hashtagName;
        this.post = post;
    }

    public static Hashtag getHashtag(String hashtagName, Post post) {
        return new Hashtag(hashtagName, post);
    }
}
