package com.petmily.petmily.service;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import com.petmily.petmily.dto.post.PostSaveDto;
import com.petmily.petmily.exception.member.MemberException;
import com.petmily.petmily.repository.IMemberRepository;
import com.petmily.petmily.repository.IPostRepository;
import com.petmily.petmily.security.JwtTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final IPostRepository postRepository;
    private final IMemberRepository memberRepository;

    public void save(PostSaveDto postSaveDto, HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = findMemberByNickname(nickname);
        Post post = Post.getPost(postSaveDto, findMember);
        postRepository.save(post);
    }

    public List<Post> findMyPost(Member member) {
        return postRepository.findAllMine(member);
    }

    private Member findMemberByNickname(String nickname) {
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        if (findMembers.size() != 1) {
            throw new JwtTokenException("잘못된 토큰입니다.");
        }
        return findMembers.get(0);
    }
}
