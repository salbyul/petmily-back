package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Image;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;

import java.util.List;

public interface IImageRepository {

    void save(Image image);

    List<Image> findByPost(Post post);

    List<Image> findByMember(Member member);

}
