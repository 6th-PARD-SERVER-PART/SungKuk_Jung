package com.pard.server.hw5_sungkukjung.like;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequiredArgsConstructor 
@RequestMapping("/like") 
public class LikeController {
    private final LikeService likeService;

    // POST /like/toggle - 좋아요/좋아요 취소 처리
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleLike(@RequestBody LikeRequest.LikeAction likeAction) {
        try {
            String message = likeService.toggleLike(likeAction);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // GET /like/post/{postId} - 게시글 좋아요 한 사용자 목록 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getLikersByPostId(@PathVariable Long postId) {
        try {
            List<LikeResponse.ViewLiker> likers = likeService.getLikersByPostId(postId);
            return ResponseEntity.ok(likers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}