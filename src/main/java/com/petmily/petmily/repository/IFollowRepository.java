package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Follow;
import com.petmily.petmily.domain.Member;

import java.util.List;

public interface IFollowRepository {

    void save(Member member, Member targetMember);

    List<Follow> findAll(Member member);

    void remove(Member member, Member targetMember);
}
