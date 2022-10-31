package com.petmily.petmily.web.controller;

import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public String member(HttpServletRequest request) {
        log.info("Here is member API");
        String token = request.getHeader("Authorization");
        String jwtToken = token.split(" ")[1].trim();
        return memberService.findByJwtToken(jwtToken);
    }
}
