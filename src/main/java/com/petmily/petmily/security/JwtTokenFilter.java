package com.petmily.petmily.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        log.info("token: {}", token);
//        Enumeration<String> headerNames = request.getHeaderNames();
//        Iterator<String> sl = headerNames.asIterator();
//        while (sl.hasNext()) {
//            System.out.println(sl.next());
//        }
//        System.out.println("-----------------------------------------");
//        System.out.println(request.getHeader("access-control-request-headers"));
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.split(" ")[1].trim();
            if (jwtTokenProvider.validateToken(jwtToken)) {
                Authentication authentication = jwtTokenProvider.authentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
