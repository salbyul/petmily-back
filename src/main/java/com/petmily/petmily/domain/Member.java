package com.petmily.petmily.domain;

import com.petmily.petmily.dto.MemberJoinDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String statusMessage;

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

    @OneToMany(mappedBy = "targetMember")
    private List<Notification> notificationList = new ArrayList<>();

    public Member(String email, String password, String nickname, LocalDateTime createdDate) {

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createdDate = createdDate;
        this.statusMessage = "상태메시지를 입력해주세요.";
    }

    public static Member getMember(MemberJoinDto memberDto) {
        return new Member(memberDto.getEmail(), memberDto.getPassword(), memberDto.getNickname(), LocalDateTime.now());
    }

    public void modifyMember(String nickname, String statusMessage) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }
}
