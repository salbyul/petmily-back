package com.petmily.petmily.repository;

import com.petmily.petmily.domain.Member;

import java.util.List;

public interface IMemberRepository {

    void save(Member member);

    Member findById(Long id);

    List<Member> findByEmail(String email);

    List<Member> findByNickname(String nickname);

    List<Member> findAllByNicknameExceptMe(String nickname, String targetNickname);

    void modifyPassword(Member member, String password);
}
