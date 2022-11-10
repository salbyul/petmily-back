package com.petmily.petmily.web.controller.like;

import com.petmily.petmily.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity like(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        Long postId = Long.valueOf(String.valueOf(map.get("postId")));
        log.info("postId: {}", postId);
        likeService.like(request, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlike")
    public ResponseEntity unLike(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        Long postId = Long.valueOf(String.valueOf(map.get("postId")));
        log.info("postId: {}", postId);
        likeService.unLike(request, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity checkLike(HttpServletRequest request, Long postId) {
        boolean check = likeService.checkLike(request, postId);
        return new ResponseEntity(check, HttpStatus.OK);
    }
}
