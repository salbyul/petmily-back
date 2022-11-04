package com.petmily.petmily.web.controller.member;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.dto.member.MemberModifyDto;
import com.petmily.petmily.dto.member.MemberProfileDto;
import com.petmily.petmily.dto.member.MemberSidebarDto;
import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public MemberSidebarDto member(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        return memberService.MemberSidebarDtoToMember(findMember);
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
    public MemberProfileDto myDetail(HttpServletRequest request, @RequestParam(value = "m", required = false) String targetNickname) {
        log.info("targetNickname: {}", targetNickname);
        if (targetNickname != null) {
            Member findMember = memberService.findByNickname(targetNickname);
            return memberService.nicknameToProfileDto(targetNickname);
        }
        String nickname = (String) request.getAttribute("nickname");
        return memberService.nicknameToProfileDto(nickname);
    }

    @PutMapping("/my-detail/modify")
    public ResponseEntity<Map<String, Object>> myDetailModified(HttpServletRequest request, @Validated @RequestBody MemberModifyDto memberModifyDto) {
        String nickname = (String) request.getAttribute("nickname");
        memberService.modifyMember(nickname, memberModifyDto);
        return new ResponseEntity("ok", HttpStatus.OK);
    }
}
