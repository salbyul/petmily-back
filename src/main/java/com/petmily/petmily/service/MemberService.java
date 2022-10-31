package com.petmily.petmily.service;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.MemberJoinDto;
import com.petmily.petmily.dto.MemberLoginDto;
import com.petmily.petmily.repository.IMemberRepository;
import com.petmily.petmily.security.JwtTokenProvider;
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
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public Long join(MemberJoinDto memberDto) {
        log.info("JOIN memberDto: {}", memberDto.getEmail());
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

    public Member login(MemberLoginDto memberLoginDto) {
        Member findMember = findByEmail(memberLoginDto.getEmail());
        if (findMember == null) throw new IllegalArgumentException("유효하지 않는 이메일입니다.");
        if (!findMember.getPassword().equals(memberLoginDto.getPassword())) throw new IllegalArgumentException("유효하지 않는 비밀번호입니다.");
        return findMember;
    }

    public Member findByEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.size() != 1) {
            throw new IllegalArgumentException("유효하지 않는 이메일입니다.");
        }
        return findMembers.get(0);
    }

    public String findByJwtToken(String token) {
        String email = tokenProvider.getEmail(token);
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.size() != 1) {
            throw new IllegalStateException("잘못된 토큰입니다.");
        }
        return findMembers.get(0).getNickname();
    }
}
