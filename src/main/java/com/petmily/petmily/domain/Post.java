package com.petmily.petmily.domain;

import com.petmily.petmily.dto.post.PostSaveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String hashtag;

    @OneToMany(mappedBy = "post")
    private List<Like> likeList  = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Notification> notificationList = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public Post(Content content, Member member, String hashtag) {
        this.content = content;
        this.member = member;
        this.hashtag = hashtag;
    }

    public static Post getPost(PostSaveDto postSaveDto, Member member) {
        return new Post(postSaveDto.getContent(), member, postSaveDto.getHashtag());
    }
}
