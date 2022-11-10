package com.petmily.petmily.service;

import com.petmily.petmily.domain.Like;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import com.petmily.petmily.exception.LikeException;
import com.petmily.petmily.repository.ILikeRepository;
import com.petmily.petmily.repository.IPostRepository;
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
public class LikeService {

    private final ILikeRepository likeRepository;
    private final MemberService memberService;
    private final IPostRepository postRepository;

    @Transactional
    public void like(HttpServletRequest request, Long postId) {
        Member member = getMember(request);
        Post post = postRepository.findById(postId);
        List<Like> findLikes = likeRepository.findByMemberPost(member, post);
        if (findLikes.size() != 0) {
            throw new LikeException("LIKE ERROR!!!");
        }
        Like like = Like.getLike(member, post);
        likeRepository.save(like);
    }

    @Transactional
    public void unLike(HttpServletRequest request, Long postId) {
        Member member = getMember(request);
        Post post = postRepository.findById(postId);
        likeRepository.remove(member, post);
    }

    public List<Like> findByMember(HttpServletRequest request) {
        Member findMember = getMember(request);
        List<Like> findLikes = likeRepository.findByMember(findMember);
        return findLikes;
    }

    public boolean checkLike(HttpServletRequest request, Long postId) {
        Member findMember = getMember(request);
        return likeRepository.checkByMemberPost(findMember, postId);
    }

    private Member getMember(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        return findMember;
    }
}
