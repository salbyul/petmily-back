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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity savePost(@Validated PostSaveDto postSaveDto, @RequestPart(value = "files") List<MultipartFile> multipartFiles) throws ServletException, IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            System.out.println(multipartFile.getOriginalFilename());
        }
        /**
         * form-data에 array를 보내는 방법은 없다.
         * 그러므로 스트링으로 모두 받고 리스트로 만드는 로직을 만들어야함!
         */
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
