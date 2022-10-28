package com.petmily.petmily.security;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.repository.IMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Component
public class MemberDetailService implements UserDetailsService {

    private final IMemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.size() != 1) {
            throw new UsernameNotFoundException("유효하지 않는 이메일");
        }
        Member findMember = findMembers.get(0);
        return new MemberDetail(findMember.getEmail(), findMember.getPassword());

    }
}
