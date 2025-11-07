package com.pard.server.hw5_sungkukjung.post;

import com.pard.server.hw5_sungkukjung.user.User;
import com.pard.server.hw5_sungkukjung.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor 
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir:uploads/posts}") // 업로드된 파일 저장 기본 디렉토리
    private String uploadDir;

    @Transactional // 새 게시글과 파일 원자성 저장
    public void createPost(PostRequest.NewPost postRequest, MultipartFile file) {
        User user = userRepository.findById(postRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + postRequest.getUserId()));

        String fileUrl = saveFileAndGetUrl(file);

        Post post = Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .description(postRequest.getDescription())
                .content(postRequest.getContent())
                .fileUploadUrl(fileUrl)
                .build();
        postRepository.save(post);
    }

    @Transactional // 조회수 안전하게 증가
    public PostResponse.ViewPost getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        post.incrementViewsCount();
        postRepository.save(post);

        return convertToViewPost(post);
    }

    @Transactional // 트랜잭션 내에서 일관성 있는 목록 조회
    public List<PostResponse.DashboardPostInfo> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByDateCreatedDesc();
        return posts.stream()
                .map(this::convertToDashboardPostInfo)
                .toList();
    }

    @Transactional // 사용자 이름으로 필터링된 목록 조회
    public List<PostResponse.DashboardPostInfo> getPostsByUsername(String username) {
        List<Post> posts = postRepository.findByUserUsernameOrderByDateCreatedDesc(username);
        return posts.stream()
                .map(this::convertToDashboardPostInfo)
                .toList();
    }

    @Transactional // 부분 업데이트 적용 및 파일 교체 처리
    public void updatePost(Long postId, PostRequest.UpdatePost postUpdate, MultipartFile file) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        post.updatePost(postUpdate);

        if (file != null && !file.isEmpty()) {
            // 기존 파일 삭제
            if (post.getFileUploadUrl() != null) {
                deleteFile(post.getFileUploadUrl());
            }
            String newFileUrl = saveFileAndGetUrl(file);
            post.setFileUploadUrl(newFileUrl);
        }

        postRepository.save(post);
    }

    @Transactional // 게시글과 연결된 저장된 파일 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // 기존 파일 삭제
        if (post.getFileUploadUrl() != null) {
            deleteFile(post.getFileUploadUrl());
        }

        postRepository.deleteById(postId);
    }

    // 엔티티 -> 상세 뷰 DTO
    private PostResponse.ViewPost convertToViewPost(Post post) {
        return PostResponse.ViewPost.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .userProfilePicture(post.getUser().getProfilePicture())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .fileUploadUrl(post.getFileUploadUrl())
                .dateCreated(post.getDateCreated())
                .dateModified(post.getDateModified())
                .likesCount(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .viewsCount(post.getViewsCount())
                .build();
    }

    // 엔티티 -> 대시보드 목록 아이템 DTO
    private PostResponse.DashboardPostInfo convertToDashboardPostInfo(Post post) {
        return PostResponse.DashboardPostInfo.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .likesCount(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .viewsCount(post.getViewsCount())
                .build();
    }

    // 업로드된 파일 저장 및 공개 URL 반환; null/empty 인 경우 무시
    private String saveFileAndGetUrl(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + uniqueFilename; // URL은 리소스 핸들러 매핑과 일치해야 함

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }

    // 파일 삭제; 잘못된 URL 무시
    private void deleteFile(String fileUrl) {
        if (fileUrl == null || !fileUrl.startsWith("/uploads/")) {
            return;
        }

        try {
            String filename = fileUrl.substring("/uploads/".length());
            Path filePath = Paths.get(uploadDir).resolve(filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Failed to delete file: " + e.getMessage());
        }
    }
}