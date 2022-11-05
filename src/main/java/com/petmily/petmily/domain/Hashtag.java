package com.petmily.petmily.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Hashtag {

    @Id @GeneratedValue
    private Long id;

    private String hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
