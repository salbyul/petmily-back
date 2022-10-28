package com.petmily.petmily.service;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.MemberJoinDto;
import com.petmily.petmily.repository.IMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    IMemberRepository memberRepository;

    @Test
    void save() {
        MemberJoinDto memberJoinDto = new MemberJoinDto("salbyul@korean", "1111", "salbyul");
        Long savedId = memberService.join(memberJoinDto);
        Member findMember = memberRepository.findById(savedId);
        assertThat(savedId).isEqualTo(findMember.getId());
        assertThat(memberJoinDto.getEmail()).isEqualTo(findMember.getEmail());
        assertThat(memberJoinDto.getPassword()).isEqualTo(findMember.getPassword());
        assertThat(memberJoinDto.getNickname()).isEqualTo(findMember.getNickname());
    }
}