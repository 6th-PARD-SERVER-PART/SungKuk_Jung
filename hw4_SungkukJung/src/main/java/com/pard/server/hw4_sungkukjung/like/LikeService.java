package com.pard.server.hw4_sungkukjung.like;

import com.pard.server.hw4_sungkukjung.post.Post;
import com.pard.server.hw4_sungkukjung.post.PostRepository;
import com.pard.server.hw4_sungkukjung.user.User;
import com.pard.server.hw4_sungkukjung.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor 
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional // 좋아요 상태 원자성 전환
    public String toggleLike(LikeRequest.LikeAction likeAction) {
        Long userId = likeAction.getUserId();
        Long postId = likeAction.getPostId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        return likeRepository.findByUserIdAndPostId(userId, postId)
                .map(existingLike -> {
                    likeRepository.delete(existingLike);
                    return "Post unliked successfully.";
                })
                .orElseGet(() -> {
                    Like newLike = Like.builder()
                            .user(user)
                            .post(post)
                            .build();
                    likeRepository.save(newLike);
                    return "Post liked successfully.";
                });
    }

    @Transactional // 트랜잭션 내에서 일관성 있는 목록 조회
    public List<LikeResponse.ViewLiker> getLikersByPostId(Long postId) {
        // 1. 게시글 존재 여부 검증
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }
        // 2. 게시글의 모든 좋아요 조회
        List<Like> likes = likeRepository.findByPostIdOrderByDateLikedAsc(postId);
        // 3. DTO로 매핑
        return likes.stream()
                .map(this::convertToViewLiker)
                .collect(Collectors.toList());
    }

    // Map entity -> response DTO
    private LikeResponse.ViewLiker convertToViewLiker(Like like) {
        User liker = like.getUser();
        return LikeResponse.ViewLiker.builder()
                .userId(liker.getId())
                .username(liker.getUsername())
                .userProfilePicture(liker.getProfilePicture())
                .dateLiked(like.getDateLiked())
                .build();
    }
}