package com.pard.server.hw5_sungkukjung.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController 
@RequiredArgsConstructor 
@RequestMapping("/post") 
public class PostController {
    private final PostService postService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 게시글 생성 및 파일 업로드 옵션
    public void createPost(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("content") String content,
            @RequestParam(value = "fileUploadUrl", required = false) MultipartFile fileUploadUrl) {
        PostRequest.NewPost postRequest = PostRequest.NewPost.builder()
                .userId(userId)
                .title(title)
                .description(description)
                .content(content)
                .build();
        postService.createPost(postRequest, fileUploadUrl);
    }

    @GetMapping("/{id}") // 게시글 조회 
    public ResponseEntity<?> findPostById(@PathVariable Long id){
        try {
            PostResponse.ViewPost post = postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}") // 작성자 이름으로 게시글 목록 조회
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        try {
            List<PostResponse.DashboardPostInfo> posts = postService.getPostsByUsername(username);
            return ResponseEntity.ok(posts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/all") // 모든 게시글 최신순 목록 조회
    public List<PostResponse.DashboardPostInfo> findAllPosts() {
        return postService.getAllPosts();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 업데이트 및 새 파일 업로드 옵션
    public void updatePost(
            @PathVariable Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "fileUploadUrl", required = false) MultipartFile fileUploadUrl) {
        PostRequest.UpdatePost postUpdate = PostRequest.UpdatePost.builder()
                .title(title)
                .description(description)
                .content(content)
                .build();
        postService.updatePost(id, postUpdate, fileUploadUrl);
    }

    @DeleteMapping("/{id}") // 게시글 삭제; 좋아요/댓글 삭제 연쇄 적용
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
