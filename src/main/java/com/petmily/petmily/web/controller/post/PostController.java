package com.petmily.petmily.web.controller.post;

import com.petmily.petmily.domain.Member;
import com.petmily.petmily.domain.Post;
import com.petmily.petmily.dto.post.PostSaveDto;
import com.petmily.petmily.dto.post.PostShowDto;
import com.petmily.petmily.service.HashtagService;
import com.petmily.petmily.service.ImageService;
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
    private final HashtagService hashtagService;
    private final ImageService imageService;

    @PostMapping("/save")
    public ResponseEntity savePost(PostSaveDto postSaveDto, @RequestPart(value = "files") List<MultipartFile> multipartFiles, HttpServletRequest request) throws IOException {
        if (multipartFiles.isEmpty()) throw new IllegalStateException("이미지는 필수!!");
        if (postSaveDto.getContent().equals("")) throw new IllegalStateException("내용은 필수!!");
        Post savedPost = postService.save(request, postSaveDto);
        hashtagService.save(request, savedPost);
        imageService.save(multipartFiles, request, savedPost);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/my-post")
    public ResponseEntity<List<PostShowDto>> findMyPost(HttpServletRequest request) throws IOException {
        List<PostShowDto> postShowDtoList = postService.showMyPost(request);
        return new ResponseEntity<List<PostShowDto>>(postShowDtoList, HttpStatus.OK);
    }

}
