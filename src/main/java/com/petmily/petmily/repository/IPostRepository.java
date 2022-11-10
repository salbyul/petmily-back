package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;

import java.util.List;

public interface IPostRepository {

    void save(Post post);

    Post findById(Long id);

    void delete(Post post);

    /**
     * 내 포스터 전부 보기
     * @param member
     * @return
     */
    List<Post> findAllByMember(Member member);

    /**
     * 내 포스터와 팔로우를 맺은 친구들 포스터 전부 보기
     * @param member
     * @return
     */
    List<Post> findAllFriend(Member member);

    List<Post> findByHashtag(String hashtag);
}
