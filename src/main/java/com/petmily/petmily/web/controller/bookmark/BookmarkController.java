package com.petmily.petmily.web.controller.bookmark;

import com.petmily.petmily.dto.post.PostBookmarkDto;
import com.petmily.petmily.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping("/all")
    public ResponseEntity<List<PostBookmarkDto>> getBookmarks(HttpServletRequest request) throws IOException {
        List<PostBookmarkDto> myAllBookmark = bookmarkService.findMyAllBookmarkPost(request);
        return new ResponseEntity<>(myAllBookmark, HttpStatus.OK);
    }

    @PostMapping("/bookmark")
    public ResponseEntity bookmark(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        long postId = ((Number) map.get("postId")).longValue();
        log.info("postId: {}", postId);
        bookmarkService.bookmark(request, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/un-bookmark")
    public ResponseEntity unBookmark(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        long postId = ((Number) map.get("postId")).longValue();
        bookmarkService.unBookmark(request, postId);
        return ResponseEntity.ok().build();
    }
}
