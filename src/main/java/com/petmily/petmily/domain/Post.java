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

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Hashtag> hashtag = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Like> likeList  = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Image> imageList = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public Post(String content, Member member) {
        this.content = content;
        this.member = member;
    }

    public static Post getPost(PostSaveDto postSaveDto, Member member) {
        return new Post(postSaveDto.getContent(), member);
    }
}
