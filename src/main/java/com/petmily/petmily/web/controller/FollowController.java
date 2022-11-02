package com.petmily.petmily.web.controller;

import com.petmily.petmily.domain.Follow;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.service.FollowService;
import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final MemberService memberService;

    @GetMapping("/follow/find-all")
    public List<Follow> followList(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        log.info("nickname : {}", nickname);
        return followService.findAll(memberService.findByNickname(nickname));
    }

    @PostMapping("/follow")
    public ResponseEntity follow(HttpServletRequest request, String targetMember) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        Member findTargetMember = memberService.findByNickname(targetMember);
        followService.follow(findMember, findTargetMember);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/follow/unfollow")
    public ResponseEntity unfollow(HttpServletRequest request, String targetMember) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        Member findTargetMember = memberService.findByNickname(targetMember);
        followService.unFollow(findMember, findTargetMember);
        return new ResponseEntity(HttpStatus.OK);
    }
}
