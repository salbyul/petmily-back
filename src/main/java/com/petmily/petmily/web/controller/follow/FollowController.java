package com.petmily.petmily.web.controller.follow;

import com.petmily.petmily.domain.Follow;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.service.FollowService;
import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private final MemberService memberService;

    @GetMapping("/find-all")
    public Object followList(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        log.info("findAll");
        log.info("nickname : {}", nickname);
        List<Follow> all = followService.findAll(memberService.findByNickname(nickname));
        for (Follow follow : all) {
            System.out.println("name: " + follow.getTargetMember().getNickname());
        }
        return all;
    }

    @PostMapping("/follow")
    public ResponseEntity follow(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        String nickname = (String) request.getAttribute("nickname");
        String email = (String) map.get("email");
        Member targetMember = memberService.findByEmail(email);
        Member findMember = memberService.findByNickname(nickname);
        followService.follow(findMember, targetMember);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/unfollow")
    public ResponseEntity unfollow(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        String email = (String) map.get("email");
        Member targetMember = memberService.findByEmail(email);
        followService.unFollow(findMember, targetMember);
        return new ResponseEntity(HttpStatus.OK);
    }
}
