package com.petmily.petmily.service;

import com.petmily.petmily.domain.*;
import com.petmily.petmily.dto.post.PostBookmarkDto;
import com.petmily.petmily.dto.post.PostShowDto;
import com.petmily.petmily.exception.post.PostException;
import com.petmily.petmily.repository.IBookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class BookmarkService {

    private final IBookmarkRepository bookmarkRepository;
    private final MemberService memberService;
    private final PostService postService;
    private final ImageService imageService;
    private final LikeService likeService;

    @Transactional
    public void bookmark(HttpServletRequest request, Long postId) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        Post findPost = postService.findById(postId);
        if (findPost == null) {
            throw new PostException("POST ID ERROR!!!");
        }

        Bookmark bookmark = new Bookmark(findMember, findPost);
        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void unBookmark(HttpServletRequest request, Long postId) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        Post findPost = postService.findById(postId);
        bookmarkRepository.removeByMemberPost(findMember, findPost);
    }

    public List<PostBookmarkDto> findMyAllBookmarkPost(HttpServletRequest request) throws IOException {
        String nickname = (String) request.getAttribute("nickname");
        List<PostBookmarkDto> result = new ArrayList<>();
        Member findMember = memberService.findByNickname(nickname);
        List<Bookmark> findBookmark = bookmarkRepository.findByMember(findMember);
        for (Bookmark bookmark : findBookmark) {
            Post findPost = postService.findById(bookmark.getPost().getId());
            boolean isLike = likeService.checkLike(request, findPost.getId());
            PostShowDto postShowDto = getPostShowDto(findPost, isLike, true);
            result.add(new PostBookmarkDto(postShowDto, bookmark.getId()));
        }
        return result;
    }

    public boolean checkBookmark(HttpServletRequest request, Post post) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        List<Bookmark> findBookmarks = bookmarkRepository.findByMemberPost(findMember, post);
        return findBookmarks.size() == 1;
    }

    private PostShowDto getPostShowDto(Post post, boolean isLike, boolean isBookmark) throws IOException {
        List<String> hashtags = new ArrayList<>();
        List<Hashtag> hashtagList = post.getHashtag();
        List<Image> imageList = post.getImageList();
        List<byte[]> images = imageService.getListByteArray(imageList);
        for (Hashtag hashtag : hashtagList) {
            hashtags.add(hashtag.getHashtagName());
        }
        PostShowDto postDto = new PostShowDto(post.getId(), post.getMember().getNickname(), hashtags, post.getContent(), images, isLike, isBookmark, post.getCreatedDate());
        return postDto;
    }

}
