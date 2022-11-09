package com.petmily.petmily.service;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.member.*;
import com.petmily.petmily.exception.member.MemberException;
import com.petmily.petmily.repository.IMemberRepository;
import com.petmily.petmily.security.JwtTokenException;
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
    private final FollowService followService;

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
            throw new JwtTokenException("유효하지 않은 토큰");
        }
        return findMembers.get(0).getNickname();
    }

    public Member findByNickname(String nickname) {
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        if (findMembers.size() != 1) {
            throw new MemberException("유효하지 않은 닉네임");
        }
        return findMembers.get(0);
    }

    public List<Member> findAllByNicknameExceptMe(String nickname, String target) {
        return memberRepository.findAllByNicknameExceptMe(nickname, target);
    }

    public MemberProfileDto nicknameToProfileDto(String nickname, String targetNickname) {
        Member findMember = findByNickname(nickname);
        if (targetNickname != null) {
            Member targetMember = findByNickname(targetNickname);
            boolean isFollow = followService.isFollow(findMember, targetMember);
            return new MemberProfileDto(targetMember.getEmail(), targetMember.getNickname(), targetMember.getStatusMessage(), isFollow);
        }
        return new MemberProfileDto(findMember.getEmail(), findMember.getNickname(), findMember.getStatusMessage(), null);
    }

    @Transactional
    public void modifyMember(String nickname, MemberModifyDto memberModifyDto) {
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        if (findMembers.size() != 1) {
            throw new JwtTokenException("유효하지 않은 토큰");
        }
        Member findMember = findMembers.get(0);
        if (nickname.equals(memberModifyDto.getNickname())) {
            findMember.modifyMember(memberModifyDto.getNickname(), memberModifyDto.getStatusMessage());
            return;
        }
        List<Member> findMembersByDto = memberRepository.findByNickname(memberModifyDto.getNickname());
        if (findMembersByDto.size() != 0) {
            log.info("닉네임 중복");
            throw new MemberException("닉네임 중복");
        }

        findMember.modifyMember(memberModifyDto.getNickname(), memberModifyDto.getStatusMessage());
    }

    public MemberSidebarDto MemberSidebarDtoToMember(Member member) {
        return new MemberSidebarDto(member.getNickname(), member.getStatusMessage());
    }
}
