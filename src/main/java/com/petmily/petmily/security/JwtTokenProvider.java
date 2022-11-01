package com.petmily.petmily.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private final String secretKey = "vamilloMacbookProAnyWhereS2TwoOfSideMonitorThisIsSoFreakingGood";
    private Key key;
    private final Long accessTime = 360000 * 1000L;
    private final MemberDetailService memberDetailService;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secretBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(secretBytes);
    }

    /**
     * 토큰 생성
     */
    public String generateToken(String email, String nickname) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setClaims(Map.of("nickname", nickname))
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + accessTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 유저 정보 추출
     */
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 유저 닉네임 추출
     */
    public String getNickname(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().toString();
    }

    /**
     * Authentication 객체 생성
     */
    public Authentication authentication(String token) {
        UserDetails userDetails = memberDetailService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }


    /**
     * 토큰 유효 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
