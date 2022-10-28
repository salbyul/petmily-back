package com.petmily.petmily.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "member")
    private List<Follow> followList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Like> likeList = new ArrayList<>();

}
