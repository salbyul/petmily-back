package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Like;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;

import java.util.List;

public interface ILikeRepository {

    void save(Like like);

    void remove(Member member, Post post);

    List<Like> findByMember(Member member);

    List<Like> findByMemberPost(Member member, Post post);

    boolean checkByMemberPost(Member member, Long postId);
}
