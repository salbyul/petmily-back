package com.petmily.petmily;

import com.petmily.petmily.dto.member.MemberJoinDto;
import com.petmily.petmily.security.JwtTokenFilter;
import com.petmily.petmily.security.JwtTokenProvider;
import com.petmily.petmily.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/logout", "/join", "/find-password").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider, memberService), UsernamePasswordAuthenticationFilter.class);
        http
                .csrf().disable()
                .formLogin().disable();
        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*")
                .maxAge(3600);
    }

    @PostConstruct
    public void init() {
        MemberJoinDto memberJoinDto = new MemberJoinDto();
        memberJoinDto.setEmail("asdf@asdf.com");
        memberJoinDto.setNickname("memberA");
        memberJoinDto.setPassword("asdfasdf");
        memberService.join(memberJoinDto);
        MemberJoinDto memberJoinDtoB = new MemberJoinDto();
        memberJoinDtoB.setEmail("google@google.com");
        memberJoinDtoB.setNickname("memberB");
        memberJoinDtoB.setPassword("asdfasdf");
        memberService.join(memberJoinDtoB);
    }
}
