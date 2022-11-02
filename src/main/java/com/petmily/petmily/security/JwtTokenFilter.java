package com.petmily.petmily.security;

import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        log.info("token: {}", token);

        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.split(" ")[1].trim();
            if (jwtTokenProvider.validateToken(jwtToken)) {
                Authentication authentication = jwtTokenProvider.authentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String nickname = memberService.findByJwtToken(jwtToken);
                request.setAttribute("nickname", nickname);
            }
        }
        filterChain.doFilter(request, response);
    }
}
