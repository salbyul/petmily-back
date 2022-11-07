package com.petmily.petmily.service;

import com.petmily.petmily.domain.Hashtag;
import com.petmily.petmily.domain.Image;
import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import com.petmily.petmily.dto.post.PostSaveDto;
import com.petmily.petmily.dto.post.PostShowDto;
import com.petmily.petmily.exception.member.MemberException;
import com.petmily.petmily.repository.IHashtagRepository;
import com.petmily.petmily.repository.IImageRepository;
import com.petmily.petmily.repository.IMemberRepository;
import com.petmily.petmily.repository.IPostRepository;
import com.petmily.petmily.security.JwtTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final IPostRepository postRepository;
    private final IMemberRepository memberRepository;
    private final HashtagService hashtagService;
    private final ImageService imageService;

    @Transactional
    public Post save(HttpServletRequest request, PostSaveDto postSaveDto) {
        Member findMember = findMemberByNickname(request);
        Post post = Post.getPost(postSaveDto, findMember);
        postRepository.save(post);
        return post;
    }

    public List<PostShowDto> showMyPost(HttpServletRequest request) throws IOException {
        Member findMember = findMemberByNickname(request);
        List<Post> myPosts = postRepository.findAllMine(findMember);
        List<PostShowDto> postShowDtoList = new ArrayList<>();
        for (int i = 0; i < myPosts.size(); i++) {
            Post post = myPosts.get(i);
            List<Hashtag> hashtags = hashtagService.findByPost(post);
            List<String> hashtagList = new ArrayList<>();
            for (Hashtag hashtag : hashtags) {
                hashtagList.add(hashtag.getHashtagName());
            }
            List<Image> images = imageService.findByPost(post);
            List<byte[]> imageArray = imageService.getListByteArray(images);
            postShowDtoList.add(new PostShowDto(hashtagList, post.getContent(), imageArray));
        }
        return postShowDtoList;
    }

    private List<Post> findMyPost(Member member) {
        return postRepository.findAllMine(member);
    }

    private Member findMemberByNickname(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        if (findMembers.size() != 1) {
            throw new JwtTokenException("잘못된 토큰입니다.");
        }
        return findMembers.get(0);
    }

}
