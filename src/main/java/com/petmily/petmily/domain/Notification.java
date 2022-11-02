package com.petmily.petmily.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Notification {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetMember_id")
    private Member targetMember;
}
