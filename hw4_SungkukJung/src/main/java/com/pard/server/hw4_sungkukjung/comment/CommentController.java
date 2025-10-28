package com.pard.server.hw4_sungkukjung.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequiredArgsConstructor 
@RequestMapping("/comment") 
public class CommentController {
    private final CommentService commentService;

    // POST: 게시글에 새 댓글 생성
    @PostMapping("")
    public ResponseEntity<String> createComment(@RequestBody CommentRequest.CreateComment commentRequest) {
        try {
            commentService.createComment(commentRequest);
            return ResponseEntity.ok("Comment created successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // GET: 특정 게시글의 모든 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<CommentResponse.ViewComment> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(comments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // PATCH: 댓글 내용 업데이트
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentRequest.UpdateComment commentUpdate) {
        try {
            commentService.updateComment(id, commentUpdate);
            return ResponseEntity.ok("Comment updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE: 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok("Comment deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}