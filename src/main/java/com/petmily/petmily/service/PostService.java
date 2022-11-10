package com.petmily.petmily.service;

import com.petmily.petmily.domain.*;
import com.petmily.petmily.dto.post.PostSaveDto;
import com.petmily.petmily.dto.post.PostShowDto;
import com.petmily.petmily.repository.IMemberRepository;
import com.petmily.petmily.repository.IPostRepository;
import com.petmily.petmily.security.JwtTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private final FollowService followService;
    private final LikeService likeService;

    @Transactional
    public Post save(HttpServletRequest request, PostSaveDto postSaveDto) {
        Member findMember = findMemberByNickname(request);
        Post post = Post.getPost(postSaveDto, findMember);
        postRepository.save(post);
        return post;
    }

    public List<PostShowDto> showMyPost(HttpServletRequest request) throws IOException {
        List<PostShowDto> allPost = new ArrayList<>();
        Member findMember = findMemberByNickname(request);
        List<Post> findPosts = postRepository.findAllByMember(findMember);
        for (Post findPost : findPosts) {
            boolean isLike = likeService.checkLike(request, findPost.getId());
            PostShowDto postShowDto = getPostShowDto(findPost, isLike);
            allPost.add(postShowDto);
        }
        Collections.sort(allPost);
        return allPost;
    }

    public List<PostShowDto> showAllPost(HttpServletRequest request) throws IOException {
        List<PostShowDto> allPost = new ArrayList<>();
        Member findMember = findMemberByNickname(request);
        List<Post> postList = findMember.getPostList();
        List<Follow> followList = followService.findAll(findMember);
        List<Member> myFriend = new ArrayList<>();


        for (Post post : postList) {
            boolean isLike = likeService.checkLike(request, post.getId());
            PostShowDto postShowDto = getPostShowDto(post, isLike);
            allPost.add(postShowDto);
        }
        addMyFriend(followList, myFriend);
        for (Member member : myFriend) {
            List<Post> postByMember = postRepository.findAllByMember(member);
            for (Post post : postByMember) {
                boolean isLike = likeService.checkLike(request, post.getId());
                PostShowDto postDto = getPostShowDto(post, isLike);
                allPost.add(postDto);
            }
        }
        Collections.sort(allPost);
        return allPost;
    }

    private PostShowDto getPostShowDto(Post post, boolean isLike) throws IOException {
        List<String> hashtags = new ArrayList<>();
        List<Hashtag> hashtagList = post.getHashtag();
        List<Image> imageList = post.getImageList();
        List<byte[]> images = imageService.getListByteArray(imageList);
        for (Hashtag hashtag : hashtagList) {
            hashtags.add(hashtag.getHashtagName());
        }
        PostShowDto postDto = new PostShowDto(post.getId(), post.getMember().getNickname(), hashtags, post.getContent(), images, isLike, post.getCreatedDate());
        return postDto;
    }

    private void addMyFriend(List<Follow> followList, List<Member> myFriend) {
        for (Follow follow : followList) {
            Member targetMember = follow.getTargetMember();
            myFriend.add(targetMember);
        }
    }

    private List<Post> findMyPost(Member member) {
        return postRepository.findAllByMember(member);
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
