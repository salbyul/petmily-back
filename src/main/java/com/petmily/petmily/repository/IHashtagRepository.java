package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Hashtag;
import com.petmily.petmily.domain.Post;

import java.util.List;

public interface IHashtagRepository {

    void save(Hashtag hashtag);

    List<Hashtag> findByPost(Post post);

    List<Hashtag> findByHashtagName(String hashtagName);
}
