package com.pard.server.hw5_sungkukjung.comment;

import com.pard.server.hw5_sungkukjung.post.Post;
import com.pard.server.hw5_sungkukjung.post.PostRepository;
import com.pard.server.hw5_sungkukjung.user.User;
import com.pard.server.hw5_sungkukjung.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service 
@RequiredArgsConstructor 
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional // 생성 작업의 원자성 보장
    public void createComment(CommentRequest.CreateComment commentRequest) {
        User user = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + commentRequest.getUserId()));

        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + commentRequest.getPostId()));

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .user(user)
                .post(post)
                .build();
        commentRepository.save(comment);
    }

    @Transactional // 트랜잭션 내에서 일관성 있는 목록 조회
    public List<CommentResponse.ViewComment> getCommentsByPostId(Long postId) {
        // 게시글 존재 여부 검증
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }

        List<Comment> comments = commentRepository.findByPostIdOrderByDateCommentedAsc(postId);
        return comments.stream()
                .map(this::convertToViewComment)
                .collect(Collectors.toList());
    }

    @Transactional // 업데이트 및 저장을 하나의 트랜잭션 내에서 수행
    public void updateComment(Long commentId, CommentRequest.UpdateComment commentUpdate) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        comment.updateComment(commentUpdate);
        commentRepository.save(comment);
    }

    @Transactional // 존재하지 않으면 실패
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    // Map entity -> response DTO for API
    private CommentResponse.ViewComment convertToViewComment(Comment comment) {
        return CommentResponse.ViewComment.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .dateCommented(comment.getDateCommented())
                .dateModified(comment.getDateModified())
                .username(comment.getUser().getUsername())
                .userProfilePicture(comment.getUser().getProfilePicture())
                .build();
    }
}