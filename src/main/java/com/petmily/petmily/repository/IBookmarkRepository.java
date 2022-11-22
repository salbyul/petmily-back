package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Bookmark;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;

import java.util.List;

public interface IBookmarkRepository {

    void save(Bookmark bookmark);

    void remove(Long id);

    List<Bookmark> findByMember(Member member);

    void removeByMemberPost(Member member, Post post);

    List<Bookmark> findByMemberPost(Member member, Post post);
}
