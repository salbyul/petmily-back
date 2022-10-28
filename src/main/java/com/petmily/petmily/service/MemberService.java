package com.petmily.petmily.service;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.MemberJoinDto;
import com.petmily.petmily.repository.IMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final IMemberRepository memberRepository;

    @Transactional
    public Long join(MemberJoinDto memberDto) {
        validateDuplicateEmail(memberDto.getEmail());
        validateDuplicateNickname(memberDto.getNickname());
        Member member = Member.getMember(memberDto);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateNickname(String nickname) {
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        if (findMembers.size() != 0) {
            throw new IllegalArgumentException("닉네임 중복");
        }
    }


    private void validateDuplicateEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.size() != 0) {
            throw new IllegalArgumentException("이메일 중복");
        }
    }
}
