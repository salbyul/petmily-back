package com.petmily.petmily.web.controller.home;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.member.MemberJoinDto;
import com.petmily.petmily.dto.member.MemberLoginDto;
import com.petmily.petmily.repository.IMemberRepository;
import com.petmily.petmily.security.JwtTokenProvider;
import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final IMemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;

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
    public String login(@RequestBody MemberLoginDto memberLoginDto, HttpServletResponse response) {
        log.info("login: {}", memberLoginDto);
        Member loginMember = memberService.login(memberLoginDto);

        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            System.out.println("headerName = " + headerName);
        }
        String header = response.getHeader("Access-Control-Allow-Origin");
        System.out.println("header = " + header);
        response.setHeader("Access-Control-Allow-Headers", "Authorization, content-type");
        return tokenProvider.generateToken(loginMember.getEmail(), loginMember.getNickname());
    }

    /**
     * TODO
     * 같은 비밀번호로 변경 허용???
     * @param map
     * @return
     */
    @PostMapping("/find-password")
    public ResponseEntity findPassword(@RequestBody Map<String, Object> map) {
        String email = (String) map.get("email");
        String nickname = (String) map.get("nickname");
        String password = (String) map.get("password");
        Member findMemberByNickname = memberService.findByNickname(nickname);
        Member findMemberByEmail = memberService.findByEmail(email);
        if (findMemberByNickname == null || findMemberByNickname != findMemberByEmail) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        memberService.changePassword(findMemberByEmail, password);
        return ResponseEntity.ok().build();
    }
}
