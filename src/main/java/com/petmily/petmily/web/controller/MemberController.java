package com.petmily.petmily.web.controller;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.MemberProfileDto;
import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String member(HttpServletRequest request) {
        log.info("Here is member API");
        String token = request.getHeader("Authorization");
        String jwtToken = token.split(" ")[1].trim();
        return memberService.findByJwtToken(jwtToken);
    }

    @GetMapping("/find-all")
    public List<Member> findAll(HttpServletRequest request, String target) {
        String nickname = (String) request.getAttribute("nickname");
        List<Member> findMembers = memberService.findAllByNicknameExceptMe(nickname, target);
        log.info("members.size(): {}", findMembers.size());
        for (Member findMember : findMembers) {
            System.out.println("findMember = " + findMember.getNickname());
        }
        return findMembers;
    }

    @GetMapping("/detail")
    public MemberProfileDto detail(String nickname) {
        return memberService.nicknameToProfileDto(nickname);
    }

    @GetMapping("/my-detail")
    public MemberProfileDto myDetail(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        return memberService.nicknameToProfileDto(nickname);
    }
}
