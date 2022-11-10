package com.petmily.petmily.web.controller.member;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.member.MemberModifyDto;
import com.petmily.petmily.dto.member.MemberProfileDto;
import com.petmily.petmily.dto.member.MemberSidebarDto;
import com.petmily.petmily.exception.member.MemberException;
import com.petmily.petmily.service.FollowService;
import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final FollowService followService;

    @GetMapping
    public MemberSidebarDto member(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        return memberService.MemberSidebarDtoToMember(findMember);
    }

    @GetMapping("/find-member")
    public List<Member> findMember(HttpServletRequest request, String target) {
        String nickname = (String) request.getAttribute("nickname");
        List<Member> findMembers = memberService.findAllByNicknameExceptMe(nickname, target);
        log.info("members.size(): {}", findMembers.size());
        for (Member findMember : findMembers) {
            System.out.println("findMember = " + findMember.getNickname());
        }
        return findMembers;
    }

    @GetMapping("/detail")
    public MemberProfileDto Detail(HttpServletRequest request, @RequestParam(value = "m", required = false) String targetNickname) {
        String nickname = (String) request.getAttribute("nickname");
        log.info("targetNickname: {}", targetNickname);
        if (targetNickname != null) {
            return memberService.nicknameToProfileDto(nickname, targetNickname);
        }
        return memberService.nicknameToProfileDto(nickname, null);
    }

    @PutMapping("/my-detail/modify")
    public ResponseEntity<Map<String, Object>> myDetailModified(HttpServletRequest request, @Validated @RequestBody MemberModifyDto memberModifyDto) {
        String nickname = (String) request.getAttribute("nickname");
        memberService.modifyMember(nickname, memberModifyDto);
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @PutMapping("/detail/follow")
    public ResponseEntity follow(HttpServletRequest request, @RequestBody HashMap<String, Object> map) {
        String nickname = (String) request.getAttribute("nickname");
        String email = (String) map.get("email");
        Member targetMember = memberService.findByEmail(email);
        Member findMember = memberService.findByNickname(nickname);
        followService.follow(findMember, targetMember);
        return ResponseEntity.ok().build();
    }
}
