package com.petmily.petmily.web.controller;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.MemberJoinDto;
import com.petmily.petmily.dto.MemberLoginDto;
import com.petmily.petmily.repository.IMemberRepository;
import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final IMemberRepository memberRepository;

    @PostMapping("/join")
    public Member join(@Validated @RequestBody MemberJoinDto memberJoinDto) {
        log.info("join: {}", memberJoinDto);
        Long savedId = memberService.join(memberJoinDto);
        Member joinedMember = memberRepository.findById(savedId);
        log.info("email: {}", joinedMember.getEmail());
        log.info("password: {}", joinedMember.getPassword());
        return joinedMember;
    }

    @PostMapping("/login")
    public Member login(@RequestBody MemberLoginDto memberLoginDto) {
        log.info("login: {}", memberLoginDto);
        return memberService.login(memberLoginDto);
    }
}
