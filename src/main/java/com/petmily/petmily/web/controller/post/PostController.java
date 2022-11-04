package com.petmily.petmily.web.controller.post;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import com.petmily.petmily.dto.post.PostSaveDto;
import com.petmily.petmily.service.MemberService;
import com.petmily.petmily.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity savePost(@Validated PostSaveDto postSaveDto, HttpServletRequest request) {
        postService.save(postSaveDto, request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/my-post")
    public ResponseEntity<List<Post>> findMyPost(HttpServletRequest request) {
        String nickname = (String) request.getAttribute("nickname");
        Member findMember = memberService.findByNickname(nickname);
        List<Post> myPost = postService.findMyPost(findMember);
        return new ResponseEntity(myPost, HttpStatus.OK);
    }

}
